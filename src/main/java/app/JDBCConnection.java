package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    private static final String DATABASE = "jdbc:sqlite:database/who.db";
    
    
    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    public ArrayList<countryAndRegion> getOrangeTableOne(String userAntigen, int userYear) {
    /**
     * Get all of the Movies in the database.
     * @return
     *    Returns an ArrayList of Movie objects
     */
    public static ArrayList<countryAndRegion> getOrangeTableOne(String userAntigen, int userYear) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<countryAndRegion> orangeTable = new ArrayList<>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            try ( // Prepare a new SQL Query & Set a timeout
                    Statement statement = connection.createStatement()) {
                statement.setQueryTimeout(30);
                // The Query
                String query = "SELECT DISTINCT\r\n" + //
                                        "    antigen AS Antigen,\r\n" + //
                                        "    year AS Year,\r\n" + //
                                        "    country AS Country,\r\n" + //
                                        "    region AS Region,\r\n" + //
                                        "    coverage AS 'Percentage of Target'\r\n" + //
                                        "FROM vaccination v\r\n" + //
                                        "NATURAL JOIN \r\n" + //
                                        "    Country c\r\n" + //
                                        "WHERE \r\n" + //
                                        "    Year =" + userYear + "\r\n" + //
                                        "    AND LOWER(antigen) LIKE '%" + userAntigen + "%'\r\n" + //
                                        "    AND coverage >= 90\r\n" + //
                                        "    AND coverage NOT LIKE ''\r\n" + //
                                        "ORDER BY coverage desc;";
                // Get Result
                ResultSet results = statement.executeQuery(query);
                // Process all of the results
                // The "results" variable is similar to an array
                // We can iterate through all of the database query results
                while (results.next()) {
                    // Create a Movie Object
                    countryAndRegion row = new countryAndRegion();
                    
                    // Lookup the columns we want, and set the movie object field
                    // BUT, we must be careful of the column type!
                    row.antigen    = results.getString("Antigen");
                    row.year  = results.getInt("Year");
                    row.country  = results.getString("Country");
                    row.region = results.getString("Region");
                    row.percentage = results.getDouble("Percentage of Target");
                    
                    // Add the movie object to the array
                    orangeTable.add(row);
                }
                // Close the statement because we are done with it
                statement.close();
            }
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movies
        return orangeTable;
    }

    public static String getNumCountries() {
        String numCountries = null;
        Connection connection = null;
        //int a = test;
        try {
            connection = DriverManager.getConnection(DATABASE);
            
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT COUNT(c.name) AS 'Number of Countries Registered'\r\n" + //
                                "     FROM country c;";
            ResultSet results = statement.executeQuery(query);

            numCountries = results.getString("Number of Countries Registered");

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return numCountries;
    }

    public static String getNumOutbreaks() {
        String numOutbreaks = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT SUM(cases) AS 'Total Number of Cases Last 5 Years' \r\n" + //
                                "    FROM infectiondata\r\n" + //
                                "    ORDER BY year DESC\r\n" + //
                                "    LIMIT 5;";
            ResultSet results = statement.executeQuery(query);

            numOutbreaks = results.getString("Total Number of Cases Last 5 Years");

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return numOutbreaks;
    }

    public static String getMostReportedDisease() {
        String mostReportedDisease = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT inf_type \r\n" + //
                                "    FROM infectiondata\r\n" + //
                                "    WHERE year >= (SELECT MAX(year) - 4 FROM infectiondata)\r\n" + //
                                "    GROUP BY inf_type\r\n" + //
                                "    ORDER BY sum(cases) DESC\r\n" + //
                                "    LIMIT 1;";
            ResultSet results = statement.executeQuery(query);

            mostReportedDisease = results.getString("inf_type");

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return mostReportedDisease;
    }

    public static String getVaccinedCountries() {
        String vaccinedCountries = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT COUNT(country)\r\n" + //
                                "    FROM vaccination\r\n" + //
                                "    WHERE \r\n" + //
                                "        year = (SELECT MAX(year) FROM vaccination)\r\n" + //
                                "        AND\r\n" + //
                                "        coverage >= 100\r\n" + //
                                "        and coverage NOT LIKE '';";
            ResultSet results = statement.executeQuery(query);

            vaccinedCountries = results.getString("COUNT(country)");

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return vaccinedCountries;
    }

}
    
  
      

    