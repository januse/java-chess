java-chess
==========
This is a command-line chess game.  The game is fully playable.  

Next Steps:
* The interface into the board should be simplified and secured.
* The state of the board should be displayed after each move.

###Getting Started: Play Chess on the Command Line

Make sure you have the java and the java compiler installed.  The easiest way to do this may be to [install the jdk](http://docs.oracle.com/javase/7/docs/webnotes/install/).

####Compile

The main method is in the game/Game.java source file.  Compile this file from the src directory.  From the base directory simply run:

```
cd src/main/java
javac game/Game.java
```

####Run

You can now run the game from the same directory with: 

```java game.Game```

###Write Chess Bots

java-chess is designed to make it easy to write your own chess bots.  There is currently a skeletal implementation of a chess bot in the RobotPlayer class in the game package.  Simply change the logic in the `Move makeNextMove(Board board)` and `PieceType getTypeForPromotion(Board board)` methods, recompile, and you'll be playing against your own custom bot (or watching your bot play itself).  

Alternatively, extend the abstract Player class in the game directory, and then alter the main method to use your new chess bot whenever you'd like it to.  Your constructor should take a Color, which it can just pass through to its Parent constructor.  It will also have to implement the two methods mentioned above.

####The Board Class

A Board object gets passed to your bot whenever it needs to make a move or decide what piece to promote to.  That object has a few methods you may find useful in writing your bot logic:

**List\<Move\> getAllLegalMovesByColor(Color color)**

This will return a list of legal moves for the color passed as an argument, given the current state of the board.

**boolean makeMove(Move move)**

This will attempt alter the state of the board by making the move passed as an argument.  If the move is not legal, the state of the board will be unchanged and the method will return false.  Otherwise, the state of the board will be changed and the method will return true.

You can use this method, in combination with the undoMove method on Move objects (documented below) to explore game trees and look ahead into the game.

####The Move Class

A move object represents one move in the game. You can get the moves that have been played on any given board with 

`board.moves;`

and the moves that are possible on a board for a given color with

`board.getAllLegalMovesByColor(color);`

**void undoMove()**

This will change the state of the board on which the move was made to undo that move.  This should only be called on the most recent move played on any given board - otherwise you're likely to put the board into an inconsistent state.

**There are also a number of methods that will give you information about the move.**  
Your bot can use these to help it decide which move to make.

**boolean isLegal()**

Returns true if move is legal, false otherwise.

**boolean isCheck()**

Returns true if move puts opponent in check, false otherwise.

**boolean isCheckMate()**

Returns true if move checkmates opponent, false otherwise.

**boolean isATake()**

Returns true if move takes opponent's piece, false otherwise.

**getStartPosition(), getEndPosition()**

Return the start and end squares of the move, respectively.
