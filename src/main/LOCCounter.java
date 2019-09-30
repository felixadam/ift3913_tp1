package main;

import org.apache.commons.io.FileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LOCCounter {

    /*
    public void getMetrics(String file) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            //LOC(in);
            in = new BufferedReader(new FileReader(file));
            //CLOC(in);
            in = new BufferedReader(new FileReader(file));
            File fichier = new File(file);
            cLOC(fichier, "foo");
            in.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    */

    int LOC(File file) throws IOException {
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        int loc = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).isEmpty())
                loc++;
        }
        System.out.println("LOC = " + loc);
        return loc;
    }

    int CLOC(File file) throws IOException {
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        int CLOC = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("//"))
                CLOC++;
            else if (lines.get(i).contains("/*") || lines.get(i).contains("/**")) {
                if (lines.get(i).contains("*/"))
                    CLOC++;
                else {
                    while(!(lines.get(i).contains("*/") || lines.get(i).contains("*/"))) {
                        CLOC++;
                        i++;
                    }
                    CLOC++;
                }
            }
        }
        System.out.println("CLOC = " + CLOC);
        return CLOC;
    }

    /*
    Hypothèse que les javadocs se trouvent juste avant chaque classe.
     */
    int cLOC(File file, String className) throws IOException {
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        int cLOC = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).isEmpty()) {
                if (lines.get(i).contains("class " + className) && !isInComment(i)) {
                    if (lines.get(i-1).contains("*/")) {
                        cLOC += docCommentNumberOfLines(lines, i);
                    }
                    cLOC += bracketCounter(lines, i);
                }
            }
        }
        System.out.println("cLOC("+className+") = " + cLOC);
        return cLOC;
    }

    int mLOC(File file, String methodName) throws IOException {
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        int mLOC = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).isEmpty()) {
                if (lines.get(i).contains(methodName) && !isInComment(i)) {
                    if (lines.get(i - 1).contains("*/")) {
                        mLOC = docCommentNumberOfLines(lines, i);
                    }
                    mLOC += bracketCounter(lines, i);
                }
            }
        }
        System.out.println("mLOC("+methodName+") = " + mLOC);
        return mLOC;
    }

    /* TODO */
    int cCLOC(File file, String className) throws IOException {
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        int cCLOC = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (!lines.get(i).isEmpty()) {
                if (lines.get(i).contains(className) && !isInComment(i)) {
                    if (lines.get(i - 1).contains("*/")) {
                        cCLOC = docCommentNumberOfLines(lines, i);
                    }
                    //commentLineCounter(lines, i);
                }
            }
        }
        System.out.println("cCLOC("+className+") = " + cCLOC);
        return cCLOC;
    }

    private int docCommentNumberOfLines(List<String> lines, int i) {
        int n = 0;
        while (!lines.get(i).contains("/**")) {
            n++;
            i--;
        }
        return n;
    }

    private static int bracketCounter(List<String> lines, int i) {
        int bracket = 0;
        int n = 0;
        if (lines.get(i).contains("{")) {
            n++;
            i++;
            bracket++;
        }
        else if (!lines.get(i).contains("{")) {
            n += 2;
            i += 2;
            bracket++;
        }
        while (bracket != 0) {
            if (lines.get(i).contains("{")) {
                bracket++;
                n++;
                i++;
            }
            else if (lines.get(i).contains("}")) {
                bracket--;
                n++;
                i++;
            }
            else if (!lines.get(i).isEmpty()) {
                n++;
                i++;
            }
            else {
                i++;
            }
        }
        return n;
    }

    /* TODO */
    private boolean isInComment(int i) {
        return false;
    }
}