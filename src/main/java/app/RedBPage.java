package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class RedBPage implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/infectionrates.html";

    // Name of the Thymeleaf HTML template page in the resources folder
    private static final String TEMPLATE = ("RedBPage.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will be filled with:
        //  - Title to give to the h1 tag
        //  - Array list of all movies for the UL element
        Map<String, Object> model = new HashMap<String, Object>();

        // Add in title for the h1 tag to the model
        

        // Add into the model the list of types to give to the dropdown
        ArrayList<String> infectiontypes = new ArrayList<String>();
        ArrayList<Integer> yeardates = new ArrayList<>(List.of(2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023,2024));

        model.put("yeardates", yeardates);
        
        
        
        infectiontypes.add("Measles");
        infectiontypes.add("Rubella");
        infectiontypes.add("Pertussis");
        model.put("infectiontypes", infectiontypes);


        
        // Look up from JDBC
        JDBCConnection jdbc = new JDBCConnection();

        /* Get the Form Data
         *  from the drop down list
         * Need to be Careful!!
         *  If the form is not filled in, then the form will return null!
        */
        
        String inftype_drop = context.formParam("inftype_drop");
        String infyear_drop = context.formParam("infyear_drop");
        


        if (inftype_drop == null ||infyear_drop == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            ArrayList<String> ecos = new ArrayList<String>();
            model.put("infectionrates", ecos);
            
        } else {
            // If NOT NULL, then lookup the movie by type!
            ArrayList<Infectionrates> infectionrates = jdbc.getInfectionrates(inftype_drop, infyear_drop);
         model.put("infectionrates", infectionrates);
        }
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
        
    }

    /**
     * This extracts the ArrayList of Movie Titles from the provided
     * array list of movies. This is needed to pass an arraylist of
     * strings to Thymeleaf as we can't use our own custom classes.
     */
    ArrayList<String> extractInfCountries(ArrayList<Infectiondata> infections) {
        ArrayList<String> countries = new ArrayList<String>();
        for (Infectiondata infectiondatas : infections) {
            countries.add(infectiondatas.country);
        }
        return countries;
    }

    ArrayList<Integer> extractInfCases(ArrayList<Infectiondata> infections) {
        ArrayList<Integer> casenumbers = new ArrayList<Integer>();
        for (Infectiondata infectiondatas : infections) {
            casenumbers.add(infectiondatas.cases);
        }
        return casenumbers;
    }
}
