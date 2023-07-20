package com.example.nodess.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;


@Data
@Entity
public class ChatNode{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 1000)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date();

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    @OneToOne(mappedBy = "node",cascade = CascadeType.REMOVE)
    private FileAttachment fileAttachment;




}
