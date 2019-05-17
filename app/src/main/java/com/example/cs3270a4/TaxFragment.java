package com.example.cs3270a4;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaxFragment extends Fragment {

    private View root;
    private Locale defaultLocale = Resources.getSystem().getConfiguration().locale;
    private NumberFormat numFormat = NumberFormat.getCurrencyInstance(defaultLocale);
    private TextView taxSeek, totalAmount, taxCost;
    private BigDecimal bdRate, bdTotalCost, bdTaxedAmount, bdDecimalRate;
    private int totalAmountSaveVal;

    private GetData mCallback;

    interface GetData{
        int getTaxRate();
    }

    public TaxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_tax, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mCallback = (GetData) activity;

        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement the GetData Interface");
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
        //queuing for a commit
        spEdit.putInt("totalAmount", totalAmountSaveVal);
        //commit to disk
        spEdit.commit();
        //spEdit.apply(); also will save to disk but not immediately

    }

    @Override
    public void onResume() {
        super.onResume();

        taxSeek = (TextView)root.findViewById(R.id.tvTaxRate);
        taxCost = (TextView)root.findViewById(R.id.tvTaxAmount);
        totalAmount = (TextView)root.findViewById(R.id.tvTotalAmount);

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        totalAmountSaveVal = sp.getInt("totalAmount", 0);

        updateTaxRate(mCallback.getTaxRate());
        updateTotalCost(totalAmountSaveVal);
    }

    public void updateTaxRate(int rate){
        bdRate = new BigDecimal(rate);
        //takes our rate and moves it 2 decimal places over
        bdDecimalRate = bdRate.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

        if(taxSeek!= null){
            taxSeek.setText(getResources().getString(R.string.tax_rate) + " "
                    + bdRate.doubleValue() + "%");
        }
    }

    public void updateTotalCost(double totalCost){
        totalAmountSaveVal = (int) totalCost;
        bdTotalCost = new BigDecimal(totalCost);

        Log.d(TAG, "updateTotalCost: We are updating cost");
        if(bdTotalCost != null && totalCost > 0){

            bdTaxedAmount = (bdTotalCost.multiply(bdDecimalRate));
            bdTotalCost = bdTotalCost.add(bdTaxedAmount);

            taxCost.setText(getResources().getString(R.string.tax_amount) + " "
                    + numFormat.format(bdTaxedAmount.doubleValue()));
            totalAmount.setText(numFormat.format(bdTotalCost.doubleValue()));


        }

        if(taxCost !=  null && totalCost == 0){
            taxCost.setText(getResources().getString(R.string.tax_amount) + " "
                    + numFormat.format(bdTaxedAmount.doubleValue()));
            totalAmount.setText(numFormat.format(bdTotalCost.doubleValue()));

        }





    }
}
