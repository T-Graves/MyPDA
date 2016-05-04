package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Tip Calculator. Calculates tips based off of user entered information.
 */
public class TipCalculatorFragment extends Fragment implements AppStatics {

    private EditText mBillAmount;
    private EditText mNumberOfPeople;
    private EditText mCustomTip;

    private RadioButton mFivePercent;
    private RadioButton mTenPercent;
    private RadioButton mFifteenPercent;

    private TextView mTipPerPerson;
    private TextView mBillPerPerson;

    private Button mCalculateTipButton;
    private Button mClearFields;

    private double billAmount = 0.0;
    private int numberOfPeople = 0;
    private double tipAmount = 0.0;
    private double tipPerPersonOutput = 0.0;
    private double billPerPerson = 0.0;


    /**
     * The onCreate method for the TipCalculatorFragment. It doesn't do anything right now.
     * @param savedInstanceState The bundle being passed in.
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    /**
     * The onCreateView method for the TipCalculatorFragment. It connects all the fields of the tip
     * form and sets onClick listeners for the two buttons.
     * @param inflater The layout inflater being passed in.
     * @param container The view group being passed in.
     * @param savedInstanceState The bundle being passed in.
     * @return The view that is created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_tip_calculator, container, false);

        mBillAmount = (EditText) view.findViewById(R.id.bill_amount_field);
        mNumberOfPeople = (EditText) view.findViewById(R.id.bill_split);
        mCustomTip = (EditText) view.findViewById(R.id.custom_tip_field);

        mFivePercent = (RadioButton) view.findViewById(R.id.radio_five_percent);
        mTenPercent = (RadioButton) view.findViewById(R.id.radio_ten_percent);
        mFifteenPercent = (RadioButton) view.findViewById(R.id.radio_fifteen_percent);

        mTipPerPerson = (TextView) view.findViewById(R.id.tip_per_person);
        mBillPerPerson = (TextView) view.findViewById(R.id.bill_per_person);

        mCalculateTipButton = (Button) view.findViewById(R.id.calculate_button);
        mCalculateTipButton.setOnClickListener(new View.OnClickListener() {
            /**
             * The onClick method for the mCalculateTipButton. It calls the getNumbersToCalculate method.
             * @param v The view being passed in.
             */
            @Override
            public void onClick(View v) {
                getNumbersToCalculate();
            }
        });

        mClearFields = (Button) view.findViewById(R.id.clear_button);
        mClearFields.setOnClickListener(new View.OnClickListener() {
            /**
             * The onClick method for the mClearFields button. It clears all fields in the form.
             * @param v The view being passed in.
             */
            @Override
            public void onClick(View v) {
                // Clear variables
                billAmount = 0.0;
                numberOfPeople = 0;
                tipAmount = 0.0;
                tipPerPersonOutput = 0.0;
                billPerPerson = 0.0;

                // Clear EditTexts
                mBillAmount.setText(null);
                mNumberOfPeople.setText(null);
                mCustomTip.setText(null);

                // Reset radio buttons
                if(mFivePercent.isChecked()){
                    mFivePercent.setChecked(false);
                }
                if(mTenPercent.isChecked()){
                    mTenPercent.setChecked(false);
                }
                if(mFifteenPercent.isChecked()){
                    mFifteenPercent.setChecked(false);
                }

                // Clear output fields
                mTipPerPerson.setText("");
                mBillPerPerson.setText("");

            }
        });

        return view;
    }

    /**
     * This method grabs the user inputs from the form and calls the calculateBill method if all
     * user input is present.
     */
    private void getNumbersToCalculate(){
        if(mBillAmount.getText() != null && !mBillAmount.getText().toString().equals("")){
            billAmount = Double.parseDouble(mBillAmount.getText().toString());
        }

        if(mNumberOfPeople.getText() != null && !mNumberOfPeople.getText().toString().equals("")){
            numberOfPeople = Integer.parseInt(mNumberOfPeople.getText().toString());
        }

        findTipAmount();
        if(billAmount != 0 && numberOfPeople != 0 && tipAmount != 0){
            calculateBill();
        }
    }

    /**
     * The findTipAmount method. It figures out what the tip amount should be based on which radio
     * button is pressed, or if there is anything in the custom tip field.
     */
    private void findTipAmount() {
        if(mCustomTip.getText() != null && !mCustomTip.getText().toString().equals("")){
            tipAmount = Integer.parseInt(mCustomTip.getText().toString());
            tipAmount = tipAmount / 100;
        }else{
            if (mFivePercent.isChecked()) {
                tipAmount = 0.05;
            } else if (mTenPercent.isChecked()) {
                tipAmount = 0.10;
            } else if (mFifteenPercent.isChecked()) {
                tipAmount = 0.15;
            }else{
                tipAmount = 0;
            }
        }
    }

    /**
     * This method calculates and output the tip per person and bill per person.
     */
    private void calculateBill(){
        tipPerPersonOutput = (double) Math.round(((billAmount * tipAmount) / numberOfPeople) * 100) / 100;
        billPerPerson = (double) Math.round((billAmount / numberOfPeople) * 100) / 100;
        String tipOutputString = "$" + tipPerPersonOutput;
        mTipPerPerson.setText(tipOutputString);
        String billPerPersonString = "$" + billPerPerson;
        mBillPerPerson.setText(billPerPersonString);
    }
}
