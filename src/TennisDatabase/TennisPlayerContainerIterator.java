
package TennisDatabase;

import java.util.Iterator;

class TennisPlayerContainerIterator implements Iterator<TennisPlayer> {

  private TennisPlayerQueue q;
  private TennisPlayerContainerNode root;

  public TennisPlayerContainerIterator( TennisPlayerContainerNode r ){
    this.q = null;
    this.root = r;
  }

  public void initPreorder(){
    this.q = new TennisPlayerQueue();
    preorderRec( this.root );
  }

  public void initInorder(){
    this.q = new TennisPlayerQueue();
    inorderRec( this.root );
  }

  public void initPostorder(){
    this.q = new TennisPlayerQueue();
    inorderRec( this.root );
  }

  private void preorderRec( TennisPlayerContainerNode currNode ){
    if( currNode != null ){
      try{this.q.enqueue( currNode.getPlayer() );} // Current
      catch(TennisDatabaseException e){
        System.err.println("Error(TennisPlayerQueue): Could not enqueue players.");
      }
      inorderRec( currNode.getLeftChild() ); // Left most
      inorderRec( currNode.getRightChild() ); // Right most
    }
  }


  private void inorderRec( TennisPlayerContainerNode currNode ){
    if( currNode != null ){
      inorderRec( currNode.getLeftChild() ); // Left most
      try{this.q.enqueue( currNode.getPlayer() );} // Current
      catch(TennisDatabaseException e){
        System.err.println("Error(TennisPlayerQueue): Could not enqueue players.");
      }
      inorderRec( currNode.getRightChild() ); // Right most
    }
  }

private void postorderRec( TennisPlayerContainerNode currNode ){
  if( currNode != null ){
    inorderRec( currNode.getLeftChild() ); // Left most
    inorderRec( currNode.getRightChild() ); // Right most
    try{this.q.enqueue( currNode.getPlayer() );} // Current
    catch(TennisDatabaseException e){
      System.err.println("Error(TennisPlayerQueue): Could not enqueue players.");
    }
  }
}

  public boolean hasNext(){
    return !this.q.isEmpty();
  }

  public TennisPlayer next(){
    try{ return q.dequeue(); }
    catch( TennisDatabaseException e ){ return null; }
  }
}
