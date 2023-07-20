package com.example.nodess.entities.vm;

import com.example.nodess.entities.FileAttachment;
import lombok.Data;

@Data
public class FileAttachmentVM {
        private String name;

    public FileAttachmentVM(FileAttachment fileAttachment) {
        this.setName(fileAttachment.getName());
    }
}
