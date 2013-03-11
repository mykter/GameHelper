package org.macnair.gamehelper;

import java.util.ArrayList;
import java.util.List;

import org.macnair.gamehelper.helpers.SimpleScoring;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * An activity representing a list of Helpers.
 * The activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link HelperListFragment} and the item details (if present) is a
 * {@link SimpleScoring}.
 * <p>
 * This activity also implements the required
 * {@link HelperListFragment.Callbacks} interface to listen for item selections.
 */
public class HelperListActivity extends Activity implements
		HelperListFragment.Callbacks, 
		PlayerDialog.PlayerDialogListener {

	private static final String TAG = "MyHelperListActivity";  

    private PlayersModel pm = null;
    private SimpleScoring currentHelper = null;
    
    public interface Helper {
    	public void updatePlayers(List<Player> newPlayers);
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helper_list);
		pm = new PlayersModel();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.players, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; show master list
	        	// Note the reveal isn't added to the back stack
	    		FragmentManager fm = getFragmentManager();
	    		fm.beginTransaction().show(fm.findFragmentById(R.id.helper_list)).commit();
	            
	            return true;
	            
	        case R.id.player_chooser:
	        	DialogFragment pc = new PlayerDialog();
	        	
	        	// Pass the saved Players as an array argument 
	        	Bundle bdl = new Bundle();
	        	bdl.putParcelableArrayList(PlayerDialog.ARG_PLAYERS, 
	        			(ArrayList<Player>)pm.getSortedPlayers(Player.SEEN_THEN_NAME_ORDER));
	        	pc.setArguments(bdl);
	        	
	        	pc.show(getFragmentManager(),"players");
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * Callback method from {@link HelperListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		// show the detail view in this activity by
		// adding or replacing the relevant helper fragment using a
		// fragment transaction.
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if (fm.findFragmentByTag(id) == null) {
			// TODO: pick the helper based on id and helper.NAME ? 
			SimpleScoring fragment = new SimpleScoring();
                        currentHelper = fragment;
			ft.replace(R.id.helper_detail_container, (Fragment) fragment, id);
			ft.addToBackStack("Select Helper");
		}
		
		// Then hide the master list. In the same transaction pressing back will remove the added fragment.
		// The user should use "Up" to reveal the list without undoing the action
		// Up icon management is done by HelperListFragment.onHiddenChanged
		ft.hide(fm.findFragmentById(R.id.helper_list));
		ft.commit();
	}

	@Override
	public void onPlayerDialogOK(List<Player> selectedPlayers) {
		// Register the selected (named) players as having been seen
		for (Player p : selectedPlayers) {
			if (! p.isAnonymous()) {
				p.setSeenNow();
			}
		}
		
		Log.d(TAG, "Got " + selectedPlayers.size() + " players");
		if (currentHelper != null) {
			currentHelper.updatePlayers(selectedPlayers);
		}
	}
}
