package app;


public class Infectiondata {

   public String infection;

   
   public String country;


   public int year;

   public String ecophase;

   public int cases;
 
   public Infectiondata() {
   }

 
   public Infectiondata(String infection, String country, int year, String ecophase, int cases) {
      this.infection = infection;
      this.country = country;
      this.year = year;
      this.ecophase = ecophase;
      this.cases = cases;
   }
}
