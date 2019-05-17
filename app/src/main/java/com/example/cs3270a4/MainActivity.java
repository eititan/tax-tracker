package com.example.cs3270a4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements TotalsFragment.onSeekChanged,
                   TaxFragment.GetData,
                   ItemsFragment.UpdateValues{

    private FragmentManager fragMan;
    private TaxFragment taxAmount;
    private TotalsFragment totalSeek;
    private ItemsFragment itemsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragMan = getSupportFragmentManager();
        fragMan.beginTransaction()
                    .replace(R.id.fragTotal, new TotalsFragment(), "FragTotals")
                    .replace(R.id.fragTax, new TaxFragment(), "FragTax")
                    .replace(R.id.fragItems, new ItemsFragment(), "FragItems")
                    .commit();
    }

    /*
     * An implemented class from TotalsFragment
     */
    @Override
    public void onSeekUpdate(int progress) {
        taxAmount = (TaxFragment) fragMan.findFragmentById(R.id.fragTax);
        itemsFrag = (ItemsFragment) fragMan.findFragmentById(R.id.fragItems);

        if(taxAmount != null){
            taxAmount.updateTaxRate(progress);
            taxAmount.updateTotalCost(itemsFrag.getTotalCost());
        }
    }

    @Override
    public int getTaxRate() {

        totalSeek = (TotalsFragment)fragMan.findFragmentById(R.id.fragTotal);

        if(totalSeek != null){
            return totalSeek.getTaxRate();
        }

        return 0;
    }

    @Override
    public void updateTotals(double totalAmount) {
        taxAmount = (TaxFragment) fragMan.findFragmentById(R.id.fragTax);

        if(taxAmount != null){
            taxAmount.updateTotalCost(totalAmount);

        }

    }
}
