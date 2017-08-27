package com.konggit.appnot;

import java.util.Date;
import java.util.Map;

/**
 * Created by erik on 23.08.2017.
 */

public class Results {

    private int numbers;
    private Map<Date, Integer> path;

    public Results(int numbers, Map<Date, Integer> path) {
        this.numbers = numbers;
        this.path = path;
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
                "numbers=" + numbers +
                ", path=" + path +
                '}';
    }
}
