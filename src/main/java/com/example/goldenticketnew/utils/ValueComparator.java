package com.example.goldenticketnew.utils;

import com.example.goldenticketnew.dtos.UserReportDto;

import java.util.Comparator;

public class ValueComparator implements Comparator<UserReportDto> {
    @Override
    public int compare(UserReportDto a, UserReportDto b) {
        if(a.getIncomeAmount() < b.getIncomeAmount())
            return 1;
        else return -1;

    }
}
