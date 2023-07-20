package com.example.nodess.services;
import com.example.nodess.configuration.ApplicationConfiguration;
import com.example.nodess.entities.FileAttachment;
import com.example.nodess.repostories.FileAttachmentRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
@EnableScheduling
public class FileUploadService {
    FileAttachmentRepository fileAttachment;
    ApplicationConfiguration app;
    @Getter
    @Setter
    String fileName;


    @Autowired
    public FileUploadService(ApplicationConfiguration app,FileAttachmentRepository fileAttachment) {
        this.app = app;
        this.fileAttachment=fileAttachment;
    }

    public FileAttachment uploadFile(MultipartFile multipartFile) {
     String fileName = (detectType(multipartFile.getContentType()));
        Date date = new Date();
        try {
            multipartFile.transferTo(new File(app.getUploadPath()+multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileAttachment.save(new FileAttachment(fileName,date));
    }

    public void deleteFile(String fileName){
        if (fileName == null) {
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(app.getUploadPath(),fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedRate = 24*3600*1000)
    public void cleanupFile() {
        Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (24*3600 * 1000));
        List<FileAttachment> filesToDeleted = fileAttachment.findByDateBeforeAndNodeIsNull(twentyFourHoursAgo);
        for (FileAttachment file : filesToDeleted) {
            log.info("removing file" + file.getName());
            deleteFile(file.getName());
            fileAttachment.deleteById(file.getId());
        }
    }


    public String detectType(String value) {
        return value;
    }


    public void deleteAllStoredFilesForUser(String image) {
        deleteFile(image);
    }
}
