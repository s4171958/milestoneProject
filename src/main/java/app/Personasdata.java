package app;

/**
 * Class represeting a Movie from the Movies database
 * For simplicity this uses public fields
 * You could use private fields with getters to be safer
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */
public class Personasdata {
   // Movie id
   public String name;

   // Movie name
   public String occupation;

   // Year the movie was made
   public int age;

   // String representing the movie genre
   public String gender;

   public String location;

   public String bio;

   public String goals;
   
   public String needs;
   /**
    * Create a movie withou setting any of the fields,
    * Fields will have default "empty" values
    */
   public Personasdata() {
   }

   /**
    * Create a movie setting all of the fields
    */
   public Personasdata(String name, String occupation, int age, String gender, String location, String bio, String needs, String goals) {
      this.name = name;
      this.occupation = occupation;
      this.age = age;
      this.gender = gender;
      this.location = location;
      this.bio = bio;
      this.needs = needs;
      this.goals = goals;
   }
}
