package com.worlddata.service;

import com.worlddata.dao.CountryDao;
import com.worlddata.model.Country;

import java.util.List;
import java.util.stream.Collectors;

import static com.worlddata.utils.Stats.mean;
import static com.worlddata.utils.Stats.standardDeviation;

public class CountryServiceImpl implements CountryService
{
    CountryDao countryDao;

    public CountryServiceImpl(CountryDao countryDao)
    {
        this.countryDao = countryDao;
    }

    @Override
    public List<Country> findAll()
    {
        return countryDao.findAll();
    }

    @Override
    public Country findByCode(String code)
    {
        return countryDao.findByCode(code);
    }

    @Override
    public void update(Country country)
    {
        countryDao.update(country);
    }

    @Override
    public void save(Country country)
    {
        countryDao.save(country);
    }

    @Override
    public void delete(Country country)
    {
        countryDao.delete(country);
    }

    @Override
    public boolean codeExists(String code)
    {
        return findAllCodes().contains(code);
    }

    public List<String> findAllCodes()
    {
        return findAll().stream()
                .map(Country::getCode)
                .collect(Collectors.toList());
    }

    @Override
    public Double getMinimumAdultLiteracy()
    {
        return findAll().stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .min((c1, c2) -> c1.getAdultLiteracyRate().compareTo(c2.getAdultLiteracyRate()))
                .map(Country::getAdultLiteracyRate)
                .orElse(null);
    }

    @Override
    public Double getMaximumAdultLiteracy()
    {
        return findAll().stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .max((c1, c2) -> c1.getAdultLiteracyRate().compareTo(c2.getAdultLiteracyRate()))
                .map(Country::getAdultLiteracyRate)
                .orElse(null);
    }

    @Override
    public Double getMeanAdultLiteracy()
    {
        return findAll().stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .mapToDouble(Country::getAdultLiteracyRate)
                .average()
                .getAsDouble();
    }



    @Override
    public Double getStandardDeviationLiteracy()
    {
        List<Double> literacyValues = findAll().stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .map(Country::getAdultLiteracyRate)
                .collect(Collectors.toList());

        return standardDeviation(literacyValues);
    }

    @Override
    public Double getMinimumInternetUsers()
    {
        return findAll().stream()
                .filter(country -> country.getInternetUsers() != null)
                .min((c1, c2) -> c1.getInternetUsers().compareTo(c2.getInternetUsers()))
                .map(Country::getInternetUsers)
                .orElse(null);
    }

    @Override
    public Double getMaximumInternetUsers()
    {
        return findAll().stream()
                .filter(country -> country.getInternetUsers() != null)
                .max((c1, c2) -> c1.getInternetUsers().compareTo(c2.getInternetUsers()))
                .map(Country::getInternetUsers)
                .orElse(null);
    }

    @Override
    public Double getMeanInternetUsers()
    {
        return findAll().stream()
                .filter(country -> country.getInternetUsers() != null)
                .mapToDouble(Country::getInternetUsers)
                .average()
                .getAsDouble();
    }


    @Override
    public Double getStandardDeviationInternetUsers()
    {
        List<Double> internetUsers = findAll().stream()
                .filter(country -> country.getInternetUsers() != null)
                .map(Country::getInternetUsers)
                .collect(Collectors.toList());

        return standardDeviation(internetUsers);
    }

    @Override
    public Double getCorrelationCoefficient()
    {
        List<Country> countries = findAllWithoutMissingData();

        List<Double> literacyValues = countries.stream()
                .map(Country::getAdultLiteracyRate)
                .collect(Collectors.toList());

        List<Double> internetUsersValues = countries.stream()
                .map(Country::getInternetUsers)
                .collect(Collectors.toList());

        double meanLiteracy = mean(literacyValues);
        double meanInternetUsers = mean(internetUsersValues);
        double stdDevLiteracy = standardDeviation(literacyValues);
        double stdDevInternetUsers = standardDeviation(internetUsersValues);
        int sampleSize = countries.size();

        double sum = 0.0;

        for (Country country : countries)
        {
            double zLiteracy = (country.getAdultLiteracyRate() - meanLiteracy);
            double zInternetUsers = (country.getInternetUsers() - meanInternetUsers);

            sum += zLiteracy * zInternetUsers;
        }

        double correlationCoefficient = (sum / (stdDevLiteracy * stdDevInternetUsers)) / (sampleSize -1);

        return correlationCoefficient;
    }

    private List<Country> findAllWithoutMissingData()
    {
        return findAll().stream()
                .filter(country -> country.getInternetUsers() != null && country.getAdultLiteracyRate() != null)
                .collect(Collectors.toList());
    }
}
