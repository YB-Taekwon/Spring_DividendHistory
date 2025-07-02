package com.ian.finance.scrap.domain;

public enum Month {

    JAN("Jan", 1),
    FEB("Feb", 2),
    MAR("Mar", 3),
    APR("Apr", 4),
    MAY("May", 5),
    JUN("Jun", 6),
    JUL("Jul", 7),
    AUG("Aug", 8),
    SEP("Sep", 9),
    OCT("Oct", 10),
    NOV("Nov", 11),
    DEC("Dec", 12);

    private String str;
    private int num;

    Month(String str, int num) {
        this.str = str;
        this.num = num;
    }

    public static int strToNum(String str) {
        for (Month m : Month.values()) {
            if (m.str.equals(str)) return m.num;
        }
        return -1;
    }
}
