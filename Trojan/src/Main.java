/*
 * Description: This class runs the game
 * Author : Gurvir Dehal
 * Last Updated : May 5th 2016
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;


public class Main {

    static int p1cardValue = 0;
    static int p2CardValue = 0;
	static Scanner keyb = new Scanner(System.in);
    static ArrayList < String > pile = new ArrayList < String > (); //Stores the played cards in case a player picks them up
    static String p1response = "";
    static String p2response = "";
    static int topCardValue = 0;
    static String cardToBeat;
    static boolean playerOneTurn = true;
    public static String fileName = "Leaderboards.txt";
    public static int[] stats = new int[4]; // the actual stats
    public static int[] readStats = new int[stats.length]; // the stats it reads in 
    /*
     * actualStats[0] = player one wins
     * actualStats[1] = player one losses
     * actualStats[2] = player two wins
     * actualStats[3] = player two losses
     */
    public static void main(String[] args) {
            String[] deck = new String[52]; //Declares the deck array containing all cards
            String[] suits = {"♠","♥","♣","♦"}; //Declares the suits of the cards 
            String[] ranks = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"}; //declares the rank of the cards
            ArrayList < String > playerOneHand = new ArrayList < String > (3); //3 cards for player one
            ArrayList < String > playerOneFaceUp = new ArrayList < String > (3); // 3 face up cards for player one
            ArrayList < String > playerOneFaceDown = new ArrayList < String > (3); //3 face down cards for player one
            ArrayList < String > playerTwoHand = new ArrayList < String > (3); //3 cards for player two
            ArrayList < String > playerTwoFaceUp = new ArrayList < String > (3);// 3 face up cards for player two
            ArrayList < String > playerTwoFaceDown = new ArrayList < String > (3); // 3 face down cards for player two
            ArrayList < String > stockPile = new ArrayList < String >(34); // A pile for the 34 remaining cards
            //Gives each card in the deck a rank and a suit
            createCards(deck, ranks, suits);
            //This shuffles the cards
            shuffleCards(deck);
            //This deals the cards
            dealCards(playerOneHand, playerOneFaceUp, playerOneFaceDown, playerTwoHand, playerTwoFaceUp, playerTwoFaceDown, deck, stockPile);
            //At this point 18 cards have been dealt and cards 19-52 are remaining
            //and will be put into a pile
            boolean run = true; // this sets the run to true so the game will run
            boolean hasPickedUp1 = false; // this sets up to see if the player has picked up their cards
            boolean hasPickedUp2 = false;
            readFile(); // read the lines in the file
          do {
        	   if(playerOneTurn == true) {
        		   System.out.println("Which card would Player " + "One" + " like to play?");
        			if(playerOneHand.isEmpty() == false) {
        		     		   printHand("One",playerOneHand);
        		     		   playCards(1,playerOneHand, stockPile, p1response, p1cardValue);
        		 	   } else if(hasPickedUp1==false) {
        		 		   System.out.println("Player " + "One" + " has picked up their face up cards!");
        		 		   System.out.println();
        		 		   playerOneHand.addAll(playerOneFaceUp);
        		 		   hasPickedUp1 = true;
        		 	   } else if(hasPickedUp1 == true) {
        		 		   System.out.println("Player One can now use their face down cards!");
        		 		   playerOneHand.clear();
        		 			   printDown("One",playerOneFaceDown);
        		 			   playFaceDown("One",playerOneHand,playerOneFaceDown);
        		 	   }
        		 	   if(playerOneFaceDown.isEmpty() && playerOneHand.isEmpty()) {
        		 		   run = false;
        		 		   System.out.println("Player One Wins!");
        		 		   stats[0]++; //player one wins + 1
        		 		   stats[3]++; //player two loss + 1
        		 	   }
        			}
        	    else { // if player one turn is false
        			if(playerTwoHand.isEmpty() == false) { // if the player still has cards in their hand 
        		 		   System.out.println("Which card would Player " + "Two" + " like to play?");
        		     		   printHand("Two",playerTwoHand);
        		     		   playCards(2,playerTwoHand, stockPile, p2response, p2CardValue);
        		 	   } else if(hasPickedUp2==false) { //if the player has not picked up their faceup cards
        		 		   System.out.println("Player " + "Two" + " has picked up their face up cards!");
        		 		   System.out.println();
        		 		   playerTwoHand.addAll(playerTwoFaceUp);
        		 		   hasPickedUp2 = true;
        		 	   } else if(hasPickedUp2 == true) { //if they have previously picked up their faceup cards and their hand is empty
        		 		   //System.out.println("Player Two can now use their face down cards!");
        		 		   playerTwoHand.clear();
        		 			   printDown("Two",playerTwoFaceDown);
        		 			   playFaceDown("Two",playerTwoHand,playerTwoFaceDown);
        		 	   }
        		 	   if(playerTwoFaceDown.isEmpty() && playerTwoHand.isEmpty()) {
        		 		   run = false;
        		 		   System.out.println("Player Two Wins");
        		 		  stats[2]++;//player two wins + 1
       		 		      stats[1]++;//player one loss + 1
        		 	   }
        			}
           }while(run);
          //if not there, then create file
          writeFile();
          printStats(stats);
           //System.out.println("Game Finished!");
    }
        //create Cards
    public static void createCards(String[] Deck, String[] Ranks, String[] Suits) {
            for (int i = 0; i < Deck.length; i++) {
                Deck[i] = Ranks[i % 13] + Suits[i / 13];
                //ranks[i%13]ensures that each card has a rank from Ace to King
                //as i%13 returns the numbers from 0 to 12
                //and suits[i/13] ensures that there are 13 cards with the same suit
                //as i/13 returns 13 zeroes, 13 ones, 13 twos and 13 threes
            }
        }
        //shuffle Cards
    public static void shuffleCards(String[] Deck) {
            for (int i = 0; i < Deck.length; i++) {
                int rand = (int)(Math.random() * Deck.length); //random number from 0 to 52
                String temp = Deck[i]; //Stores the card number into the temporary variable
                Deck[i] = Deck[rand]; //Sets the card at position i equal to the card at position random
                Deck[rand] = temp; //Sets the card at position random equal to the card at position i
                //basically it switches the cards
            }
        }
        //deal
    public static void dealCards(ArrayList<String> p1Hand, ArrayList<String>p1Up, ArrayList<String>p1Down, ArrayList<String> p2Hand, ArrayList<String>p2Up, ArrayList<String>p2Down, String[] Deck,ArrayList<String>StockPile) {
        for (int i = 0; i < 3; i++) {
            p1Hand.add(Deck[i]); //assigns the first player the first 3 cards from the deck
            p1Up.add(Deck[i + 3]); //Face up cards are from cards 4 5 6
            p1Down.add(Deck[i + 6]); //Face down cards are from 7 8 9
            p2Hand.add(Deck[i + 9]); //assigns the second player cards 10 11 12
            p2Up.add(Deck[i + 12]); // assigns the face up cards from 13 14 15
            p2Down.add(Deck[i + 15]); //assigns the player the face down cards 16 17 18
        }
        for (int i = 0; i < Deck.length - 18; i++) {
            StockPile.add(Deck[i + 18]); //cards 19 to 52 are put in the stockpile
        }
    }
    
    /**
     * Updates the cards in the player Hand by replacing the old ones with new ones.
     * @param playerHand 
     * @param StockPile 
     * @param response 
     */
    public static void switchCards(ArrayList<String> playerHand,ArrayList<String>StockPile, String response) {
    	String[] s = response.split(",");
    	int[] a = new int[s.length];
    	for(int i = 0; i < s.length; i++) {
    		a[i] = Integer.parseInt(s[i]);
    	
    	if(StockPile.isEmpty() == false) { //if the StockPile has cards in it then run the code
    		playerHand.set(a[i] - 1, StockPile.get(0)); //change the card that was just played with the first card from the stockPile
    		StockPile.remove(0); //remove the first card from the stockpile
    		StockPile.trimToSize();
    	} else { //if StockPile is empty then just remove the card from the player hand
    		playerHand.remove(a[i]-1);
    		playerHand.trimToSize();
    	}
    	}
    }
    //prints out the cards in the player Hand
    public static void printHand(String num, ArrayList<String> p1Hand){
    	organizeCards(p1Hand);
        for (int i = 0; i < p1Hand.size(); i++) {
            System.out.println("Player " + num + " Card " + (i + 1) + " : " + p1Hand.get(i));
        }
        System.out.println();
    }
    //prints out cards in the face down pile
    public static void printDown(String num,ArrayList<String>p2Down){
        for (int i = 0; i < p2Down.size(); i++) {
            System.out.println("Player " + num + " FaceDown: " + (i + 1) + " : "+ "???");
        }
    System.out.println();
    }
    // gives an integer value to a card
    public static int assignValue(String playerCardRank) {
    	int playerCardValue = 0;
        try {
           playerCardValue = Integer.parseInt(playerCardRank); //Attempts to convert playerCardRank to integer
            if (playerCardValue == 1) {
                playerCardValue = 10; 
            }
        } catch (NumberFormatException e) { //if not a number, then it must be J,Q,K or A.
            if (playerCardRank.equals("J")) {
                playerCardValue = 11;
            } else if (playerCardRank.equals("Q")) {
                playerCardValue = 12;
            } else if (playerCardRank.equals("K")) {
                playerCardValue = 13;
            } else if (playerCardRank.equals("A")) {
                playerCardValue = 14;
            }
        }
        return playerCardValue;
    }
    // the big method that compares the two cards and then determines what will happen next
    public static void playCards(int num, ArrayList<String> playerHand,ArrayList<String>StockPile,String response,int cardValue) {
    	boolean invalidCard = false;
         do {
         if(canPlay(playerHand) == false) { // if the player has no cards that can beat the previous card
        	 invalidCard = false;
        	 System.out.println("Player " + num + " is unable to go, and have picked up the center pile!"); 
        	 System.out.println();
        	 playerHand.addAll(pile); //adds all the cards from the pile to the playerHand
        	 pile.clear(); //removes all the cards from the pile
        	 System.out.println("Player " + num + " : " + playerHand);
        	 topCardValue = 0; //resets the game by setting the topCard to 0
        	 cardToBeat = "";
         } else {
        	 invalidCard = false;
        	response = keyb.nextLine();
        	String cardRank = null;
        	String[] s = response.split(",");
        	int[] a = new int[s.length];
        	for(int i = 0; i < s.length; i++) {
        		a[i] = Integer.parseInt(s[i]);
        		cardRank = playerHand.get(a[i]-1).substring(0,1);
        		System.out.println(cardRank);
        	}
	        cardValue = assignValue(cardRank);
	         //String cardRank = playerHand.get(Integer.parseInt(response) - 1).substring(0, 1);
	         //cardValue = assignValue(cardRank);
	         //The following code sets up the wild cards in the game
	         if(cardValue == 10) { // clears the pile if a ten has been played
	        	topCardValue = 0;
	        	pile.clear();
	        	System.out.println("The pile has been cleared!");
	        	System.out.println(pile);
	        	switchCards(playerHand, StockPile, response);
	        	playerOneTurn = !playerOneTurn; //lets the player go again
	         } else if(cardValue == 2) { // allows anything to beat a 2
	        	 cardValue = 0;
	        	 updateTopCard(playerHand,StockPile,response,cardValue);
	         } else if(cardValue == 3) { //makes a 3 equal to the card it was played on
	        	 updateTopCard(playerHand,StockPile,response,cardValue);
	         } else if(cardValue == 7) {
	        	 updateTopCard(playerHand,StockPile,response,cardValue);
	         } else if(cardValue == 8) {
	        	 updateTopCard(playerHand,StockPile,response,cardValue);
	        	 playerOneTurn = !playerOneTurn; //skips the next persons turn and the player goes again
	         } else if(topCardValue == 7) {
	        	 if(cardValue <= topCardValue) { // if the previous card is a 7 then this card must be less than or equal to 7
	        		 updateTopCard(playerHand,StockPile,response,cardValue);
		         } else {
		        	 System.out.println("Invalid Card. Please pick again.");
		        	 invalidCard = true; // causes the program to loop through this line again
		        	 //because they played the wrong card
		         }
	         }
	         else if(cardValue >= topCardValue) {
	        	 updateTopCard(playerHand,StockPile,response,cardValue);
	         } else {
	        	 System.out.println("Invalid Card. Please pick again.");
	        	 invalidCard = true;
	         }
         }
         }while(invalidCard);
         playerOneTurn = !playerOneTurn; //switches the value of playerOneTurn to the opposite
    }
    //This method checks to see if the player has a card that can beat the
    //card that was previously played
    public static boolean canPlay(ArrayList<String>playerHand) {
    	boolean canPlay = false;
	    	for(int i = 0; i < playerHand.size(); i++) {
	    		String cardRank = playerHand.get(i).substring(0,1);
	    		int cardValue = assignValue(cardRank);
	    		//if you have a wild card then you can definitely play
	    		if(cardValue == 2 || cardValue == 3 || cardValue == 7 || cardValue == 8 || cardValue == 10 ) {
	    			canPlay = true;
	    			break;
	    		}
	    		//if the last card was a 7 you must have a card less than or equal to 7 in order to play
	    		if(topCardValue == 7) {
	    			if(cardValue <= topCardValue) {
	    				canPlay = true;
	    				break;
	    			}
	    		}
	    		// you must have a card greater than or equal to the card beneath it in order to play
	    		else if(cardValue >= topCardValue) {
	    			canPlay = true;
	    			break;
	    		} 
	    	}
    	return canPlay;
    }
    //This method organizes the cards so it is easy for the player
    //to look at their hand
    public static void organizeCards(ArrayList<String>playerHand) {
		String[] num = {"11","12","13","14"};
		String[] rank = {"J","Q","K","A"};
		switchString(playerHand,rank,num); //switches the letters such as 'J' or 'Q' with their respective number values
    	Collections.sort(playerHand, new Comparator<String>() { //sorts the cards in the hand
    	    public int compare(String object1, String object2) { //overrides the default compare method
    	        return extractInt(object1) - extractInt(object2); //sees which card has a bigger value
    	    }
    	    int extractInt(String card) { //extracts the integer from string
    	        String number = card.replaceAll("\\D", ""); //removes all non digit characters (0-9)
    	        return Integer.parseInt(number); // returns the string as an integer
    	    }
    	});
    	switchString(playerHand,num,rank); //switches the numbers back into letters
    }
    //This method is designed to take an arraylist and it replaces the beginning of each
    //element with something else. For example, if x[0] = 'A' and y[0] = 'B', then this method would
    //replace the 'A' at the beginning of each element with a 'B'.
	public static void switchString(ArrayList<String>playerHand,String[]x,String[]y) {

    	for(int i = 0 ; i < playerHand.size(); i++) {
    		if(playerHand.get(i).startsWith(x[0])){
    			playerHand.set(i, playerHand.get(i).replaceFirst(x[0], y[0]));
    		}
    		else if(playerHand.get(i).startsWith(x[1])){
    			playerHand.set(i, playerHand.get(i).replaceFirst(x[1], y[1]));
    		}
    		else if(playerHand.get(i).startsWith(x[2])){
    			playerHand.set(i, playerHand.get(i).replaceFirst(x[2], y[2]));
    		}
    		else if(playerHand.get(i).startsWith(x[3])){
    			playerHand.set(i, playerHand.get(i).replaceFirst(x[3], y[3]));
    		}
    	}
	}
	//Similar to the playCards method but does not use the switchCards method
	public static void playFaceDown(String num,ArrayList<String>playerHand,ArrayList<String> faceDown) {

	    int response = keyb.nextInt();
	    String cardRank = faceDown.get(response - 1).substring(0, 1);
	    int cardValue = assignValue(cardRank);
	    if(cardValue == 10) {
	    	topCardValue = 0;
        	pile.clear();
        	System.out.println("The pile has been cleared!");
        	System.out.println(pile);
        	playerOneTurn = !playerOneTurn; //lets the player go again
	    } else if(cardValue == 7) {
	    	topCardValue = 7;
	    	 cardToBeat = faceDown.get(response-1);
		   	 pile.add(cardToBeat);
		   	 System.out.println("The Card to beat is the: " + cardToBeat);
	    } else if(cardValue == 8) {
	    	 cardToBeat = faceDown.get(response-1);
		   	 pile.add(cardToBeat);
		   	 System.out.println("The Card to beat is the: " + cardToBeat);
	    	topCardValue = 8;
	    	playerOneTurn=!playerOneTurn;
	    } else if(cardValue == 2) {
	    	 cardToBeat = faceDown.get(response-1);
		   	 pile.add(cardToBeat);
		   	 System.out.println("The Card to beat is the: " + cardToBeat);
	    	topCardValue = 0;
	    } else if(cardValue == 3) {
	    	 //cardToBeat = faceDown.get(response-1);
		   	 pile.add(faceDown.get(response-1));
		   	 System.out.println("The Card to beat is the: " + cardToBeat);
	    	cardValue = topCardValue;
	    }
	    else if(topCardValue == 7) {
	       	 if(cardValue <= topCardValue) {
			   	 topCardValue = cardValue;
			   	 cardToBeat = faceDown.get(response-1);
			   	 pile.add(cardToBeat);
			   	 System.out.println("The Card to beat is the: " + cardToBeat);
			   	 System.out.println(pile);
			   	 System.out.println();
	       	 } else {
	       		 System.out.println("Player " + num + " was unlucky and have picked up the center pile!");
	        	 System.out.println();
	        	 //System.out.println("TPV: " + topCardValue);
	        	 pile.add(faceDown.get(response-1));
	        	 playerHand.addAll(pile);
	        	 pile.removeAll(pile);
	        	 System.out.println("Player " + num + " : " + playerHand);
	        	 topCardValue = 0;
	       	 }
         }
	    else if(cardValue >= topCardValue) {
		   	 topCardValue = cardValue;
		   	 cardToBeat = faceDown.get(response-1);
		   	 pile.add(cardToBeat);
		   	 System.out.println("The Card to beat is the: " + cardToBeat);
		   	 System.out.println(pile);
		   	 System.out.println();
	    } else {
	    	 System.out.println("Player " + num + " was unlucky and have picked up the center pile!");
        	 System.out.println();
        	 //System.out.println("TPV: " + topCardValue);
        	 pile.add(faceDown.get(response-1));
        	 playerHand.addAll(pile);
        	 pile.removeAll(pile);
        	 System.out.println("Player " + num + " : " + playerHand);
        	 topCardValue = 0;
	    }
	    faceDown.remove(response-1);
	    playerOneTurn = !playerOneTurn;
	}
	/**
	 * This changes the top card in the deck of played cards. The top card is replaced with the most
	 * recently played card.
	 * @param playerHand
	 * @param StockPile
	 * @param response
	 * @param cardValue
	 */
	public static void updateTopCard(ArrayList<String>playerHand,ArrayList<String>StockPile, String response, int cardValue) {
    	String[] s = response.split(",");
    	int[] a = new int[s.length];
    	for(int i = 0; i < s.length; i++) {
    		a[i] = Integer.parseInt(s[i]);
    	
   	 if(cardValue != 3) {
   		 topCardValue = cardValue;
   		 cardToBeat = playerHand.get(a[i]-1);
   	 } else {
   		 cardValue = topCardValue;
   	 }
   	 System.out.println("The Card to beat is the: " + cardToBeat);
   	 pile.add(cardToBeat);
   	 System.out.println(pile);
   	 System.out.println();
        switchCards(playerHand, StockPile, response);
    	}
	}
	/**
	 * Attempts to read the file and take in the saved player stats.
	 */
	public static void readFile() {
        // Open the file and read in the data
        BufferedReader inputStream = null;
		try {
            inputStream = new BufferedReader(new FileReader(fileName));
            //read the first four lines
            for(int i = 0; i < readStats.length; i++ ) {
            	readStats[i] = Integer.parseInt(inputStream.readLine());
            }
            stats = readStats;
        } catch (FileNotFoundException ex) {
            //System.out.println(ex);
        	//if file not created then create the file
        	System.out.println("Creating file...");
             writeFile();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        // Close the file if it's open
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
    }
        
	}
	/**
	 * Attempts to write to the leaderboards file
	 * and add the player stats to it.
	 */
	public static void writeFile() {
        PrintWriter outputStream = null;
        try {
            // Create a new file
            outputStream = new PrintWriter(new FileWriter(fileName));
            for(int i = 0; i < stats.length; i++) {
            	outputStream.println(stats[i]);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        // Close the file if it is open
        if (outputStream != null) {
            outputStream.close();
        }
	}
	public static void printStats(int[]stats) {
        System.out.println("P1 Wins: " + stats[0]);
        System.out.println("P1 Loss: " + stats[1]);
        System.out.println("P2 Wins: " + stats[2]);
        System.out.println("P2 Loss: " + stats[3]);
	}
	
 	/* To Do:
	 * Allow the player to play doubles,triples or quads
	 * On facedown, fix the fact that 3's change into the last played card.
	 */
}