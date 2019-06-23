package Examples;

public class ArrayStack {

    private Object[] stack; // array of stack entries
    private int topIndex;    // index of top entry
    private static final int DEFAULT_MAX_SIZE = 100;

    public ArrayStack() {
        stack = new Object[DEFAULT_MAX_SIZE];
        topIndex = -1;
    } // end default constructor

    public ArrayStack(int maxSize) {
        stack = new Object[maxSize];
        topIndex = -1;
    } // end constructor

    public void push(Object newEntry) {
        topIndex++;
        stack[topIndex] = newEntry;
    } // end push

    public Object pop() {
        Object top = null;
        if (!isEmpty()) {
            top = stack[topIndex];
            stack[topIndex] = null;
            topIndex--;
        } // end if
        return top;
    } // end pop

    public Object peek() {
        Object top = null;
        if (!isEmpty()) {
            top = stack[topIndex];
        }
        return top;
    } // end peek

    public boolean isEmpty() {
        return topIndex < 0;
    } // end isEmpty

    public void clear() {
        for (; topIndex > -1; topIndex--) {
            stack[topIndex] = null;
        }
    } // end clear

} // end ArrayStack
