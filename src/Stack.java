public class Stack {
    Stack1Node top;

    public Stack() {
        top = null;
    }

    public void push(String s) {
        Stack1Node p = new Stack1Node(s);
        p.next = null;
        if(top == null) {
            top = p;
        }else {
            p.next = top;
            top = p;
        }
    }

    public String pop() {
        if(isEmpty()) {
            System.out.println("Stack is Empty");
            return null;
        }else {
            String x = top.command;
            top = top.next;
            return x;
        }
    }

    public boolean isEmpty() {
        return top == null;
    }
}
