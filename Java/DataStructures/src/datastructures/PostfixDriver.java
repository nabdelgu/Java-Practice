package datastructures;


import datastructures.Postfix;

// CMPS390
// PostfixDriver.java
// Kuo-pao Yang

public class PostfixDriver {

    public static void main(String[] args) {
        System.out.println("Testing postfix expressions with\n"
                + "a = 2.0, b = 3.0, c = 4.0, d = 5.0\n");
        testPostfix("a+b");
        testPostfix("a-b+c*d");
        testPostfix("(a+b)*c-d");
        testPostfix("a+b*(c-d)");
        testPostfix("(a+b)/(c-d)");
        testPostfix("a*(b/(c-d))");
    } // end main

    public static void testPostfix(String infixExpression) {
        String postfixExpression = Postfix.convertToPostfix(infixExpression);
        System.out.println("Infix: " + infixExpression);
        System.out.println("Postfix: " + postfixExpression);
        System.out.println("Value: " + Postfix.evaluatePostfix(postfixExpression));
        System.out.println();
    } // end testPostfix
} // end PostfixDriver
/* OUTPUT
Testing postfix expressions with
a = 2.0, b = 3.0, c = 4.0, d = 5.0

Infix: a+b
Postfix: ab+
Value: 5.0

Infix: a-b+c*d
Postfix: ab-cd*+
Value: 19.0

Infix: (a+b)*c-d
Postfix: ab+c*d-
Value: 15.0

Infix: a+b*(c-d)
Postfix: abcd-*+
Value: -1.0

Infix: (a+b)/(c-d)
Postfix: ab+cd-/
Value: -5.0

Infix: a*(b/(c-d))
Postfix: abcd-/*
Value: -6.0
*/
