package org.macnair.gamehelper;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.LayoutTransition;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;

/**
 * An activity representing a list of Helpers. The
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link HelperListFragment} and the item details (if present) is a
 * {@link HelperDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link HelperListFragment.Callbacks} interface to listen for item selections.
 */
public class HelperListActivity extends Activity implements
		HelperListFragment.Callbacks {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helper_list);

		final ViewGroup twopane = (ViewGroup) findViewById(R.id.helper_list).getParent();
		LayoutTransition transitioner = new LayoutTransition();
		Animator anim = AnimatorInflater.loadAnimator(this, R.animator.slide_out_left);
		transitioner.setAnimator(LayoutTransition.DISAPPEARING, anim);
		anim = AnimatorInflater.loadAnimator(this, R.animator.slide_in_left);
		transitioner.setAnimator(LayoutTransition.APPEARING, anim);
		twopane.setLayoutTransition(transitioner);
        
		// helper list can be accessed like this:
		// ((HelperListFragment) getFragmentManager().findFragmentById(R.id.helper_list)).
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; show master list
	        	View master = getFragmentManager().findFragmentById(R.id.helper_list).getView();
	    		master.setVisibility(View.VISIBLE);
	        	
	        	// Set the home icon to no longer be an up icon
	        	ActionBar actionBar = getActionBar();
	            actionBar.setDisplayHomeAsUpEnabled(false);
	            
	            return true;
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
		// In two-pane mode, show the detail view in this activity by
		// adding or replacing the detail fragment using a
		// fragment transaction.
		Bundle arguments = new Bundle();
		arguments.putString(HelperDetailFragment.ARG_ITEM_ID, id);
		HelperDetailFragment fragment = new HelperDetailFragment();
		fragment.setArguments(arguments);
		getFragmentManager().beginTransaction()
				.replace(R.id.helper_detail_container, (Fragment) fragment).commit();
		
		// Then hide the master list
		View master = getFragmentManager().findFragmentById(R.id.helper_list).getView();
		master.setVisibility(View.GONE);
    	
		// Set the home icon to be an up icon
    	ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
