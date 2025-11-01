package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
public class vaccRates implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/vaccRates.html";

    // Name of the Thymeleaf HTML template page in the resources folder
    private static final String TEMPLATE = ("vaccRates.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will be filled with:
        //  - Title to give to the h1 tag
        //  - Array list of all movies for the UL element
        Map<String, Object> model = new HashMap<>();

        // Add into the model the list of types to give to the dropdown
        ArrayList<String> types = JDBCConnection.getAntigen();
        
        model.put("antigenTypes", types);

       

        /* Get the Form Data
         *  from the drop down list
         * Need to be Careful!!
         *  If the form is not filled in, then the form will return null!
        */
        String antigentype_drop = context.formParam("antigentype_drop");
        if (antigentype_drop == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            model.put("title_drop", new String("No Results to show for dropbox"));
            ArrayList<String> movies = new ArrayList<>();
            model.put("movies_drop", movies);
        } else {
            // If NOT NULL, then lookup the movie by type!
            model.put("title_drop", new String(antigentype_drop + " Movies"));
            ArrayList<countryAndRegion> orangeTableOne = JDBCConnection.getOrangeTableOne(antigentype_drop, 2024);
            ArrayList<String> titles = extractCountry(orangeTableOne);
            model.put("movies_drop", titles);
        }

        String movietype_textbox = context.formParam("movietype_textbox");
        if (movietype_textbox == null || movietype_textbox == "") {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            model.put("title_text", new String("No Results to show for textbox"));
            ArrayList<String> movies = new ArrayList<>();
            model.put("movies_text", movies);
        } else {
            // If NOT NULL, then lookup the movie by type!
            model.put("title_text", new String(movietype_textbox + " Movies"));
            ArrayList<countryAndRegion> orangeTableOne = JDBCConnection.getOrangeTableOne(movietype_textbox, 2024);
            ArrayList<String> titles = extractCountry(orangeTableOne);
            model.put("movies_text", titles);
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
    ArrayList<String> extractCountry(ArrayList<countryAndRegion> orangeTableOne) {
        ArrayList<String> country = new ArrayList<>();
        for (countryAndRegion row : orangeTableOne) {
            country.add(row.country);
        }
        return country;
    }

}
