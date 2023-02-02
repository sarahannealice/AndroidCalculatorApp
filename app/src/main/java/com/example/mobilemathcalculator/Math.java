package com.example.mobilemathcalculator;

import static com.example.mobilemathcalculator.MainActivity.*;

import android.widget.Toast;
import java.text.DecimalFormat;

public class Math {
    //variables
    private static double MAXLIMIT = 999999999999999D;
    private static double MINLIMIT = -999999999999999D;
    private static double tempX;
    static DecimalFormat trailingZero = new DecimalFormat("#0.#####");

    public static String sum(double leftNum, double rightNum) {
        tempX = leftNum + rightNum;

        if (tempX > MAXLIMIT) {
            return "MAX LIMIT";
        } else if (tempX < MINLIMIT) {
            return "MIN LIMIT";
        } else {
            return trailingZero.format(tempX);
        }
    }//end sum method

    public static String difference(double leftNum, double rightNum) {
        tempX = leftNum - rightNum;

        if (tempX > MAXLIMIT) {
            return "MAX LIMIT";
        } else if (tempX < MINLIMIT) {
            return "MIN LIMIT";
        } else {
            return trailingZero.format(tempX);
        }
    }//end difference method

    public static String product (double leftNum, double rightNum) {
        tempX = leftNum * rightNum;

        if (tempX > MAXLIMIT) {
            return "MAX LIMIT";
        } else if (tempX < MINLIMIT) {
            return "MIN LIMIT";
        } else {
            return trailingZero.format(tempX);
        }
    }//end product method

    public static String quotient (double leftNum, double rightNum) {
        tempX = leftNum - rightNum;

        if (rightNum == 0) {
            return "NaN";//impossible calculation
        } else if (tempX > MAXLIMIT) {
            return "MAX LIMIT";
        } else if (tempX < MINLIMIT) {
            return "MIN LIMIT";
        } else {
            return trailingZero.format(tempX);
        }
    }//end quotient method
}//end math class