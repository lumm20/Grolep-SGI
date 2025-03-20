package com.mycompany.sginegocio.excepciones;

public class UserException extends Exception {
    public static final int USER_NOT_FOUND = 0;
    public static final int WRONG_PASS = 1;
    public static final int EMPTY_FIELD = 2;
    public static final int UNSUCCESFUL_SIGNIN = 3;
    public static final int USERNAME_CONFLICT = 4;
    public static final int EMAIL_CONFLICT = 5;
    private int type;

    public UserException(String message, int type) {
        super(message);
        this.type = type;
    }

    public int getType() {return type;}
}
