package org.federico.usercreation.application;

import jakarta.ws.rs.BadRequestException;


public class CountryNotAllowedException extends BadRequestException {
    public CountryNotAllowedException() {
        super("The country is not allowed");
    }
}
