package com.priboy.volunteer.util;

import java.time.LocalDate;

public class CheckDate {
    static public LocalDate checkLocalDate(String birth){
        // проверка на наличие даты
        LocalDate localDate = null;
        if(!birth.equals("")){
            localDate = LocalDate.parse(birth);
        }
        return localDate;
    }
}
