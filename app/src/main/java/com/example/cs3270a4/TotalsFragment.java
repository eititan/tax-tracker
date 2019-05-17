package com.example.cs3270a4;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;


/**
 * A simple {@link Fragment} subclass.
 * SeekBar class
 */
public class TotalsFragment extends Fragment {

    private View root;
    private SeekBar seekBar;
    private onSeekChanged mCallback;

    interface onSeekChanged{
        void onSeekUpdate(int value);
    }

    public TotalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_totals, container, false);
    }

    /*
     * saving our state right before we change our state
     * sp object is writing when a state is changed
     */
    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEdit = sp.edit();
        //queuing for a commit
        spEdit.putInt("seekValue", seekBar.getProgress());
        //commit to disk
        spEdit.commit();
        //spEdit.apply(); also will save to disk but not immediately

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (onSeekChanged) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
            + " must implement the onSeekChanged Interface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        seekBar = (SeekBar)root.findViewById(R.id.seekBar);
        seekBar.setMax(25);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCallback.onSeekUpdate(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //getting our changed state value
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        int seekValue = sp.getInt("seekValue", 13);
        seekBar.setProgress(seekValue);


    }

    public int getTaxRate(){

        return seekBar.getProgress();
    }


}
