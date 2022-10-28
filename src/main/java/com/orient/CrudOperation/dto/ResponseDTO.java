package com.orient.CrudOperation.dto;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ResponseDTO {
    private Integer errodCode;
    private String errorMessage;
    private String successMessage;
    private Object object;

    private ResponseDTO(){
    }
    public static ResponseDTO of(Object obj){
        ResponseDTO resp=new ResponseDTO();
        resp.setObject(obj);

        return resp;
    }

    public static ResponseDTO of(Object obj,String successMessage){
        ResponseDTO resp=new ResponseDTO();
        resp.setObject(obj);
        resp.setSuccessMessage(successMessage);

        return resp;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Integer getErrodCode() {
        return errodCode;
    }

    public void setErrodCode(Integer errodCode) {
        this.errodCode = errodCode;
    }
}
