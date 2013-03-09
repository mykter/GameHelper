package org.macnair.gamehelper;

import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ArrayAdapter;

public class PlayerDialog extends DialogFragment {
	/* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface PlayerDialogListener {
        public void onPlayerDialogOK(List<Player> selectedPlayers);
    }
    
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
        ListView savedPlayers = (ListView) pdView.findViewById(R.id.player_list);
        savedPlayers.setAdapter(new ArrayAdapter<Player>(getActivity(),R.layout.dialog_player_select_item, R.id.player_select_name, players.toArray(new Player[players.size()])));
        savedPlayers.setItemsCanFocus(false); // to allow entire row to handle touch events
        savedPlayers.setOnItemClickListener(new ListView.OnItemClickListener() { // toggle checked on touch
        	 @Override
             public void onItemClick(AdapterView<?> a, View v, int i, long l) {
        		 CheckBox box = (CheckBox) v.findViewById(R.id.player_select_checkbox);
        		 box.setChecked(!box.isChecked());
        	 }
        });
        
        builder.setTitle(R.string.players)
        	.setView(pdView)
        	.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	PlayerDialog.this.getDialog().cancel();
                }
            })
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int id) {
            		List<Player> players = new ArrayList<Player>();
            		players.add(new Player(true,"Player 1"));
            		mListener.onPlayerDialogOK(players);
            	}
            });
        
        // Create the AlertDialog object and return it
        return builder.create();
	}

}

