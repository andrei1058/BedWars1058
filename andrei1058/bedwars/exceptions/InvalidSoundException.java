package com.andrei1058.bedwars.exceptions;

import com.andrei1058.bedwars.Main;

public class InvalidSoundException extends Throwable {


    public InvalidSoundException(String s) {
        super(s + "is not a valid " + Main.getServerVersion()+" sound!");
    }
}
