package com.worlddata.utils;

import com.worlddata.model.Country;

import java.text.DecimalFormat;
import java.util.Collections;

public class Formatter
{
    public static final String CODE_COLUMN_HEADER = "Code";
    public static final String NAME_COLUMN_HEADER = "Country Name";
    public static final String INTERNETUSERS_COLUMN_HEADER = "Internet Users (%)";
    public static final String LITERACY_COLUMN_HEADER = "Literacy Rate (%)";

    private static final String NULL_TO_PRINT = "--";
    private static final int CODE_COLUMN_WIDTH = 10;
    private static final int NAME_COLUMN_WIDTH = 40;
    private static final int INTERNETUSERS_COLUMN_WIDTH = 20;
    private static final int LITERACY_COLUMN_WIDTH = 20;
    private static final int STATS_NAME_COLUMN_WIDTH = 30;
    private static final int STATS_VALUE_COLUMN_WIDTH = 10;
    private static final int MENU_WIDTH = 40;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");


    public static String statsTableRow(String statName, double statValue)
    {
        return statName +
                emptySpaces(STATS_NAME_COLUMN_WIDTH - statName.length()) +
                emptySpaces(STATS_VALUE_COLUMN_WIDTH -  doubleToFormattedString(statValue).length()) +
                doubleToFormattedString(statValue);
    }

    public static String statsTableDivider()
    {
        return divider(STATS_NAME_COLUMN_WIDTH + STATS_VALUE_COLUMN_WIDTH);
    }

    public static String countryTableRow(Country country)
    {
        StringBuilder row = new StringBuilder();

        row.append(country.getCode());
        row.append(emptySpaces(CODE_COLUMN_WIDTH - country.getCode().length()));

        row.append(country.getName());
        row.append(emptySpaces(NAME_COLUMN_WIDTH - country.getName().length()));

        String internetUsersFormatted = doubleToFormattedString(country.getInternetUsers());
        row.append(emptySpaces(INTERNETUSERS_COLUMN_WIDTH - internetUsersFormatted.length()));
        row.append(internetUsersFormatted);

        String literacyRateFormatted = doubleToFormattedString(country.getAdultLiteracyRate());
        row.append(emptySpaces(LITERACY_COLUMN_WIDTH - literacyRateFormatted.length()));
        row.append(literacyRateFormatted);

        return row.toString();
    }

    public static String countryTableHeader()
    {
        StringBuilder header = new StringBuilder();

        header.append(CODE_COLUMN_HEADER);
        header.append(emptySpaces(CODE_COLUMN_WIDTH - CODE_COLUMN_HEADER.length()));

        header.append(NAME_COLUMN_HEADER);
        header.append(emptySpaces(NAME_COLUMN_WIDTH - NAME_COLUMN_HEADER.length()));

        header.append(emptySpaces(INTERNETUSERS_COLUMN_WIDTH - INTERNETUSERS_COLUMN_HEADER.length()));
        header.append(INTERNETUSERS_COLUMN_HEADER);

        header.append(emptySpaces(LITERACY_COLUMN_WIDTH - LITERACY_COLUMN_HEADER.length()));
        header.append(LITERACY_COLUMN_HEADER);

        return header.toString();
    }

    private static String emptySpaces(int numberOfEmptySpaces)
    {
        return String.join("", Collections.nCopies(numberOfEmptySpaces, " "));
    }

    private static String doubleToFormattedString(Double d)
    {
        String formattedDouble = NULL_TO_PRINT;
        if (d != null)
        {
            formattedDouble = DECIMAL_FORMAT.format(d);
        }
        return formattedDouble;
    }

    public static String countryTableDivider()
    {
        return divider(countryTableHeader().length());
    }

    public static String menuDivider()
    {
        return divider(MENU_WIDTH);
    }

    private static String divider(int numberOfDashes)
    {
        return String.join("", Collections.nCopies(numberOfDashes, "-"));
    }
}


