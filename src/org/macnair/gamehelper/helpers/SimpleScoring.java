package org.macnair.gamehelper.helpers;

import java.util.List;

import org.macnair.gamehelper.HelperListActivity;
import org.macnair.gamehelper.Player;
import org.macnair.gamehelper.R;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a single Helper detail screen. This fragment is
 * contained in a {@link HelperListActivity}
 */
public class SimpleScoring
		extends Fragment
		implements HelperListActivity.Helper {
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

		updatePlayers(null);
		return rootView;
	}

	@Override
	public void updatePlayers(List<Player> newPlayers) {
		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		if (newPlayers != null) {
			for (Player p : newPlayers) {
				Log.d(NAME,"Adding " + p.getName());
				transaction.add(R.id.simple_scorer, new ScoreTranscript());
			}
			transaction.commit();
		}
	}
}

