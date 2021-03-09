// Functionality COMPLETE
// TODO: commentary

package TennisDatabase;

public class TennisMatch implements TennisMatchInterface{
  private String idPlayer1, idPlayer2, tournament, matchScore;
  private int dateYear, dateMonth, dateDay, winner;

  // TennisMatch constructor.
  public TennisMatch(
    String idPlayer1, String idPlayer2, int dateYear, int dateMonth,
    int dateDay, String tournament, String matchScore
    ){

    this.idPlayer1 = idPlayer1;
    this.idPlayer2 = idPlayer2;
    this.dateYear = dateYear;
    this.dateMonth = dateMonth;
    this.dateDay = dateDay;
    this.tournament = tournament;
    this.matchScore = matchScore;

    computeWinner();

  }

  private void computeWinner(){
    int pointage = 0;

    String[] scores = matchScore.split(",");
    for( String score : scores ){
      String[] appendedScore = score.split("-");
      if( Integer.parseInt(appendedScore[0]) > Integer.parseInt(appendedScore[1]) )
        pointage++;
      else if( Integer.parseInt(appendedScore[0]) < Integer.parseInt(appendedScore[1]) )
        pointage--;
    }

    // When pointage is positive, then player 1 is winner.
    if(pointage > 0)
      this.winner = 1;
    // When pointage is negative, then player 2 is winner.
    else if(pointage < 0)
      this.winner = 2;
    // When pointage is 0, then there is no winner.
    else
      this.winner = 0;

  }

  // YYYYMMDD
  // Compares tennis matches (by date)
  public int compareTo(TennisMatch p){
    int matchDate = 0, otherMatchDate = 0;
    matchDate += (this.dateYear * 10000) + (this.dateMonth * 100) + (this.dateDay);
    otherMatchDate += (p.getDateYear() * 10000) + (p.getDateMonth() * 100) + (p.getDateDay());

    return matchDate - otherMatchDate;
  }

  // TennisMatch getters.
  public String getIdPlayer1(){ return this.idPlayer1; }
  public String getIdPlayer2(){ return this.idPlayer2; }
  public int getDateYear(){ return this.dateYear; }
  public int getDateMonth(){ return this.dateMonth; }
  public int getDateDay(){ return this.dateDay; }
  public String getTournament(){ return this.tournament; }
  public String getMatchScore(){ return this.matchScore; }
  public int getWinner(){ return this.winner; }

}
