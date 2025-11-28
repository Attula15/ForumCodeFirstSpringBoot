package com.bherincs.forumpractice.controllers.helper;

import com.bherincs.forumpractice.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;

public class ControllerHelper {
    public static String fetchUserNameFromToken(HttpServletRequest request, JwtService jwtService){
        String token = request.getHeader("Authorization").substring(7);
        return jwtService.extractUsername(token);
    }
}
