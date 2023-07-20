package com.example.nodess.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class FileAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @OneToOne
    private ChatNode node;

    public FileAttachment(String fileName, Date date) {
        this.name =fileName;
        this.date = date;
    }


}
