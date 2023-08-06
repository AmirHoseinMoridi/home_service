package com.example.home_service.util;

public class Regex {
    public static final String POSITIVE_INTEGERS = "^[1-9]\\d*$";
    public static final String STRING_WITHOUT_NUMBER = "^[a-zA-Z\\s-]+$";
    public static final String DOUBLE = "^[-+]?\\d*\\.?\\d+$";
    public static final String DATE = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    public static final String TIME = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
    public static final String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String IMAGE_JPG = "^.+\\.(?i)(jpg|jpeg)$";
    public static final String EIGHT_CHARACTERS_WITH_LETTERS_AND_NUMBERS = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8}$";
}
