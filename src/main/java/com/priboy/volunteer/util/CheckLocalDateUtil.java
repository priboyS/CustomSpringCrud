package com.priboy.volunteer.util;

import java.time.LocalDate;

public class CheckLocalDateUtil {

    // converter for birth, if null
    static public LocalDate checkLocalDate(String birth){
        LocalDate localDate = null;
        if(!birth.equals("")){
            localDate = LocalDate.parse(birth);
        }
        return localDate;
    }
}
