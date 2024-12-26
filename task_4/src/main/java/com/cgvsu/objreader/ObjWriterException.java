package com.cgvsu.objreader;

public class ObjWriterException extends RuntimeException {
    public ObjWriterException(String errorMessage) {
        super("Error in ObjWriter: " + errorMessage);
    }
}