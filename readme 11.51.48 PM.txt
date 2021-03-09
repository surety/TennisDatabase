CS 102 - Assignment2 Fall 2020
Zeus Polanco Salgado
pola5392@kettering.edu

TennisMatchContainer list choice: LinkedList
The reason why I chose LinkedLists for this project is because of the deletePlayer
function. When a player is deleted, the matches corresponding to that player also have
to be deleted from TennisMatchContainer. When removing an object from ArrayList, there is
a shift to cover up holes on the internal list while the LinkedList class just
changes the references which take up less time than shifting objects. A less technical
reason is that I had never worked with JCF LinkedLists so I thought it would be good
practice.
