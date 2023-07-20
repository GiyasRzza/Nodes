package com.example.nodess.repostories;


import com.example.nodess.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
            User findByUserName(String userName);

            Page<User> findByUserNameNot(String username,Pageable pageable);

            @Transactional
             void deleteByUserName(String userName);
}
