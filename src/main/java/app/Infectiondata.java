package app;

/**
 * Class represeting a Movie from the Movies database
 * For simplicity this uses public fields
 * You could use private fields with getters to be safer
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */
public class Infectiondata {
   // Movie id
   public String infection;

   // Movie name
   public String country;

   // Year the movie was made
   public int year;

   // String representing the movie genre
   public String ecophase;

   public int cases;
   /**
    * Create a movie withou setting any of the fields,
    * Fields will have default "empty" values
    */
   public Infectiondata() {
   }

   /**
    * Create a movie setting all of the fields
    */
   public Infectiondata(String infection, String country, int year, String ecophase, int cases) {
      this.infection = infection;
      this.country = country;
      this.year = year;
      this.ecophase = ecophase;
      this.cases = cases;
   }
}
