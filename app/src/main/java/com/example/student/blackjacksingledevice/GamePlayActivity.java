package com.example.student.blackjacksingledevice;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GamePlayActivity extends Activity {

    //it works!!

    ArrayList<String> allNames;  //To store values of EditTexts (names)
    String player = "";
    LinearLayout myLinearLayout;

    //Create a game object outside the onCreate method to be able to access it in the other methods
    //private static GamePlayActivity game = new GamePlayActivity();
    Intent intent;
    //Ditto for TextView and DialogFragment:
    TextView textView;
    HitOrStickDialogBox dialogBox;
    FragmentManager manager;
    GamePlayActivity game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        allNames = new ArrayList<>();
        allNames = getIntent().getStringArrayListExtra("all");
        //Add the dealer to the end of the array list of players
        allNames.add("Dealer");

        game = this;
        // To test that the ArrayList successfully transferred over with the Intent:
        for (int i = 0; i < allNames.size(); i++) {
            player = allNames.get(i);
            Toast.makeText(game, "Player " + (i + 1) + ": " + player, Toast.LENGTH_SHORT).show();
        }

        myLinearLayout = (LinearLayout) findViewById(R.id.myLinearLayout);
        /*textView = (TextView)findViewById(R.id.textView);
        textView.setText("Here we go!");*/
        //myLinearLayout.addView(textView);
        /*manager = getFragmentManager();
        dialogBox = new HitOrStickDialogBox();*/
        //myLinearLayout.addView(textView);
        game.startGame(allNames);
        //grid.addView(myLinearLayout);
    }


    public GamePlayActivity() {
    }


    public void startGame(ArrayList<String> playerNames) {

        boolean[] deck = new boolean[52];
        int amountOfPlayers = playerNames.size();

        //A two-dimensional array of all the players and their cards
        int[][] players = new int[amountOfPlayers + 1][12];

        //populate all the players with a value of -1
        for (int i = 0; i < players.length; i++)
            populateDeck(players[i]);

        //deal 2 cards to each player
        for (int i = 0; i < players.length; i++) {
            dealCards(deck, players[i]);
        }

        for (int i = 0; i < players.length - 1; i++) {
            TextView textView = new TextView(game);
            textView.setText("\nIt is " + playerNames.get(i) + "'s turn.");
            textView.setPadding(15, 0, 0, 0);
            myLinearLayout.addView(textView);

            playersTurn(i, players, deck, playerNames);
        }
        dealersTurn(players, deck, playerNames);
    }

    //populates the available card spaces to with a value of -1
    public void populateDeck(int[] playerDeck) {
        for (int i = 0; i < playerDeck.length; i++) {
            playerDeck[i] = -1;
        }
    }

    //deals 2 cards to all the players and flips the value of the card to true in the deck array and makes sure that one card is not dealt twice
    //removed static
    public void dealCards(boolean[] deck, int[] playerhand) {
        int count = 0;
        while (count < 2) {
            int deal = (int) (Math.random() * 52);
            while (deck[deal])
                deal = (int) (Math.random() * 52);
            deck[deal] = true;
            playerhand[count] = deal;
            ++count;
        }
    }


    public void printCards(int[][] playersHand, ArrayList<String> playersNames) {
        for (int i = 0; i < playersNames.size(); i++) {
            String player = playersNames.get(i);
            TextView playersCards = new TextView(game);
            playersCards.setText(player + ":");
            playersCards.setPadding(15, 0, 0, 0);
            myLinearLayout.addView(playersCards);
            for (int j = 0; j < playersHand[0].length && playersHand[1][j] != -1; j++) {
                String cardFaceValue = getCardFaceValueText(playersHand, 1, j);
                TextView card = new TextView(game);
                card.setText(cardFaceValue);
                card.setPadding(15, 0, 0, 0);
                myLinearLayout.addView(card);
                //if(playersNames[player] == "Dealer" && currentPlayer != playersHand.length -1)
                //break;
            }
        }
    }

    //gets the number and the suit of each card using an algorithm
    private static String getCardFaceValueText(int[][] playersHands, int player, int card) {
        int cardNumber;
        int suit;
        String cardFaceValue="";
        cardNumber = (playersHands[player][card] % 13) + 1;
        switch (cardNumber) {
            case 1: cardFaceValue="Ace of "; break;
            case 11: cardFaceValue="Jack of "; break;
            case 12: cardFaceValue="Queen of "; break;
            case 13: cardFaceValue="King of "; break;
            default: cardFaceValue= cardNumber + " of "; break;
        }
        suit = playersHands[player][card] / 13;
        switch(suit) {
            case 0: cardFaceValue+="Spades"; break;
            case 1: cardFaceValue+="Diamonds"; break;
            case 2: cardFaceValue+="Clubs"; break;
            case 3: cardFaceValue+="Hearts"; break;
            default: cardFaceValue+="Unknown suit"; break;
        }
        return cardFaceValue;
    }

    public void playersTurn(int i, int[][] players,boolean[] deck, ArrayList<String> playersName) {
        game.printCards(players, playersName);
        if(isBlackJack(players[i])){
            TextView textView = new TextView(game);
            textView.setText(playersName.get(i) + " has BlackJack!! Your turn is over. You Won!");
            textView.setPadding(15, 0, 0, 0);
            myLinearLayout.addView(textView);
            return;
        }
        game.promptUser(i, players, deck, playersName);

    }

    public static boolean isBlackJack(int[] playerHand){
        boolean oneTenValueCard=false;
        boolean oneAceCard=false;
        for(int i = 0; i < 2; i++) {
            if (playerHand[i]%13==0)
                oneAceCard=true;
            else if(playerHand[i]%13>=9)
                oneTenValueCard=true;
        }
        if(oneAceCard && oneTenValueCard)
            return true;//blackjack can only happen where there are 2 cards and not more.
        return false;
    }

    //will use this method instead of the original prompt user method until we can get the dialog box and the toast messages to work
   /* public void promptUser(int currentPlayer, int[][] players,boolean[] deck, ArrayList<String> playersNames) {
        boolean isHit = true;
        do {
            int sumOfCards = countCards(players[currentPlayer]);
            if (sumOfCards > 21) {
                //Not sure what goes here: I think player busts so I added a TextView:
                TextView textView = new TextView(game);
                textView.setText("The value of your cards is greater than 21; Sorry to say this, but you busted!");
                myLinearLayout.addView(textView);
                //Toast.makeText(GamePlayActivity.this, "This toast message works", Toast.LENGTH_LONG).show();
                break;
            }
            if (isHit) {
                hit(deck, players[currentPlayer]);
            }
            isHit = false;
        } while (isHit);
    }*/
    public void promptUser(int currentPlayer, int[][] players, boolean[] deck, ArrayList<String> playersNames) {
        boolean isHit = true;
        do{
            int sumOfCards = 25;//countCards(players[currentPlayer]);
            if (sumOfCards > 21) {
                TextView textView = new TextView(game);
                textView.setText(playersNames.get(currentPlayer) + ", sorry to say this, but you Busted. Your turn is over; You lost.");
                textView.setPadding(15, 0, 0, 0);
                myLinearLayout.addView(textView);

                break;
            }
            /*intent = new Intent(this, HitOrStickDialogBox.class);
            intent.putExtra("current player", playersNames.get(currentPlayer));
            intent.putExtra("sum of cards", sumOfCards);
            new HitOrStickDialogBox().show(getFragmentManager(), "My Fragment");
            Bundle bundle = getIntent().getExtras();
            isHit = bundle.getBoolean("Player choice");*/

            //TO SHOW dialogBox, RIGHT NOW WITHOUT INTENT:
            manager = getFragmentManager();
            dialogBox = new HitOrStickDialogBox();
            dialogBox.show(manager, "dialogBox");
            if(isHit)  {
                //Toast.makeText(GamePlayActivity.this, "The activity is working", Toast.LENGTH_LONG).show();
                hit(deck, players[currentPlayer]);
                TextView textview2 = new TextView(game);
                textview2.setText("You were dealt a " + getCardFaceValueText(players, currentPlayer, getPlayersFirstEmptyCardIndex(players[currentPlayer])-1));
                textview2.setPadding(15, 0, 0, 0);
                myLinearLayout.addView(textview2);
                //need to add here if its a 10,11,12 that its king queen or jack, removed toast and system.out.println
            }
            else{
                TextView textView3 = new TextView(game);
                textView3.setText(playersNames.get(currentPlayer) + ", your turn is over.");
                textView3.setPadding(15, 0, 0, 0);
                myLinearLayout.addView(textView3);

            }
        } while(isHit);
        // input.close();
    }

    public static int countCards(int[] cards) {
        int sum = 0;
        boolean aceFound = false;
        int c = 0;  //changed to 0
        for (int i = 0; i < cards.length && cards[i] != -1; i++) {//the loop doesn't need to go through the whole array
            c = cards[i] % 13; //divide to get card number, but it's 1 off (lower because of 0 index)
            if (c >= 0 && c < 9)
                sum += c + 1;
            if (c >= 9 && c <= 12)
                sum += 10;
            if (c == 0)
                aceFound = true;
        }
        if (aceFound)
            sum+= (isSumLessThanEleven(sum)?10:0);//After summing all cards, add ten if there are any aces with room to be high
        return sum;
    }


    public static boolean isSumLessThanEleven(int sum) {
        if (sum < 11)
            return true;
        else
            return false;
    }

    public static int hit(boolean[] deck, int[] whosTurn) {
        int x = (int)(Math.random() * 52);
        while(deck[x] == true) {
            x = (int)(Math.random() * 52);
        }
        deck[x] = true;
        whosTurn[getPlayersFirstEmptyCardIndex(whosTurn)] = x;
        return x;
    }

    private static int getPlayersFirstEmptyCardIndex(int[] whosTurn) {
        int i;
        for(i= 0; i < whosTurn.length; i++) {
            if (whosTurn[i] == -1) {
                break;
            }
        }
        return i;
    }

    public void dealersTurn(int [][] players, boolean [] deck, ArrayList<String> playersNames){
        int dSum = countCards(players[players.length - 1]);
        boolean isHit;

        TextView textView = new TextView(game);
        textView.setText("\nIt is the Dealer's turn. \nThe Dealer's cards are: "); //added this to print dealers first 2 cards
        textView.setPadding(15, 0, 0, 0);
        myLinearLayout.addView(textView);

        /*NOT SURE HOW TO HANDLE {} FOR THIS FOR-LOOP, BUT ADDED THE TEXTVIEW FOR WHEN WE'RE READY:*/
        for (int i = 0; i < 2; i++) {
            TextView textView1 = new TextView(game);
            textView1.setText(getCardFaceValueText(players, players.length -1, i)); //added i, which was the missing int parameter
            textView1.setPadding(15, 0, 0, 0);
            myLinearLayout.addView(textView1);

        }
        //check for blackjack here! //WE MUST DO
        do{
            isHit = false;
            if (dSum < 17){
                hit(deck, players[players.length - 1]);
                dSum = countCards(players[players.length - 1]);

                /*AGAIN, NOT SURE WHERE TO PUT THIS, BUT THE TEXTVIEW IS READY:*/
                TextView textView2 = new TextView(game);
                textView2.setText("Let's see what the Dealer has dealt....");
                textView2.setPadding(15, 0, 0, 0);
                myLinearLayout.addView(textView2);
                //MAYBE A DIALOG FRAGMENT HERE, SO MORE INTERACTIVE AND FUN???
                //Scanner input = new Scanner(System.in);
                //input.nextLine();
                TextView textView3 = new TextView(game);
                textView3.setText("The Dealer was dealt a " + getCardFaceValueText(players, players.length - 1, getPlayersFirstEmptyCardIndex(players[players.length - 1]) - 1)
                        + ".\nYour cards now count up to " + dSum);
                textView3.setPadding(15, 0, 0, 0);
                myLinearLayout.addView(textView3);

                isHit = true;
            }else if(isThereOneAceHigh(players[players.length - 1], dSum) && dSum == 17){ // this is what the method is expecting... the isThereOneAce method is also expecting to be passed in something
                hit(deck, players[players.length - 1]);
                dSum = countCards(players[players.length - 1]);
                TextView textView4 = new TextView(game);
                textView4.setText("Let's see what the Dealer has dealt.... The Dealer was dealt a "
                        + getCardFaceValueText(players, players.length - 1, getPlayersFirstEmptyCardIndex(players[players.length - 1]) - 1)
                        + ".\nYour cards now count up to " + dSum);
                textView4.setPadding(15, 0, 0, 0);
                myLinearLayout.addView(textView4);
                //MAYBE ADD A FRAGMENT, SO MORE INTERACTIVE, OTHERWISE THE APP JUST PUTS EVERYTHING ON THE SCREEN.... NOT VERY FUN.... :-(
                //Scanner input = new Scanner(System.in);
                //input.nextLine();

                isHit = true;
            }else{   //stick
                TextView textView5 = new TextView(game);
                textView5.setText("All players cards will be displayed: ");
                textView5.setPadding(15, 0, 0, 0);
                myLinearLayout.addView(textView5);

                //printCards(players, playersNames);
                if(dSum > 21){
                    TextView textView6 = new TextView(game);
                    textView6.setText("The Dealer busted with a total of " + dSum);
                    textView6.setPadding(15, 0, 0, 0);
                    myLinearLayout.addView(textView6);

                    for(int i = 0; i < players.length - 1; i++){
                        if(countCards(players[i]) <= 21) {//checks if the player is not busted (used to be in the isPlayerNotBusted method)
                            TextView textView7 = new TextView(game);
                            textView7.setText(playersNames.get(i) + ": won!");
                            textView7.setPadding(15, 0, 0, 0);
                            myLinearLayout.addView(textView7);

                        } else {
                            TextView textView8 = new TextView(game);
                            textView8.setText(playersNames.get(i) + ": lost :-( " );
                            textView8.setPadding(15, 0, 0, 0);
                            myLinearLayout.addView(textView8);

                        }
                    }
                }else
                    for(int i = 0; i < players.length - 1; i++){
                        if(countCards(players[i]) <= 21){//checks if the player is not busted
                            if(countCards(players[i]) > dSum) {
                                TextView textView9 = new TextView(game);
                                textView9.setText(playersNames.get(i) + ": won!");
                                textView9.setPadding(15, 0, 0, 0);
                                myLinearLayout.addView(textView9);

                            }else if(countCards(players[i]) == dSum) {
                                TextView textView10 = new TextView(game);
                                textView10.setText(playersNames.get(i) + " and the Dealer -- PUSH, DRAW");
                                textView10.setPadding(15, 0, 0, 0);
                                myLinearLayout.addView(textView10);

                            }else {//player has less than dealerSum
                                TextView textView11 = new TextView(game);
                                textView11.setText(playersNames.get(i) + " lost.");
                                textView11.setPadding(15, 0, 0, 0);
                                myLinearLayout.addView(textView11);

                            }
                        }else {
                            TextView textView12 = new TextView(game);
                            textView12.setText(playersNames.get(i) + " lost.");
                            textView12.setPadding(15, 0, 0, 0);
                            myLinearLayout.addView(textView12);

                        }
                    }
            }
        }while(isHit);
    }

    public static boolean isThereOneAceHigh(int [] dealer, int dealersSum){
        int sum = 0;
        boolean aceFound = false;
        for (int i = 0; i < dealer.length && dealer[i] != -1 ; i++ ) {//the loop doesn't need to go through the whole array
            int c = dealer[i] % 13; //divide to get card number, but it's 1 off (lower because of 0 index)
            if (c >= 0 && c < 9)
                sum += c + 1;
            else if (c >= 9 && c <= 12)
                sum += 10;
            if (c == 0) {
                aceFound=true;
                if(isSumLessThanEleven(sum)) //this method used to be called aceIsHigh(int sum)
                    return true;
                else
                    return false;
            }
        }
        return aceFound;
    }
}
//Misc. code (INCLUDING REMOVED TOAST MESSAGES/SYSTEM.OUT.PRINLN()'S, ORGANIZED BY METHOD:
    /*startGame method:
        //Toast.makeText(game, "It is " + playerNames.get(i) + "'s turn.", Toast.LENGTH_SHORT).show();
        //System.out.println("It is " + playerNames[i] + "'s turn.");
    * populateDeck method:
    *   N/A
    * dealCards method:
    *   N/A
    * printCards method:
    *   None
    * getCardFaceValueText method:
    *   N/A
    * playersTurn method:
    *    //Toast.makeText(GamePlayActivity.this, playersName.get(i) + " has BlackJack!! Your turn is over. You Won!", Toast.LENGTH_LONG).show();
    * isBlackJack method:
    *   N/A
    * promptUser method:
    *   textView: //Toast.makeText(GamePlayActivity.this, "It seems like it is working", Toast.LENGTH_LONG).show();
          //Toast.makeText(GamePlayActivity.this, playersNames.get(currentPlayer) + ", you Busted. Your turn is over; You lost ", Toast.LENGTH_LONG).show();
          //System.out.println(playersNames.get(currentPlayer)  + ", you Busted. Your turn is over; You lost ");
        textView2:  //Toast.makeText(GamePlayActivity.this, "You were dealt a " + getCardFaceValueText(players, currentPlayer, getPlayersFirstEmptyCardIndex(players[currentPlayer])-1),
                        //Toast.LENGTH_SHORT).show();
                //System.out.println("You were dealt a " + getCardFaceValueText(players, currentPlayer, getPlayersFirstEmptyCardIndex(players[currentPlayer])-1));
        textView3: //Toast.makeText(GamePlayActivity.this, "The activity is really working", Toast.LENGTH_LONG).show();
                //Toast.makeText(GamePlayActivity.this, playersNames.get(currentPlayer) + ", Your turn is over", Toast.LENGTH_LONG).show();
                //System.out.println(playersNames.get(currentPlayer) + ", Your turn is over");*
    * countCards method:
    *   N/A
    * isSumLessThanEleven:
    *   N/A
    * hit method:
    *   N/A
    * getPlayersFirstEmptyCard method:
    *   N/A
    * dealersTurn method:
    *   TEXTVIEW:  //System.out.println("It is the dealer's turn. \nThe dealers cards are:"); //added this to print dealers first 2 cards
        //Toast.makeText(GamePlayActivity.this, "It is the dealer's turn. \nThe dealers cards are: ", Toast.LENGTH_LONG).show();
        TEXTVIEW1: //Toast.makeText(GamePlayActivity.this, getCardFaceValueText(players, players.length -1, i), Toast.LENGTH_LONG).show();
           // System.out.println(getCardFaceValueText(players, players.length -1, i));
        TEXTVIEW2:   //Toast.makeText(GamePlayActivity.this, "To see what the dealer has dealt, press enter!", Toast.LENGTH_LONG).show();
                //System.out.println("To see what the dealer has dealt, press enter!");
        TEXTVIEW3:   //Toast.makeText(GamePlayActivity.this, "The dealer was dealt a "
                //+ getCardFaceValueText(players, players.length - 1, getPlayersFirstEmptyCardIndex(players[players.length - 1])-1) + " your cards now"
                //+ " count up to " + dSum, Toast.LENGTH_LONG).show();
                //System.out.println("The dealer was dealt a "
                //+ getCardFaceValueText(players, players.length - 1, getPlayersFirstEmptyCardIndex(players[players.length - 1])-1) + " your cards now"
                //+ " count up to " + dSum);
        TEXTVIEW4:  //Toast.makeText(GamePlayActivity.this, "To see what the dealer has dealt, press enter!", Toast.LENGTH_LONG).show();
                //System.out.println("To see what the dealer has dealt, press enter!");
                 //Toast.makeText(GamePlayActivity.this, "The dealer was dealt a "
                //+ getCardFaceValueText(players, players.length - 1, getPlayersFirstEmptyCardIndex(players[players.length - 1])-1) + " your cards now"
                //+ " count up to " + dSum, Toast.LENGTH_LONG).show();
                //System.out.println("The dealer was dealt a "
                //+ getCardFaceValueText(players, players.length - 1, getPlayersFirstEmptyCardIndex(players[players.length - 1])-1) + " your cards now"
                //+ " count up to " + dSum);
        TEXTVIEW5:   //Toast.makeText(GamePlayActivity.this, "\nAll players cards will be displayed:", Toast.LENGTH_LONG).show();
                //System.out.println("\nAll players cards will be displayed:");
        TEXTVIEW6:   //Toast.makeText(GamePlayActivity.this, "Dealer busted with a total of " + dSum, Toast.LENGTH_LONG).show();
                    //System.out.println("Dealer busted with a total of " + dSum );
        TEXTVIEW7:   //Toast.makeText(GamePlayActivity.this, playersNames.get(i) + ": won!", Toast.LENGTH_LONG).show();
                            //System.out.println(playersNames.get(i) + ": won!");
        TEXTVIEW8:   //Toast.makeText(GamePlayActivity.this, playersNames.get(i) + ": lost.", Toast.LENGTH_LONG).show();
                            //System.out.println(playersNames.get(i) + ": lost.");
        TEXTVIEW9:     //Toast.makeText(GamePlayActivity.this, playersNames.get(i) + ": won!", Toast.LENGTH_LONG).show();
                                //System.out.println(playersNames.get(i) + ": won!");
        TEXTVIEW10:  //Toast.makeText(GamePlayActivity.this, playersNames.get(i) + " and the Dealer -- PUSH, DRAW", Toast.LENGTH_LONG).show();
                                //System.out.println(playersNames.get(i) + " and the Dealer -- PUSH, DRAW");
        TEXTVIEW11: //Toast.makeText(GamePlayActivity.this, playersNames.get(i) + " lost.", Toast.LENGTH_LONG).show();
                                //System.out.println(playersNames.get(i) + " lost.");
        TEXTVIEW12: //Toast.makeText(GamePlayActivity.this, playersNames.get(i) + " lost.", Toast.LENGTH_LONG).show();
                            //System.out.println(playersNames.get(i) + " lost.");
    * isThereOneAceHigh method:
    *   N/A
    *
    * */




// Misc code that we are temporarily placing here in case we need it later:

/* VARIABLES:
    GridView myLayout; //changed from static
    Gallery playersGallery;
    List<Gallery> allGalleries;
    ImageView image;*/

 /* FROM WITHIN ONCREATE() METHOD:
 //myLayout = (GridView) findViewById(R.id.gridView1);
        //myLayout.setAdapter(new ImageAdapter(this));
    myLinearLayout = (LinearLayout)findViewById(R.id.myLinearLayout);
        //dynamically create a gallery for each player to hold their cards
        for (int i = 0; i < allNames.size(); i++) { //will need to find way to account for dealer
            grid = new GridView(GamePlayActivity.this);
            grid.setId(i);
            grid.setNumColumns(GridView.AUTO_FIT);
            //grid.setPadding(5, 5, 5, 5);
            grid.setHorizontalSpacing(5);
            grid.setVerticalSpacing(5);
            grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            grid.setAdapter(new ImageAdapter(this));
            myLayout.addView(grid);
            *//*playersGallery = new Gallery(GamePlayActivity.this);
            playersGallery.setId(i); //the allNames.get(i) method did not work here so the id is set to the array
            playersGallery.setAdapter(new ImageAdapter(this));
            myLayout.addView(playersGallery); *//*
        }
*/
//Store the deck of cards in an integer array so that it can be accessed easily
    /*Integer[] cards = {R.drawable.card0, R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6,
            R.drawable.card7, R.drawable.card8, R.drawable.card9, R.drawable.card10, R.drawable.card11, R.drawable.card12, R.drawable.card13, R.drawable.card14,
            R.drawable.card15, R.drawable.card16, R.drawable.card17, R.drawable.card18, R.drawable.card19, R.drawable.card20, R.drawable.card20, R.drawable.card21,
            R.drawable.card22, R.drawable.card23, R.drawable.card24, R.drawable.card25, R.drawable.card26, R.drawable.card27, R.drawable.card28, R.drawable.card29,
            R.drawable.card30, R.drawable.card31, R.drawable.card32, R.drawable.card33, R.drawable.card34, R.drawable.card35, R.drawable.card36, R.drawable.card37,
            R.drawable.card38, R.drawable.card39, R.drawable.card40, R.drawable.card41, R.drawable.card42, R.drawable.card43, R.drawable.card44, R.drawable.card45,
            R.drawable.card46, R.drawable.card47, R.drawable.card48, R.drawable.card49, R.drawable.card50, R.drawable.card51, R.drawable.card52}; **/
//GridView grid; //"kishmo kain hu"
//Gallery playersGallery;
//grid = (GridView) findViewById(R.id.gridView1);
//dynamically create Galleries with amount corresponding with # of players:
        /*for (int i = 0; i < allNames.size(); i++) {
            playersGallery = new Gallery(GamePlayActivity.this);
            playersGallery.setId(i); //the allNames.get(i) method did not work here so the id is set to the array
            playersGallery.setPadding(5, 5, 5, 5);
            // playersGallery.setAdapter(new ImageAdapter(this));
            grid.addView(playersGallery);
        }**/