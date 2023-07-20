package com.example.nodess.controllers;

import com.example.nodess.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping(path = "/api/1.0")
public class UploadController {
    FileUploadService fileUploadService;

    @Autowired
    public UploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
    //@ProfileImage(types = "application/png")
    @PostMapping(path = "/node-attachments")
    public Map<String,String> uploadFile(@RequestParam(name = "file") MultipartFile multipartFile) {
        fileUploadService.uploadFile(multipartFile);
        return Collections.singletonMap("name", String.valueOf(fileUploadService));
    }
}
