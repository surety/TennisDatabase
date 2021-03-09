
// CS-102 Kettering University, Fall 2020
// Zeus Polanco Salgado
package TennisDatabase; // This class has to be included in the TennisDatabase package
import java.util.*;
import java.io.*;

// Class implementing the manager for a tennis database.
public class TennisDatabase implements TennisDatabaseInterface {

  private TennisPlayerContainer tpc; //Internal container for tennis players.
  private TennisMatchContainer tmc; //Internal container for tennis matches.

  // Default constructor.
  public TennisDatabase(){
      this.tpc = new TennisPlayerContainer();
      this.tmc = new TennisMatchContainer();
  }

  // Desc.: Loads data from file following the format described in the specifications.
  // Output: Throws an unchecked (non-critical) exception if the loading is not fully successfull.
  //         Throws a checked (critical) exception if the file (file name) does not exist.
  public void loadFromFile( String fileName ) throws TennisDatabaseException, TennisDatabaseRuntimeException{
    try{
      File file = new File(fileName);
      Scanner input = new Scanner(file);

      while (input.hasNextLine()) {
        String line = input.nextLine();
        String[] parsedData = line.split("/");

        // Inserting match into TennisMatchContainer and TennisMatchContainer
        if (parsedData[0].equals("MATCH")){
          insertMatch(
            parsedData[1], parsedData[2], // Player 1 ID, Player 2 ID
            Integer.parseInt(parsedData[3].substring(0,4)), // Year
            Integer.parseInt(parsedData[3].substring(4,6)), // Month
            Integer.parseInt(parsedData[3].substring(6)), // Day
            parsedData[4], parsedData[5] // Tournament, Score
          );

        // Inserting player into TennisPlayerContainer
        }else if (parsedData[0].equals("PLAYER")){

          insertPlayer(
            parsedData[1], // ID
            parsedData[2], parsedData[3], // First Name, Last Name
            Integer.parseInt(parsedData[4]), // Birth Year
            parsedData[5] // Country
          );

      }}
      input.close();
    }catch(FileNotFoundException e){
      throw new TennisDatabaseException(String.format("\nError: File \"%s\" not found!",fileName));
    }catch(ArrayIndexOutOfBoundsException e){
      throw new TennisDatabaseRuntimeException("\nPartial line in input file. Incomplete data.");
    }catch(Exception e){
      throw new TennisDatabaseException("Error: Could not load data.");
    }
  }

  // Desc.: Search for a player in the database by input id, and returns a copy of that player (if found).
  // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
  public TennisPlayer getPlayer( String id ) throws TennisDatabaseRuntimeException{
    try{ return tpc.getPlayer( id ); }
    catch( TennisDatabaseRuntimeException e ){
      throw new TennisDatabaseRuntimeException(String.format("Error: Player id \"%s\" not found in TennisPlayerContainer.", id));
    }
  }

  // Desc.: Returns copies (deep copies) of all players in the database arranged in the output array (sorted by id, alphabetically).
  // Output: Throws an unchecked (non-critical) exception if there are no players in the database.
  public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException{
    return this.tpc.getAllPlayers();
  }

  // Desc.: Returns copies (deep copies) of all matches of input player (id) arranged in the output array (sorted by date, most recent first).
  // Input: The id of a player.
  // Output: Throws a checked (critical) exception if the player (id) does not exists.
  //         Throws an unchecked (non-critical) exception if there are no matches (but the player id exists).
  public TennisMatch[] getMatchesOfPlayer( String playerId  ) throws TennisDatabaseException, TennisDatabaseRuntimeException{
    // TODO:..
    return this.tpc.getMatchesOfPlayer( playerId );
  }

  // Desc.: Returns copies (deep copies) of all matches in the database arranged in the output array (sorted by date, most recent first).
  // Output: Throws an unchecked (non-critical) exception if there are no matches in the database.
  public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException{
    return this.tmc.getAllMatches();
  }

  // Desc.: Insert a player into the database.
  // Input: All the data required for a player.
  // Output: Throws a checked (critical) exception if player id is already in the database.
  public void insertPlayer( String id, String firstName, String lastName, int year, String country ) throws TennisDatabaseException{
    if(
      id == null ||
      firstName == null || lastName == null ||
      country == null
    ){
      throw new TennisDatabaseException("Error: Partial match information.");
    }

    TennisPlayer newPlayer = new TennisPlayer(id, year, firstName, lastName, country);
    try{
      this.tpc.insertPlayer(newPlayer);
    }
    catch(TennisDatabaseException e ){
      throw e;
    }
  }

  // Desc.: Insert a match into the database.
  // Input: All the data required for a match.
  // Output: Throws a checked (critical) exception if a player does not exist in the database.
  //         Throws a checked (critical) exception if the match score is not valid.
  public void insertMatch( String idPlayer1, String idPlayer2, int year, int month, int day, String tournament, String score ) throws TennisDatabaseException{
    try{
      if(
        idPlayer1 == null || idPlayer2 == null ||
        tournament == null || score == null
      ){
        throw new TennisDatabaseException(" Partial match information. ");
      }

      if( ( day > 31 ) || ( month > 12 ) ){
        throw new TennisDatabaseException("Error: Incorrect  date format. ") ;
      }

      TennisMatch newMatch = new TennisMatch(idPlayer1, idPlayer2, year, month, day, tournament, score);
      this.tmc.insertMatch(newMatch);
      this.tpc.insertMatch(newMatch);
    }
    catch(NumberFormatException e){
      throw new TennisDatabaseException("Error: Incorrect input format. Please try again.");
    }
    catch(ArrayIndexOutOfBoundsException e){
      throw new TennisDatabaseException("Error: Incorrect match input. Please try again.");
    }
  }

  // ----- Assignment2 additions -----

  public void saveToFile( String fileName ) throws TennisDatabaseException{
      File file = new File( String.format("%s.txt",fileName) );
      try{
        FileWriter fr = new FileWriter( file );
        TennisPlayerContainerIterator playerIterator = this.tpc.iterator();
        playerIterator.initPreorder();
        while( playerIterator.hasNext() ){
          TennisPlayer p = playerIterator.next();
          fr.write( String.format("PLAYER/%s/%s/%s/%d/%s\n",
            p.getId(), p.getFirstName(), p.getLastName(),
            p.getBirthYear(), p.getCountry()
          ));
        }
        Iterator<TennisMatch> matchIterator = this.tmc.iterator();
        while( matchIterator.hasNext() ){
          TennisMatch m = matchIterator.next();
          fr.write( String.format("MATCH/%s/%s/%d/%s/%s\n",
              m.getIdPlayer1(), m.getIdPlayer2(),
              (m.getDateYear() * 10000) + (m.getDateMonth() * 100) + (m.getDateDay()),
              m.getTournament(), m.getMatchScore()
          ));
        }
        fr.close();
      }catch(IOException e){
        throw new TennisDatabaseException("Error: Could not write file.");
      }
  }

  public void reset(){
    this.tmc.setMatchList( new LinkedList<TennisMatch>() );
    this.tpc.reset();
  }

  public void deletePlayer( String id ){
    this.tmc.deleteMatchesOfPlayer( id );
    this.tpc.deletePlayer( id );
  }

}
