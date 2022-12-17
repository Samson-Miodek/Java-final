package ru.sadykoff;

//2 Разработать набор классов с необходимым составом полей и методов для хранения
//и обработки всех данных, хранящихся в CSV-файле вашего варианта задания.
public class Country {

    private final String name;
    private final String subregion;
    private final String region;
    private final long internetUsers;
    private final long population;

    public Country(String name, String subregion, String region, long internetUsers, long population) {
        this.name = name;
        this.subregion = subregion;
        this.region = region;
        this.internetUsers = internetUsers;
        this.population = population;
    }


    public String getName() {
        return name;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getRegion() {
        return region;
    }

    public long getInternetUsers() {
        return internetUsers;
    }

    public long getPopulation() {
        return population;
    }
}
