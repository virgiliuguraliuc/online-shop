package org.fasttrackit.onlineshop.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
    //made constructor with message string
}
