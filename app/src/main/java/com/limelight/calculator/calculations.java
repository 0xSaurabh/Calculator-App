package com.limelight.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;

public class calculations {
    private ArrayList<String> equationArray = new ArrayList<>();
    private ArrayList<String> postfixExpression= new ArrayList<>();
    private ArrayList<String> operatorPostfix = new ArrayList<>();
    private ArrayList<BigDecimal> solvePostfix = new ArrayList<>();
    private String tempString;

    void createEquationCharacterArray(String equationString) {
        //System.out.println(equationString);
        for (int i = 0; i<equationString.length();i++){
            tempString = "";
            tempString += equationString.charAt(i);
            if(i == 0)
              equationArray.add("(");
            if(symbolCheck(tempString)) {
                equationArray.add(tempString);
            }
            else {
                if((equationArray.size() == 0) ||
                        ( equationArray.get(equationArray.size()-1).length() > 0
                                && symbolCheck(equationArray.get(equationArray.size()-1)))) {
                    equationArray.add("");
                }
                equationArray.set(equationArray.size()-1,equationArray.get(equationArray.size()-1)+ tempString);
            }
            if(i == equationString.length()-1)
                equationArray.add(")");
        }
        //System.out.println(equationArray);
        //System.out.println(equationArray.size());
    }

    boolean symbolCheck(String symbol){
        String[] symbols = {"(",")","+","-","/","*"};
        for(int i = 0; i < symbols.length; i++) {
            if (symbol.contentEquals(symbols[i]))
                return true;
        }
        return false;
    }

    boolean operatorCheck(String symbol){
        String[] symbols = {"+","-","/","*"};
        for(int i = 0; i < symbols.length; i++) {
            if (symbol.contentEquals(symbols[i]))
                return true;
        }
        return false;
    }

    String createPostfix(){
        for(int i = 0; i < equationArray.size(); i++){
            if(symbolCheck(equationArray.get(i)))
                setOperatorPostfix(equationArray.get(i));
            else
                postfixExpression.add(equationArray.get(i));
            //System.out.println(postfixExpression);
        }
        return calculateAnswer();
    }

    void setOperatorPostfix(String operator){
        if(!operatorPostfix.isEmpty())
            tempString = operatorPostfix.get(operatorPostfix.size()-1);
        if(operator.contentEquals("(")) {
            operatorPostfix.add(operator);
        } else if(operator.contentEquals("/") || operator.contentEquals("*")){
            if(tempString.contentEquals("/") || tempString.contentEquals("*")) {
                postfixExpression.add(tempString);
                operatorPostfix.remove(operatorPostfix.size()-1);
                setOperatorPostfix(operator);
            } else {
                operatorPostfix.add(operator);
            }
        } else if(operator.contentEquals("+") || operator.contentEquals("-")){
            if(operatorCheck(tempString)) {
                postfixExpression.add(tempString);
                operatorPostfix.remove(operatorPostfix.size()-1);
                setOperatorPostfix(operator);
            } else {
                operatorPostfix.add(operator);
            }
        } else if(operator.contentEquals(")")) {
            while(!operatorPostfix.get(operatorPostfix.size()-1).contentEquals("(")) {
                postfixExpression.add(operatorPostfix.get(operatorPostfix.size()-1));
                operatorPostfix.remove(operatorPostfix.size()-1);
            }
            operatorPostfix.remove(operatorPostfix.size()-1);
        }
    }

    String calculateAnswer(){
        BigDecimal answer = BigDecimal.ZERO;
        while(!postfixExpression.isEmpty()) {
            tempString = postfixExpression.get(0);
            if(!symbolCheck(tempString))
                solvePostfix.add(new BigDecimal(tempString));
            else {
                switch(tempString) {
                    case "+":
                        answer = solvePostfix.get(solvePostfix.size()-2).add(solvePostfix.get(solvePostfix.size()-1));
                        break;
                    case "-":
                        answer = solvePostfix.get(solvePostfix.size()-2).subtract(solvePostfix.get(solvePostfix.size()-1));
                        break;
                    case "*":
                        answer = solvePostfix.get(solvePostfix.size()-2).multiply(solvePostfix.get(solvePostfix.size()-1));
                        break;
                    case "/":
                        answer = solvePostfix.get(solvePostfix.size()-2).divide(solvePostfix.get(solvePostfix.size()-1),15,BigDecimal.ROUND_HALF_UP);
                        break;
                }
                solvePostfix.remove(solvePostfix.size()-1);
                solvePostfix.set(solvePostfix.size()-1,answer);
            }
            postfixExpression.remove(0);
        }
        tempString = "";
        tempString += answer;
        return tempString;
    }
}
