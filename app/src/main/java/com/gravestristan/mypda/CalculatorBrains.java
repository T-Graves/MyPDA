package com.gravestristan.mypda;

/**
 * This is the brains of the basic calculator. The calculations are done here.
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
     * The basic constructor for the class. It initializes the operator and operand containsers.
     */
    public CalculatorBrains() {
        mOperand = 0;
        mWaitingOperand = 0;
        mWaitingOperator = "";
    }

    /**
     * This method is used to set mOperand.
     * @param operand The operand to be used.
     */
    public void setOperand(double operand) {
        mOperand = operand;
    }

    /**
     * The result to be passed back.
     * @return The calculated number.
     */
    public double getResult() {
        return mOperand;
    }

    /**
     * A toString method to turn a double into a string.
     * @return The double that has been turned into a string.
     */
    public String toString(){
        return Double.toString(mOperand);
    }

    /**
     * This method is used to handle the clear, invert, and toggle sign buttons if they are pressed.
     * otherwise it calls the performWaitingOperation method to do the regular operations like
     * +, -, *, or /
     * @param operator The operator that has been pressed.
     * @return The calculated double.
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
     * This method does the regular calculations.
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
