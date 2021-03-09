// TODO: Player match list.


// Giuseppe Turini
// CS-102, Fall 2020
// Assignment 1

package TennisDatabase;
import java.util.Iterator;

// Interface (package-private) providing the specifications for the TennisPlayerContainer class.
class TennisPlayerContainer implements TennisPlayerContainerInterface{

   private TennisPlayerContainerNode root;
   private int numPlayers;

   // Desc.: Search for a player in this container by input id, and returns a copy of that player (if found).
   // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
   public TennisPlayer getPlayer( String id ) throws TennisDatabaseRuntimeException{
     return getNodeById( id ).getPlayer();
   }

   // Desc.: Insert a tennis player into this container.
   // Input: A tennis player.
   // Output: Throws a checked (critical) exception if player id is already in this container.
   public void insertPlayer( TennisPlayer p ) throws TennisDatabaseException{
       TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(p);
       // If list is empty.
       if( this.root == null ) {
        this.root = newNode;
        this.numPlayers++;
      }
       // When list is not empty.
       else{
           try{
             insertPlayerRec(newNode, this.root);
             this.numPlayers++;
           }
           catch( TennisDatabaseException e ){ throw e; }
       }
  }
  private void insertPlayerRec(
    TennisPlayerContainerNode newNode, TennisPlayerContainerNode currNode
  ) throws TennisDatabaseException{
    try{
      if( newNode.getPlayer().compareTo( currNode.getPlayer() ) > 0 ){
          if( currNode.getRightChild() == null )
              currNode.setRightChild( newNode );
          else
              insertPlayerRec( newNode, currNode.getRightChild() );
      }
      else if( newNode.getPlayer().compareTo( currNode.getPlayer() ) < 0 ){
          if( currNode.getLeftChild() == null )
              currNode.setLeftChild( newNode );
          else
            insertPlayerRec( newNode, currNode.getLeftChild() );
      }
      else
        throw new TennisDatabaseException("Error: Player with that id already exists in database.");
    }catch( TennisDatabaseException e ){ throw e; }
  }

   // Desc.: Insert a tennis match into the lists of both tennis players of the input match.
   // Input: A tennis match.
   // Output: Throws a checked (critical) exception if the insertion is not fully successful.
   // WARNING: Older version
   public void insertMatch( TennisMatch m ) throws TennisDatabaseException{
      try{
        String pId1 = m.getIdPlayer1();
        String pId2 = m.getIdPlayer2();
        TennisPlayerContainerNode pcn1 = getNodeById( pId1 );
        TennisPlayerContainerNode pcn2 = getNodeById( pId2 );
        pcn1.insertMatch( m );
        pcn2.insertMatch( m );

      // Throws error when player is not found.
      }catch( NullPointerException e ){
        throw new TennisDatabaseException("Error: Player id incorrect or does not exist.");
      }
   }

   // Desc.: Returns all players in the database arranged in the output array (sorted by id, alphabetically).
   // Output: Throws an unchecked (non-critical) exception if there are no players in this container.
   // WARNING: Older version
   public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException{
     if ( this.numPlayers == 0 )
      throw new TennisDatabaseRuntimeException( "Error: No players found in container. " );

     TennisPlayer[] temp = new TennisPlayer[ this.numPlayers ];
     TennisPlayerContainerIterator iter = new TennisPlayerContainerIterator( this.root );
     iter.initInorder();

     for(int i=0; i<this.numPlayers; i++ )
       temp[i] = iter.next();
     return temp;
   }

   // Desc.: Returns copies (deep copies) of all matches of input player (id) arranged in the output array (sorted by date, most recent first).
   // Input: The id of a player.
   // Output: Throws a checked (critical) exception if the player (id) does not exists.
   //         Throws an unchecked (non-critical) exception if there are no matches (but the player id exists).
   // WARNING: Older version
   public TennisMatch[] getMatchesOfPlayer( String playerId  ) throws TennisDatabaseException, TennisDatabaseRuntimeException{
     return getNodeById( playerId ).getMatches();
   }

   private TennisPlayerContainerNode getNodeById( String id ){
     TennisPlayerContainerNode playerNode = null;
     TennisPlayerContainerNode currNode = this.root;
     while( playerNode == null ){
       if( currNode == null )
         throw new TennisDatabaseRuntimeException("Error: Could not find player.");
       else if( id.equals( currNode.getPlayer().getId() ) )
         playerNode = currNode;
       else if( id.compareTo( currNode.getPlayer().getId() )  > 0 )
         currNode = currNode.getRightChild();
       else if( id.compareTo( currNode.getPlayer().getId() )  < 0 )
         currNode = currNode.getLeftChild();
     }
     return playerNode;
   }

   // ----- Assignment2 additions -----

   public int getNumPlayers(){ return this.numPlayers; }

   public void deletePlayer( String id ){
     deleteMatchesOfPlayer( id );
     this.root = deletePlayerRec( this.root, getNodeById( id ) );
     this.numPlayers--;
   }
   private TennisPlayerContainerNode deletePlayerRec(  TennisPlayerContainerNode currNode, TennisPlayerContainerNode TARGET ){
     if( currNode == null ) return null;

     // Finding parent of TARGET
     if (TARGET.getPlayer().compareTo( currNode.getPlayer() ) < 0)
      currNode.setLeftChild( deletePlayerRec(currNode.getLeftChild(), TARGET) );
     else if (TARGET.getPlayer().compareTo( currNode.getPlayer() ) > 0)
      currNode.setRightChild( deletePlayerRec(currNode.getRightChild(), TARGET) );
     // Replacing TARGET node
     else{
       if( currNode.getLeftChild() == null && currNode.getRightChild() == null )
        return null;
       else if( currNode.getLeftChild() == null )
        return currNode.getRightChild();
       else if( currNode.getRightChild() == null )
        return currNode.getLeftChild();
       else{
        TennisPlayerContainerNode minNode = getMinNode( currNode.getRightChild() );
        currNode.setPlayer( minNode.getPlayer() );
        currNode.setMatchList( minNode.getMatchList() );
        currNode.setRightChild( deletePlayerRec( currNode.getRightChild(), minNode )  );
       }
     }
     return currNode;
   }
   private TennisPlayerContainerNode getMinNode( TennisPlayerContainerNode currNode ){
     if(currNode.getLeftChild() == null)
      return currNode;
    return getMinNode(currNode.getLeftChild());
   }

   public void deleteMatchesOfPlayer( String id ){
     TennisPlayerContainerIterator playerIterator = iterator();
     playerIterator.initInorder();
     while( playerIterator.hasNext() )
      getNodeById( playerIterator.next().getId() ).deleteMatchesOfPlayer( id );
   }

   public TennisPlayerContainerIterator iterator(){
     return new TennisPlayerContainerIterator( this.root );
   }

   public void reset(){
     this.root = null;
     this.numPlayers = 0;
   }

}
