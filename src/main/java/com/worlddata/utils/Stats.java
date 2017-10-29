package com.worlddata.utils;

import java.util.List;

public class Stats
{
    public static double mean(List<Double> values)
    {
        return values.stream()
                .mapToDouble(d -> d)
                .average()
                .getAsDouble();
    }

    public static double standardDeviation(List<Double> values)
    {
        double mean = mean(values);
        int numberOfValues = values.size();
        double deviation = 0.0;
        for (Double value : values)
        {
            double deviationFromMean = value - mean;
            deviation += deviationFromMean * deviationFromMean;
        }
        return Math.sqrt(deviation / numberOfValues);
    }
}
