package com.example.nodess.repostories;

import com.example.nodess.entities.ChatNode;
import com.example.nodess.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NodeRepository extends JpaRepository<ChatNode,Long>, JpaSpecificationExecutor<ChatNode> {
      Page<ChatNode> findByUser(User user, Pageable pageable);
        //Page<ChatNode> findByIdLessThan(long id,Pageable pageable);
       // Page<ChatNode> findByIdLessThanAndUser(long id,User user,Pageable pageable);
//        long countByIdGreaterThan(long id);
//        long countByIdGreaterThanAndUser(long id,User user);
//        List<ChatNode> findByIdGreaterThan(long id , Sort sort);
//        List<ChatNode> findByIdGreaterThanAndUser(long id, Sort sort, User user);
}
