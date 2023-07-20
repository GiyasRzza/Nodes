package com.example.nodess.repostories;

import com.example.nodess.entities.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment,Long> {


    List<FileAttachment> findByDateBeforeAndNodeIsNull(Date date);
}
