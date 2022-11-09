package com.company.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

// from Dan's class demo
public class ErrorMessage {
    private String errorMsg;
//    private int status;
//    private String errorCode;
    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    LocalDateTime timestamp;

    public ErrorMessage(String errorMsg, HttpStatus httpStatus) {
        this.errorMsg = errorMsg;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }

    public int getHttpStatusCode() {
        return httpStatus.value();
    }

    public String getHttpStatusString() {
        return httpStatus.toString();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
