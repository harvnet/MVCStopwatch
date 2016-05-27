package com.algonquincollege.harv0116.mvcstopwatch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 *
 * Purpose of this app is to make a stopwatch program with start/pause and reset functionality
 * @author Paul Harvey (harv0116@algonquinlive.com)
 *
 */

public class AboutDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //TODO pro-tip: cascading messages
        builder.setTitle(R.string.action_about)
                .setMessage(R.string.author)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}