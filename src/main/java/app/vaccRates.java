package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

//TODO: Finish CSS
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

       

       //TODO: nest if/else statement so that both boxes need to be filled before database is called
        String antigentype_drop = context.formParam("antigentype_drop");
        if (antigentype_drop == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            model.put("title_drop", new String("No Results to show for dropbox"));
            ArrayList<String> movies = new ArrayList<>();
            model.put("movies_drop", movies);
        } else {
            // If NOT NULL, then lookup the movie by type!
            model.put("title_drop", new String(antigentype_drop + " Statistics"));
            ArrayList<countryAndRegion> orangeTableOne = JDBCConnection.getOrangeTableOne(antigentype_drop, 2024);
           
            model.put("antigen_drop", orangeTableOne);
        }

        
        //TODO: change conditional statement to accept only numbers (years that database has data in)
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

    //TODO: add other extract methods for other columns, create a table in html and populate with the following data
    ArrayList<String> extractCountry(ArrayList<countryAndRegion> orangeTableOne) {
        ArrayList<String> country = new ArrayList<>();
        for (countryAndRegion row : orangeTableOne) {
            country.add(row.country);
        }
        return country;
    }

    ArrayList<String> extractRegion(ArrayList<countryAndRegion> orangeTableOne) {
        ArrayList<String> region = new ArrayList<>();
        for (countryAndRegion row : orangeTableOne) {
            region.add(row.region);
        }
        return region;
    }

    ArrayList<String> extractCoverage(ArrayList<countryAndRegion> orangeTableOne) {
        ArrayList<String> coverage = new ArrayList<>();
        for (countryAndRegion row : orangeTableOne) {
            coverage.add(row.percentage);
        }
        return coverage;
    }
}
