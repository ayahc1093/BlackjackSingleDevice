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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GamePlayActivity extends Activity {

    //Store the deck of cards in an integer array so that it can be accessed easily
    private static Integer[] cards = {R.drawable.card0, R.drawable.card1, R.drawable.card2, R.drawable.card3, R.drawable.card4, R.drawable.card5, R.drawable.card6,
                R.drawable.card7, R.drawable.card8, R.drawable.card9, R.drawable.card10, R.drawable.card11, R.drawable.card12, R.drawable.card13, R.drawable.card14,
                R.drawable.card15, R.drawable.card16, R.drawable.card17, R.drawable.card18, R.drawable.card19, R.drawable.card20, R.drawable.card20, R.drawable.card21,
                R.drawable.card22, R.drawable.card23, R.drawable.card24, R.drawable.card25, R.drawable.card26, R.drawable.card27, R.drawable.card28, R.drawable.card29,
                R.drawable.card30, R.drawable.card31, R.drawable.card32, R.drawable.card33, R.drawable.card34, R.drawable.card35, R.drawable.card36, R.drawable.card37,
                R.drawable.card38, R.drawable.card39, R.drawable.card40, R.drawable.card41, R.drawable.card42, R.drawable.card43, R.drawable.card44, R.drawable.card45,
                R.drawable.card46, R.drawable.card47, R.drawable.card48, R.drawable.card49, R.drawable.card50, R.drawable.card51, R.drawable.card52};

    ArrayList<String> allNames;  //To store values of EditTexts (names)
    String player = "";
    static GridView myLayout;
    Gallery playersGallery;
    List<Gallery> allGalleries;
    ImageView image;

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

        myLayout = (GridView) findViewById(R.id.gridView1);
        myLayout.setAdapter(new ImageAdapter(this));

        //dynamically create a gallery for each player to hold their cards
        for (int i = 0; i < allNames.size(); i++) {
            playersGallery = new Gallery(GamePlayActivity.this);
            playersGallery.setId(i); //the allNames.get(i) method did not work here so the id is set to the array
            myLayout.addView(playersGallery);
        }
        image = new ImageView(this); //instantiated here
    }

    public static void startGame(ArrayList<String> playerNames) {
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
    public static void dealCards(boolean[] deck, int[] playerhand) {
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

        public static void printCards(int[][] playersHand, ArrayList<String> playersNames) {


            for (int i = 0; i < playersHand.length; i++) {
                for (int j = 0; j < playersHand[i].length && playersHand[i][j] != -1; j++) {



                    image.setImageResource(cards[playersHand[i][j]]);
                    myLayout.addView(image);

                }
            }
        }
    public class ImageAdapter extends BaseAdapter {

        Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return cards.length;
        }

        public Object getItem(int position) {

            return cards[position];
        }

        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(cards[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
            //imageView.setBackgroundResource(itemBackground);
            return imageView;
        }


    }

}
