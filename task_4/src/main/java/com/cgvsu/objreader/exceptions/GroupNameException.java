package com.cgvsu.objreader.exceptions;

public class GroupNameException extends ObjReaderException {
    public GroupNameException(int lineIndex) {
        super("Group must have a name.", lineIndex);
    }
}
