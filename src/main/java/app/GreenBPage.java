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
        ArrayList<Studentsdata> students = jdbc.getStudentsdata();
         ArrayList<String> Snames = new ArrayList<String>();
          ArrayList<String> ids = new ArrayList<String>();
        ArrayList<String> Pnames = new ArrayList<String>();
        ArrayList<String> Occupations = new ArrayList<String>();
        ArrayList<Integer> Ages = new ArrayList<Integer>();
        ArrayList<String> Genders = new ArrayList<String>();
        ArrayList<String> Locations = new ArrayList<String>();
        ArrayList<String> Bios = new ArrayList<String>();
        ArrayList<String> Goals = new ArrayList<String>();
        ArrayList<String> Needs = new ArrayList<String>();
        for (Personasdata persona : personas) {
            Pnames.add(persona.name);
        }
        for (Personasdata persona : personas){
            Occupations.add(persona.occupation);
        }
        for (Personasdata persona : personas){
            Ages.add(persona.age);
        }
        for (Personasdata persona : personas){
            Genders.add(persona.gender);
        }
        for (Personasdata persona : personas){
            Locations.add(persona.location);
        }
        for (Personasdata persona : personas){
            Bios.add(persona.bio);
        }
        for (Personasdata persona : personas){
            Goals.add(persona.goals);
        }
        for (Personasdata persona : personas){
            Needs.add(persona.needs);
        }
         for (Studentsdata student : students){
            Snames.add(student.name);
        }
        for (Studentsdata student : students){
            ids.add(student.id);
        }
      
      

        // Finally put all of these movies into the model
             model.put("pname1", Pnames.get(0));
        model.put("occupation1", Occupations.get(0));
        model.put("age1", Ages.get(0));
        model.put("gender1", Genders.get(0));
        model.put("location1", Locations.get(0));
        model.put("bio1", Bios.get(0));
        model.put("goals1", Goals.get(0));
        model.put("needs1", Needs.get(0));

         model.put("pname2", Pnames.get(1));
        model.put("occupation2", Occupations.get(1));
        model.put("age2", Ages.get(1));
        model.put("gender2", Genders.get(1));
        model.put("location2", Locations.get(1));
        model.put("bio2", Bios.get(1));
        model.put("goals2", Goals.get(1));
        model.put("needs2", Needs.get(1));

        model.put("sname1", Snames.get(0));
        model.put("id1", ids.get(0));

        model.put("sname2", Snames.get(1));
        model.put("id2", ids.get(1));
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
          
    }

}
