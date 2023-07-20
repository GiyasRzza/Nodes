package com.example.nodess.exceptionAndResponse;

import com.example.nodess.entities.vm.UserVM;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserVM userVM;

}
