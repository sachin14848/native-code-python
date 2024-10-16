package com.cricketService.utils;

import org.springframework.stereotype.Component;

@Component
public class DateConverted {


    public Long stringDateToLongMilliseconds(String date) {
        // Implement conversion logic here
        return Long.parseLong(date);
    }

}
