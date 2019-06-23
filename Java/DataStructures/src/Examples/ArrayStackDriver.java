package Examples;


import datastructures.ArrayStack;

// CMPS390
// ArrayStackDriver.java
// Kuo-pao Yang

public class ArrayStackDriver {

    public static void main(String[] args) {
        testStackOperations();
    }  // end main

    public static void testStackOperations() {
        System.out.println("Create a stack: ");
        ArrayStack myStack = new ArrayStack();
        System.out.println("isEmpty() returns " + myStack.isEmpty());

        System.out.println("\nTesing push to stack:" + " Austin Cory Jason");
        myStack.push("Austin");
        myStack.push("Cory");
        myStack.push("Jason");

        System.out.println("\n\nTesting peek and pop:");
        while (!myStack.isEmpty()) {
            String top = (String) myStack.peek();
            System.out.println(top + " is at the top of the stack.");

            top = (String) myStack.pop();
            System.out.println(top + " is removed from the stack.\n");
        } // end while

        System.out.println("After peek and pop, the stack should be empty: ");
        System.out.println("isEmpty() returns " + myStack.isEmpty());

        System.out.println("\n\nTesting clear:");
        System.out.println("Add to stack:" + " Ericka Jassica Megan");
        myStack.push("Ericka");
        myStack.push("Jassica");
        myStack.push("Megan");
        myStack.clear();
        System.out.println("After clear, the stack should be empty: ");
        System.out.println("isEmpty() returns " + myStack.isEmpty());

    } // end testStackOperations

}  // end ArrayStackDriver

/* OUTPUT
 Create a stack: 
 isEmpty() returns true
 
 Tesing push to stack: Austin Cory Jason
 
 
 Testing peek and pop:
 Jason is at the top of the stack.
 Jason is removed from the stack.
 
 Cory is at the top of the stack.
 Cory is removed from the stack.
 
 Austin is at the top of the stack.
 Austin is removed from the stack.
 
 After peek and pop, the stack should be empty: 
 isEmpty() returns true
 
 
 Testing clear:
 Add to stack: Ericka Jassica Megan
 After clear, the stack should be empty: 
 isEmpty() returns true
*/
