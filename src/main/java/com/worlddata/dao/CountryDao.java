package com.worlddata.dao;


import com.worlddata.model.Country;

import java.util.List;

public interface CountryDao
{
    List<Country> findAll();
    Country findByCode(String code);
    void update(Country country);
    void save(Country country);
    void delete(Country country);
}
