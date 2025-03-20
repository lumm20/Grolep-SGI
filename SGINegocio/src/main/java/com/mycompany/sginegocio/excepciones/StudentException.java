package com.mycompany.sginegocio.excepciones;

public class StudentException extends Exception {
    public static final int INVALID_NAME = 1; 
    private int type;

    public StudentException(String message, int type) {
        super(message);
        this.type = type;
    }

    public int getType() {return type;}
}