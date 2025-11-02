package app;

public class countryAndRegion {
   // Antigen column
   public String antigen;

   // Year Column
   public String year;

   // Country Column
   public String country;

   //Regioni Column
   public String region;

   // Vaccination coverage column
   public String percentage;

   /**
    * Create a movie withou setting any of the fields,
    * Fields will have default "empty" values
    */
   public countryAndRegion() {
   }

   /**
    * Create a movie setting all of the fields
    */
   public countryAndRegion(String antigen, String year, String country, String percentage, String region) {
      this.antigen = antigen;
      this.year = year;
      this.country = country;
      this.region = region;
      this.percentage = percentage;
   }
}
