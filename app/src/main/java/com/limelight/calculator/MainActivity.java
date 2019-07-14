package com.limelight.calculator;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText displayExpression;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonDot;
    private Button buttonPlus;
    private Button buttonMinus;
    private Button buttonMultiply;
    private Button buttonDivision;
    private Button buttonClear;
    private Button equalsButton;
    private String newText;
    private TextView displayAnswer;
    private Vibrator hapticFeedback;

    private char lastCharacter;
    private int numberOfDecimals = 0;
    private ArrayList<Boolean> decimalUsed = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        initializeViews();
        setUpOnClickListeners();
    }

    void initializeViews(){
        //Points various view subtypes to their objects
        displayExpression = findViewById(R.id.displayExpression);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonDot = findViewById(R.id.buttonDot);
        buttonPlus =  findViewById(R.id.buttonPlus);
        buttonMinus =  findViewById(R.id.buttonMinus);
        buttonMultiply =  findViewById(R.id.buttonMultiplication);
        buttonDivision =  findViewById(R.id.buttonDivision);
        buttonClear = findViewById(R.id.buttonClear);
        equalsButton = findViewById(R.id.equalsButton);
        displayAnswer = findViewById(R.id.displayAnswer);
        hapticFeedback = (Vibrator)this.getSystemService(VIBRATOR_SERVICE);
        decimalUsed.add(numberOfDecimals,false);
        displayExpression.setShowSoftInputOnFocus(false);
    }

    //Sets up OnClickListeners for buttons
    public void setUpOnClickListeners(){

        //On Click Listener for Number Buttons and Dot Button
        View.OnClickListener numberListener = new View.OnClickListener() {
            //Overrides onClick method
            @Override
            public void onClick(View v) {
                Button numButton = (Button)v;
                newText = displayExpression.getText().toString();
                if(newText.length()>0)
                    lastCharacter = newText.charAt(newText.length() - 1); //gets last character from displayExpression
                if(numButton.getId() == R.id.buttonDot) {
                    if (decimalUsed.get(numberOfDecimals))
                        return;
                    else if ((operatorCheck(lastCharacter) || newText.length() < 1)) {
                        displayExpression.append("0");
                        //addToArray("0");
                    }
                }
                String number = numButton.getText().toString(); //Gets text from button
                //addToArray(number);
                displayExpression.append(number);//Adds the number to the displayExpression Text.
                if(numButton.getId() == R.id.buttonDot)
                    decimalUsed.add(numberOfDecimals,true);
            }
        };

        //On Click Listener for Operators and Clear Button
        View.OnClickListener operatorListener = new View.OnClickListener() {
            //Overrides onClick method
            @Override
            public void onClick(View v) {

                Button operator = (Button)v;
                String operators = operator.getText().toString(); //gets operator from button pressed
                newText = displayExpression.getText().toString(); //gets text from displayExpression.

                //checks if the length of string is greater than zero
                if(newText.length() > 0) {
                    lastCharacter = newText.charAt(newText.length() - 1); //gets last character from displayExpression
                    //Check's if the button pressed is Clear Button
                    if (operator.getId() == R.id.buttonClear) {
                        //if(expressionArray.isEmpty())
                        //convertToArrayList();
                        newText = newText.substring(0, newText.length() - 1); //creates a new sub-string
                        displayExpression.setText(newText); //sets text
                        if(operatorCheck(lastCharacter)) {
                            decimalUsed.remove(numberOfDecimals);
                            --numberOfDecimals;
                        }
                        if(lastCharacter == '.')
                            decimalUsed.set(numberOfDecimals,false);
                    }
                    //Adds Operators to strings
                    else {
                        //checks if last character is an operator
                        // true means last character was an operator
                        if (operatorCheck(lastCharacter)) {
                            newText = newText.substring(0, newText.length() - 1) + operators; //replaces last operator
                            displayExpression.setText(newText); //replaces text
                        } else{
                            if(lastCharacter == '.') {
                                displayExpression.append("0");
                                //addToArray("0");
                            }
                            displayExpression.append(operators); //Adds the operator by appending text
                        }
                        //addToArray(operators);
                        numberOfDecimals++; //increments number of decimals that can be added.
                        decimalUsed.add(numberOfDecimals,false);
                    }
                    //Sets cursor to the last of the text
                    displayExpression.setSelection(displayExpression.getText().length());
                }
            }
        };

        //Long Press OnClickListener for clear button
        View.OnLongClickListener clearButtonLongPress = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //convertToArrayList();
                displayExpression.setText(""); //Clears the mathematical expression
                decimalUsed.clear(); //Clears the decimal array
                numberOfDecimals = 0; //sets number of decimals used to 0
                decimalUsed.add(numberOfDecimals,false); // initializes the Decimal Used ArrayList
                hapticFeedback.vibrate(1); //Gives Haptic feedback on Long Press
                displayAnswer.setText(""); //Resets displayAnswer TextView
                //clearStack(); // Clears the stack
                return false;
            }
        };

        //Equal Button OnClickListener
        View.OnClickListener equalsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //convertToArrayList();
                //convertToStack();
            }
        };

        //OnClickListeners for Numbers
        button0.setOnClickListener(numberListener);
        button1.setOnClickListener(numberListener);
        button2.setOnClickListener(numberListener);
        button3.setOnClickListener(numberListener);
        button4.setOnClickListener(numberListener);
        button5.setOnClickListener(numberListener);
        button6.setOnClickListener(numberListener);
        button7.setOnClickListener(numberListener);
        button8.setOnClickListener(numberListener);
        button9.setOnClickListener(numberListener);
        buttonDot.setOnClickListener(numberListener);

        //OnCLickListeners for Operators and Clear Button
        buttonClear.setOnClickListener(operatorListener);
        buttonPlus.setOnClickListener(operatorListener);
        buttonMinus.setOnClickListener(operatorListener);
        buttonMultiply.setOnClickListener(operatorListener);
        buttonDivision.setOnClickListener(operatorListener);
        equalsButton.setOnClickListener(equalsListener);

        //OnLongPressListener for equals button
        buttonClear.setOnLongClickListener(clearButtonLongPress);
    }

    //Checks if the last character in displayExpression is an operator
    public boolean operatorCheck(char lastCharacter){
        char[] operators = {'+','-','*','/'}; //operators
        for(int i = 0; i<4;i++){
            if(operators[i] == lastCharacter) //checks if any operator is equal to the character passed
                return true; //returns true if operator is found
        }
        return false; //returns false if operator isn't found
    }
}
