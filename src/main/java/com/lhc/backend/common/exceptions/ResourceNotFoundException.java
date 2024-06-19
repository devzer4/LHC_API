package com.lhc.backend.common.exceptions;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String statement) {
        super(statement);
    }
}
