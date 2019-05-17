package com.example.cs3270a4;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    private View root;
    private TextInputEditText edtAmount1, edtAmount2, edtAmount3, edtAmount4;
    private double a1, a2, a3,a4, totalValue=0, intIn;
    private UpdateValues mCallback;

    interface UpdateValues {
        void updateTotals(double totalCost);
    }

    public ItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (UpdateValues) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement the onSeekChanged Interface");
        }
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
        //queuing for a commit: I need all 4 variables to repopulate the EditTexts
        spEdit.putInt("a1", (int) a1);
        spEdit.putInt("a2", (int) a2);
        spEdit.putInt("a3", (int) a3);
        spEdit.putInt("a4", (int) a4);
        //commit to disk
        spEdit.commit();
        //spEdit.apply(); also will save to disk but not immediately

    }

    @Override
    public void onResume() {
        super.onResume();

        edtAmount1 = (TextInputEditText)root.findViewById(R.id.amount1);
        edtAmount2 = (TextInputEditText)root.findViewById(R.id.amount2);
        edtAmount3 = (TextInputEditText)root.findViewById(R.id.amount3);
        edtAmount4 = (TextInputEditText)root.findViewById(R.id.amount4);

        repopulateItems();

        edtAmount1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if(input == null || input.isEmpty()){
                    a1 = 0;
                }else {
                    intIn = Double.parseDouble(input);
                    a1 = intIn;
                }

                totalValue = a1 + a2 + a3 + a4;
                mCallback.updateTotals(totalValue);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtAmount2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if(input == null || input.isEmpty()){
                    a2 = 0;
                }else {
                    intIn = Double.parseDouble(input);
                    a2 = intIn;
                }

                totalValue = a1 + a2 + a3 + a4;
                mCallback.updateTotals(totalValue);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtAmount3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if(input == null || input.isEmpty()){
                    a3 = 0;
                }else {
                    intIn = Double.parseDouble(input);
                    a3 = intIn;
                }

                totalValue = a1 + a2 + a3 + a4;
                mCallback.updateTotals(totalValue);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtAmount4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if(input == null || input.isEmpty()){
                    a4 = 0;
                }else {
                    intIn = Double.parseDouble(input);
                    a4 = intIn;
                }

                totalValue = a1 + a2 + a3 + a4;
                mCallback.updateTotals(totalValue);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public double getTotalCost(){
        return totalValue;
    }


    private void repopulateItems(){
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        a1 = sp.getInt("a1", 0);
        a2 = sp.getInt("a2", 0);
        a3 = sp.getInt("a3", 0);
        a4 = sp.getInt("a4", 0);
        totalValue = a1+a2+a3+a4;
        mCallback.updateTotals(totalValue);

        //Hardcoded strings BUT are ONLY string representations of numbers
        edtAmount1.setText((int)a1+"");
        edtAmount2.setText((int)a2+"");
        edtAmount3.setText((int)a3+"");
        edtAmount4.setText((int)a4+"");

    }

}
