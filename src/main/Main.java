package main;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String args[]) {
        String test = "C:\\Users\\Felix\\Documents\\UDEM A2019\\Qualité logiciel et métriques\\tp1\\test\\test2.java";
        File file = new File(test);
        LOCCounter counter = new LOCCounter();
        //counter.getMetrics(test);
        try {
            counter.LOC(file);
            counter.CLOC(file);
            counter.cLOC(file, "BestClass");
            counter.mLOC(file, "foo");
            counter.mLOC(file, "ping");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
