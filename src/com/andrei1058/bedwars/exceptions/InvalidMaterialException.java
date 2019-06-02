package com.andrei1058.bedwars.exceptions;

import com.andrei1058.bedwars.Main;

public class InvalidMaterialException extends Exception {

    public InvalidMaterialException(String s){
        super(s + " is not a valid " + Main.getServerVersion()+" material!");
    }
}
