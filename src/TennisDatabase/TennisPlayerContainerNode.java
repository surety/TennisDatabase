
package TennisDatabase;

// Class
class TennisPlayerContainerNode implements TennisPlayerContainerNodeInterface {

   private TennisPlayer player;
   private SortedLinkedList<TennisMatch> matchList;

   // TennisPlayerContainerNode children
   private TennisPlayerContainerNode left, right;

   // Constructor
   public TennisPlayerContainerNode( TennisPlayer p){
     setPlayer( p );
     setMatchList( new SortedLinkedList<TennisMatch>() );
   }

   // Accessors (getters).
   public TennisPlayer getPlayer(){ return this.player; }
   //public TennisPlayerContainerNode getPrev(){ return this.prev; }
   //public TennisPlayerContainerNode getNext(){ return this.next; }

   // Modifiers (setters).
   //public void setPrev( TennisPlayerContainerNode p ){ this.prev = p; }
   //public void setNext( TennisPlayerContainerNode n ){ this.next = n; }

   // Desc.: Insert a TennisMatch object (reference) into this node.
   // Input: A TennisMatch object (reference).
   // Output: Throws a checked (critical) exception if match cannot be inserted in this player list.
   public void insertMatch( TennisMatch m ) throws TennisDatabaseException{
     try{
       this.matchList.insert( m );
     }catch(Exception e){}
   }

   // Desc.: Returns all matches of this player arranged in the output array (sorted by date, most recent first).
   // Output: Throws an unchecked (non-critical) exception if there are no matches for this player.
   public TennisMatch[] getMatches() throws TennisDatabaseRuntimeException{
     TennisMatch[] temp = new TennisMatch[ matchList.size() ];
     for (int i = 0; i<matchList.size(); i++)
       temp[i] = matchList.get( i );
     return temp;
   }

   // ----- Assignment2 additions -----

   public TennisPlayerContainerNode getRightChild(){
     return this.right;
   }

   public TennisPlayerContainerNode getLeftChild(){
     return this.left;
   }

   public SortedLinkedList<TennisMatch> getMatchList(){
     return this.matchList;
   }

   public void setRightChild( TennisPlayerContainerNode newNode ){
     this.right = newNode;
   }

   public void setLeftChild( TennisPlayerContainerNode newNode ){
     this.left = newNode;
   }

   public void setMatchList( SortedLinkedList<TennisMatch> tmSLL ){
     this.matchList = tmSLL;
   }

   public void setPlayer( TennisPlayer p ){
     this.player = p;
   }

   public void deleteMatchesOfPlayer( String id ){
     for( int i=0; i < this.matchList.size(); i++ )
       if( this.matchList.get(i).getIdPlayer1().equals(id) || this.matchList.get(i).getIdPlayer2().equals(id)){
         this.matchList.delete(i);
         i--;
       }
   }

}
