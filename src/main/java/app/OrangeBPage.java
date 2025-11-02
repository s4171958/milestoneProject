package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;


public class OrangeBPage implements Handler {

    
    public static final String URL = "/infectiondata.html";

    
    private static final String TEMPLATE = ("OrangeBPage.html");

    @Override
    public void handle(Context context) throws Exception {
       
        Map<String, Object> model = new HashMap<String, Object>();

        
       

       
        JDBCConnection jdbc = new JDBCConnection();

        context.render(TEMPLATE, model);

        // TODO: Generate tables and write SQL queries
    }
}