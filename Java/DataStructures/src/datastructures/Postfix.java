package datastructures;

// CMPS390
// Postfix.java
// Algorithms for two methods: convertToPostfix and evaluatePostfix
public class Postfix {

    /**
     * Task: Creates a postfix expression that represents a given infix
     * expression.
     *
     * @param infix a string that is a valid infix expression
     * @return a string that is the postfix expression equivalent of infix
     */
    /**
     * Algorithm convertToPostfix(infix) // Convert infix to postfix expression
     * operatorStack = a new empty stack while (infix has characters left to
     * parse) Get an item if (item is an operand) Append it to the postfix
     * expression else if (item == '(') operatorStack.push(item) else if (item
     * == ')') top = operatorStack.pop() while (top is not '(') // not append
     * '(' to postfix expression Append it to the postfix expression top =
     * operatorStack.pop() else // item is an operator: '+', '-', '*', or '/'
     * while (!operatorStack.empty()) top = operatorStack.peek() if (item
     * precedence <= top) Appenchd it to the postfix expression
     * operatorStack.pop() else break operatorStack.push(item) while
     * (!operatorStack.empty()) top = operatorStack.pop(); Append it to the
     * postfix expression return the postfix expression
     */
    public static String convertToPostfix(String infix) {
        //(a+b)*c-d
        ArrayStack stack = new ArrayStack();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < infix.length(); i++) {
            char value = infix.charAt(i);
            if (isVariable(value)) {
                sb.append(value);
                
            } else {
                
                if (getPrecedence(value) > 0) {
                    while (!stack.isEmpty() && (getPrecedence(value) <= getPrecedence((char) stack.peek()))) {
                        
                        sb.append(stack.pop());
                    }
                }
                if (value != ')') {
                    stack.push(value);
                }

            }

            if (value == ')') {

                while (!stack.isEmpty() && !stack.peek().equals('(')) {
                    
                    sb.append(stack.pop());
                }
                stack.pop();
            }
        }

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    } // end convertToPostfix

    /**
     * Algorithm evaluatePostfix(postfix) // Evaluates a postfix expression.
     * valueStack = a new empty stack while (postfix has characters left to
     * parse) Get an item if item is an operand valueStack.push(item) else //
     * item is an operator: '+', '-', '*', or'/' operand2 = valueStack.pop()
     * operand1 = valueStack.pop() result = operand1 item opernad2
     * valueStack.push(result) return valueStack.pop()
     */
    public static double evaluatePostfix(String postfix) {
        ArrayStack stack = new ArrayStack();

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            //if it is a number
            if (isVariable(c)) {
                stack.push(valueOf(c));
            } else {

                Double x, y;
                // System.out.println(stack.peek());
                
                // System.out.println(stack.peek());
                x = (Double) stack.pop();
              
                y = (Double) stack.pop();
                
                
                Double z = null;

                switch (c) {
                    case '+':
                        z = y + x;
                        break;
                    case '-':
                        z = y - x;
                        break;
                    case '*':
                        z = y * x;
                        break;
                    case '/':
                        z = y / x;
                        break;
                }
                stack.push(z);

            }
        }
        return (double) stack.pop();
    } // end evaluatePostfix

    /**
     * Task: Determines the precedence of a given operator.
     *
     * @param operator a character that is (, ), +, -, *, or /
     * @return an integer that indicates the precedence of operator: 0 if ( or
     * ), 1 if + or -, 2 if * or /, -1 if anything else
     */
    private static int getPrecedence(char operator) {
        switch (operator) {
            case '(':
            case ')':
                return 0;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        } // end switch
        return -1;
    } // end getPrecedence

    private static boolean isVariable(char ch) {
        return (ch == 'a') || (ch == 'b') || (ch == 'c') || (ch == 'd');
    } // end isVariable

    private static double valueOf(char variable) {
        switch (variable) {
            case 'a':
                return 2.0;
            case 'b':
                return 3.0;
            case 'c':
                return 4.0;
            case 'd':
                return 5.0;
        } // end switch
        return 0;
    } // end valueOf

} // end Postfix
