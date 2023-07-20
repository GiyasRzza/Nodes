package com.example.nodess.LocalAnatation;

import com.example.nodess.services.FileUploadService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class ProfileImageValidator implements ConstraintValidator<ProfileImage,String> {

        FileUploadService fileUploadService;
        String[] types;
        @Autowired
    public ProfileImageValidator(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Override
    public void initialize(ProfileImage constraintAnnotation) {
        types= constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String type = fileUploadService.getFileName();
            log.info(type);
        for (String supported:types
             ) {
            if (type.equalsIgnoreCase(supported)) {
                    return true;
            }
        }
        return false;
    }
}
