package com.example.nodess.LocalAnatation;

import com.example.nodess.entities.User;
import com.example.nodess.repostories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

  public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername,String>{

    @Autowired
    UserRepository userRepository;
    @Override
    public boolean isValid(String userName, ConstraintValidatorContext context) {
        User user= userRepository.findByUserName(userName);
        return user == null;
    }
}
