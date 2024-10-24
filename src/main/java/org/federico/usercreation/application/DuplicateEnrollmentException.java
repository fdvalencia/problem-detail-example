package org.federico.usercreation.application;

public class DuplicateEnrollmentException extends RuntimeException {
    public DuplicateEnrollmentException(String userAlreadyExists) {
        super(userAlreadyExists);
    }
}
