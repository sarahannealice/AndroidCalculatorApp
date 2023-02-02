package com.example.mobilemathcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    //views
    Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero;
    Button btnClear, btnPosNeg, btnDel, btnDec;
    Button btnDiv, btnMult, btnSub, btnAdd, btnCalc;
    static TextView displayMini;
    static TextView displayMain;

    //variables
    static DecimalFormat trailingZero = new DecimalFormat("#0.#####");
    static private String rightEq;
    static private String leftEq;
    static private double leftNum;
    static private double rightNum;
    static private int calcClick;//counter for calculate button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing buttons -- numbers
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive);
        btnSix = findViewById(R.id.btnSix);
        btnSeven = findViewById(R.id.btnSeven);
        btnEight = findViewById(R.id.btnEight);
        btnNine = findViewById(R.id.btnNine);
        btnZero = findViewById(R.id.btnZero);
        btnPosNeg = findViewById(R.id.btnPosNeg);
        btnDec = findViewById(R.id.btnDec);

        //initializing buttons -- operands
        btnDiv = findViewById(R.id.btnDiv);
        btnMult = findViewById(R.id.btnMult);
        btnSub = findViewById(R.id.btnSub);
        btnAdd = findViewById(R.id.btnAdd);
        btnCalc = findViewById(R.id.btnCalc);

        //initializing buttons -- miscellaneous
        btnDel = findViewById(R.id.btnDel);
        btnClear = findViewById(R.id.btnClear);

        //initializing textviews
        displayMini = findViewById(R.id.displayMini);
        displayMain = findViewById(R.id.displayMain);

        //set num button event to same listener
        btnOne.setOnClickListener(numBtnClicked);
        btnTwo.setOnClickListener(numBtnClicked);
        btnThree.setOnClickListener(numBtnClicked);
        btnFour.setOnClickListener(numBtnClicked);
        btnFive.setOnClickListener(numBtnClicked);
        btnSix.setOnClickListener(numBtnClicked);
        btnSeven.setOnClickListener(numBtnClicked);
        btnEight.setOnClickListener(numBtnClicked);
        btnNine.setOnClickListener(numBtnClicked);
        btnZero.setOnClickListener(numBtnClicked);
        btnPosNeg.setOnClickListener(numBtnClicked);
        btnDec.setOnClickListener(numBtnClicked);

        //set operand button event to same listener
        btnDiv.setOnClickListener(opBtnClicked);
        btnMult.setOnClickListener(opBtnClicked);
        btnSub.setOnClickListener(opBtnClicked);
        btnAdd.setOnClickListener(opBtnClicked);
        btnCalc.setOnClickListener(opBtnClicked);

        //set miscellaneous button event to same listener
        btnDel.setOnClickListener(miscBtnClicked);
        btnClear.setOnClickListener(miscBtnClicked);
    }//end onCreate


    //------------------------------ONCLICK LISTENERS------------------------------//
    //onClick for numbers
    public View.OnClickListener numBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //resets main display if number is pressed directly after 'calculate'
            if (calcClick == 1) {
                displayMain.setText("0");
                rightEq = displayMain.getText().toString();
                calcClick = 0;
            } else {
                rightEq = displayMain.getText().toString();
            }

            switch (view.getId()) {
                case R.id.btnOne:
                    addToDisplay("1", rightEq);
                    break;
                case R.id.btnTwo:
                    addToDisplay("2", rightEq);
                    break;
                case R.id.btnThree:
                    addToDisplay("3", rightEq);
                    break;
                case R.id.btnFour:
                    addToDisplay("4", rightEq);
                    break;
                case R.id.btnFive:
                    addToDisplay("5", rightEq);
                    break;
                case R.id.btnSix:
                    addToDisplay("6", rightEq);
                    break;
                case R.id.btnSeven:
                    addToDisplay("7", rightEq);
                    break;
                case R.id.btnEight:
                    addToDisplay("8", rightEq);
                    break;
                case R.id.btnNine:
                    addToDisplay("9", rightEq);
                    break;
                case R.id.btnZero:
                    addToDisplay("0", rightEq);
                    break;
                case R.id.btnPosNeg:
                    positiveNegative(rightEq);
                    break;
                case R.id.btnDec:
                    addToDisplay(".", rightEq);
                    break;
            }
        }
    };//end number button listener

    //onClick for operands
    public View.OnClickListener opBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rightEq = displayMain.getText().toString();
            leftEq = displayMini.getText().toString();

            //reset counter for 'calculate' check
            if (calcClick == 1 ) {
                calcClick = 0;
            }

            switch (view.getId()) {
                case R.id.btnDiv:
                    addToMiniDisplay("÷", rightEq, leftEq);
                    break;
                case R.id.btnMult:
                    addToMiniDisplay("*", rightEq, leftEq);
                    break;
                case R.id.btnSub:
                    addToMiniDisplay("-", rightEq, leftEq);
                    break;
                case R.id.btnAdd:
                    addToMiniDisplay("+", rightEq, leftEq);
                    break;
                case R.id.btnCalc:
                    calcClick = 1;//
                    addToMiniDisplay("=", rightEq, leftEq);
                    break;
            }
        }
    };//end operand button listener

    //onClick for miscellaneous
    public View.OnClickListener miscBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnDel:
                    rightEq = displayMain.getText().toString();
                    deleteDigit(rightEq);
                    break;
                case R.id.btnClear:
                    displayMain.setText("0");
                    displayMini.setText("");
                    break;
            }
        }
    };//end miscellaneous button listener


    //----------------------------------METHODS----------------------------------//
    //method for converting inputted number to positive/negative
    public static void positiveNegative(String rightEq) {
        String tempX;
        //regex for 'one' digit
        Pattern pattern = Pattern.compile("^[1-9]$");
        Matcher matcher = pattern.matcher(rightEq.substring(0,1));
        boolean isDigit = matcher.matches();

        //checks string for '-' and adds/removes it accordingly
        if (rightEq.equals("0")) {
            //do nothing -- 0 cannot be a negative number
        } else if (rightEq.equals("0.")) {
            displayMain.setText("0");
        } else if (isDigit) {
            tempX = '-' + rightEq;
            displayMain.setText(tempX);
        } else {
            tempX = rightEq.substring(1);
            displayMain.setText(tempX);
        }
    }//end positive-negative method

    //method for delete button
    public static void deleteDigit(String rightEq) {
        String tempX;
        //regex for 'one' digit
        Pattern pattern = Pattern.compile("^[0-9]$");
        Matcher matcher = pattern.matcher(rightEq);
        boolean oneDigitLeft = matcher.matches();

        if (rightEq.equals("0")) {
            //do nothing -- keeps screen at 0
        } else if (oneDigitLeft) {
            //changes screen to 0 when last digit is to be removed
            displayMain.setText("0");
        } else {
            //removes last digit in the String
            tempX = rightEq.substring(0, rightEq.length()-1);
            displayMain.setText(tempX);
        }
    }//end delete button method

    //method to add inputted number to main display
    public static void addToDisplay(String inputDigit, String rightEq) {
        String tempX = rightEq.substring(rightEq.length()-1);//gets last char in a String
        String tempMiniDisplay = displayMini.getText().toString();

        if (tempMiniDisplay.equals("MAX LIMIT") || tempMiniDisplay.equals("MIN LIMIT") || tempMiniDisplay.equals("NAN")) {
            displayMini.setText("");
            displayMain.setText(inputDigit);
        } else if (rightEq.length() > 13) {
            //do nothing
        } else if (rightEq.equals("0")) {
            //keeps 0 and adds decimal after
            if (inputDigit.equals(".")) {
                rightEq += inputDigit;
                displayMain.setText(rightEq);
            } else {
                displayMain.setText(inputDigit);
            }
        } else if (inputDigit.equals(".")) {
            //prevents adding too many decimals throughout input String
            if (!tempX.equals(".") && !rightEq.contains(".")) {
                rightEq += inputDigit;
                displayMain.setText(rightEq);
            }
        } else {
            rightEq += inputDigit;
            displayMain.setText(rightEq);
        }
    }//end main display method

    //takes input and sorts according to request by user and prints to mini-display
    //**some calculations are printed to main display in this method**//
    public void addToMiniDisplay(String tempOp, String rightEq, String leftEq) {
        String tempX;

        if (leftEq.equals("MAX LIMIT") || leftEq.equals("MIN LIMIT") || leftEq.equals("NAN")) {
            displayMini.setText("");
            displayMain.setText("0");
        } else if (tempOp.equals("=")) {//calculate is pressed
            if (rightEq.equals("0") && leftEq.equals("")) {
                //do nothing
            } else if (!leftEq.equals("")) {
                StringBuffer sb = new StringBuffer(leftEq);
                sb.deleteCharAt(sb.length()-1);//deletes last character of String
                rightNum = Double.parseDouble(rightEq);
                leftNum = Double.parseDouble(sb.toString());//converts sb to String to double

                //sends both left and right numbers to corresponding math functions in Math class
                //prints to main display and resets mini-display to blank
                if (leftEq.charAt(leftEq.length() - 1) == '+') {
                    tempX = Math.sum(leftNum, rightNum);
                    if (tempX.equals("MAX LIMIT") || tempX.equals("MIN LIMIT")) {
                        Toast.makeText(getApplicationContext(),"max limit is 14 digits", Toast.LENGTH_SHORT).show();
                    }
                    displayMini.setText("");
                    displayMain.setText(tempX);
                } else if (leftEq.charAt(leftEq.length() - 1) == '-') {
                    tempX = Math.difference(leftNum, rightNum);
                    if (tempX.equals("MAX LIMIT") || tempX.equals("MIN LIMIT")) {
                        Toast.makeText(getApplicationContext(),"max limit is 14 digits", Toast.LENGTH_SHORT).show();
                    }
                    displayMini.setText("");
                    displayMain.setText(tempX);
                } else if (leftEq.charAt(leftEq.length() - 1) == '*') {
                    tempX = Math.product(leftNum, rightNum);
                    if (tempX.equals("MAX LIMIT") || tempX.equals("MIN LIMIT")) {
                        Toast.makeText(getApplicationContext(),"max limit is 14 digits", Toast.LENGTH_SHORT).show();
                    }
                    displayMini.setText("");
                    displayMain.setText(tempX);
                } else if (leftEq.charAt(leftEq.length() - 1) == '÷') {
                    tempX = Math.quotient(leftNum, rightNum);
                    if (tempX.equals("MAX LIMIT") || tempX.equals("MIN LIMIT")) {
                        Toast.makeText(getApplicationContext(),"max limit is 14 digits", Toast.LENGTH_SHORT).show();
                    }
                    displayMini.setText("");
                    displayMain.setText(tempX);
                }
            }//end press calc statement
        } else if (!leftEq.equals("")) {//not first time pressing operand in equation
            StringBuffer sb = new StringBuffer(leftEq);
            sb.deleteCharAt(sb.length()-1);
            rightNum = Double.parseDouble(rightEq);
            leftNum = Double.parseDouble(sb.toString());

            //sends both left and right numbers to corresponding math functions in Math class
            //prints to mini-display and resets main display to "0"
             if (leftEq.charAt(leftEq.length()-1) == '+') {
                tempX = Math.sum(leftNum, rightNum);
                if (tempX.equals("MAX LIMIT") || tempX.equals("MIN LIMIT")) {
                    displayMini.setText(tempX);
                    Toast.makeText(getApplicationContext(),"max limit is 14 digits", Toast.LENGTH_SHORT).show();
                } else {
                    displayMini.setText(tempX + tempOp);
                }
            } else if (leftEq.charAt(leftEq.length()-1) == '-') {
                tempX = Math.difference(leftNum, rightNum);
                if (tempX.equals("MAX LIMIT") || tempX.equals("MIN LIMIT")) {
                    displayMini.setText(tempX);
                    Toast.makeText(getApplicationContext(),"max limit is 14 digits", Toast.LENGTH_SHORT).show();
                } else {
                    displayMini.setText(tempX + tempOp);
                }
            } else if (leftEq.charAt(leftEq.length()-1) == '*') {
                tempX = Math.product(leftNum, rightNum);
                if (tempX.equals("MAX LIMIT") || tempX.equals("MIN LIMIT")) {
                    displayMini.setText(tempX);
                    Toast.makeText(getApplicationContext(),"max limit is 14 digits", Toast.LENGTH_SHORT).show();
                } else {
                    displayMini.setText(tempX + tempOp);
                }
            } else if (leftEq.charAt(leftEq.length()-1) == '÷') {
                tempX = Math.quotient(leftNum, rightNum);
                if (tempX.equals("MAX LIMIT") || tempX.equals("MIN LIMIT")) {
                    displayMini.setText(tempX);
                    Toast.makeText(getApplicationContext(),"max limit is 14 digits", Toast.LENGTH_SHORT).show();
                } else if (tempX.equals("NAN")) {
                    displayMini.setText(tempX);
                } else {
                    displayMini.setText(tempX + tempOp);
                }
            }
            displayMain.setText("0");

        } else if (leftEq.equals("")) {//first time pressing operand in equation
            if (rightEq.substring(rightEq.length()-1).equals(".")) {//removes trailing decimal
                rightEq = rightEq.substring(0,rightEq.length()-1);
                tempX = rightEq + tempOp;
                displayMini.setText(tempX);
                displayMain.setText("0");
            } else {
                tempX = trailingZero.format(Double.parseDouble(rightEq)) + tempOp;
                displayMini.setText(tempX);
                displayMain.setText("0");
            }
        } else if (rightEq.equals("0")) {//press operand twice consecutively
            //removes operand and replaces with new choice
            tempX = leftEq.substring(0, leftEq.length()-1) + tempOp;
            displayMini.setText(tempX);
            displayMain.setText("0");
        }
    }//end mini display method
}//end main activity class