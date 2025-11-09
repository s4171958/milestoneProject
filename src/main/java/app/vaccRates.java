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
        
        //dropdown table
        String regionType_drop = context.formParam("regionTypes");
        // year textbox
        String year = context.formParam("inf_year_numbox");

        int numyear = -1;
        
        // in case numyear is empty
        try {
        if (year != null && !year.isEmpty()) {
            numyear = Integer.parseInt(year);
        }
        } catch (NumberFormatException e) {
            // leave numyear = -1 if parsing fails
        }

        //prints table one
        if (regionType_drop == null || numyear < 2000 || numyear > 2024) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            model.put("title_drop", new String("Invalid input for dropbox and/or textbox"));
            ArrayList<String> empty = new ArrayList<>();
            model.put("region_drop", empty);
        } else {
            // If NOT NULL, then lookup the movie by type!
            model.put("title_drop", new String("Table 1: All countries in the year " + year + " in region " + regionType_drop + " reaching +90% of vaccine target"));
            ArrayList<countryAndRegion> orangeTableOne = JDBCConnection.getOrangeTableOne(regionType_drop, year);
           
            model.put("region_drop", orangeTableOne);
        }

        
        //orangetabletwo drop table
        ArrayList<String> antigenTypes = JDBCConnection.getAntigen();
        model.put("antigenTypes", antigenTypes);
        String antigenType_drop = context.formParam("antigenType");

        //orangetabletwo year textbox
        String yearTwo = context.formParam("inf_year_numbox_two");

        int numyearTwo = -1;

        // in case numyear is empty
        try {
        if (yearTwo != null && !yearTwo.isEmpty()) {
            numyearTwo = Integer.parseInt(yearTwo);
        }
        } catch (NumberFormatException e) {
            // leave numyear = -1 if parsing fails
        }

        if (antigenType_drop == null || numyearTwo < 2000 || numyearTwo > 2024) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            // Also store empty array list for completness
            model.put("title_drop_two", new String("Invalid input for dropbox and/or textbox"));
            ArrayList<String> empty = new ArrayList<>();
            model.put("orangeTableTwo", empty);
        } else {
            // If NOT NULL, then lookup the movie by type!
            model.put("title_drop_two", new String("Table 2:Countries that have made 90% of their " + antigenType_drop + " target, in Year, " + year));
            ArrayList<orangeTableTwo> orangeTableTwo = JDBCConnection.getOrangeTableTwo(yearTwo, antigenType_drop);
           
            model.put("orangeTableTwo", orangeTableTwo);
        }



        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

  
}
