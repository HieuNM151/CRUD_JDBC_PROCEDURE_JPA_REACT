package com.example.demo.exceptions;

public class NotFondException extends RuntimeException{

    private String message;

    public NotFondException(String message){
        super(message);
    }

}
