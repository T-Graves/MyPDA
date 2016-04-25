package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Tristan on 4/19/2016.
 */
public class BasicCalculatorFragment extends Fragment implements AppStatics {

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

    private Button mDot;
    private Button mEquals;

    private Button mClear;

    private EditText mCalcScreen;
    private EditText mCurrentResultScreen;

    private Double val1;
    private Double val2;
    private Double result;

    private boolean add, sub, multi, div = false;
    private boolean operationEnd = false;
    private boolean operationError = false;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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

        mDot = (Button) view.findViewById(R.id.dot);
        mEquals = (Button) view.findViewById(R.id.equals);

        mClear = (Button) view.findViewById(R.id.clear);

        mCalcScreen = (EditText) view.findViewById(R.id.calc_screen);
        mCurrentResultScreen = (EditText) view.findViewById(R.id.current_result_screen);

        mCurrentResultScreen.setText("0");

        mNum0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentResultScreen.getText().toString().equals("0")){
                    mCurrentResultScreen.setText("0");
                }else{
                    mCurrentResultScreen.setText(mCurrentResultScreen.getText() + "0");
                }
            }
        });
        mNum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentResultScreen.getText().toString().equals("0")) {
                    mCurrentResultScreen.setText("1");
                } else {
                    mCurrentResultScreen.setText(mCurrentResultScreen.getText() + "1");
                }
            }
        });
        mNum2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentResultScreen.getText().toString().equals("0")) {
                    mCurrentResultScreen.setText("2");
                } else {
                    mCurrentResultScreen.setText(mCurrentResultScreen.getText() + "2");
                }
            }
        });
        mNum3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentResultScreen.getText().toString().equals("0")) {
                    mCurrentResultScreen.setText("3");
                } else {
                    mCurrentResultScreen.setText(mCurrentResultScreen.getText() + "3");
                }
            }
        });
        mNum4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentResultScreen.getText().toString().equals("0")) {
                    mCurrentResultScreen.setText("4");
                } else {
                    mCurrentResultScreen.setText(mCurrentResultScreen.getText() + "4");
                }
            }
        });
        mNum5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentResultScreen.getText().toString().equals("0")) {
                    mCurrentResultScreen.setText("5");
                } else {
                    mCurrentResultScreen.setText(mCurrentResultScreen.getText() + "5");
                }
            }
        });
        mNum6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentResultScreen.getText().toString().equals("0")){
                    mCurrentResultScreen.setText("6");
                }else{
                    mCurrentResultScreen.setText(mCurrentResultScreen.getText() + "6");
                }
            }
        });
        mNum7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentResultScreen.getText().toString().equals("0")){
                    mCurrentResultScreen.setText("7");
                }else{
                    mCurrentResultScreen.setText(mCurrentResultScreen.getText() + "7");
                }
            }
        });
        mNum8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentResultScreen.getText().toString().equals("0")){
                    mCurrentResultScreen.setText("8");
                }else{
                    mCurrentResultScreen.setText(mCurrentResultScreen.getText() + "8");
                }
            }
        });
        mNum9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentResultScreen.getText().toString().equals("0")){
                    mCurrentResultScreen.setText("9");
                }else{
                    mCurrentResultScreen.setText(mCurrentResultScreen.getText() + "9");
                }
            }
        });
        mDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentResultScreen.setText(mCurrentResultScreen.getText() + ".");
            }
        });


        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operationEnd){
                    if(result != null){
                        val1 = result;
                    }else{
                        val1 = Double.parseDouble(mCurrentResultScreen.getText().toString());
                    }

                    mCalcScreen.setText(val1 + " + ");

                    result = null;
                    operationEnd = false;
                }else{
                    Double valTemp = Double.parseDouble(mCurrentResultScreen.getText().toString());
                    if(val1 != null){
                        val1 = val1 + valTemp;
                    }else{
                        val1 = valTemp;
                    }

                    if(mCalcScreen.getText() == null){
                        mCalcScreen.setText(val1 + " + ");
                    }else{
                        mCalcScreen.setText(mCalcScreen.getText().toString() + valTemp + " + ");
                    }

                }

                add = true;
                sub = false;
                multi = false;
                div = false;
                mCurrentResultScreen.setText("0");
            }
        });
        mSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operationEnd){
                    if(result != null){
                        val1 = result;
                    }else{
                        val1 = Double.parseDouble(mCurrentResultScreen.getText().toString());
                    }

                    mCalcScreen.setText(val1 + " - ");

                    result = null;
                    operationEnd = false;
                }else{
                    Double valTemp = Double.parseDouble(mCurrentResultScreen.getText().toString());
                    if(val1 != null){
                        val1 = val1 - valTemp;
                    }else{
                        val1 = valTemp;
                    }

                    if(mCalcScreen.getText() == null){
                        mCalcScreen.setText(val1 + " - ");
                    }else{
                        mCalcScreen.setText(mCalcScreen.getText().toString() + valTemp + " - ");
                    }
                }

                add = false;
                sub = true;
                multi = false;
                div = false;
                mCurrentResultScreen.setText("0");
            }
        });
        mMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operationEnd){
                    if(result != null){
                        val1 = result;
                    }else{
                        val1 = Double.parseDouble(mCurrentResultScreen.getText().toString());
                    }

                    mCalcScreen.setText(val1 + " * ");

                    result = null;
                    operationEnd = false;
                }else{
                    Double valTemp = Double.parseDouble(mCurrentResultScreen.getText().toString());
                    if(val1 != null){
                        val1 = val1 * valTemp;
                    }else{
                        val1 = valTemp;
                    }

                    if(mCalcScreen.getText() == null){
                        mCalcScreen.setText(val1 + " * ");
                    }else{
                        mCalcScreen.setText(mCalcScreen.getText().toString() + valTemp + " * ");
                    }
                }

                add = false;
                sub = false;
                multi = true;
                div = false;
                mCurrentResultScreen.setText("0");
            }
        });
        mDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(operationEnd){
                    if(result != null){
                        val1 = result;
                    }else{
                        val1 = Double.parseDouble(mCurrentResultScreen.getText().toString());
                    }

                    mCalcScreen.setText(val1 + " / ");

                    result = null;
                    operationEnd = false;
                }else{
                    if(!operationError){
                        Double valTemp = Double.parseDouble(mCurrentResultScreen.getText().toString());
                        if(val1 != null){
                            val1 = val1 / valTemp;
                        }else{
                            val1 = valTemp;
                        }
                        if(mCalcScreen.getText() == null){
                            mCalcScreen.setText(val1 + " / ");
                        }else{
                            mCalcScreen.setText(mCalcScreen.getText().toString() + valTemp + " / ");
                        }
                    }
                }

                add = false;
                sub = false;
                multi = false;
                div = true;
                mCurrentResultScreen.setText("0");
            }
        });

        mEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(val1 != null){
                    val2 = Double.parseDouble(mCurrentResultScreen.getText().toString());
                    if(add){
                        result = val1 + val2;
                        mCurrentResultScreen.setText(result + "");
                        mCalcScreen.setText(mCalcScreen.getText().toString() + val2);
                        add = false;
                    }
                    if(sub){
                        result = val1 - val2;
                        mCurrentResultScreen.setText(result + "");
                        mCalcScreen.setText(mCalcScreen.getText().toString() + val2);
                        sub = false;
                    }
                    if(multi){
                        result = val1 * val2;
                        mCurrentResultScreen.setText(result + "");
                        mCalcScreen.setText(mCalcScreen.getText().toString() + val2);
                        multi = false;
                    }
                    if(div){
                        if(val2 == 0.0){
                            operationError = true;
                            mCurrentResultScreen.setText("cannot divide by 0");
                            return;
                        }else{
                            result = val1 / val2;
                            mCurrentResultScreen.setText(result + "");
                            mCalcScreen.setText(mCalcScreen.getText().toString() + val2);
                        }
                        div = false;
                    }
                    operationEnd = true;
                }else{
                    val1 = Double.parseDouble(mCurrentResultScreen.getText().toString());
                    if(val1 == 0.0){
                        mCurrentResultScreen.setText("0");
                    }else{
                        mCurrentResultScreen.setText(val1 + "");
                    }
                    operationEnd = true;
                }
            }
        });
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentResultScreen.setText("0");
                mCalcScreen.setText(null);
                add = false;
                sub = false;
                multi = false;
                div = false;
                operationEnd = false;
                operationError = false;

                val1 = null;
                val2 = null;
                result = null;
            }
        });

        return view;
    }
}
