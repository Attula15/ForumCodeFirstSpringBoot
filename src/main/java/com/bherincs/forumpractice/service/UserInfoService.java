package com.bherincs.forumpractice.service;

import com.bherincs.forumpractice.controllers.dto.UserLoginDTO;
import com.bherincs.forumpractice.database.ForumUser;
import com.bherincs.forumpractice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserInfoService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public String addUser(UserLoginDTO registerData) {
        registerData.setPassword(encoder.encode(registerData.getPassword()));

        ForumUser newForumUser = new ForumUser(registerData.getPassword(), registerData.getUsername());

        newForumUser.setRoles("ROLE_USER");

        repository.save(newForumUser);
        return "User registered";
    }

    @Override
    public UserInfoDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ForumUser> nullableUser = repository.findByUsername(username);

        if(nullableUser.isEmpty()){
            throw new UsernameNotFoundException("User not find with username: " + username);
        }

        ForumUser forumUser = nullableUser.get();

        return new UserInfoDetails(forumUser);
    }
}
