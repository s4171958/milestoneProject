package app;

public class Infectionrates {
  

   public String infection;

   
   public String country;


   public int year;

   public double cases;
 
   public Infectionrates() {
   }

 
   public Infectionrates(String infection, String country, int year, int cases) {
      this.infection = infection;
      this.country = country;
      this.year = year;
      this.cases = cases;
   }
}


