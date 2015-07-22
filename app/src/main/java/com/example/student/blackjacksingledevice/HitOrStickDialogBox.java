package com.example.student.blackjacksingledevice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by mcberliner on 7/14/2015.
 */
public class HitOrStickDialogBox extends DialogFragment {

    //DOESN'T SEEM TO BE NECESSARY:
    Intent intent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String playersName = getArguments().getString("current player");
        int sumOfCards = getArguments().getInt("sum of cards");
        intent = new Intent(getActivity(), GamePlayActivity.class);
        //Create new Bundle to be sent back during onClick to GamePlayActivity.java
        final Bundle bundleFromHitOrStick = new Bundle();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle(playersName +"'s turn")
                .setMessage("Your cards add up to: " + sumOfCards + ". \nWould you like to hit or stick?")
                .setPositiveButton("Hit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(HitOrStickDialogBox.this);
                        //Intent.putExtra("Player Choice", true);
                        //bundleFromHitOrStick.putBoolean("Player choice", true);
                        //HitOrStickDialogBox.this.startActivity(intent);
                    }


                }).setNegativeButton("Stick", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(HitOrStickDialogBox.this);
                        //intent.putExtra("Player Choice", false);
                        //bundleFromHitOrStick.putBoolean("Player choice", false);
                        //HitOrStickDialogBox.this.startActivity(intent);
                    }
                });
        AlertDialog dialog = alertDialogBuilder.create();
        return dialog;
    }
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}

 /*DialogInterface.OnClickListener pListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            intent.putExtra("Player choice", true);
            HitOrStickDialogBox.this.startActivity(intent);
        }
    };

    DialogInterface.OnClickListener nListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            intent.putExtra("Player choice", false);
            HitOrStickDialogBox.this.startActivity(intent);
        }
    };*/
/*
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
*/
/**
 * Created by mcberliner on 7/14/2015.
 *//*
public class HitOrStickDialogBox extends DialogFragment {
    Intent intent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        */
/*String playersName = intent.getStringExtra("current player");
        int sumOfCards = intent.getIntExtra("sum of cards", 0);*//*
        setCancelable(false);
        return inflater.inflate(R.layout.dialog_fragment_layout, null);
    }
}
       */
/* AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(getActivity()).setTitle(playersName + " turn").setMessage("Your cards add up to: " + sumOfCards + "Would you like to hit or stick?")
                .setPositiveButton("Hit", pListener).setNegativeButton("Stick", nListener);
        return alerDialogBuilder.create();
    }
*/

    /*DialogInterface.OnClickListener pListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            intent.putExtra("Player choice", true);
            HitOrStickDialogBox.this.startActivity(intent);
        }
    };
    DialogInterface.OnClickListener nListener = new DialogInterface.OnClickListener() {
        @Override
         public void onClick(DialogInterface arg0, int arg1) {
            intent.putExtra("Player choice", false);
            HitOrStickDialogBox.this.startActivity(intent);
        }
    };*//*
*/