package com.example.nodess.entities;

import com.example.nodess.LocalAnatation.UniqueUsername;
import com.example.nodess.shared.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "{message.userName.Null}")
    @Size(min = 4,max = 255)
    @UniqueUsername
    @JsonView(Views.Base.class)
    private String userName;
    @NotNull(message = "{message.displayName.Null}")
    @Size(min = 4,max = 255)
    @JsonView(Views.Base.class)
    private String displayName;
    @NotNull(message = "{message.password.Null}")
    @Size(min = 8,max = 255)
    //@JsonIgnore json datada bu field i gormezden gelmek ucundur
    private String password;
    @JsonView(Views.Base.class)
    private String image;
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<ChatNode> nodes;
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Token> tokens;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("My Admin");
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
    //@Pattern(regexp = "^[a-zA-Z0-9]{6,12}$",
    // message = "username must be of 6 to 12 length with no special characters")

