package org.macnair.gamehelper;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.app.Activity;

/**
 * An activity representing a list of Helpers. The
 * activity presents the list of items and item details side-by-side using two
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
		HelperListFragment.Callbacks {

    @SuppressWarnings("unused")
	private static final String TAG = "MyHelperListActivity";  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helper_list);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; show master list
	    		FragmentManager fm = getFragmentManager();
	    		fm.beginTransaction().show(fm.findFragmentById(R.id.helper_list)).commit();
	            
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
		// show the detail view in this activity by
		// adding or replacing the relevant helper fragment using a
		// fragment transaction.
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		SimpleScoring fragment = new SimpleScoring();
		ft.replace(R.id.helper_detail_container, (Fragment) fragment);
		ft.commit();

		// Then hide the master list, in a separate transaction so the first back press merely reveals the list
		ft = fm.beginTransaction();
		ft.hide(fm.findFragmentById(R.id.helper_list));
		// Up icon management is done by HelperListFragment.onHiddenChanged
		ft.addToBackStack(null);
		ft.commit();
	}
}
