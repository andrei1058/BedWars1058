package com.andrei1058.bedwars.exceptions;

import com.andrei1058.bedwars.Main;

public class InvalidEffectException extends Throwable {

    public InvalidEffectException(String message) {
        super(message + " is not a valid " + Main.getServerVersion() + " effect! Using defaults..");
    }
}
