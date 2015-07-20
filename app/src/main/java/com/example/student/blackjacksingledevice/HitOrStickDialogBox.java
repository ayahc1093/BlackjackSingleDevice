package com.example.student.blackjacksingledevice;

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

    Intent intent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String playersName = intent.getStringExtra("current player");
        int sumOfCards = intent.getIntExtra("sum of cards", 0);
        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(getActivity()).setTitle(playersName + " turn").setMessage("Your cards add up to: " + sumOfCards + "Would you like to hit or stick?")
                .setPositiveButton("Hit", pListener).setNegativeButton("Stick", nListener);
        return alerDialogBuilder.create();
    }

    DialogInterface.OnClickListener pListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            //intent.putExtra("Player choice", true);
            //HitOrStickDialogBox.this.startActivity(intent);
        }
    };

    DialogInterface.OnClickListener nListener = new DialogInterface.OnClickListener() {
        @Override
         public void onClick(DialogInterface arg0, int arg1) {
            //intent.putExtra("Player choice", false);
            //HitOrStickDialogBox.this.startActivity(intent);

        }
    };
}
