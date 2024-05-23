package com.otus.java.advanced.jdkinstruments.javac;

import java.util.ArrayList;
import java.util.List;

// javac -d javac-target -verbose -Xlint:rawtypes,unchecked .\src\main\java\com\otus\java\advanced\jdkinstruments\javac\XLint.java
// javac @javac-args/xlint-options
public class XLint {

    List<String> textList = new ArrayList();

    public void addText(String text) {
        textList.add(text);
    }

    public List getTextList() {
        return this.textList;
    }
}
