/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursionPractice;

/**
 *
 * @author Noah
 */
public class JavaPractice {

    /**
     * @param args the command line arguments
     */
    static int number[] = {1,10,8,9};

    public static void main(String[] args) {
        System.out.println(largestRecursive(number,number.length-1));
       // System.out.println(sumArray(number,number.length-1));
    }

    public static int largestRecursive(int[] m, int counter) {
       // System.out.println(m[counter]);
        if(counter > 0)            
            return Math.max(m[counter], largestRecursive(m,counter-1));
        else
            return m[0];
    }
    
    public static int sumArray(int[] m, int counter) {
        if(counter > 0)
            return m[counter] + sumArray(m,counter-1);
        else
            return m[0];
    } 

    public static int largest(int[] m) {
        int result = m[0];

        //loop through every elements ans store the largest one
        for (int i = 0; i <= m.length; i++) {
            if (m[i] > result) {
                result = m[i];
            }
        }

        return result;

    }

    public static void reduceByOne(int n) {
        if (n != 0) {
            reduceByOne(n - 1);
        }

        System.out.println("Completed Call: " + n);
    }

    public static long factorial(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

}
