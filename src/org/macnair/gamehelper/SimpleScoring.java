package org.macnair.gamehelper;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Helper detail screen. This fragment is
 * either contained in a {@link HelperListActivity} in two-pane mode (on
 * tablets) or a {@link HelperDetailActivity} on handsets.
 */
public class SimpleScoring extends Fragment {
	final static String NAME = "Simple Scoring";
	
	@Override
	public String toString() {
		return NAME;
	}
	
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SimpleScoring() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_helper_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		((TextView) rootView.findViewById(R.id.helper_detail))
					.setText("Simple Scorer!");

		return rootView;
	}
}
