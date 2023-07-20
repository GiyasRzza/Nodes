package com.example.nodess.entities.vm;

import com.example.nodess.entities.ChatNode;
import lombok.Data;

import java.util.Date;
@Data
public class NodeVM {

    private Long id;

    private  String content;

    private Date timestamp;

    private UserVM user;
    private FileAttachmentVM   fileAttachmentVM;

    public NodeVM(ChatNode node) {
        this.setId(node.getId());
        this.setContent(node.getContent());
        this.setTimestamp(node.getTimestamp());
        this.setUser(new UserVM(node.getUser()));
        if (node.getFileAttachment() != null) {
            this.fileAttachmentVM = new FileAttachmentVM(node.getFileAttachment());
        }
    }

}
