package com.example.nodess.services;

import com.example.nodess.SwingUI.Checker;
import com.example.nodess.entities.vm.UpdateUserVM;
import com.example.nodess.entities.User;
import com.example.nodess.exceptionAndResponse.NotFoundExeption;
import com.example.nodess.repostories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    FileUploadService fileUploadService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void save(User user) {
        Checker.setUserName(user.getUsername());
        Checker.setPassword(user.getPassword());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Checker.setEncrptPassword(user.getPassword());
        userRepository.save(user);
    }

    public Page<User> getUsers(User user,Pageable page) {
        if (user != null) {
                return userRepository.findByUserNameNot(user.getUsername(),page);
        }
        else {
            return userRepository.findAll(page);
        }

    }

    public User getByUserName(String username) {
        User inDB  =userRepository.findByUserName(username);
        if (inDB == null) {
            throw  new NotFoundExeption();
        }
        else {
            return inDB;
        }
    }

    public User updateUser(String userName, UpdateUserVM updatedUser) {
        User inDB  = userRepository.findByUserName(userName);
        inDB.setDisplayName(updatedUser.getDisplayName());
        userRepository.save(inDB);
        return inDB;
    }

    public void delete(String userName) {
        User inDB = userRepository.findByUserName(userName);
        fileUploadService.deleteAllStoredFilesForUser(inDB.getImage());
        userRepository.deleteByUserName(userName);

    }

}
