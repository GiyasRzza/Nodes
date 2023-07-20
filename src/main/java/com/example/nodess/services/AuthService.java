package com.example.nodess.services;

import com.example.nodess.credentials.Credentials;
import com.example.nodess.entities.Token;
import com.example.nodess.entities.User;
import com.example.nodess.entities.vm.UserVM;
import com.example.nodess.exceptionAndResponse.AuthResponse;
import com.example.nodess.repostories.TokenRepository;
import com.example.nodess.repostories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    UserService service;
    TokenRepository tokenRepository;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    PasswordEncoder passwordEncoder;
     UserRepository userRepository;

    public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder= passwordEncoder;
        this.tokenRepository=tokenRepository;
    }

    public AuthResponse authenticate(Credentials credentials) {
       User inDB= service.getByUserName(credentials.getUserName());
        if (inDB == null) {
            throw new RuntimeException(String.valueOf(HttpStatus.UNAUTHORIZED));
        }
        if (!passwordEncoder.matches(credentials.getPassword(),inDB.getPassword())){
            throw new RuntimeException(String.valueOf(HttpStatus.UNAUTHORIZED));
        }
        else {
            UserVM  userVM  = new UserVM(inDB);
            String token =  generateRandomToken();
            Token tokenEntity = new Token();
            tokenEntity.setUser(inDB);
            tokenEntity.setToken(token);
            tokenRepository.save(tokenEntity);
            AuthResponse response = new AuthResponse();
            response.setUserVM(userVM);
            response.setToken(token);
            return response;
        }
    }
    @Transactional
    public UserDetails getUserDetails(String token) {
        Optional<Token> tokenOptional= tokenRepository.findById(token);
        return tokenOptional.<UserDetails>map(Token::getUser).orElse(null);
    }


    public String generateRandomToken(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


    public void clearToken(String token) {
        tokenRepository.deleteById(token);
    }
}
