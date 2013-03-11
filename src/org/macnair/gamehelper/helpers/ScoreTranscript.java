package org.macnair.gamehelper.helpers;

import java.util.ArrayList;

import org.macnair.gamehelper.HelperListActivity;
import org.macnair.gamehelper.R;
import org.macnair.gamehelper.R.id;
import org.macnair.gamehelper.R.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A fragment representing a single Helper detail screen. This fragment is
 * contained in a {@link HelperListActivity}
 */
public class ScoreTranscript extends Fragment implements OnClickListener, OnKeyListener {
	static final String TAG = "ScoreTranscript";
	private EditText mAddScoreText;
	private ArrayAdapter<Integer> mAdapter;
	private ArrayList<Integer> mScores = new ArrayList<Integer>();
	private ListView mTranscript;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ScoreTranscript() {
	}
	
	/** Called when the user touches the sub-total button */
	public void transcriptSubtotal(View view) {
		Log.d(TAG,String.format("Subtotal: %d", 1));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.score_transcript, container, false);
		
        mAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_list_item_1, mScores);
        mTranscript = (ListView) rootView.findViewById(R.id.list_transcript);
        mTranscript.setAdapter(mAdapter);
        
        mAddScoreText = (EditText) rootView.findViewById(R.id.text_transcript_input);
        mAddScoreText.setOnClickListener(this);
        mAddScoreText.setOnKeyListener(this);
		
		mTranscript.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view).getText().toString();
                Log.d(TAG,String.format("Item clicked: %s", item));
			}
		});
		
		return rootView;
	}
	
	private void addScore() {
	    String text = mAddScoreText.getText().toString();
	    try {
	    	mAdapter.add(Integer.parseInt(text));
	    } catch (java.lang.NumberFormatException e) { }
	    mAddScoreText.setText(null);
	    
	    updateRunningTotal();
	}
	 
	public void onClick(View v) {
		addScore();
    }
	
	public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                	addScore();
                    return true;
            }
        }
        return false;
    }
	
	public void updateRunningTotal() {
		Integer sum = 0;
		for (Integer i:mScores) {
			sum += i;
		}
		TextView mRunningTotalText = (TextView) getView().findViewById(R.id.running_total);
		mRunningTotalText.setText(sum.toString());
	}
}