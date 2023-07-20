package com.example.nodess.exceptionAndResponse;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ErrorHandler implements ErrorController {

    private final ErrorAttributes errorAttributes;
    @Autowired
    public ErrorHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    @ExceptionHandler(ValidationException.class)
    ApiError handleError(WebRequest webRequest){
        Map<String,Object> stringObjectMap= this.errorAttributes.getErrorAttributes(webRequest,
                ErrorAttributeOptions.of(Include.BINDING_ERRORS, Include.EXCEPTION,Include.MESSAGE,Include.STACK_TRACE));
        String message= (String) stringObjectMap.get("message");
      int status= (int) stringObjectMap.get("status");
      String path= (String) stringObjectMap.get("path");
        List<FieldError> fieldErrors = (List<FieldError>) stringObjectMap.get("errors");
        Map<String,String> validation = new HashMap<>();
        if (stringObjectMap.containsKey("errors")) {
            for (FieldError fieldError:fieldErrors
            ) {
                validation.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
        }
//        List<FieldError> fieldErrors = (List<FieldError>) stringObjectMap.get("errors");
//        Map<String,String> validation = new HashMap<>();
//        if (fieldErrors!=null) {
//            for (FieldError fieldError:fieldErrors
//            ) {
//                validation.put(fieldError.getField(),fieldError.getDefaultMessage());
//            }


        return new ApiError(status,message,path,validation);
    }
    @Bean
    public String getErrorPath(){
        return "/error";
    }


}
