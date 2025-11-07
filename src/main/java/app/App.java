package app;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;


public class App {

    public static final int         JAVALIN_PORT    = 7001;
    public static final String      CSS_DIR         = "css/";
    public static final String      IMAGES_DIR      = "images/";

    public static void main(String[] args) {
        // Create our HTTP server and listen in port 7001
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));
            
            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);


        // Configure Web Routes
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // Note in this example we must add Movies Type as a GET and a POST!
        
        // ADD ALL OF YOUR WEBPAGES HERE
        app.get(PageIndex.URL, new PageIndex());   
        app.get(vaccRates.URL, new vaccRates());
        app.get(GreenBPage.URL, new GreenBPage());
        app.get(OrangeBPage.URL, new OrangeBPage());

        // POST pages can accept form data
        app.post(vaccRates.URL, new vaccRates());
        app.post(OrangeBPage.URL, new OrangeBPage());
    }

}
