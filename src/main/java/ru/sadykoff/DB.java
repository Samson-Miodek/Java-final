package ru.sadykoff;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.sql.*;
import java.util.Formatter;
import java.util.Locale;

public final class DB {

    private static Connection connection;

    //4 Создать файл БД SQL Lite и подключить ее к проекту.
    //5 В БД создать набор таблиц (по 3-ей нормальной форме) согласно набору объектов.
    public static void createDb() throws SQLException, ClassNotFoundException {
        if (connection != null)
            return;

        Class.forName("org.sqlite.JDBC");

        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
        var statement = connection.createStatement();

        statement.execute("drop table 'country';");
        statement.execute(
                "CREATE TABLE if not exists 'country' (" +
                    "'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "'name' text, " +
                    "'subregion' text, " +
                    "'region' text, " +
                    "'internet_users' INT, " +
                    "'population' INT);");
        statement.close();
    }

    //6 Все данные из набора объектов сохранить в БД.
    public static void insertData(String name, String subregion, String region, long internetUsers, long population) throws SQLException {
        final var SQL = "INSERT INTO 'country' ('name','subregion','region','internet_users','population') VALUES (?,?,?,?,?);";
        var statement = connection.prepareStatement(SQL);

        statement.setString(1, name);
        statement.setString(2, subregion);
        statement.setString(3, region);
        statement.setLong(4, internetUsers);
        statement.setLong(5, population);
        statement.executeUpdate();
        statement.close();
    }


    //задание 1
    //Постройте график процентного соотношения пользователей в интернете от всего населения по субрегионам.
    public static PieDataset getInternetUsersPercentSubregionDataset() throws SQLException {
        final var SQL = "SELECT subregion, CAST(sum(internet_users) AS real)/sum(population) AS percent FROM country GROUP BY subregion;";
        var statement = connection.createStatement();
        var rs = statement.executeQuery(SQL);

        var dataset = new DefaultPieDataset();

        while (rs.next()) {
            var subregion = rs.getString("subregion");
            var percent = rs.getDouble("percent");
            dataset.setValue(subregion, percent);
        }

        statement.close();

        return dataset;
    }

    //задание 2
    //Выведите название страны с наименьшим кол-вом зарегистрированных в ин-ете пользователей в Восточной Европе.
    public static String getCountryNameWithSmallestRegisteredInternetUsersBySubregion(String subregion) throws SQLException {
        final var SQL = String.format("SELECT name,min(internet_users) FROM country WHERE subregion = '%s';", subregion);
        var statement = connection.createStatement();
        var rs = statement.executeQuery(SQL);
        var countryName = rs.getString("name");
        statement.close();
        return countryName;
    }

    //задание 3
    //Выведите в консоль название странЫ процент зарегистрированных в интернете пользователей которОЙ находится в промежутке от 75% до 85%
    public static String getCountryNameWhereInternetUsersBetween(double from, double to) throws SQLException {
        final var SQL = new Formatter(Locale.US).format("SELECT name FROM country WHERE cast(internet_users as real)/population BETWEEN %f and %f;", from, to).toString();
        var statement = connection.createStatement();
        var rs = statement.executeQuery(SQL);
        var countryName = rs.getString("name");
        statement.close();
        return countryName;
    }
}
