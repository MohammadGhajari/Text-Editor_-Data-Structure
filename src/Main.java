// author Mohammad Ghajari 4001262079
// simple text editor structure
// this program uses linked list for stack and other data structures
// please use methods that have numbered from 1 to 16 and commented before their prototype

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Test.txt"));
        String s = "";
        while (scanner.hasNext())
            s += scanner.nextLine() + "\n";

        PageLinkedList pageLinkedList = new PageLinkedList();
        pageLinkedList.parse(s);
    }
}