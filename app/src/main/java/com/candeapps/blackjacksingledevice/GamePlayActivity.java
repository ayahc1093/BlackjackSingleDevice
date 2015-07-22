package com.candeapps.blackjacksingledevice;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*import com.candeapps.blackjacksingledevice.blackjacksingledevice.R;*/

import java.util.ArrayList;

public class GamePlayActivity extends Activity implements HitOrStickDialogBox.NoticeDialogListener {

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
    boolean isHit = true;
    //Button btnPlayGame;

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
        game.startGame(allNames);
    }


    public GamePlayActivity() {
    }

    //PUT BUTTON FOR DEALER'S TURN HERE!!!!
       /* Button btnDealersTurn = new Button(game);
        btnDealersTurn.setText("Dealer's Turn");
        btnDealersTurn.setOnClickListener(new MyDealersTurnBtnOnClickListener(players, deck, playerNames));
        myLinearLayout.addView(btnDealersTurn);*/
    //game.dealersTurn(players, deck, playerNames);

    public void startGame(final ArrayList<String> playerNames) {

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

        //call the print cards method over here so that the cards should only print once
        game.printCards(players, playerNames);
        //Button btnPlayGame = (Button)findViewById(R.id.btnPlayGame);

        for (int i = 0; i < players.length - 2; i++) {
            TextView textView = new TextView(game);
            textView.setText("\nIt is " + playerNames.get(i) + "'s turn.");
            textView.setPadding(15, 0, 0, 0);
            myLinearLayout.addView(textView);
            game.playersTurn(i, players, deck, playerNames);
        }
    }

    public void playersTurn(int i, int[][] players,boolean[] deck, ArrayList<String> playersName) {
        //We dont need to call this method more than once - the cards will stay on the board so that all players can see them at all times
        //game.printCards(players, playersName);
        if(isBlackJack(players[i])){
            TextView textView = new TextView(game);
            textView.setText(playersName.get(i) + " has BlackJack!! Your turn is over. You Won!");
            textView.setPadding(15, 0, 0, 0);
            myLinearLayout.addView(textView);
            return;
        }
        Button btnPlayGame = new Button(game);
        btnPlayGame.setText("Play Game");
        btnPlayGame.setOnClickListener(new MyPlayGameBtnOnClickListener(i, players, deck, playersName));
        myLinearLayout.addView(btnPlayGame);
        //game.promptUser(i, players, deck, playersName);
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
            for (int j = 0; j < playersHand[i].length && playersHand[i][j] != -1; j++) {
                String cardFaceValue = getCardFaceValueText(playersHand, i, j);
                TextView card = new TextView(game);
                card.setText(cardFaceValue);
                card.setPadding(15, 0, 0, 0);
                myLinearLayout.addView(card);
                if(playersNames.get(i) == "Dealer")
                break;
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

    public void promptUser(int currentPlayer, int[][] players, boolean[] deck, ArrayList<String> playersNames) {
        //boolean isHit = true;
        do{
            int sumOfCards = countCards(players[currentPlayer]);
            if (sumOfCards > 21) {
                TextView textView = new TextView(game);
                textView.setText(playersNames.get(currentPlayer) + ", sorry to say this, but you Busted. Your turn is over; You lost.");
                textView.setPadding(15, 0, 0, 0);
                myLinearLayout.addView(textView);
                break;
            }
            Bundle bundleFromGamePlayActivity = new Bundle();
            bundleFromGamePlayActivity.putString("current player", playersNames.get(currentPlayer));
            bundleFromGamePlayActivity.putInt("sum of cards", sumOfCards);
            Toast.makeText(game, "Out of fragment: isHit = " + isHit, Toast.LENGTH_SHORT).show();
            HitOrStickDialogBox dialogBox = new HitOrStickDialogBox();
            dialogBox.setArguments(bundleFromGamePlayActivity);
            dialogBox.show(getFragmentManager(), "My Fragment");


            if(isHit)  {
                //Toast.makeText(GamePlayActivity.this, "The activity is working", Toast.LENGTH_LONG).show();
                hit(deck, players[currentPlayer]);
                TextView textview2 = new TextView(game);
                textview2.setText(playersNames.get(currentPlayer)+ ", you were dealt a " + getCardFaceValueText(players, currentPlayer, getPlayersFirstEmptyCardIndex(players[currentPlayer])-1));
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
        Button btnDealersTurn = new Button(game);
        btnDealersTurn.setText("Dealer's Turn");
        btnDealersTurn.setOnClickListener(new MyDealersTurnBtnOnClickListener(players, deck, playersNames));
        myLinearLayout.addView(btnDealersTurn);
        // input.close();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        gotHit(true);
        Toast.makeText(game, " ++ isHit = " + isHit, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        gotHit(false);
        Toast.makeText(game, "-- isHit = " + isHit, Toast.LENGTH_SHORT).show();
    }

    public boolean gotHit(Boolean value){
        return isHit = value;
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
                        } else {
                            TextView textView12 = new TextView(game);
                            textView12.setText(playersNames.get(i) + " lost.");
                            textView12.setPadding(15, 0, 0, 0);
                            myLinearLayout.addView(textView12);

                        }
                    }
            }
        }while(isHit);
        Button btnContinue = new Button(game);
        btnContinue.setText("Continue");
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GamePlayActivity.this, EndGameActivity.class);
                startActivity(intent);
            }
        });
        myLinearLayout.addView(btnContinue);
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

    public class MyPlayGameBtnOnClickListener implements View.OnClickListener  {

        int i;
        int[][] player;
        boolean[] deck;
        ArrayList<String> playerNames;
        public MyPlayGameBtnOnClickListener(int i, int[][] player, boolean[] deck, ArrayList<String> playerNames) {
            this.i = i;
            this.player = player;
            this.deck = deck;
            this.playerNames = playerNames;
        }

        @Override
        public void onClick(View v)        {
            promptUser(i, player, deck, playerNames);
        }
    };

    public class MyDealersTurnBtnOnClickListener implements View.OnClickListener  {

        int[][] player;
        boolean[] deck;
        ArrayList<String> playerNames;
        public MyDealersTurnBtnOnClickListener(int[][] player, boolean[] deck, ArrayList<String> playerNames) {
            this.player = player;
            this.deck = deck;
            this.playerNames = playerNames;
        }

        @Override
        public void onClick(View v) {
            dealersTurn(player, deck, playerNames);
        }
    };
}
