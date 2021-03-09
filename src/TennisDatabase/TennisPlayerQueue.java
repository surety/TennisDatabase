package TennisDatabase;

//TODO Resize array when full
class TennisPlayerQueue implements TennisPlayerQueueInterface {
   private int size, front, back;
   private TennisPlayer[] playerList;

   public TennisPlayerQueue(){
     this.size = this.front = this.back = 0;
     this.playerList = new TennisPlayer[ 50 ];
   }

   // Desc.: Check if the queue is empty.
   // Output: True or false.
   public boolean isEmpty(){ return this.size == 0 ;}

   // Desc.: Insert a tennis player at the back of this queue.
   // Input: A tennis player.
   // Output: Throws a checked (critical) exception if the insertion fails.
   public void enqueue( TennisPlayer p ) throws TennisDatabaseException{
     try{
       // Resizing queue
       if( this.size == this.playerList.length ){
         // Here internal array is full, resize it.
         int newLength = this.playerList.length * 2;
         TennisPlayer[] temp = new TennisPlayer[ newLength ];

         for( int i=0; i<this.playerList.length; i++)
          temp[i] = this.playerList[i];

          this.playerList = temp;
        }

        this.playerList[ this.back ] = p;
        this.back++;
        this.size++;
      }catch(Exception e){
        throw new TennisDatabaseException("Error: Could not enqueue player.");
      }
   }

   // Desc.: Extract (return and remove) a tennis player from the front of this queue.
   // Output: Throws a checked (critical) exception if the extraction fails.
   public TennisPlayer dequeue() throws TennisDatabaseException{
     if( isEmpty() )
      throw new TennisDatabaseException("Queue empty.");

     int pos = this.front;
     this.front = (this.front + 1) % this.playerList.length;
     this.size--;
     return this.playerList[ pos ];
   }

   // Desc.: Return (without removing) the tennis player at the front of this queue.
   // Output: Throws a checked (critical) exception if the queue is empty.
   public TennisPlayer peek() throws TennisDatabaseException{
     if( isEmpty() )
      throw new TennisDatabaseException("Queue empty.");

     return this.playerList[ this.front ];
   }

}
