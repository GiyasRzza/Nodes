package com.example.nodess.controllers;
import com.example.nodess.LocalAnatation.CurrentUser;
import com.example.nodess.entities.vm.UpdateUserVM;
import com.example.nodess.entities.User;
import com.example.nodess.entities.vm.UserVM;
import com.example.nodess.services.UserService;
import jakarta.validation.Valid;
import com.example.nodess.shared.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "/api/1.0")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping(path = "/users")
    public GenericResponse createUser(@Valid @RequestBody User user){
                userService.save(user);
          return new GenericResponse("User Created");
      }

    @GetMapping(path = "/users")
    Page<UserVM> getUsers(Pageable page, @CurrentUser User user){
        return userService.getUsers(user, page).map(UserVM::new);
    }
    @GetMapping(path = "/users/{username}")
    UserVM getUser(@PathVariable("username") String username){
        User user =userService.getByUserName(username);
        return new UserVM(user);
    }

    @PutMapping(path = "/users/{userName}")
    public UserVM updateUsers(@RequestBody UpdateUserVM updatedUser, @PathVariable String userName){
       User user =userService.updateUser(userName,updatedUser);
       return new UserVM(user);
    }

    @DeleteMapping(path = "/users/{userName}")
    @PreAuthorize("#userName == principal.userName")
    GenericResponse deleteUser(@PathVariable String userName){
        userService.delete(userName);
        return  new GenericResponse("User Removed");
    }



}
