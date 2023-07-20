package com.example.nodess.services;

import com.example.nodess.entities.ChatNode;
import com.example.nodess.entities.User;
import com.example.nodess.repostories.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NodeSecurityService {

    NodeRepository nodeRepository;

    public NodeSecurityService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public boolean isAllowedToDelete(long id, User loggedUser){
        Optional<ChatNode> optionalChatNode = nodeRepository.findById(id);
        if (optionalChatNode.isPresent()) {
            return false;
        }
        ChatNode chatNode = optionalChatNode.get();
        return chatNode.getUser().getId() == loggedUser.getId();

    }
}
