package com.worlddata.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Country implements Comparable<Country>
{
    @Id
    private String code;
    @Column
    private String name;
    @Column
    private Double internetUsers;
    @Column
    private Double adultLiteracyRate;

    public Country() {}

    public Country(CountryBuilder builder)
    {
        this.code = builder.code;
        this.name = builder.name;
        this.internetUsers = builder.internetUsers;
        this.adultLiteracyRate = builder.adultLiteracyRate;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getInternetUsers()
    {
        return internetUsers;
    }

    public void setInternetUsers(Double internetUsers)
    {
        this.internetUsers = internetUsers;
    }

    public Double getAdultLiteracyRate()
    {
        return adultLiteracyRate;
    }

    public void setAdultLiteracyRate(Double adultLiteracyRate)
    {
        this.adultLiteracyRate = adultLiteracyRate;
    }

    @Override
    public String toString()
    {
        return code + " " +
                name +
                " (internet users: " + internetUsers +
                " literacy: " + adultLiteracyRate + ")";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Country country = (Country)o;

        return code != null ? code.equals(country.code) : country.code == null;

    }

    @Override
    public int hashCode()
    {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public int compareTo(Country o)
    {
        return this.getCode().compareTo(o.getCode());
    }

    public static class CountryBuilder
    {
        private String code;
        private String name;
        private Double internetUsers;
        private Double adultLiteracyRate;

        public CountryBuilder(String code, String name)
        {
            this.code = code;
            this.name = name;
        }

        public CountryBuilder withInternetUsers(Double internetUsers)
        {
            this.internetUsers = internetUsers;
            return this;
        }

        public CountryBuilder withAdultLiteracyRate(Double adultLiteracyRate)
        {
            this.adultLiteracyRate = adultLiteracyRate;
            return this;
        }

        public Country build()
        {
            return new Country(this);
        }
    }
}
