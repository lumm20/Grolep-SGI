package com.mycompany.sginegocio.excepciones;

public class PaymentException extends Exception {
    public static final int STUDENT_NOT_FOUND = 0;
    public static final int CASHIER_NOT_FOUND = 1;
    public static final int EMPTY_FIELD = 2;
    public static final int UNSUCCESFUL_REGISTER = 3;
    private int type;

    public PaymentException(String message, int type) {
        super(message);
        this.type = type;
    }

    public int getType() {return type;}}
