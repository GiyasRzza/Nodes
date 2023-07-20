package com.example.nodess.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Token {
    @Id
    private String token;
    @ManyToOne
    private User user;

}
