package app;

/**
 * Class represeting a Movie from the Movies database
 * For simplicity this uses public fields
 * You could use private fields with getters to be safer
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */
public class Studentsdata {
   // Movie id
   public String name;

   // Movie name
  public String id;
   
    
   public Studentsdata() {
   }

   /**
    * Create a movie setting all of the fields
    */
   public Studentsdata(String name, String id) {
      this.name = name;
      this.id = id;
   }
}
