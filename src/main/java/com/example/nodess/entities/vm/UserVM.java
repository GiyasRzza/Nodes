package com.example.nodess.entities.vm;

import com.example.nodess.entities.User;
import lombok.Data;

@Data
public class UserVM  {
    private String userName;
    private String displayName;
    private String image;

    public UserVM(User user) {
        this.setUserName(user.getUsername());
        this.setDisplayName(user.getDisplayName());
        this.setImage(user.getImage());
    }
}
