package com.test.pdftextparser;

import java.io.PrintStream;


public class Main {

    private static final String PDFFILENAME = "/home/file.pdf";
    private static final String DESTINATIONFILENAME = "/home/text.txt";
    private static final String DESTINATIONFOLDER = "/home/text.txt";

    public static void main(String[] args) {
        PDFParser parser = new PDFParser(PDFFILENAME);
        parser.writePDFtexToFile(DESTINATIONFILENAME);
    }
}
