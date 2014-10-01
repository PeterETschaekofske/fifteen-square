import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
/*Peter Tschaekofske
 * EECS 1510 
 * Dr. Thomas 5/1/2014
 *The purpose of the project is to create the game fifteeen square. We are to use the techniques and methods that we learned from chapters 8-19 to do a whole number of things
 *first we need to create the board using a GUI that will create the board, the option buttons, and the frame that will contain the whole game.
 * the next thing we need is to actually make the buttons do something when they are clicked. We then want each of the buttons to have full functionality
 * and do what we need them to do when clicked. The board pieces need to move, the save and load need to save a current game you want and then load that saved
 * game later. We also want the help button to display a message that will assist the user on the basics behind the game. After we get functionality in the buttons
 * we have to make sure that the game knows when the user has won, and it can offer the option to play again, or they can leave the program and be done with 
 * the game entirely if the want to.  
*/ 
public class FiffteenSquare extends JFrame

{ // important variables needed for the program 
  // the variables that are static is that they will used as static references later in the program. 	
  // create an array of buttons for the board pieces 
  // these important variables and objects are needed to be accessed later in the program. so we need to make them
  // data fields so they can be used throughout the entire class that they are declared in.
  //in this case, they will be used throughout our entire fifteen square class.
   static JButton [][] buttons = new JButton [4][4]; // our double array for our board pieces
   private final int SIZE_X = (75);// a constant declared to use for the size of the board pieces
   private final int SIZE_Y = (70);// another constant used to for the vertical size of the board pieces
    int  CR    = 0;// we create a data field for our clicked piece so that it can be used in all of the move mehtods
    int  CC    = 0;// and along with the Listener we will use that will find the source of the clicked button
                   // and then store the row and column of the clicked board piece into these two variables.
                   // CR = Clicked Row , CC = Clicked Column 
    static int  ZR    = 3;//for the same purposes as creating data fields for the clicked piece, we also need 2  						
    static int  ZC    = 3;//data fields to store the location of the Zero Piece that is our blank tile.
    					  // we will have a method that will find the location of the Zero "blank" piece, and then
    					  // store the row = ZR and the column = ZC into these two data fields
    static int  moves = 0;// we need a data field to keep trak of the moves that are taken in the current game. we will save the value of them here
    int  wins  = 0; // we need a data field to keep track of the the number of wins that the user has from not the current game but from previous games as well.
   static boolean isShuffle ; // we need to use a boolean data field as a check to make sure that we are not checking for a winner until we are done shuffling the board.
   static JLabel Moves = new JLabel ("Moves  " +  moves); // creating a data field for the Jlabel of our moves counter so that we can use them in other methods. 
   static int Games  = 0;// this is used as a counter to keep track of the games that have been played so far.
   static JLabel GamesPlayed = new JLabel ("Games " + Games);// this is the label that is used to display the current amount of games played. it needs to be a data field so that we can use it our save,load, and winner method
   JLabel WinGames = new JLabel("Games Won " + wins);// this is the Jlabel that is used to display the amount of games won. for the same reasons as Games and Moves Jlabel it needs to be a data field.
   
// Now we need to create a constructor for our GUI. Which will create the frame, the listeners, the buttons and arrange them all in appropriate size and location   
	public FiffteenSquare ()
	
{    setLayout(null);// so that we don't use the default (border) layout. 
	 setSize (297,500);// sets the size of the JFrame
	 setLocationRelativeTo(null);// so that the location of the frame is set to the middle of the screeen
	 setResizable(false);// so that the user cannot expand or compress the size of the frame
	 
	 // create the listeners that the buttons will use to respond to events 
	 BoardListener listener1 = new BoardListener ();// for the fifteen puzzle board
	 HelpListener  listener2 = new HelpListener  ();// for the Help button
	 SaveListener  listener3 = new SaveListener  ();// for the Save button
	 LoadListener  listener4 = new LoadListener  ();// for the Load button
	 ExitListener  listener5 = new ExitListener  ();// for the exit Button
	 NewGameListener listener6 = new NewGameListener ();// for the New Game button
	// NewGameListener listener6 = new NewGameListener ();
	 // we will now use a nested for to go through our array of JButtons that we created as a data field and 
	 // set their text, size and location. we must go through all 4 rows and 4 columns
	 for (int row = 0; row <= 3; row++)
	  { 
	    for (int column = 0; column <=3; column++)
		{
	        buttons [row][column] = new JButton (row*4 + column + 1+   "");// creates the button and sets the text using a formla derived that will have the buttons show 1-15 initially
	        buttons [row][column] .setBounds  (SIZE_X * column, SIZE_Y * row,SIZE_X,SIZE_Y);// using the set bounds method, this will set the location and size of each board piece. for our location
	        																				// we need to multiply our x location by the column and y by the row so that they are placed in the correct order for 1-15.
	        																			    // this is where our Size_X and Size_Y constants come in handy so that we have consistency in out locations and sizes.
	        // the next button will not land on top of the previous one. 
	                  					
	        add(buttons[row][column]);// each button then gets added to the frame     
	        
	        buttons[row][column].addActionListener(listener1);// then they are attached to the same listener so that when they are clicked, they will use the move method to move accordingly 
		}
		   
	  }
	 
	  buttons [3][3].setText("0");// we then set the last piece on the lower right corner to be our zero piece 
	  buttons [3][3].setVisible(false);// then set it to not be visible so that it is now our "blank" piece.
	  

       // Now what we do is create the other options buttons and then set their location, size, and add them to the board 
	  // we must also add the appropriate listener to each button
	  JButton Save = new JButton ("Save"); Save.setBounds ( 0,300,70,50); Save.setVisible(true); add (Save);
	  JButton Load = new JButton ("Load"); Load.setBounds (75,300,70,50); Load.setVisible(true); add (Load);
	  JButton Help = new JButton ("Help"); Help.setBounds(150,300,70,50); Help.setVisible(true); add (Help);
	  JButton Exit = new JButton ("Exit"); Exit.setBounds(225,300,70,50); Exit.setVisible(true); add (Exit); 
	  JButton NewGame = new JButton ("New Game"); NewGame.setBounds(75,355, 95,50); NewGame.setVisible(true); add (NewGame);
	  Moves.setBounds(0,400,70,50);Moves.setVisible(true);add (Moves);
	  GamesPlayed.setBounds(220,400,70,50); GamesPlayed.setVisible(true); add (GamesPlayed);
	  WinGames.setBounds(200,420,90,50);WinGames.setVisible(true);add (WinGames);
	  Moves.setText("Moves  " +  moves);
	  Help.addActionListener(listener2); 
	  Save.addActionListener(listener3);
	  Load.addActionListener(listener4);
	  Exit.addActionListener(listener5);
	  NewGame.addActionListener(listener6);
	  shuffle();// now that board is technically already in the winning condition we need to shuffle it before the user can see it and do anything 
	  setVisible(true);// set the frame and everything now inside of it to be visible after the board has been shuffled 
}

	
	
	class BoardListener implements ActionListener // Our listener for the board pieces.
	{
		@Override
		public void actionPerformed (ActionEvent  e)
		{
			// This will process through the array and then find the source of the click when the user clicks on the board.
			// our if statement say that if the source of the button is equal to the row and column in our array that the nested for loop is currently processing through,
			// then we store row and column into our CR and CC data fields. 
			for (int row = 0; row <= 3; row++)
			{
				for (int column = 0; column <= 3; column++)
				{
					
					if (e.getSource() == buttons [row][column])
					{
						CR = row;
						CC = column;
						// now that we now a button has been clicked and the now the row and column of the piece that was clicked we then use our move method to move the pieces 
						// but we should only be able to move the pieces if the Clicked piece and the Zero piece is in either the same row or column but not both
						// because then we would be talking about the zero piece and we should not able to move the blank tile if we clicked the blank tile since the blank tile 
						// is what makes it possible to move the pieces on the board at all
						if ((ZR == CR) ^ (ZC == CC))
						{
						   moves++;// now that we have made a valid move we increment the move counter
						   Moves.setText("Moves  " +  moves);// set the label to display the # of moves with in the incremented value for moves
						   masterMove ();
						}   
						
					}
				}
				
			}
			// We must use an if statement that will only check for a winner when the board is done shuffling and not before since the initial state of the baord before shuffling is already a winning alignment see Shuffle method for more detail
            if (!isShuffle)
            {
            	checkWinner();
            }
			
			
		}
	}// end of Board listener 
	class NewGameListener implements ActionListener 
	{  //listener used so that when the user wants to start a new game, the board will shuffle again.  
		@Override
		public void actionPerformed (ActionEvent e)
		{
		shuffle();// also adds 1 to the game counter
	   
		}
	}
	class ExitListener implements ActionListener 
	{  //listener used so that when the user wants to exit the game he is currently playing, his board will be saved and he can load it later. 
		@Override
		public void actionPerformed (ActionEvent e)
		{
		Save ();
	    System.exit(0);// used to make the program end when he clicks the button
		}
	}
	
   class HelpListener implements ActionListener
   { // listener used to respond to when the help button is clicked. It will give information on how to play the game and how to win.
	   @Override
	 public void actionPerformed (ActionEvent e)  
	   {
		   JOptionPane.showMessageDialog(null , "The name of the game is 15 squared!\n"+ "click the butttons to move them so that they are aligned 1-15 from left to right!!!\n"+"Valid moves are those that are when you click in the same row and column as the blank piece!\n"+"Good Luck");
	   }
   }

   class SaveListener implements ActionListener
   {   // Listener used to respond to when the save button is pressed. It will call the save method.
	   @Override 
	   public void actionPerformed (ActionEvent e)
	   {
	   Save();
	   }
   }
	
   class LoadListener implements ActionListener
   {// Listener used to respond to when the Load button is pressed. It will call the load mehtod
	   @Override
	   public void actionPerformed (ActionEvent e)
	   {
	    Load();
	   }
   }
	
	// this method is used to make all the moves possible. It will call the other smaller methods that perform the actual "moving" of the pieces.
   //  This method will compare the distances between the ZeroRow and ClickedRow if they are in the same column
   //  and also the ZeroCol and ClickedCol and if they are in the same row. Depending on these distances, if they are in the same row or column, and if the ZR or ZC is < or > the CR or CC, we call the different methods that will process these types of moves
	public void masterMove  ()
	
	{   getZero ();// we of course need to use a method that will find the row column of the zero piece so that we can do these comparisons
		if (ZR == CR || ZC == CC)// condition the only valid moves are those where the column or row of the clicked piece is equal to the zero row or column
		{
			if (Math.abs (ZC - CC)== 1 || Math.abs(ZR - CR) == 1)// move1 where the distance between the zero row and zero column is 1 meaning they are directly next
			{													 // to each other. we then run adjacent move ()  which does this.this can be used for when they are directly 
																 // on top or below one another as well meaning they are in the same column 
				adjacentmove();
		    }
		} 
		
			                   
		 if (Math.abs (ZC - CC) == 2 && (ZR == CR) || Math.abs(ZR - CR) == 2 && (ZC == CC))// move2 where the distance between the zero row or column is 2 meaning that they are two away from 
			
			 
			 
			 // each other and they are in the same row with a button that is in the way between the zero piece and clicked piece 
		 	// if the distance between them is 2 and they are in the same row then we are switching columns.if they are in the same column then we have a switching of rows.
		 {
			 
				if (ZC > CC)
				{	
					move2Column();// move 2 right columns  
				
				}
				if (ZC < CC)
				{
					move5Column ();// move 2 left Column
					
				}
				if (ZR > CR)
				{
					move6Row();// move 2 down 
				}
				if (ZR < CR)
				{
					move8Row ();// move 2 up 
				}
				
			}
		   
			if (Math.abs(ZC - CC) == 3 && (ZR == CR) || Math.abs (ZR - CR) == 3 && (ZC == CC) )
			// This is a situation where we have two buttons that in the way of the zero piece and the clicked piece. 
			// however, as to how we handle them in which method we call, we are pretty much doing the same thing as with the distance being 2 except
			// the methods that we call are specifically for moving all  pieces. 	
			{
				
			    if (ZC > CC)
				{
					move3Column (); // move 3 right horizontally 
					
				}
				if (ZC < CC) // move 3 left horizontally 
				{
					move4Column ();
					
	     		}
				if (ZR < CR) // move 3 up vertically 
				{
					move7Row ();
					
				}	
			    if (ZR > CR) // move 3 down vertically 
			    {	
			    	move9Row ();
			    	
			    }	
			}
	}
			
	
		
		 
	public void adjacentmove () // horizontal move where ZC - CC = 1 
	// instead of trying to move the buttons, we can just get the text from each of the buttons and then reset them accordingly to what the text of the buttons will be after the move
	{
	 String temp = buttons[CR][CC].getText();// we store a temp string variable that will be equal to the clicked row and column to be reset to the zero row and column since in the move for where they are directly
	 										 // next to each other we are just to switch the text of the clicked piece and zero piece and their visibility.  
	 buttons [CR][CC].setText(buttons[ZR][ZC].getText());// we now get the text of the zero row and column and set it to the cr and cc since they are switching locations 
	 buttons [ZR][ZC].setText(temp);// we now set the text of the ZR ZC to the clicked Row and Col that will complete the move 
	 buttons[CR][CC].setVisible(false);	 // since the CR and CC now has the Zero Pieces text of "0" we need to set that to false 
	 buttons[ZR][ZC].setVisible(true);   // now we set the Zero piece to visible since it now contains the text of the clicked row and column
	 ZC = CC; // now we switch the ZC to equal the CC and the ZR to be the Clicked Row now that the Zero piece has been switched to the location of the clicked piece. 
	 ZR = CR;// this will update the ZR and ZC after each move so that we can make sure its in the correct location after each type of move. This will be
	 			// repeated after each move method 
	}
	public void move2Column  () // horizontal move where ZC - CC = 2 where there is one buttons in the way between the clicked button and zero button this is where ZC > CC
						  // move to the right
	{
	// We are basically doing the same thing as before but this time the button that is in the same row as the clicked piece and zero piece is now taken into effect and move according the how the move should process 
    // our clicked piece will still get  the Zero piece's text but the Zero Piece will have the button in the way's text, and then the button in the way will now have the text of the clicked piece
	String temp = buttons [CR][CC].getText();
	String temp2 = buttons [ZR][ZC - 1].getText();
	String temp3 = buttons [ZR][ZC].getText();
	buttons [ZR][ZC].setText(temp2);
	buttons [CR][CC].setText(temp3);
	buttons [ZR][ZC - 1].setText(temp);
	buttons [CR][CC].setVisible(false);
    buttons [ZR][ZC].setVisible (true);
	buttons [ZR][ZC -1].setVisible(true);
	ZC = CC;// same as before the value of the ZC and ZR must become the value of the Clicked Row and Col as with any possible move 
	ZR = CR;
	}
	public void move3Column ()// moves 3 to the right horizontally 
	{// this is an extension of the previous move2 method except we know have an extra piece on the way of the zero pice and the clicked piece
	 // the text of the each button will be of the one to the left of it and the clicked piece will still have the zero pieces text and thus be set to not visible 
	 // we also store the ZR and ZC to be the CR and CC as with any move
		String temp1 = buttons [CR][CC].getText();
		String temp2 = buttons [ZR][ZC - 2].getText();
		String temp3 = buttons [ZR][ZC - 1].getText();
		String temp4 = buttons [ZR][ZC].getText();
		buttons [ZR][ZC].setText(temp3);
		buttons [CR][CC].setText(temp4);
		buttons [ZR][ZC- 2].setText(temp1);
		buttons [ZR][ZC - 1].setText(temp2);
		buttons [CR][CC].setVisible(false);
		buttons [ZR][ZC].setVisible (true);
		buttons [ZR][ZC -1].setVisible(true);
		buttons [ZR][ZC - 2].setVisible(true);	
		ZC = CC;
		ZR = CR;
	}
	public void move4Column () // moves 3 to the left horizontally 
	{// this the same method almost as the move3Column method expcept we are going in the opposite direction so we must refer have the correct buttons in the way of the zero and clicked piece 
	 // to be the the same column + however far away they are from the zero piece since the zero piece in this case is in column 0
		String temp1 = buttons [CR][CC].getText();
		String temp2 = buttons [ZR][ZC + 2].getText();
		String temp3 = buttons [ZR][ZC + 1].getText();
		String temp4 = buttons [ZR][ZC].getText();
		buttons [ZR][ZC].setText(temp3);
		buttons [CR][CC].setText(temp4);
		buttons [ZR][ZC + 2].setText(temp1);
		buttons [ZR][ZC + 1].setText(temp2);
		buttons [CR][CC].setVisible(false);
		buttons [ZR][ZC].setVisible (true);
		buttons [ZR][ZC + 1].setVisible(true);
		buttons [ZR][ZC + 2].setVisible(true);	
		ZC = CC;
		ZR = CR;
	}
	
	public void move5Column () // move 2 to the right 
	{// this is the same as move2Column except since this is for the case where the  the zero piece is the a column to the left of the clicked piece so the button
		// in the way is in the next column to the right of the zero piece. Other than that it is practically the same as move2Column
		String temp = buttons [CR][CC].getText();
		String temp2 = buttons [ZR][ZC + 1].getText();
		String temp3 = buttons [ZR][ZC].getText();
		buttons [ZR][ZC].setText(temp2);
		buttons [CR][CC].setText(temp3);
		buttons [ZR][ZC + 1].setText(temp);
		buttons [CR][CC].setVisible(false);
		buttons [ZR][ZC].setVisible (true);
		buttons [ZR][ZC + 1].setVisible(true);
		ZC = CC;
		ZR = CR;
		
	}
	public void move6Row ()// move 2 down vertically 
	{// this is the same method as move2 except this is where the zero piece and clicked piece are in the same column but in different rows
	 // so we must be switching rows using the same method as the others 
		String temp = buttons [CR][CC].getText();
		String temp2 = buttons [ZR-1][ZC].getText();
		String temp3 = buttons [ZR][ZC].getText();
		buttons [ZR][ZC].setText(temp2);
		buttons [CR][CC].setText(temp3);
		buttons [ZR-1][ZC].setText(temp);
		buttons [CR][CC].setVisible(false);
		buttons [ZR][ZC].setVisible (true);
		buttons [ZR-1][ZC].setVisible(true);
		ZC = CC;
		ZR = CR;
	}
	public void move7Row ()// move 3 up vertically
	{// This is the same method as move6Row but there is only one more button that is in the way between the zero piece and clicked piece 
	 // move4Column is set in the same way as this, except for this method we are to be switching rows instead of columns. 
	String temp1 = buttons [CR][CC].getText();
	String temp2 = buttons [ZR + 2][ZC].getText();
	String temp3 = buttons [ZR + 1][ZC].getText();
	String temp4 = buttons [ZR][ZC].getText();
	buttons [ZR][ZC].setText(temp3);
	buttons [CR][CC].setText(temp4);
	buttons [ZR + 2 ][ZC].setText(temp1);
	buttons [ZR + 1 ][ZC].setText(temp2);
	buttons [CR][CC].setVisible(false);
	buttons [ZR][ZC].setVisible (true);
	buttons [ZR + 1 ][ZC].setVisible(true);
	buttons [ZR + 2][ZC].setVisible(true);	
	ZC = CC;
	ZR = CR;
	}
	public void move8Row ()// move 2 up vertically 
	{// This is the same method as move5Col except we are switching rows instead of columns 
		String temp = buttons [CR][CC].getText();
		String temp2 = buttons [ZR+1][ZC].getText();
		String temp3 = buttons [ZR][ZC].getText();
		buttons [ZR][ZC].setText(temp2);
		buttons [CR][CC].setText(temp3);
		buttons [ZR+1][ZC].setText(temp);
		buttons [CR][CC].setVisible(false);
		buttons [ZR][ZC].setVisible (true);
		buttons [ZR+1][ZC].setVisible(true);
		ZC = CC;
		ZR = CR;
	}
	public void move9Row ()// move 3 up vertically
	{// this is the same method as move3Col except we are switching rows instead of columns. other than that we are doing the same thing 
	String temp1 = buttons [CR][CC].getText();
	String temp2 = buttons [ZR - 2][ZC].getText();
	String temp3 = buttons [ZR - 1][ZC].getText();
	String temp4 = buttons [ZR][ZC].getText();
	buttons [ZR][ZC].setText(temp3);
	buttons [CR][CC].setText(temp4);
	buttons [ZR - 2 ][ZC].setText(temp1);
	buttons [ZR - 1 ][ZC].setText(temp2);
	buttons [CR][CC].setVisible(false);
	buttons [ZR][ZC].setVisible (true);
	buttons [ZR - 1 ][ZC].setVisible(true);
	buttons [ZR - 2][ZC].setVisible(true);	
	ZC = CC;
	ZR = CR;
	}

	public boolean checkWinner ()
	{
		// this method is boolean that if each spot in the array of JButtons is equal to what its supposed to be for a winning board
		// that is 1-15 from left to right with the blank being buttons[3][3]
				if (buttons [0][0].getText().equals  ("1") &&  buttons [0][1].getText().equals  ("2")
				&&  buttons [0][2].getText().equals  ("3") &&  buttons [0][3].getText().equals  ("4")
				&&  buttons [1][0].getText().equals  ("5") &&  buttons [1][1].getText().equals  ("6")
				&&  buttons [1][2].getText().equals  ("7") &&  buttons [1][3].getText().equals  ("8")
				&&  buttons [2][0].getText().equals  ("9") &&  buttons [2][1].getText().equals  ("10")
				&&  buttons [2][2].getText().equals  ("11")&&  buttons [2][3].getText().equals  ("12")
				&&  buttons [3][0].getText().equals  ("13")&&  buttons [3][1].getText().equals  ("14")
				&&  buttons [3][2].getText().equals  ("15"))
				{
					wins++;// we increment the win counter since they have won the game 
					WinGames.setText("Games Won " + wins);// set the text of the label to now display the correct number of games won
					// after they have won a dialog box will show to ask if they want to play again
					int option = JOptionPane.showConfirmDialog(null, " if Would you Like to Play Again & Save select yes, if you dont want to play again click No");
					
					if (option == 0) //if they click yes that we re-shuffle the board and save their progress
					{    
						
						shuffle();
						Save();
			        }
					if (option == 1)// if the click no we will re-shuffle the board, save that board and then exit 
					{
						shuffle();
						Save();
						System.exit(0);// exit the program since they do not want to play again
					}
					if (option == 2)// since clicking cancel is the same as no, we can do the same thing as we do for if they click no
					{
						shuffle();
						Save();
						System.exit(0);// since 
					}
				
					
					return true;// since they indeed have won the game
				}
				return false;// if they have not made the winning combination after going through the method then we return false.
		
					
	}



	public static void shuffle ()
	 {
	// the shuffle is going to generate a random row and column and then perform a click on the array location of the randomn row column
	// and thus instantiate the move method from the listener. Not all of the clicks it generates will be valid moves so that is why we must have a high
    // amount of clicks we generate using a for loop.
	// of course we must increment the games counter since we only shuffle the board when we start a new game 
		 isShuffle = true;// our boolean value we set to true at the beginning so that we dont check for a winner in our listener 
		                  // since the same listener is used for shuffling as checking a winner, we must be able to seperate checking for winner as we are clicking on the board
		 				  // or else it will display a win has happened before the user starts playing the game
		 Games++;
		 GamesPlayed.setText("Games  " +  Games);
		 for (int click = 0; click < 200; click++)
		 {
		  int randCol = (int) (Math.random()*4);
		  int randRow = (int) (Math.random()*4);
		  buttons[randRow][randCol].doClick();
		 }
		 moves = 0;
		 Moves.setText("Moves " + moves);// move counter is reset to 0 so that it will not count random clicks as moves 
		 isShuffle = false;// now that the method is done with its random moves, we can check for a winner from now on by setting our boolean to false 
		 				   // and only checking for winner if the boolean value is false.
	 }




	private static void getZero ()// this method is used to find the row and column # that the "blank" piece is in.
	 						  // using a nested for loop to process through the array of the board, we do a comparison that if the text on the piece is equal to 16
	 					      // which is the "blank", then it will store the row and column number it was found in into two variables, one being the row,
	 {
		for (int row = 0; row <= 3; row++)
		{
			for (int column = 0; column <= 3; column++)
			{
				if (buttons[row][column].getText () .equals ("0"))
				{
	               	ZR = row;
	                ZC = column;
	                return;
				}    
			}
		}
			
	}



	private  void Save ()
	{
		File Game = new File ("Game.dat");// we create a new file for our information to be saved to 
		try 
		{
		
			DataOutputStream output = new DataOutputStream (new FileOutputStream (Game));// create a stream so that we can put our information into that file
			for (int row = 0; row <= 3; row++)
			{
				for (int column =0; column <= 3; column++)	
				{    	
              output.writeUTF (buttons[row][column].getText());// we write the text of the buttons in the array into the file from processing through the array and then 
              												   // getting their text and writing it into the file
				}
			}
		    output.writeInt(moves);// we write the value of our moves counter
		    output.writeInt(Games);// we write the value of our Games counter
		    output.writeInt(wins);// we write the value of our games won counter 
		    
			output.close(); // then we close the output stream now that we are done writing things onto the file
		}
		// two if statements that will let the user now if we could not save the game.
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Game");
		}
		catch (IOException ioe)
		{
			System.out.println("ioe");
		}		
	}
	private void Load ()
	{   // load is just the same as save, but instead of writing things onto the file we are reading from it
		// we just need to reset the values for moves, wins , and games, as the old values from the save. we do 
		// this by creating new variables that will be equal to the read data for the moves wins and games that was 
		// previously stored. we then set our data fields to be equal to these new variables and then reset the text
		// the labels to include the data fields containing the read input. After that we must switch the visibility 
		// of the zero piece, get the old one that is read from the file. And then set that to not visible so that 
		// the correct zero piece is in its correct location and visibility. 
		try 
		{
			DataInputStream input = new DataInputStream (new FileInputStream ("Game.dat"));
			for (int row =0; row <=3; row ++)
			{
				for (int column = 0; column <= 3; column++)
				{
					 buttons[row][column].setText(input.readUTF());
				}
			}
			int savedmoves = (input.readInt()); 
			int savedgames = (input.readInt());
			int savedwins  = (input.readInt());
			wins  = savedwins;
			moves = savedmoves;
			Games = savedgames;
			Moves.setText("Moves  " +  moves);
			GamesPlayed.setText("Games " + Games );
			WinGames.setText("Games Won  " + wins);
			buttons[ZR][ZC].setVisible(true);
			getZero ();
			buttons[ZR][ZC].setVisible(false);
			input.close();
		}
		// two catches to handle any issue along the way 
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Game");
		}
		catch (IOException ioe)
		{
			System.out.println("ioe");
		}
	}
	// Amazing ! only one line for main!
	 public static void main (String args [])
	{ 
	  FiffteenSquare Game = new FiffteenSquare (); // create an instance of the class to display the board and start a new game 
	}

}

   