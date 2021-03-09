// Functionality COMPLETE
// TODO: commentary

package TennisDatabase;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;

// Sorted array-based list of tennis matches (implements TennisMatchContainerInterface)
class TennisMatchContainer implements TennisMatchContainerInterface{

  //Internal LinkedList implemented to store TennisMatch objects.
  private LinkedList<TennisMatch> matchList;

  // Default constructor.
  public TennisMatchContainer(){
    // Init internal array and fields, representing an empty sorted list.
    setMatchList( new LinkedList<TennisMatch>() );
  }

  // Desc.: Insert a tennis match into this container.
  // Input: A tennis match.
  // Output: Throws a checked (critical) exception if the container is full.
  public void insertMatch( TennisMatch m ) throws TennisDatabaseException{
    this.matchList.add( m );
    Collections.sort( this.matchList );
  }

  // Desc.: Returns all matches in the database arranged in the output array (sorted by date, most recent first).
  // Output: Throws an exception if there are no matches in this container.
  public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException{
    if (this.matchList.size() == 0)
      throw new TennisDatabaseRuntimeException("Error: Database does not have matches.");
    TennisMatch[] temp = new TennisMatch[ this.matchList.size() ];
    ListIterator<TennisMatch> listIter = this.matchList.listIterator(0);
    for(int i=0; listIter.hasNext(); i++)
      temp[i] = listIter.next();
    return temp;
   }

  // Desc.: Returns all matches of input player (id) arranged in the output array (sorted by date, most recent first).
  // Input: The id of the tennis player.
  // Output: Throws an unchecked (non-critical) exception if there are no tennis matches in the list.
  public TennisMatch[] getMatchesOfPlayer( String playerId ) throws TennisDatabaseRuntimeException{
    LinkedList<TennisMatch> tmList = new LinkedList<TennisMatch>();
    ListIterator<TennisMatch> iter = this.matchList.listIterator( 0 );
    TennisMatch[] playerMatches;

    // Collecting all player matches.
    while( iter.hasNext() ){
      TennisMatch tm = iter.next();
      if( tm.getIdPlayer1().equals(playerId) || tm.getIdPlayer2().equals(playerId) )
        tmList.add( tm );
    }

    // Converting player matches to array.
    playerMatches = new TennisMatch[tmList.size()];
    for(int i=0; !tmList.isEmpty(); i++ )
      playerMatches[i] = tmList.poll();

    return playerMatches;
  }

  // ----- Assignment2 additions -----

  public Iterator<TennisMatch> iterator(){
    return this.matchList.listIterator( 0 );
  }

  public int getNumMatches(){
    return this.matchList.size();
  }

  public void deleteMatchesOfPlayer( String id ){
    // Regular for loop used bc iterator can not be used while list is being modified.
    for( int i=0; i < this.matchList.size(); i++ )
      if( this.matchList.get(i).getIdPlayer1().equals(id) || this.matchList.get(i).getIdPlayer2().equals(id)){
        this.matchList.remove(i);
        i--;
      }

  }

  public void setMatchList(LinkedList<TennisMatch> newMatchList){
    this.matchList = newMatchList;
  }

}
