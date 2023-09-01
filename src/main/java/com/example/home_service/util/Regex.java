package com.example.home_service.util;

public class Regex {
    public static final String STRING_WITHOUT_NUMBER = "^[a-zA-Z\\s-]+$";
    public static final String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String IMAGE_JPG = "^.+\\.(?i)(jpg|jpeg)$";
    public static final String EIGHT_CHARACTERS_WITH_LETTERS_AND_NUMBERS = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8}$";

    public static final String DIGIT_STRING = "^[0-9]+$";
}
