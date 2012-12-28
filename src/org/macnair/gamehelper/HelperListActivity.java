package org.macnair.gamehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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
public class HelperListActivity extends FragmentActivity implements
		HelperListFragment.Callbacks {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helper_list);

		// In two-pane mode, list items should be given the
		// 'activated' state when touched.
		((HelperListFragment) getSupportFragmentManager().findFragmentById(
				R.id.helper_list)).setActivateOnItemClick(true);

		// TODO: If exposing deep links into your app, handle intents here.
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
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.helper_detail_container, fragment).commit();
	}
}
