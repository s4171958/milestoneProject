package app;


public class Personasdata {
  
   public String name;

  
   public String occupation;

 
   public int age;

   
   public String gender;

   public String location;

   public String bio;

   public String goals;
   
   public String needs;
   
   public Personasdata() {
   }

 
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
