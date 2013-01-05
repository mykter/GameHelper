package org.macnair.gamehelper;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
	    		final View helper_list = findViewById(R.id.helper_list);
	    		final View detail_container = findViewById(R.id.helper_detail_container);
	    		Animator master_anim = AnimatorInflater.loadAnimator(this, R.animator.slide_left_tozero);
	    		Animator detail_anim = AnimatorInflater.loadAnimator(this, R.animator.slide_left_todivider);
	    		master_anim.setTarget(helper_list);
	    		master_anim.start();
	    		detail_anim.setTarget(detail_container);
	    		AnimatorSet animSet = new AnimatorSet();
	    		animSet.play(detail_anim).with(master_anim);
	    		animSet.start();
	        	
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
		// show the detail view in this activity by
		// adding or replacing the relevant helper fragment using a
		// fragment transaction.
		SimpleScoring fragment = new SimpleScoring();
		getFragmentManager().beginTransaction()
				.replace(R.id.helper_detail_container, (Fragment) fragment).commit();
		
		// Then hide the master list
		final View helper_list = findViewById(R.id.helper_list);
		final View detail_container = findViewById(R.id.helper_detail_container);
		Animator master_anim = AnimatorInflater.loadAnimator(this, R.animator.slide_left_tonegativedivider);
		Animator detail_anim = AnimatorInflater.loadAnimator(this, R.animator.slide_left_tozero);
		master_anim.setTarget(helper_list);
		detail_anim.setTarget(detail_container);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(detail_anim).with(master_anim);
		animSet.start();
		
		// Set the home icon to be an up icon
    	ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
