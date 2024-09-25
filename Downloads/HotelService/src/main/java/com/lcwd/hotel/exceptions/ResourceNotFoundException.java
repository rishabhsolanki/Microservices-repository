package com.lcwd.hotel.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s) {
        super();
    }
    public ResourceNotFoundException(){
        super("Resource not found");
    }
}
