package com.konggit.appnot;

import java.util.Date;
import java.util.Map;

public class Results {

    private int dayDefaultNumber;
    private int numbers;
    private Map<Date, Integer> path;

    public Results(int dayDefaultNumber, int numbers, Map<Date, Integer> path) {
        this.dayDefaultNumber = dayDefaultNumber;
        this.numbers = numbers;
        this.path = path;
    }

    public int getDayDefaultNumber() {
        return dayDefaultNumber;
    }

    public void setDayDefaultNumber(int dayDefaultNumber) {
        this.dayDefaultNumber = dayDefaultNumber;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public Map<Date, Integer> getPath() {
        return path;
    }

    public void setPath(Map<Date, Integer> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Results{" +
                "dayDefaultNumber=" + dayDefaultNumber +
                ", numbers=" + numbers +
                ", path=" + path +
                '}';
    }
}
