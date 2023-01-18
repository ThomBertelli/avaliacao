package com.attornatus.avaliacao.exception.handlers;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException (String exceptionMessage){
        super(exceptionMessage);
    }
}
