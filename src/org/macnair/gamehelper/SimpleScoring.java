package org.macnair.gamehelper;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a single Helper detail screen. This fragment is
 * contained in a {@link HelperListActivity}
 */
public class SimpleScoring extends Fragment {
	final static String NAME = "Simple Scoring";
	
	@Override
	public String toString() {
		return NAME;
	}
	
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
		View rootView = inflater.inflate(R.layout.simple_scorer, container, false);

		Fragment transcriptFragment[] = {new ScoreTranscript(), new ScoreTranscript(), new ScoreTranscript(), new ScoreTranscript()};
		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.add(R.id.simple_scorer, transcriptFragment[0]);
		transaction.add(R.id.simple_scorer, transcriptFragment[1]);
		transaction.add(R.id.simple_scorer, transcriptFragment[2]);
		transaction.add(R.id.simple_scorer, transcriptFragment[3]);
		transaction.commit();
		return rootView;
	}
}

