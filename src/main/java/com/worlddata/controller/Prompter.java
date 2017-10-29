package com.worlddata.controller;

import com.worlddata.model.Country;
import com.worlddata.service.CountryService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.worlddata.utils.Formatter.*;

public class Prompter
{
    private CountryService countryService;
    private Map<Integer, String> menu;
    private BufferedReader reader;

    public Prompter(CountryService countryService)
    {
        this.countryService = countryService;
        this.menu = new HashMap<>();
        this.reader = new BufferedReader(new InputStreamReader(System.in));

        initializeMenu();
    }

    private void initializeMenu()
    {
        menu.put(1, "View all countries");
        menu.put(2, "View statistics");
        menu.put(3, "Add new country");
        menu.put(4, "Update country");
        menu.put(5, "Delete country");
        menu.put(6, "Exit");
    }

    public void run()
    {
        int choice = 0;

        while (choice != 6)
        {
            try
            {
                choice = promptAction();
                switch(choice)
                {
                    case 1:
                        viewAllCountries();
                        break;
                    case 2:
                        viewStatistics();
                        break;
                    case 3:
                        addNewCountry();
                        break;
                    case 4:
                        updateCountryData();
                        break;
                    case 5:
                        deleteCountry();
                        break;
                    case 6:
                        System.out.println("Exiting the application");
                        System.exit(0);
                        break;
                    default:
                        System.out.printf("Unknown choice:  '%s'. Try again.  %n%n%n",
                                choice);
                }
            } catch (IOException | IllegalArgumentException e)
            {
                System.out.println("Problem with input");
                e.printStackTrace();
            }
        }
    }

    private int promptAction() throws IOException
    {
        System.out.printf("%n %n Menu: %n");
        System.out.println(menuDivider());

        menu.entrySet().forEach(option ->
                System.out.printf("%s - %s %n",
                option.getKey(),
                option.getValue()));

        System.out.println(menuDivider());
        System.out.print("What do you want to do:  ");

        return Integer.parseInt(reader.readLine());
    }

    private void viewAllCountries()
    {
        System.out.println();
        System.out.println(countryTableHeader());
        System.out.println(countryTableDivider());
        countryService.findAll()
                .forEach(country -> System.out.println(countryTableRow(country)));
    }

    private void viewStatistics()
    {
        System.out.println();
        System.out.println("Statistics");
        System.out.println(statsTableDivider());
        System.out.println();

        System.out.println(LITERACY_COLUMN_HEADER);
        System.out.println(statsTableDivider());
        System.out.println(statsTableRow("Maximum", countryService.getMaximumAdultLiteracy()));
        System.out.println(statsTableRow("Minimum", countryService.getMinimumAdultLiteracy()));
        System.out.println(statsTableRow("Mean", countryService.getMeanAdultLiteracy()));
        System.out.println(statsTableRow("Standard Deviation", countryService.getStandardDeviationLiteracy()));
        System.out.println(statsTableDivider());

        System.out.println(INTERNETUSERS_COLUMN_HEADER);
        System.out.println(statsTableDivider());
        System.out.println(statsTableRow("Maximum", countryService.getMaximumInternetUsers()));
        System.out.println(statsTableRow("Minimum", countryService.getMinimumInternetUsers()));
        System.out.println(statsTableRow("Mean", countryService.getMeanInternetUsers()));
        System.out.println(statsTableRow("Standard Deviation", countryService.getStandardDeviationInternetUsers()));

        System.out.println(statsTableDivider());
        System.out.println(statsTableRow("Correlation Coefficient", countryService.getCorrelationCoefficient()));
    }

    private void addNewCountry() throws IOException, IllegalArgumentException
    {
        System.out.println("Add new country");
        System.out.println(menuDivider());
        Country newCountry = promptForNewCountry();
        countryService.save(newCountry);
        System.out.println("Country added: " + newCountry);
    }

    private void updateCountryData() throws IOException, IllegalArgumentException
    {
        //It was not specified which information should be updated
        //I decided to only allow editing stats, country code and name may not be updated
        System.out.println("Update country");
        System.out.println(menuDivider());
        Country country = promptForExistingCountry();

        System.out.println("Edit data for " + country);
        Double internetUsers = promptForInternetUsers();
        Double adultLiteracyRate = promptForAdultLiteracyRate();

        country.setInternetUsers(internetUsers);
        country.setAdultLiteracyRate(adultLiteracyRate);

        countryService.update(country);

        System.out.println("Data updated: " + country);
    }

    private void deleteCountry() throws IOException
    {
        System.out.println("Delete country");
        System.out.println(menuDivider());
        Country country = promptForExistingCountry();
        countryService.delete(country);
        System.out.println("Country deleted: " + country);
    }

    private Country promptForNewCountry() throws IOException
    {
        String code = promptForCountryCode();
        if (countryService.codeExists(code))
        {
            throw new IllegalArgumentException("This country code already exists in the database");
        }
        String name = promptForCountryName();
        Double internetUsers = promptForInternetUsers();
        Double adultLiteracyRate = promptForAdultLiteracyRate();

        return new Country.CountryBuilder(code, name)
                .withInternetUsers(internetUsers)
                .withAdultLiteracyRate(adultLiteracyRate)
                .build();
    }

    private Country promptForExistingCountry() throws IOException, IllegalArgumentException
    {
        String code = promptForCountryCode();
        if (!countryService.codeExists(code))
        {
            throw new IllegalArgumentException("This is not a valid country code");
        }
        return countryService.findByCode(code);
    }

    private String promptForCountryCode() throws IOException, IllegalArgumentException
    {
        System.out.print("Enter the country code: ");
        String code = reader.readLine();
        if (code.length() != 3)
        {
            throw new IllegalArgumentException("This is not a valid country code");
        }
        return code.toUpperCase();
    }

    private String promptForCountryName() throws IOException, IllegalArgumentException
    {
        System.out.print("Enter the country name: ");
        String name = reader.readLine();

        if (name.isEmpty())
        {
            throw new IllegalArgumentException("The country name may not be empty");
        }

        return name;
    }

    private Double promptForInternetUsers() throws IOException
    {
        Double internetUsers = null;
        System.out.print("Enter the rate of internet users: ");
        String input = reader.readLine();

        if (!input.isEmpty())
        {
            internetUsers = Double.parseDouble(input);
        }

        return internetUsers;
    }

    private Double promptForAdultLiteracyRate() throws IOException
    {
        Double literacy = null;
        System.out.print("Enter the rate of adult literacy: ");
        String input = reader.readLine();

        if (!input.isEmpty())
        {
            literacy = Double.parseDouble(input);
        }

        return literacy;
    }
}
