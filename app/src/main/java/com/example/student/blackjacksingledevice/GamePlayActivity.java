package com.example.student.blackjacksingledevice;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class GamePlayActivity extends Activity {

    //Store the deck of cards in an integer array so that it can be accessed easily
    Integer[] cards = {R.drawable.card0, R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6,
                R.drawable.card7, R.drawable.card8, R.drawable.card9, R.drawable.card10, R.drawable.card11, R.drawable.card12, R.drawable.card13, R.drawable.card14,
                R.drawable.card15, R.drawable.card16, R.drawable.card17, R.drawable.card18, R.drawable.card19, R.drawable.card20, R.drawable.card20, R.drawable.card21,
                R.drawable.card22, R.drawable.card23, R.drawable.card24, R.drawable.card25, R.drawable.card26, R.drawable.card27, R.drawable.card28, R.drawable.card29,
                R.drawable.card30, R.drawable.card31, R.drawable.card32, R.drawable.card33, R.drawable.card34, R.drawable.card35, R.drawable.card36, R.drawable.card37,
                R.drawable.card38, R.drawable.card39, R.drawable.card40, R.drawable.card41, R.drawable.card42, R.drawable.card43, R.drawable.card44, R.drawable.card45,
                R.drawable.card46, R.drawable.card47, R.drawable.card48, R.drawable.card49, R.drawable.card50, R.drawable.card51, R.drawable.card52};

    ArrayList<String> allNames;  //To store values of EditTexts (names)
    String player = "";
    LinearLayout myLinearLayout;
    GridView grid; //"kishmo kain hu"
    Gallery playersGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        allNames = new ArrayList<>();
        allNames = getIntent().getStringArrayListExtra("all");
        //Add the dealer to the end of the array list of players
        allNames.add("dealer");

        // To test that the ArrayList successfully transferred over with the Intent:
        for (int i = 0; i < allNames.size(); i++) {
            player = allNames.get(i);
            Toast.makeText(GamePlayActivity.this, "Player " + (i + 1) + ": " + player, Toast.LENGTH_SHORT).show();
        }

        myLinearLayout = (LinearLayout)findViewById(R.id.myLinearLayout);
        grid = (GridView) findViewById(R.id.gridView1);
        //dynamically create GridViews with amount corresponding with # of players:
        for (int i = 0; i < allNames.size(); i++) {
            playersGallery = new Gallery(GamePlayActivity.this);
            playersGallery.setId(i); //the allNames.get(i) method did not work here so the id is set to the array
            playersGallery.setPadding(5, 5, 5, 5);
           // playersGallery.setAdapter(new ImageAdapter(this));
            grid.addView(playersGallery);
        }

        //GamePlayActivity game = new GamePlayActivity();
        //game.startGame(allNames);
    }

   /* public GamePlayActivity() {}
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

        printCards(players, playerNames);
    }

    //populates the available card spaces to with a value of -1
    public static void populateDeck(int[] playerDeck) {
        for( int i = 0;i < playerDeck.length; i++){
            playerDeck[i] = -1;
        }
    }

    //deals 2 cards to all the players and flips the value of the card to true in the deck array and makes sure that one card is not dealt twice
    public void dealCards(boolean[] deck, int[] playerhand) {
        int count = 0;
                while(count < 2) {
                    int deal = (int)(Math.random() * 52);
                    while (deck[deal])
                        deal = (int)(Math.random() * 52);
                    deck[deal] = true;
                    playerhand[count] = deal;
                    ++count;
                }
            }

        public void printCards(int[][] playersHand, ArrayList<String> playersNames) {


            for (int i = 0; i < playersHand.length; i++) {
                for (int j = 0; j < playersHand[i].length && playersHand[i][j] != -1; j++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(R.drawable.card0);


                }
            }
        }**/
    public class ImageAdapter extends BaseAdapter {

        Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return cards.length;
        }

        public Object getItem(int position) {

            return position;
        }

        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setImageResource(cards[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, GridView.AUTO_FIT));
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView)convertView;
            }
            return imageView;
        }
    }
}

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