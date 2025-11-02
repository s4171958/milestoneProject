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
public class GreenBPage implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/infectiondata.html";

    // Name of the Thymeleaf HTML template page in the resources folder
    private static final String TEMPLATE = ("GreenBPage.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will be filled with:
        //  - Title to give to the h1 tag
        //  - Array list of all movies for the UL element
        Map<String, Object> model = new HashMap<String, Object>();

        // Add in title for the h1 tag to the model
        model.put("title", new String("About Us"));

        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the movies
        ArrayList<Personasdata> personas = jdbc.getPersonasdata();
        ArrayList<String> Pnames = new ArrayList<String>();
        for (Personasdata persona : personas) {
            Pnames.add(persona.name);
        }

        // Finally put all of these movies into the model
        //model.put("pnames", Pnames);

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

}
