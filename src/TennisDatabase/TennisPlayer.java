// Functionality COMPLETE
// TODO: commentary

package TennisDatabase;

public class TennisPlayer implements TennisPlayerInterface{
  private int birthYear;
  private String id, firstName, lastName, country;

  // TennisPlayer builder.
  public TennisPlayer(String id, int birthYear, String firstName, String lastName, String country){
    this.id = id;
    this.birthYear = birthYear;
    this.firstName = firstName;
    this.lastName = lastName;
    this.country = country;
  }

  public int compareTo(TennisPlayer p){
    // Comparing the id of this player to input player id
    return this.id.compareTo( p.id );
  }

  // TennisPlayer getters.
  public String getId(){ return id; }
  public String getFirstName(){ return firstName; }
  public String getLastName(){ return lastName; }
  public int getBirthYear(){ return birthYear; }
  public String getCountry(){ return country; }

}
