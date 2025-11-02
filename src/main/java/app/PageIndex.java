package app;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

//TODO: finish css component of web page
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    // Name of the Thymeleaf HTML template page in the resources folder
    private static final String TEMPLATE = ("index.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will remain empty
        Map<String, Object> model = new HashMap<>();
        
       

        String numCountries = JDBCConnection.getNumCountries();
        model.put("numCountries", numCountries);

        String numOutbreaks = JDBCConnection.getNumOutbreaks();
        model.put("numOutbreaks", numOutbreaks);

        String vaccinatedCountries = JDBCConnection.getVaccinedCountries();
        model.put("vaccinatedCountries",vaccinatedCountries);

        String mostReportedDisease = JDBCConnection.getMostReportedDisease();
        model.put("mostReportedDisease",mostReportedDisease);

        
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

}
