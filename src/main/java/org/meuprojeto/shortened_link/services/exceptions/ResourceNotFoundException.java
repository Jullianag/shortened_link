package org.meuprojeto.shortened_link.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
