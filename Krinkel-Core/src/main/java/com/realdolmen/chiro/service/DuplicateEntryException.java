package com.realdolmen.chiro.service;

/**
 * Should be thrown when an attempt is made to save a duplicate entry.
 *
 */
public class DuplicateEntryException extends Exception{

    public DuplicateEntryException() {
    }

    public DuplicateEntryException(String message) {
        super(message);
    }
}
