package com.example.nodess.services;

import com.example.nodess.entities.ChatNode;
import com.example.nodess.entities.FileAttachment;
import com.example.nodess.entities.User;
import com.example.nodess.entities.vm.NodeSubmitVm;
import com.example.nodess.repostories.FileAttachmentRepository;
import com.example.nodess.repostories.NodeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NodeService {

    Specification<ChatNode> specification;
    NodeRepository nodeRepository;
    UserService service;
    FileUploadService fileUploadService;
    FileAttachmentRepository fileAttachmentRepository;


    public NodeService(NodeRepository nodeRepository,
    FileAttachmentRepository fileAttachmentRepository,
    FileUploadService fileUploadService,UserService userService) {
        this.nodeRepository = nodeRepository;
        this.fileUploadService = fileUploadService;
        this.service=userService;
        this.fileAttachmentRepository =fileAttachmentRepository;
    }

    public Page<ChatNode> getNodes(Pageable page) {
        return nodeRepository.findAll(page);
    }

    public void save(NodeSubmitVm node, User user) {
        ChatNode chatNode = new ChatNode();
        chatNode.setId(node.getAttachmentId());
        chatNode.setUser(user);
        nodeRepository.save(chatNode);
        Optional<FileAttachment> optionalAttachment = fileAttachmentRepository.findById(node.getAttachmentId());
        if (optionalAttachment.isPresent()) {
            FileAttachment fileAttachment = optionalAttachment.get();
            fileAttachment.setNode(chatNode);
        }

    }

//

    public Page<ChatNode> getOldNodes(Long id, String username, Pageable pageable){
        specification = idLessThan(id);
        if (username != null) {
            User inDB = service.getByUserName(username);
                specification =specification.and(userIs(inDB));
            //return nodeRepository.findByIdLessThanAndUser(id,inDB,pageable);
        }
             return nodeRepository.findAll(specification,pageable);
            //return nodeRepository.findByIdLessThan(id,pageable);
    }

    public long getNewNodesCount(long id, String username) {
        specification = idGreaterThan(id);
        if (username != null){
            User inDB = service.getByUserName(username);
            //return nodeRepository.countByIdGreaterThanAndUser(id,inDB);
            specification =specification.and(userIs(inDB));
        }
                return nodeRepository.count(specification);
            //return nodeRepository.countByIdGreaterThan(id);
    }



    public List<ChatNode> getNewNodes(long id, String username, Sort sort) {
        specification = idGreaterThan(id);
        if (username != null) {
            User inDB = service.getByUserName(username);
            specification = specification.and(userIs(inDB));
           // return nodeRepository.findByIdGreaterThanAndUser(id,sort,inDB);
        }
        return nodeRepository.findAll(specification,sort);
       // return nodeRepository.findByIdGreaterThan(id,sort);
    }
    public Page<ChatNode> getNodeOfUser(String username, Pageable pageable) {
        User inDB = service.getByUserName(username);
        return nodeRepository.findByUser(inDB,pageable);
    }
    public void delete(long id){
        ChatNode node  = nodeRepository.getOne(id);
        if (node.getFileAttachment() != null) {
            String fileName = node.getFileAttachment().getName();
            fileUploadService.deleteFile(fileName);
        }
        nodeRepository.deleteById(id);
    }
            //Iki terefli relation elediyimiz ucun bu artiq gecersiz bir methoddur

//    public void deletedNodesOfUser(String userName){
//        User inDB = service.getByUserName(userName);
//        Specification<ChatNode> userOwned= userIs(inDB);
//        List<ChatNode> nodesToBeRemoved =  nodeRepository.findAll(userOwned);
//        nodeRepository.deleteAll(nodesToBeRemoved);
//    }

                //************************* Other Method*******************

//    public List<ChatNode> getNewNodesOfUser(long id, Sort sort, String username) {
//        User inDB = service.getByUserName(username);
//        return nodeRepository.findByIdGreaterThanAndUser(id,sort,inDB);
//
//    }
//public  Page<ChatNode> getOldNodeOfUser(long id, String username, Pageable pageable){
////        User inDB = service.getByUserName(username);
////        return nodeRepository.findByIdLessThanAndUser(id,inDB,pageable);
////    }

    //    public long getNewNodesCountOfUser(long id , String username) {
//        User inDB = service.getByUserName(username);
//        return nodeRepository.countByIdGreaterThanAndUser(id,inDB);
//    }


    Specification<ChatNode> idLessThan(long id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("id"),id);
    }
    Specification<ChatNode> userIs(User user){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"),user);

    }
    Specification<ChatNode> idGreaterThan(long id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"),id);
    }

}
