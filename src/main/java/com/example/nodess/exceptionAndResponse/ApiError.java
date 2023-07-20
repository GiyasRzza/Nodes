package com.example.nodess.exceptionAndResponse;

import com.example.nodess.shared.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.Date;
import java.util.Map;
@Data
@JsonView(Views.Base.class)
public class ApiError {
    @JsonView(Views.Base.class)
    private int status;
    @JsonView(Views.Base.class)
    private String message;

    private String path;
    private long timestamp = new Date().getTime();
    private Map<String,String> validationErrors;

    public ApiError(int status, String message, String path, Map<String, String> validationErrors) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.validationErrors=validationErrors;

    }
}
