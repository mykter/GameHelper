package org.macnair.gamehelper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;

public class PlayerDialog extends DialogFragment {
	/* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface PlayerDialogListener {
        public void onPlayerDialogOK(List<Player> selectedPlayers);
    }
    
    private static final String TAG = "PlayerDialog";
    
    public final static String ARG_PLAYERS="players";
    public final static String ARG_MAX_PLAYERS="max";
    public final static String ARG_MIN_PLAYERS="min";
    
    // Use this instance of the interface to deliver action events
    private PlayerDialogListener mListener;
    private List<Player> players = null; 
    
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
        // prevent direct entry of number of players (so soft keyboard doesn't open when the dialog opens)
        anon.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        
        anon.setMinValue(0);
        anon.setMaxValue(16); //TODO: configurable min and max, determined by helper from getarguments
        
        // Populate the list with the saved players (Player.toString)
        players = getArguments().getParcelableArrayList(ARG_PLAYERS);
        ListView savedPlayers = (ListView) pdView.findViewById(R.id.player_select_list);
        savedPlayers.setAdapter(new ArrayAdapter<Player>(getActivity(),R.layout.dialog_player_select_item, players.toArray(new Player[players.size()])));
        savedPlayers.setItemsCanFocus(false); // to allow entire row to handle touch events
              
        builder.setTitle(R.string.players)
        	.setView(pdView)
        	.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	PlayerDialog.this.getDialog().cancel();
                }
            })
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int id) {
            		List<Player> selectedPlayers = new ArrayList<Player>();
            		
            		NumberPicker anon = (NumberPicker) PlayerDialog.this.getDialog().findViewById(R.id.anonymous_players);
            		for (int i = 0; i<anon.getValue(); i++) {
            			selectedPlayers.add(new Player(true, getActivity().getString(R.string.anonymous_prefix) + (i + 1)));
            		}
            		
            		ListView savedPlayers = (ListView) PlayerDialog.this.getDialog().findViewById(R.id.player_select_list);
            		SparseBooleanArray checked = savedPlayers.getCheckedItemPositions();
            		// Note getCheckedItemPositions only works if using a Checkable item in the list, like CheckedTextView
            		for (int i = 0; i < savedPlayers.getCount(); i++)
                    {
                        if(checked.get(i) == true) {
                        	selectedPlayers.add((Player) savedPlayers.getItemAtPosition(i));
                        	Log.d(TAG, "Selected player " + selectedPlayers.get(selectedPlayers.size() -1));
                        }
                    }     
            		
            		mListener.onPlayerDialogOK(selectedPlayers);
            	}
            });
        
        // Create the AlertDialog object and return it
        return builder.create();
	}
}
