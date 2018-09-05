
public class Main {

	public static void main(String[] args) {
		
		String[] deck = new String[52]; //Declares the deck array containing all cards
		String[] suits = {"Spades","Hearts","Clubs","Diamonds"}; //Declares the suits of the cards
		String[] ranks = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"}; //declares the rank of the cards
		String[] playerOneHand = new String [3];
		String[] playerOneFaceUp = new String [3];
		String[] playerOneFaceDown = new String [3];
		String[] playerTwoHand = new String [3];
		String[] playerTwoFaceUp = new String [3];
		String[] playerTwoFaceDown = new String [3];
		
		for(int i = 0; i < 52; i++) {
			deck[i] = ranks[i%13] + " " + suits[i/13]; 
		}
		for(int i = 0; i < 52; i++) {
			int rand = (int)(Math.random() * 52);
			String temp = deck[i]; //Stores the card number into the temporary variable
			deck[i] = deck[rand]; //Changes the card number to a random number
			deck[rand] = temp; //This ensures that each card only appears once
		}
		for(int i = 0; i < 3; i++) {
			playerOneHand[i] = deck[i]; //assigns the first player the first 3 cards from the deck
			playerOneFaceUp[i] = deck[i+3]; //Face up cards are from cards 4 5 6
			playerOneFaceDown[i] = deck[i+6];//Face down cards are from 6 7 8
			playerTwoHand[i] = deck[i+9]; //assigns the first player 3 cards from the deck
			playerTwoFaceUp[i] = deck[i+12]; //assigns the first player 3 cards from the deck
			playerTwoFaceDown[i] = deck[i+15]; //assigns the first player 3 cards from the deck
		}
		//Shows what cards in player's hand
		for(int i = 0; i < 3; i ++) {
			System.out.println("Player One Hand: " + playerOneHand[i]);
		}
		System.out.println();
		for(int i = 0; i < 3; i ++) {
			System.out.println("Player One FaceUp: " + playerOneFaceUp[i]);
		}	
		System.out.println();
		for(int i = 0; i < 3; i ++) {
			System.out.println("Player One FaceDown: " +playerOneFaceDown[i]);
		}	
		System.out.println();
		for(int i = 0; i < 3; i ++) {
			System.out.println("Player Two Hand: " +playerTwoHand[i]);
		}		
		System.out.println();
		for(int i = 0; i < 3; i ++) {
			System.out.println("Player Two FaceUp: " +playerTwoFaceUp[i]);
		}		
		System.out.println();
		for(int i = 0; i < 3; i ++) {
			System.out.println("Player Two FaceDown: " +playerTwoFaceDown[i]);
		}
		}
		

}
