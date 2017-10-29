package com.worlddata.service;

import com.worlddata.model.Country;

import java.util.List;

public interface CountryService
{
    List<Country> findAll();
    Country findByCode(String code);
    void update(Country country);
    void save(Country country);
    void delete(Country country);
    boolean codeExists(String code);
    Double getMinimumAdultLiteracy();
    Double getMaximumAdultLiteracy();
    Double getMeanAdultLiteracy();
    Double getStandardDeviationLiteracy();
    Double getMinimumInternetUsers();
    Double getMaximumInternetUsers();
    Double getMeanInternetUsers();
    Double getStandardDeviationInternetUsers();
    Double getCorrelationCoefficient();
}
