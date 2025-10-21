package app;

public class countryAndRegion {
   // Antigen column
   public String antigen;

   // Year Column
   public int year;

   // Country Column
   public String country;

   //Regioni Column
   public String region;

   // Vaccination coverage column
   public double percentage;

   /**
    * Create a movie withou setting any of the fields,
    * Fields will have default "empty" values
    */
   public countryAndRegion() {
   }

   /**
    * Create a movie setting all of the fields
    */
   public countryAndRegion(String antigen, int year, String country, double percentage, String region) {
      this.antigen = antigen;
      this.year = year;
      this.country = country;
      this.region = region;
      this.percentage = percentage;
   }
}
