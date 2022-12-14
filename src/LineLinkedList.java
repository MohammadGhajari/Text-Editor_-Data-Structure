import java.util.Scanner;

public class LineLinkedList {
    Line first;
    int lines = 0;

    public LineLinkedList() {
        first = null;
    }

    public void addLine(String s) {
        Line n = new Line(s);

        if(first == null) {
            first = n;
        }else {
            Line p = first, t = null;
            while (p != null) {
                t = p;
                p = p.next;
            }
            t.next = n;
            n.prev = t;
        }
    }

    public void traversLine() {
        Line p = first;
        while (p != null) {
            System.out.println(p.content);
            p = p.next;
        }
    }

    public void parseLine(String s) {
        Scanner scanner = new Scanner(s);
        while (scanner.hasNext()) {
            addLine(scanner.nextLine());
            lines++;
        }
    }

    //related to append on class Page
    public void appendLine(String s, int pageNum) {
        int count = 0;
        Scanner scanner = new Scanner(s);
        int a = lines;
        while (scanner.hasNext()) {
            addLine(scanner.nextLine());
            lines++;
            count++;
        }
        PageLinkedList.stack1.push("append " + a + " " + count + " " + s + " " + pageNum);
    }

    //related to insert on class Page
    public void insertLine(String s, int n, int pageNum) {
        Line l = first;
        Line newLine=  new Line(s);
        for(int i = 0; i < n - 2 && i < lines; i++)
            l = l.next;
        newLine.next = l.next;
        l.next = newLine;
        PageLinkedList.stack1.push("insert " + n + " " + s + " " + pageNum);
        lines++;
    }

    //related to remove on class Page
    public void removeLine(int n, int pageNum) {
        n--;
        if(first == null)
            System.out.println("empty");
        else {
            Line l = first, p = null;
            if(n >= lines) {
                System.out.println("not found");
                return;
            }
            int i = 0;
            while (l != null && i < n) {
                p = l;
                l = l.next;
                i++;
            }
            if(n == 0)  {
                first = first.next;
                PageLinkedList.stack1.push("remove " + n + " " + first.content + " " + pageNum);
            } else {
                p.next = l.next;
                PageLinkedList.stack1.push("remove " + n + " " + l.content + " " + pageNum);
            }
            lines--;
        }
    }

    //related to swap on class Page
    public void swapLine(int n, int m, int pagNum) {
        Line l = first, firstLine, secondLine;
        if(m > lines || n > lines)
            System.out.println("does not valid");
        else {
            for(int i = 0; i < n - 1; i++)
                l = l.next;
            firstLine = l;
            l = first;

            for(int i = 0; i < m - 1; i++)
                l = l.next;
            secondLine = l;

            String p = firstLine.content;
            firstLine.content = secondLine.content;
            secondLine.content = p;
            PageLinkedList.stack1.push("swap " + n + " " + m + " " + pagNum);
        }
    }

    //related to replace on class Page
    public void replaceLine(int n, String s, int pageNum) {
        if(first == null)
            System.out.println("empty");
        else {
            Line l = first, p = null;
            if(n > lines) {
                System.out.println("not found");
                return;
            }
            int i = 0;
            while (l != null && i < n) {
                p = l;
                l = l.next;
                i++;
            }
            PageLinkedList.stack1.push("replace " + n + " " + p.content + "$" + s + " " + pageNum);
            p.content = s;
        }
    }

    public Line getLineI(int index) {
        index--;
        Line l = first;
        if(lines < index) {
            System.out.println("Not Found");
            return null;
        }else {
            for(int i = 0; i < index; i++) {
                l = l.next;
            }
            return l;
        }
    }
}