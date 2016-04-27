package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

/**
 * Created by Tristan on 4/19/2016.
 */
public class BasicCalculatorFragment extends Fragment implements AppStatics, View.OnClickListener {

    private Button mNum0;
    private Button mNum1;
    private Button mNum2;
    private Button mNum3;
    private Button mNum4;
    private Button mNum5;
    private Button mNum6;
    private Button mNum7;
    private Button mNum8;
    private Button mNum9;

    private Button mAdd;
    private Button mSub;
    private Button mMulti;
    private Button mDiv;
    private Button mToggleSign;
    private Button mInvert;

    private Button mDot;
    private Button mEquals;

    private Button mClear;

    private EditText mCurrentResultScreen;

    private Boolean userIsInTheMiddleOfTypingANumber = false;
    private CalculatorBrains mCalcBrains;
    private static final String DIGITS = "0123456789.";

    DecimalFormat df = new DecimalFormat("@############");


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mCalcBrains = new CalculatorBrains();
        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_calculator, container, false);

        mNum0 = (Button) view.findViewById(R.id.num0);
        mNum1 = (Button) view.findViewById(R.id.num1);
        mNum2 = (Button) view.findViewById(R.id.num2);
        mNum3 = (Button) view.findViewById(R.id.num3);
        mNum4 = (Button) view.findViewById(R.id.num4);
        mNum5 = (Button) view.findViewById(R.id.num5);
        mNum6 = (Button) view.findViewById(R.id.num6);
        mNum7 = (Button) view.findViewById(R.id.num7);
        mNum8 = (Button) view.findViewById(R.id.num8);
        mNum9 = (Button) view.findViewById(R.id.num9);

        mAdd = (Button) view.findViewById(R.id.add);
        mSub = (Button) view.findViewById(R.id.sub);
        mMulti = (Button) view.findViewById(R.id.multi);
        mDiv = (Button) view.findViewById(R.id.div);
        mToggleSign = (Button) view.findViewById(R.id.toggle_sign);
        mInvert = (Button) view.findViewById(R.id.invert);

        mDot = (Button) view.findViewById(R.id.dot);
        mEquals = (Button) view.findViewById(R.id.equals);

        mClear = (Button) view.findViewById(R.id.clear);

        mCurrentResultScreen = (EditText) view.findViewById(R.id.current_result_screen);

        mNum0.setOnClickListener(this);
        mNum1.setOnClickListener(this);
        mNum2.setOnClickListener(this);
        mNum3.setOnClickListener(this);
        mNum4.setOnClickListener(this);
        mNum5.setOnClickListener(this);
        mNum6.setOnClickListener(this);
        mNum7.setOnClickListener(this);
        mNum8.setOnClickListener(this);
        mNum9.setOnClickListener(this);
        mDot.setOnClickListener(this);

        mAdd.setOnClickListener(this);
        mSub.setOnClickListener(this);
        mMulti.setOnClickListener(this);
        mDiv.setOnClickListener(this);
        mToggleSign.setOnClickListener(this);
        mInvert.setOnClickListener(this);

        mEquals.setOnClickListener(this);
        mClear.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        String buttonPressed = ((Button) v).getText().toString();

        if (DIGITS.contains(buttonPressed)) {
            if (userIsInTheMiddleOfTypingANumber) {
                if (buttonPressed.equals(".") && mCurrentResultScreen.getText().toString().contains(".")) {

                }else{
                    mCurrentResultScreen.append(buttonPressed);
                }
            } else {
                if (buttonPressed.equals(".")) {
                    mCurrentResultScreen.setText(0 + buttonPressed);
                } else {
                    mCurrentResultScreen.setText(buttonPressed);
                }

                userIsInTheMiddleOfTypingANumber = true;
            }
        } else {
            if (userIsInTheMiddleOfTypingANumber) {
                mCalcBrains.setOperand(Double.parseDouble(mCurrentResultScreen.getText().toString()));
                userIsInTheMiddleOfTypingANumber = false;
            }
            mCalcBrains.performOperation(buttonPressed);
            mCurrentResultScreen.setText(df.format(mCalcBrains.getResult()));
        }
    }
}
