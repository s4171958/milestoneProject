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
        ArrayList<String> types = JDBCConnection.getRegion();
        model.put("regionTypes", types);

        String regionType_drop = context.formParam("regionTypes");
        String year = context.formParam("inf_year_numbox");


        if (regionType_drop == null || (year == null && (year.compareTo("2024") <= 0) && (year.compareTo("2000") >= 0))) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            model.put("title_drop", new String("No Results to show for dropbox and/or textbox"));
            ArrayList<String> empty = new ArrayList<>();
            model.put("region_drop", empty);
        } else {
            // If NOT NULL, then lookup the movie by type!
            model.put("title_drop", new String(regionType_drop + " Statistics, Year: " + year));
            ArrayList<countryAndRegion> orangeTableOne = JDBCConnection.getOrangeTableOne(regionType_drop, year);
           
            model.put("region_drop", orangeTableOne);
        }

        
        //TODO: change conditional statement to accept only numbers (years that database has data in)
        String movietype_textbox = context.formParam("movietype_textbox");
        if (movietype_textbox == null || movietype_textbox == "") {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            model.put("title_text", new String("No Results to show for textbox"));
            ArrayList<String> movies = new ArrayList<>();
            model.put("movies_text", movies);
        } 

        ArrayList<String> antigenTypes = JDBCConnection.getAntigen();
        model.put("antigenTypes", antigenTypes);

        String antigenType_drop = context.formParam("antigenType");

        if (antigenType_drop == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            model.put("title_drop_two", new String("No Results to show for dropbox and/or textbox"));
            ArrayList<String> empty = new ArrayList<>();
            model.put("orangeTableTwo", empty);
        } else {
            // If NOT NULL, then lookup the movie by type!
<<<<<<< HEAD
            model.put("title_drop_two", new String(regionType_drop + " Statistics, Year: " + year));
=======
            model.put("title_drop", new String(antigenType_drop + " Type, Year: " + year));
>>>>>>> 528bbc2b156d066e3a024816efa9e8a25ce23395
            ArrayList<orangeTableTwo> orangeTableTwo = JDBCConnection.getOrangeTableTwo(antigenType_drop, "2024");
           
            model.put("orangeTableTwo", orangeTableTwo);
        }



        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

  
}
