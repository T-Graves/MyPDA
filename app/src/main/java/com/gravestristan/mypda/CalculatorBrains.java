package com.gravestristan.mypda;

/**
 * Created by Tristan on 4/26/2016.
 */
public class CalculatorBrains implements AppStatics {

    private double mOperand;
    private double mWaitingOperand;
    private String mWaitingOperator;

    public static final String ADD = "+";
    public static final String SUBTRACT = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";
    public static final String TOGGLE_SIGN = "+/-";
    public static final String INVERT = "1/x";

    public static final String CLEAR = "CLEAR";


    /**
     *
     */
    public CalculatorBrains() {
        mOperand = 0;
        mWaitingOperand = 0;
        mWaitingOperator = "";
    }

    /**
     *
     * @param operand
     */
    public void setOperand(double operand) {
        mOperand = operand;
    }

    /**
     *
     * @return
     */
    public double getResult() {
        return mOperand;
    }

    /**
     *
     * @return
     */
    public String toString(){
        return Double.toString(mOperand);
    }

    /**
     *
     * @param operator
     * @return
     */
    protected double performOperation(String operator){
        if (operator.equals(CLEAR)) {
            mOperand = 0;
            mWaitingOperator = "";
            mWaitingOperand = 0;
        }else if (operator.equals(INVERT)){
            if (mOperand != 0){
                mOperand = 1 / mOperand;
            }
        }else if (operator.equals(TOGGLE_SIGN)){
            mOperand = -mOperand;
        }else{
            performWaitingOperation();
            mWaitingOperator = operator;
            mWaitingOperand = mOperand;
        }

        return mOperand;
    }

    /**
     *
     */
    protected void performWaitingOperation() {
        if (mWaitingOperator.equals(ADD)) {
            mOperand = mWaitingOperand + mOperand;
        } else if(mWaitingOperator.equals(SUBTRACT)) {
            mOperand = mWaitingOperand - mOperand;
        } else if(mWaitingOperator.equals(MULTIPLY)){
            mOperand = mWaitingOperand * mOperand;
        } else if(mWaitingOperator.equals(DIVIDE)){
            if (mOperand != 0){
                mOperand = mWaitingOperand / mOperand;
            }
        }
    }

}
