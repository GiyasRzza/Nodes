package com.example.nodess.controllers;
import com.example.nodess.credentials.Credentials;
import com.example.nodess.exceptionAndResponse.AuthResponse;
import com.example.nodess.services.AuthService;
import com.example.nodess.shared.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path ="/api/1.0")
public class AuthController {
    private final AuthService service;
    @PostMapping(path = "/auth")
    public AuthResponse handleAuthentication(@RequestBody Credentials credentials) {
        log.info(service.authenticate(credentials).getToken());
        return service.authenticate(credentials);
    }
        @PostMapping(path = "/logout")
        GenericResponse handleLogout(@RequestHeader(name = "Authorization") String authorization){
            String token= authorization.substring(7);
            service.clearToken(token);
            return new GenericResponse("Logout Success");
        }
}