
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PageLinkedList {
    private Page first = null;
    private Page currentPage;
    private int currentPageNum;
    static int pageCount = 0;
    public static Stack stack1 = new Stack(), stack2 = new Stack();
    public PageLinkedList() {
        first = null;
    }

    //1 parse
    public void parse (String s) throws FileNotFoundException {
        String t = "";
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals("$")) {
                t += s.charAt(i) + "";
            }else {
                Page p = new Page(t, pageCount + 1);
                addPage(p);
                i++;
                t = "";
            }
        }
        Page p = new Page(t, pageCount + 1);
        addPage(p);

    }

    public void travers() {
        Page p = first;
        while (p != null) {
            Line l = p.lineLinkedList.first;
            while (l != null) {
                System.out.println(l.content);
                l  = l.next;
            }
            p = p.next;
            System.out.println("$");
        }
    }

    public void newPage(String Name) {
        Page n = new Page("", pageCount + 1);

        if(first == null) {
            first = n;
        }else {
            Page p = first, t = null;
            while (p != null) {
                t = p;
                p = p.next;
            }
            t.next = n;
        }
        pageCount++;
    }

    public void addPage(Page p) {
        currentPage = p;
        currentPageNum = ++pageCount;
        if(first == null) {
            first = p;
        }else {
            Page n = first, t = null;
            while (n != null) {
                t = n;
                n = n.next;
            }
            t.next = p;
            p.prev = t;
        }
    }

    public Page getPageI(int index) {
        index--;
        Page p = first;
        if(pageCount < index) {
            System.out.println("Not Found");
            return null;
        }else {
            for(int i = 0; i < index; i++) {
                p = p.next;
            }
            return p;
        }
    }
    //2 where
    public int where() {
        return currentPageNum;
    }

    public static int getPageCount() {
        return pageCount;
    }

    public Page getCurrentPage() {
        return currentPage;
    }
    //3 showN
    public void showN(int n) {
        Scanner scanner = new Scanner(currentPage.getPageContent());
        for(int i = 0; i < n && scanner.hasNext(); i++) {
            System.out.println(scanner.nextLine());
        }
    }
    //5 previousPage
    public void previousPage() {
        if(currentPage.prev != null) {
            currentPage = currentPage.prev;
            currentPageNum--;
        }
    }
    //6 nextPage
    public void nextPage() {
        if(currentPage.next != null) {
            currentPage = currentPage.next;
            currentPageNum++;
        }
    }
    //12 find
    public void find(String s) {
        Page p = first;

        while (p != null) {
            int i = 1;
            while (p.lineLinkedList.getLineI(i) != null) {
                if(p.lineLinkedList.getLineI(i).content.contains(s)) {
                    System.out.println("line number: " + i + "," + "  sentence: " +  "\"" + p.lineLinkedList.getLineI(i).content + "\"");
                }
                i++;
            }
            p = p.next;
        }

    }
    //13 find and replace
    public void findAndReplace(String s, String t) {
        Page p = first;
        while (p != null) {
            int i = 1;
            while (p.lineLinkedList.getLineI(i) != null) {
                if(p.lineLinkedList.getLineI(i).content.contains(s)) {
                    p.lineLinkedList.getLineI(i).content = p.lineLinkedList.getLineI(i).content.replaceAll(s, t);
                }
                i++;
            }
            p = p.next;
        }
        stack1.push("findAndReplace " + t + "$" + s);
    }
    //14 save
    public void save() throws FileNotFoundException {
        Page p = first;
        String s = "";
        while (p != null) {
            int i = 1;
            while (p.lineLinkedList.getLineI(i) != null) {
                s += p.lineLinkedList.getLineI(i).content + "\n";
                i++;
            }
            s += "$\n";
            p = p.next;
        }
        s = s.substring(0, s.length() - 3);
        PrintWriter printWriter = new PrintWriter("saved.txt");
        printWriter.print(s);
        printWriter.close();
    }
    //15 undo
    public void undo() {
        String s = stack1.pop();

        //  "append String"//
        //  "insert int String"//
        //  "remove int String"//
        //  "replace int String"
        //  "swap int int"//
        //  findAndReplace String1 String2"//
        if(s != null) {
            stack2.push(s);

            if(s.contains("swap"))
                swapUndoRedo(s);
            else if(s.contains("insert"))
                insertUndo(s);
            else if(s.contains("findAndReplace"))
                findAndReplaceUndoRedo(s, "undo");
            else if(s.contains("append"))
                appendUndo(s);
            else if(s.contains("remove"))
                removeUndo(s);
            else if(s.contains("replace"))
                replaceUndo(s);
        }
    }
    //16 redo
    public void redo() {
        String s = stack2.pop();

        if(s != null) {
            stack1.push(s);

            if(s.contains("swap"))
                swapUndoRedo(s);
            else if(s.contains("insert"))
                insertRedo(s);
            else if(s.contains("findAndReplace"))
                findAndReplaceUndoRedo(s, "redo");
            else if(s.contains("append"))
                appendRedo(s);
            else if(s.contains("remove"))
                removeRedo(s);
            else if(s.contains("replace"))
                replaceRedo(s);
        }
    }

    public void swapUndoRedo(String s) {
        String q = "", t = s.substring(5);
        String nStr = "", mStr = "";
        for(int i = s.length() - 1; i >= 0; i--) {
            if(!(s.charAt(i) + "").equals(" "))
                q += s.charAt(i);
            else
                break;
        }
        String r = q;
        q = "";
        for(int i = r.length() - 1; i >= 0; i--) {
            q += r.charAt(i);
        }
        boolean flag = true, flag2 = false;
        for(int i = 0; i < t.length(); i++) {
            if(!(t.charAt(i) + "").equals(" ") && flag) {
                nStr += t.charAt(i);
                flag = false;
            }else if(!(t.charAt(i) + "").equals(" ")){
                mStr += t.charAt(i);
                if((t.charAt(i + 1) + "").equals(" "))
                    flag2 = true;
            }else if(flag2)
                break;
        }
        getPageI(Integer.parseInt(q)).swap(Integer.parseInt(nStr), Integer.parseInt(mStr));
        stack1.pop();
    }
    public void insertUndo(String s) {
        String t = s.substring(7), q  ="";
        for(int i = s.length() - 1; i >= 0; i--) {
            if(!(s.charAt(i) + "").equals(" "))
                q += s.charAt(i);
            else
                break;
        }
        String r = q;
        q = "";
        for(int i = r.length() - 1; i >= 0; i--) {
            q += r.charAt(i);
        }
        String n = "";
        for(int i = 0; i < t.length(); i++) {
            if(!(t.charAt(i) + "").equals(" ")) {
                n += t.charAt(i);
            }else
                break;
        }
        getPageI(Integer.parseInt(q)).remove(Integer.parseInt(n));
        stack1.pop();

    }
    public void insertRedo(String s) {
        String pagNum = "";
        for(int i = s.length() - 1; i >= 0; i--) {
            if(!(s.charAt(i) + "").equals(" "))
                pagNum += s.charAt(i);
            else
                break;
        }
        String r = pagNum;
        pagNum = "";
        for(int i = r.length() - 1; i >= 0; i++) {
            pagNum += r.charAt(i);
        }
        String t = s.substring(7, s.length() - pagNum.length() - 1);
        String n = "";
        for(int i = 0; i < t.length(); i++) {
            if(!(t.charAt(i) + "").equals(" ")) {
                n += t.charAt(i);
            }else
                break;
        }
        String passage = s.substring(7 + n.length() + 1, s.length() - pagNum.length() - 1);
        getPageI(Integer.parseInt(pagNum)).insert(passage, Integer.parseInt(n));
        stack1.pop();

    }
    public void findAndReplaceUndoRedo(String s, String witch) {
        String str1 = "", str2 = "";
        s = s.substring(15);

        boolean flag = true;
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals("$")) {
                if(flag)
                    str1 += s.charAt(i);
                else
                    str2 += s.charAt(i);
            }else
                flag = false;
        }
        if(witch.equals("undo"))
            findAndReplace(str1, str2);
        else
            findAndReplace(str2, str1);
        stack1.pop();

    }
    public void appendUndo(String s) {
        String pagNum = "", lines = "", count = "";
        for(int i = s.length() - 1; i >= 0; i--) {
            if(!(s.charAt(i) + "").equals(" "))
                pagNum += s.charAt(i);
            else
                break;
        }
        String r = pagNum;
        pagNum = "";
        for(int i = r.length() - 1; i >= 0; i--) {
            pagNum += r.charAt(i);
        }
        s = s.substring(7);
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals(" "))
                lines += s.charAt(i);
            else
                break;
        }
        s = s.substring(lines.length() + 1);
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals(" "))
                count += s.charAt(i);
            else
                break;
        }
        s.substring(count.length(), s.length() - pagNum.length());
        for(int i = 0; i < Integer.parseInt(count); i++) {
            getPageI(Integer.parseInt(pagNum)).remove(Integer.parseInt(lines) + 1);
            stack1.pop();
        }
    }
    public void appendRedo(String s) {
        String pagNum = "", lines = "", count = "";
        for(int i = s.length() - 1; i >= 0; i--) {
            if(!(s.charAt(i) + "").equals(" "))
                pagNum += s.charAt(i);
            else
                break;
        }
        String r = pagNum;
        pagNum = "";
        for(int i = r.length() - 1; i >= 0; i--) {
            pagNum += r.charAt(i);
        }
        s = s.substring(7);
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals(" "))
                lines += s.charAt(i);
            else
                break;
        }
        s = s.substring(lines.length() + 1);
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals(" "))
                count += s.charAt(i);
            else
                break;
        }
        s = s.substring(count.length() + 1, s.length() - pagNum.length());
        getPageI(Integer.parseInt(pagNum)).append(s);
        stack1.pop();
    }
    public void removeUndo(String s) {
        String pageNum = "", LineNum = "";
        for(int i = s.length() - 1; i >= 0; i--) {
            if(!(s.charAt(i) + "").equals(" "))
                pageNum += s.charAt(i);
            else
                break;
        }
        String r = pageNum;
        pageNum = "";
        for(int i = r.length() - 1; i >= 0; i--) {
            pageNum += r.charAt(i);
        }
        s = s.substring(7);
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals(" "))
                LineNum += s.charAt(i);
            else
                break;
        }
        s = s.substring(LineNum.length() + 1, s.length() - pageNum.length());
        getPageI(Integer.parseInt(pageNum)).insert(s, Integer.parseInt(LineNum) + 1);
        stack1.pop();
    }
    public void removeRedo(String s) {
        String pageNum = "", LineNum = "";
        for(int i = s.length() - 1; i >= 0; i--) {
            if(!(s.charAt(i) + "").equals(" "))
                pageNum += s.charAt(i);
            else
                break;
        }
        String r = pageNum;
        pageNum = "";
        for(int i = r.length() - 1; i >= 0; i--) {
            pageNum += r.charAt(i);
        }
        s = s.substring(7);
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals(" "))
                LineNum += s.charAt(i);
            else
                break;
        }
        getPageI(Integer.parseInt(pageNum)).remove(Integer.parseInt(LineNum) + 1);
        stack1.pop();
    }
    public void replaceUndo(String s) {
        String pageNum = "", LineNum = "";
        for(int i = s.length() - 1; i >= 0; i--) {
            if(!(s.charAt(i) + "").equals(" "))
                pageNum += s.charAt(i);
            else
                break;
        }
        String r = pageNum;
        pageNum = "";
        for(int i = r.length() - 1; i >= 0; i--) {
            pageNum += r.charAt(i);
        }
        s = s.substring(8);
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals(" "))
                LineNum += s.charAt(i);
            else
                break;
        }
        s = s.substring(LineNum.length() + 1, s.length() - pageNum.length() - 1);
        String firstLine = "", secondLine = "";
        boolean flag1 = true, flag2 = true;
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals("$") && flag1) {
                firstLine += s.charAt(i);
                if((s.charAt(i + 1) + "").equals("$")) {
                    flag1 = false;
                    i++;
                }
            }else if(flag2) {
                secondLine += s.charAt(i);
            }
        }
        getPageI(Integer.parseInt(pageNum)).replace(2, firstLine);
        stack1.pop();
    }
    public void replaceRedo(String s) {
        String pageNum = "", LineNum = "";
        for(int i = s.length() - 1; i >= 0; i--) {
            if(!(s.charAt(i) + "").equals(" "))
                pageNum += s.charAt(i);
            else
                break;
        }
        String r = pageNum;
        pageNum = "";
        for(int i = r.length() - 1; i >= 0; i--) {
            pageNum += r.charAt(i);
        }
        s = s.substring(8);
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals(" "))
                LineNum += s.charAt(i);
            else
                break;
        }
        s = s.substring(LineNum.length() + 1, s.length() - pageNum.length() - 1);
        String firstLine = "", secondLine = "";
        boolean flag1 = true, flag2 = true;
        for(int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i) + "").equals("$") && flag1) {
                firstLine += s.charAt(i);
                if((s.charAt(i + 1) + "").equals("$")) {
                    flag1 = false;
                    i++;
                }
            }else if(flag2) {
                secondLine += s.charAt(i);
            }
        }
        getPageI(Integer.parseInt(pageNum)).replace(2, secondLine);
        stack1.pop();
    }
}
