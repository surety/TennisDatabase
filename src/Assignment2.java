
// CS-102 Kettering University, Fall 2020
// Zeus Polanco Salgado

import java.util.*;
import java.io.File;
import TennisDatabase.*;

// Assignment 2 class to manage user input-output
public class Assignment2 {
  static TennisDatabase tdb;
  // Main function ( entry point ) of our Assignment 1 program for CS-102
  public static void main( String[] args ){
    try{
      String fileName;
      tdb = new TennisDatabase();
      fileName = args[0];
      tdb.loadFromFile( fileName );
    }
    catch(TennisDatabaseRuntimeException e){
      System.out.println(e.getMessage());
    }
    catch(TennisDatabaseException e){
      System.out.println(e.getMessage());
    }
    catch(ArrayIndexOutOfBoundsException e){
      System.err.println("Error: No arguments.");
      System.err.println("Exiting program... ");
      System.exit(0);
    }

    commandLine("help");
  }

  // Manages command line and execution of commands.
  private static void commandLine(String command){
    // Swtich block executes command.
    switch(command){
      case "printAllPlayers" : printAllPlayers();
      break;
      case "printPlayerMatches" : printPlayerMatches();
      break;
      case "printAllMatches" : printAllMatches();
      break;
      case "insertPlayer" : insertInputPlayer();
      break;
      case "insertMatch" : insertInputMatch();
      break;
      case "printPlayer": printPlayer();
      break;
      case "deletePlayer": deletePlayer();
      break;
      case "resetDatabase": resetDatabase();
      break;
      case "importFromFile": importFromFile();
      break;
      case "exportToFile": exportToFile();
      break;
      case "help" : help();
      break;
      case "exit" : exit();
      default : System.out.format("\n \"%s\" is not listed as a command. Please try again.",command);
    }

    // Accepts new command after previous is executed.
    Scanner scan = new Scanner( System.in );
    System.out.print("\nEnter command --> ");
    command = scan.nextLine();

    commandLine(command);

  }

  // Prints out all matches stored in match container.
  private static void printAllPlayers (){
    try{
      TennisPlayer[] allPlayers = tdb.getAllPlayers();
      if( allPlayers != null ){
        for( TennisPlayer p : allPlayers ){
          // Compute Win/Loss record of player
          int wins = 0, losses = 0;
          TennisMatch[] playerMatches = tdb.getMatchesOfPlayer( p.getId() );
          for( TennisMatch m : playerMatches ){
            int player = 2;
            if( m.getIdPlayer1().equals( p.getId() ) )
              player = 1;

            if( m.getWinner() == player )
              wins++;
            else
              losses++;
          }// ENF OF MATCH FOR

          System.out.format("\n%s: %s %s",
            p.getId(), p.getFirstName(), p.getLastName());
          System.out.format("\nBorn on %d, %s",
            p.getBirthYear(), p.getCountry());
          System.out.format("\nWin/Loss Record: %d/%d",
            wins, losses);
          System.out.println();
        }// END OF PLAYER FOR
      } // END OF IF
      else
        System.out.print("\nNo players in database.");

    } // END OF TRY
    catch(TennisDatabaseRuntimeException e){System.out.println(e.getMessage());}
    catch(TennisDatabaseException e ){System.out.println(e.getMessage());}

  }

  // Prints out all matches for a player.
  private static void printPlayerMatches(){
    String id;
    Scanner scan = new Scanner( System.in );
    System.out.print("\nEnter player id --> ");
    id = scan.nextLine();

    try{
      TennisMatch[] playerMatches = tdb.getMatchesOfPlayer( id );
      printMatches( playerMatches );
    }catch(TennisDatabaseRuntimeException e){
      System.out.println(e.getMessage());
    }catch(TennisDatabaseException e){
      System.out.println(e.getMessage());
    }
  }

  // Prints out all matches stored in match container.
  private static void printAllMatches(){
    try{
      TennisMatch[] allMatches = tdb.getAllMatches();
      printMatches( allMatches );
    }catch(TennisDatabaseRuntimeException e){
      System.out.println(e.getMessage());
    }

  }

  private static void printMatches( TennisMatch[] allMatches ){
    try{
      if( allMatches != null )
        for( TennisMatch m : allMatches ){

          String winner;
          switch( m.getWinner() ){
            case 1 : winner = tdb.getPlayer( m.getIdPlayer1() ).getFirstName();
            break;
            case 2 : winner = tdb.getPlayer( m.getIdPlayer2() ).getFirstName();
            break;
            default :;
            case 0 : winner = "Tie!";
          }

          System.out.format("\n%s, %s vs %s, %d/%d/%d,",
            m.getTournament(),
            tdb.getPlayer( m.getIdPlayer1() ).getFirstName(),
            tdb.getPlayer( m.getIdPlayer2() ).getFirstName(),
            m.getDateMonth(), m.getDateDay(), m.getDateYear() );
          System.out.format("\n%s, Winner: %s",
            m.getMatchScore(), winner );
          System.out.println();
        }
        else
          System.out.println("Error: TennisDatabase has no matches.");
      }catch(TennisDatabaseRuntimeException re){
        System.err.print("Unable to print matches.");
      }
  }

  private static void insertInputPlayer(){
      try{
        TennisMatch newPlayer;
        String id, firstName, lastName, country;
        int birthYear;
        Scanner scan = new Scanner( System.in );

        System.out.print("\nEnter player first name --> ");
        firstName = scan.nextLine();
        System.out.print("\nEnter player last name --> ");
        lastName = scan.nextLine();
        System.out.print("\nEnter player country --> ");
        country = scan.nextLine();
        System.out.print("\nEnter player birth year --> ");
        birthYear = scan.nextInt();

        id = lastName.substring(0, 3).toUpperCase();
        id += String.valueOf(birthYear).substring(2);

        tdb.insertPlayer(id, firstName, lastName, birthYear, country);

      }
      catch( TennisDatabaseException e){}
      catch(InputMismatchException e){
          System.err.println("Error: Incorrect data input. Please try again.");
      }
    }

  private static void insertInputMatch(){

    try{
      TennisMatch newMatch;
      String idPlayer1, idPlayer2, tournament, matchScore, date;
      int dateYear, dateMonth, dateDay;
      Scanner scan = new Scanner( System.in );

      System.out.print("\nEnter tournament name --> ");
      tournament = scan.nextLine();
      System.out.print("\nEnter player 1 id --> ");
      idPlayer1 = scan.nextLine();
      System.out.print("\nEnter player 2 id --> ");
      idPlayer2 = scan.nextLine();
      System.out.print("\nEnter match score (N-N,N-N,...,N-N) --> ");
      matchScore = scan.nextLine();
      System.out.print("\nEnter match date (YYYY/MM/DD) --> ");
      date = scan.nextLine();

      dateYear = Integer.parseInt(date.substring(0,4)); // Year
      dateMonth = Integer.parseInt(date.substring(5,7)); // Month
      dateDay = Integer.parseInt(date.substring(8)); // Day

      tdb.insertMatch(idPlayer1, idPlayer2, dateYear, dateMonth, dateDay, tournament, matchScore);
    }
    catch(TennisDatabaseException e){
      System.out.println(e.getMessage());
    }
    catch(NumberFormatException e){
      System.err.println("Error: Incorrect data input. Please try again.");
    }
    catch(TennisDatabaseRuntimeException e){
      System.out.println(e.getMessage());
    }
  }

  private static void printPlayer(){

    String id;
    int wins = 0, losses = 0;
    Scanner scan = new Scanner( System.in );
    System.out.print("\nEnter player id --> ");
    id = scan.nextLine();
    try{

      TennisPlayer p = tdb.getPlayer( id );
      TennisMatch[] playerMatches = tdb.getMatchesOfPlayer( p.getId() );
      for( TennisMatch m : playerMatches ){
        if( m.getWinner() == 1 )
          wins++;
        else if( m.getWinner() == 2 )
          losses++;
      }

      System.out.format("\n%s: %s %s",
        p.getId(), p.getFirstName(), p.getLastName());
      System.out.format("\nBorn on %d, %s",
        p.getBirthYear(), p.getCountry());
      System.out.format("\nWin/Loss Record: %d/%d",
        wins, losses);
      System.out.println();
    }catch(TennisDatabaseException e){
      System.out.println(e.getMessage());
    }catch(TennisDatabaseRuntimeException e){
      System.out.println(e.getMessage());
    }

  }

  private static void deletePlayer(){
    String id;
    Scanner scan = new Scanner( System.in );
    System.out.print("\nEnter player id --> ");
    id = scan.nextLine();
    tdb.deletePlayer( id );
  }

  private static void resetDatabase(){
    tdb.reset();
  }

  private static void importFromFile(){
    System.out.println("Loading data from file...");
    Scanner scan = new Scanner( System.in );
    String fileName;
    System.out.print("\nEnter file name --> ");
    fileName = scan.nextLine();
    try{ tdb.loadFromFile( fileName ); }
    catch(TennisDatabaseException e){
      System.out.println( e.getMessage() );
    }
  }

  private static void exportToFile(){
    Scanner scan = new Scanner( System.in );
    String fileName;
    System.out.print("\nEnter file name --> ");
    fileName = scan.nextLine();
    File file = new File( String.format("%s.txt",fileName) );
    if( file.exists() )
      System.out.println("File already exists. Overwritting file...");
    try{
      tdb.saveToFile( fileName );
    }catch(TennisDatabaseException e){
      System.out.println(e.getMessage());
    }catch(TennisDatabaseRuntimeException e){
    System.out.println(e.getMessage());
    }
  }

  // Prints out list of commands.
  private static void help(){

    System.out.print("\nList of Commands:"
      + "\nhelp - Prints out list of commands."
      + "\nexit - Safely exits program."
      + "\nprintAllPlayers - Prints all stored players in TennisDatabase."
      + "\nprintPlayerMatches - Prints matches with specefic player recorded."
      + "\nprintAllMatches - Prints all stored matches in TennisDatabase."
      + "\ninsertPlayer - Allows you to insert new player data."
      + "\ninsertMatch - Allows you to insert new match data."
      + "\nprintPlayer - Prints single stored player in TennisDatabase."
      + "\ndeletePlayer - Deletes single player in TennisDatabase."
      + "\nresetDatabase - Resets entire TennisDatabase."
      + "\nimportFromFile - Loads data from saved file into TennisDatabase."
      + "\nexportToFile - Exports entire data from TennisDatabase in to a file."
      + "\n"
      );
  }

  // Safely exits program.
  private static void exit(){
    System.exit(0);
  }

}
