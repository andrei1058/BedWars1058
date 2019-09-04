package com.andrei1058.bedwars.api.exceptions;

import com.andrei1058.bedwars.api.server.VersionSupport;

public class InvalidMaterialException extends Exception {

    public InvalidMaterialException(String s) {
        super(s + " is not a valid " + VersionSupport.getName() + " material! Using defaults..");
    }
}
