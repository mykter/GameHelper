package org.macnair.gamehelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class PlayerDialog extends DialogFragment {
	/* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface PlayerDialogListener {
        public void onPlayerDialogOK(DialogFragment dialog);
    }
    
    // Use this instance of the interface to deliver action events
    PlayerDialogListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate mListener, the PlayerDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the listener so we can send events to the host
            mListener = (PlayerDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement PlayerDialogListener");
        }
    }
    
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View pdView = inflater.inflate(R.layout.dialog_player_select, null);
        NumberPicker anon = (NumberPicker) pdView.findViewById(R.id.anonymous_players);
        anon.setMinValue(0);
        anon.setMaxValue(16);
        
        // prevent direct entry of number of players (so soft keyboard doesn't open when the dialog opens)
        anon.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        
        builder.setTitle(R.string.players)
        	.setView(pdView)
        	.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	PlayerDialog.this.getDialog().cancel();
                }
            })
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int id) {
            		mListener.onPlayerDialogOK(PlayerDialog.this);
            	}
            });
        
        // Create the AlertDialog object and return it
        return builder.create();
	}

}
