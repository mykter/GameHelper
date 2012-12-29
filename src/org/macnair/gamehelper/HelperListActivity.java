package org.macnair.gamehelper;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

		// helper list can be accessed like this:
		// ((HelperListFragment) getFragmentManager().findFragmentById(R.id.helper_list)).
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; show master list
	        	FragmentTransaction ft = getFragmentManager().beginTransaction();
	        	/*ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
	        	  .show(getFragmentManager().findFragmentById(R.id.helper_list))
	        	  .commit();
	        	*/
	        	final long duration = getResources().getInteger(android.R.integer.config_mediumAnimTime);
	        	View master = getFragmentManager().findFragmentById(R.id.helper_list).getView();
	    		//View detail = findViewById(R.id.helper_detail_container);
	    		//master.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
	    		master.setVisibility(View.VISIBLE);
	    		//master.animate().translationX(0).setDuration(duration);
	        	//detail.animate().translationXBy(master.getWidth()).setDuration(duration);
	        	
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
		/*FragmentTransaction ft = getFragmentManager().beginTransaction();
    	  ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
    	  .hide(getFragmentManager().findFragmentById(R.id.helper_list))
    	  .commit();
    	*/
		final long duration = getResources().getInteger(android.R.integer.config_mediumAnimTime);
		View master = getFragmentManager().findFragmentById(R.id.helper_list).getView();
		//View detail = findViewById(R.id.helper_detail_container);
		master.setVisibility(View.GONE);
		//master.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0));
		//master.animate().translationX(-1 * master.getWidth()).setDuration(duration);
    	//detail.animate().translationXBy(-1 * master.getWidth()).setDuration(duration);
    	
		// Set the home icon to be an up icon
    	ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
