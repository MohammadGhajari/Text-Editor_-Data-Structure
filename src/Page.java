public class Page {
    Page next, prev;
    private int pageNum;
    private String pageContent;
    private int lineHeight;
    LineLinkedList lineLinkedList = new LineLinkedList();


    public Page(String s, int pageNum) {
        pageContent = s;
        lineLinkedList.parseLine(s);
        lineHeight = lineLinkedList.lines;
        next = null;
        prev = null;
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return pageContent;
    }
    //4 lines
    public int lines() {
        return lineHeight;
    }
    //7 append
    public void append(String s) {
        pageContent += s;
        lineLinkedList.appendLine(s, pageNum);
        lineHeight = lines();
    }
    //8 insert
    public void insert(String s, int i) {
        lineLinkedList.insertLine(s, i, pageNum);
    }
    //9 remove
    public void remove(int n) { lineLinkedList.removeLine(n, pageNum);
    }
    //10 swap
    public void swap(int n, int m) {
        lineLinkedList.swapLine(n, m, pageNum);
    }
    //11 replace
    public void replace(int n, String s) {
        lineLinkedList.replaceLine(n, s, pageNum);
    }

    public void showPage() {
        lineLinkedList.traversLine();
    }

    public int getPageNum() {
        return pageNum;
    }

    public String getPageContent() {
        return pageContent;
    }

    public int getLineHeight() {
        return lineHeight;
    }
}
