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

    
    public static ArrayList<countryAndRegion> getOrangeTableOne(String region, String userYear) {
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
                                        "    c.name AS Country,\r\n" + //
                                        "    region AS Region,\r\n" + //
                                        "    coverage AS 'Percentage of Target'\r\n" + //
                                        "FROM vaccination v\r\n" + //
                                        "INNER JOIN \r\n" + //
                                        "    Country c ON v.country = c.countryID\r\n" + //
                                        "WHERE \r\n" + //
                                        "    Year =" + userYear + "\r\n" + //
                                        "    AND UPPER(region) LIKE '%" + region + "%'\r\n" + //
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
                    row.year  = results.getString("Year");
                    row.country  = results.getString("Country");
                    row.region = results.getString("Region");
                    row.percentage = results.getString("Percentage of Target");
                    
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

    public static ArrayList<String> getRegion() {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<String> region = new ArrayList<>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            try ( // Prepare a new SQL Query & Set a timeout
                    Statement statement = connection.createStatement()) {
                statement.setQueryTimeout(30);
                // The Query
                String query = "SELECT DISTINCT region\r\n" + //
                                        "FROM country c;";
                // Get Result
                ResultSet results = statement.executeQuery(query);
                // Process all of the results
                // The "results" variable is similar to an array
                // We can iterate through all of the database query results
                while (results.next()) {
                    
                    String regionName = results.getString("region");
                    
                    // Add the movie object to the array
                    region.add(regionName);
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
        return region;
    }

    public static ArrayList<String> getAntigen() {
            // Create the ArrayList to return - this time of Movie objects
            ArrayList<String> antigen = new ArrayList<>();

            // Setup the variable for the JDBC connection
            Connection connection = null;

            try {
                // Connect to JDBC data base
                connection = DriverManager.getConnection(DATABASE);

                try ( // Prepare a new SQL Query & Set a timeout
                        Statement statement = connection.createStatement()) {
                    statement.setQueryTimeout(30);
                    // The Query
                    String query = "SELECT DISTINCT antigen\r\n" + //
                                            "FROM vaccination v;";
                    // Get Result
                    ResultSet results = statement.executeQuery(query);
                    // Process all of the results
                    // The "results" variable is similar to an array
                    // We can iterate through all of the database query results
                    while (results.next()) {
                        
                        String antigenName = results.getString("antigen");
                        
                        // Add the movie object to the array
                        antigen.add(antigenName);
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
            return antigen;
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
 //TODO: add second table data for orange level code
    public static ArrayList<orangeTableTwo> getOrangeTableTwo(String year, String antigen) {
        // Create the ArrayList to return - this time of Movie objects
        ArrayList<orangeTableTwo> orangeTable = new ArrayList<>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            try ( // Prepare a new SQL Query & Set a timeout
                    Statement statement = connection.createStatement()) {
                statement.setQueryTimeout(30);
                // The Query
                String query = "SELECT\r\n" + //
                                        "    antigen AS Antigen,\r\n" + //
                                        "    year AS Year,\r\n" + //
                                        "    COUNT(name) AS 'No. Countries',\r\n" + //
                                        "    region AS Region\r\n" + //
                                        "FROM\r\n" + //
                                        "(SELECT DISTINCT\r\n" + //
                                        "    antigen,\r\n" + //
                                        "    c.name,    \r\n" + //
                                        "    coverage,\r\n" + //
                                        "    region,\r\n" + //
                                        "    year\r\n" + //
                                        "FROM vaccination v\r\n" + //
                                        "INNER JOIN \r\n" + //
                                        "    Country c\r\n" + //
                                        "ON v.country = c.countryid\r\n" + //
                                        "WHERE coverage >= 90\r\n" + //
                                        "AND coverage NOT LIKE ''\r\n" + //
                                        "ORDER BY region)\r\n" + //
                                        "WHERE LOWER(antigen) LIKE '%" + antigen + "%'\r\n" + //
                                        "    AND year LIKE '" + year + "'\r\n" + //
                                        "GROUP BY region;";
                // Get Result
                ResultSet results = statement.executeQuery(query);
                // Process all of the results
                // The "results" variable is similar to an array
                // We can iterate through all of the database query results
                while (results.next()) {
                    // Create a Movie Object
                    orangeTableTwo row = new orangeTableTwo();
                    
                    // Lookup the columns we want, and set the movie object field
                    // BUT, we must be careful of the column type!

                    row.antigen = results.getString("Antigen");
                    row.year = results.getString("Year");
                    row.numCountries = results.getString("No. Countries");
                    row.region = results.getString("Region");
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

    public ArrayList<Personasdata> getPersonasdata() {
        
        ArrayList<Personasdata> personas = new ArrayList<Personasdata>();

        Connection connection = null;

        try {
           
            connection = DriverManager.getConnection(DATABASE);

        
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            
            String query = "SELECT * FROM Personas";
            
       
            ResultSet results = statement.executeQuery(query);

       
            while (results.next()) {
               
                Personasdata persona = new Personasdata();

               
                persona.name    = results.getString("PersonaName");
                persona.occupation  = results.getString("Occupation");
                persona.gender  = results.getString("Gender");
                persona.age = results.getInt("Age");
                persona.location = results.getString("Location");
                persona.bio = results.getString("Bio");
                persona.needs = results.getString("Needs");
                persona.goals = results.getString("Goals");
              
                personas.add(persona);
            }

            
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

 
        return personas;
    }


    public ArrayList<Studentsdata> getStudentsdata() {
        
        ArrayList<Studentsdata> students = new ArrayList<Studentsdata>();

       
        Connection connection = null;

        try {
            
            connection = DriverManager.getConnection(DATABASE);

        
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

           
            String query = "SELECT * FROM Students";
            
           
            ResultSet results = statement.executeQuery(query);

           
            while (results.next()) {
             
                Studentsdata student = new Studentsdata();

                
                student.name    = results.getString("StudentName");
                student.id = results.getString("StudentID");
               
                students.add(student);
            }

          
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

       
        return students;
    }
    // TODO: Add a method to collect data for infections.

    public ArrayList<Infectiondata> getInfectiondata(String ecophase, String infection, String year, String ordering) {
        ArrayList<Infectiondata> infections = new ArrayList<Infectiondata>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = """
                    select description as Infection, name  as Country, phase  as Economic_Phase, infectiondata.year as Year, cases Cases
                from infectiondata
                inner join infection_type
                on id = inf_type
                inner join country
                on countryID = infectiondata.country                    
                inner join economy 
                on economy = economyID
                where Infection = '""" + infection + "'  and Economic_phase = '" + ecophase + "' and Year = (" + year + 
                 ") order by " + ordering; 
                                
                            
                            
                
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Movie Object
                Infectiondata infectiontable = new Infectiondata();

                infectiontable.infection    = results.getString("Infection");
                infectiontable.country  = results.getString("Country");
                infectiontable.ecophase  = results.getString("Economic_Phase");
                infectiontable.year = results.getInt("Year");
                infectiontable.cases = results.getInt("Cases");
                infections.add(infectiontable);
            }

            // Close the statement because we are done with it
            statement.close();
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
        return infections;
    }

    public ArrayList<Infectionrates> getInfectionrates(String infection, String year) {
        ArrayList<Infectionrates> infectionrates = new ArrayList<Infectionrates>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = """
                    select name, description, year, round(avg(cases / 100000),3) as cases
from country
inner join infectiondata
on country.countryID = infectiondata.country
inner join infection_type
on id = inf_type
where description = '""" + infection + "' and year = " + year + """

union 

select name, description, year, round((cases / 100000) , 3) as avgcases
from country
inner join infectiondata
on country.countryID = infectiondata.country
inner join infection_type
on id = inf_type
where description = '""" + infection + "' and year = " + year + """
        
and avgcases > 
(
select round(avg(cases / 100000),3)
from country
inner join infectiondata
on country.countryID = infectiondata.country
inner join infection_type
on id = inf_type
where description = '""" + infection + "' and year = " + year + ")";
            
                    
                    
                            
                            
                
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Movie Object
                Infectionrates infectionratestable = new Infectionrates();

                infectionratestable.infection    = results.getString("description");
                infectionratestable.country  = results.getString("name");
                infectionratestable.year  = results.getInt("year");
                infectionratestable.cases = results.getDouble("cases");
                
                infectionrates.add(infectionratestable);
            }

            // Close the statement because we are done with it
            statement.close();
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
        infectionrates.get(0).country = "Global Average";
        return infectionrates;
    }

    

    
}
    
  
      

    