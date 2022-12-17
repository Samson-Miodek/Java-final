package ru.sadykoff;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        initialization();

        //Задание 1
        //Постройте график процентного соотношения пользователей в интернете от всего населения по субрегионам.
        PieChart.showDataset(DB.getInternetUsersPercentSubregionDataset());

        System.out.println("Задание 2");
        System.out.println("Выведите название страны с наименьшим кол-вом зарегистрированных в ин-ете пользователей в Восточной Европе.");
        System.out.println(DB.getCountryNameWithSmallestRegisteredInternetUsersBySubregion("Eastern Europe"));

        System.out.println();

        System.out.println("Задание 3");
        System.out.println("Выведите в консоль название страны, процент зарегистрированных в интернете пользователей которой находится в промежутке от 75% до 85%");
        System.out.println(DB.getCountryNameWhereInternetUsersBetween(0.75, 0.85));
    }

    private static void initialization() throws IOException, SQLException, ClassNotFoundException {
        //1 Выбрать свой вариант задания согласно номеру в таблице.
        var reader = new CSVReader(new FileReader("src/main/resources/Country.csv"), ',', '"');
        var head = reader.readNext();

        var countries = new ArrayList<Country>();

        //3 Распарсив данные в файле CSV, нужно по ним создать набор объектов, заполнив все необходимые поля.
        String[] data;
        while ((data = reader.readNext()) != null) {
            var countryOrArea = data[0];
            var subregion = data[1];
            var region = data[2];
            var internetUsers = parseLong(data[3]);
            var population = parseLong(data[4]);
            countries.add(new Country(countryOrArea, subregion, region, internetUsers, population));
        }

        //4 Создать файл БД SQL Lite и подключить ее к проекту.
        //5 В БД создать набор таблиц (по 3-ей нормальной форме) согласно набору объектов.
        //6 Все данные из набора объектов сохранить в БД.
        DB.createDb();

        for (var county : countries)
            DB.insertData(county.getName(), county.getSubregion(), county.getRegion(), county.getInternetUsers(), county.getPopulation());
    }

    private static long parseLong(String str) {
        if (str == null || str.length() == 0)
            return 0;

        return Long.parseLong(str.replace(",", ""));
    }
}
