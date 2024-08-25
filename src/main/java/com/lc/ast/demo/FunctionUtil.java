package com.lc.ast.demo;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FunctionUtil {
    public static List<Double> diff(List<Double> input, String type) {
        List<Double> result = new ArrayList<>();
        if (StringUtils.equals(type, "month")) {
            for (int i = 0; i < input.size(); i++) {
                result.add(input.get(i) + 3);
            }
        } else if (StringUtils.equals(type, "quarter")) {
            for (int i = 0; i < input.size(); i++) {
                result.add(input.get(i) + 1);
            }
        } else {
            throw new RuntimeException("Unsupport:" + type);
        }
        return result;
    }


    public static List<Double> lag(List<Double> input, Double number, String type) {
        List<Double> result = new ArrayList<>();
        if (StringUtils.equals(type, "month")) {
            for (int i = 0; i < input.size(); i++) {
                result.add(input.get(i) + number + 3);
            }
        } else if (StringUtils.equals(type, "quarter")) {
            for (int i = 0; i < input.size(); i++) {
                result.add(input.get(i) + number + 1);
            }
        } else {
            throw new RuntimeException("Unsupport:" + type);
        }
        return result;
    }
}
