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

    /**
     * Get all of the Movies in the database.
     * @return
     *    Returns an ArrayList of Movie objects
     */
    public ArrayList<countryAndRegion> getOrangeTableOne(String userAntigen, int userYear) {
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

    public static int getNumCountries() {
        int numCountries = 0;
        Connection connection = null;

        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT COUNT(name) AS 'Number of Countries Registered'\r\n" + //
                                "     FROM country c;";
            ResultSet results = statement.executeQuery(query);

            numCountries = results.getInt("Number of Countries Registered");

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

    public static int getNumOutbreaks() {
        int numOutbreaks = 0;
        Connection connection = null;

        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT SUM(cases) AS 'Total Number of Cases Last 5 Years' \r\n" + //
                                "    FROM infectiondata\r\n" + //
                                "    ORDER BY year DESC\r\n" + //
                                "    LIMIT 5;";
            ResultSet results = statement.executeQuery(query);

            numOutbreaks = results.getInt("Total Number of Cases Last 5 Years");

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

    

    /**
     * Get all the movies in the database by a given type.
     * Note this takes a string of the type as an argument!
     * This has been implemented for you as an example.
     * HINT: you can use this to find all of the horror movies!
     */
    // public ArrayList<Movie> getMoviesByType(String movieType) {
    //     ArrayList<Movie> movies = new ArrayList<>();

    //     // Setup the variable for the JDBC connection
    //     Connection connection = null;

    //     try {
    //         // Connect to JDBC data base
    //         connection = DriverManager.getConnection(DATABASE);

    //         // Prepare a new SQL Query & Set a timeout
    //         Statement statement = connection.createStatement();
    //         statement.setQueryTimeout(30);

    //         // The Query
    //         String query = "SELECT * FROM movie WHERE mvtype = '" + movieType + "'";
    //         System.out.println(query);
            
    //         // Get Result
    //         ResultSet results = statement.executeQuery(query);

    //         // Process all of the results
    //         while (results.next()) {
    //             // Create a Movie Object
    //             Movie movie = new Movie();

    //             movie.id    = results.getInt("mvnumb");
    //             movie.name  = results.getString("mvtitle");
    //             movie.year  = results.getInt("yrmde");
    //             movie.genre = results.getString("mvtype");

    //             movies.add(movie);
    //         }

    //         // Close the statement because we are done with it
    //         statement.close();
    //     } catch (SQLException e) {
    //         // If there is an error, lets just pring the error
    //         System.err.println(e.getMessage());
    //     } finally {
    //         // Safety code to cleanup
    //         try {
    //             if (connection != null) {
    //                 connection.close();
    //             }
    //         } catch (SQLException e) {
    //             // connection close failed.
    //             System.err.println(e.getMessage());
    //         }
    //     }

    //     // Finally we return all of the movies
    //     return movies;
    // }

    // TODO: Keep adding more methods here to answer all of the questions from the Studio Class activities
}
