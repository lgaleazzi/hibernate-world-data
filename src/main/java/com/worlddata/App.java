package com.worlddata;

import com.worlddata.controller.Prompter;
import com.worlddata.dao.CountryDao;
import com.worlddata.dao.CountryDaoImpl;
import com.worlddata.service.CountryService;
import com.worlddata.service.CountryServiceImpl;

public class App
{

    public static void main(String[] args)
    {
        CountryDao countryDao = new CountryDaoImpl();
        CountryService countryService = new CountryServiceImpl(countryDao);
        Prompter prompter = new Prompter(countryService);

        prompter.run();
    }
}
