package com.test.pdftextparser;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.SimpleTextExtractionStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inetbeans
 */
public class PDFParser {

    private PdfReader reader;
    private int numberOfPages;

    public PDFParser(File file) {
        try {
            this.reader = new PdfReader(new FileInputStream(file));
            this.numberOfPages = new PdfDocument(new PdfReader(new FileInputStream(file))).getNumberOfPages();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDFParser.class.getName()).log(Level.SEVERE, "File is not found!", ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFParser.class.getName()).log(Level.SEVERE, "input/output error!", ex);
        }
    }

    public PDFParser(String filename) {
        this(new File(filename));
    }

    public StringBuilder getTextFromPdf() {
        StringBuilder result = new StringBuilder();
        PdfDocument document = new PdfDocument(this.reader);
        SimpleTextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
        for (int i = 1; i < document.getNumberOfPages(); i++) {
            result.append(PdfTextExtractor
                            .getTextFromPage(
                                    document.getPage(i), 
                                    strategy
                            )
                            .replace("-\n", "")
                    );
        }
        return result;

    }

    public void writePDFtextToFileUncorrect(String fileName) {
        StringBuilder result = new StringBuilder();
        PdfDocument pdf = new PdfDocument(this.reader);
        int i = 1;
        do {
            result.append(PdfTextExtractor.getTextFromPage(pdf.getPage(i)));
            i++;
        } while (i < this.numberOfPages);
        try (FileWriter writer = new FileWriter(new File(fileName))) {
            writer.write(result.toString());
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDFParser.class.getName()).log(Level.SEVERE, "File is not found...", ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFParser.class.getName()).log(Level.SEVERE, "Input/output error...", ex);
        }
    }

    public void writePDFtexToFile(String filename) {
        FileWriter writer;
        try {
            writer = new FileWriter(new File(filename));
            writer.write(getTextFromPdf().toString());
            writer.flush();
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDFParser.class.getName()).log(Level.SEVERE, "File is not found!", ex);
        } catch (PdfException ex) {
            Logger.getLogger(PDFParser.class.getName()).log(Level.SEVERE, "pdf exception", ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFParser.class.getName()).log(Level.SEVERE, "input/output error!", ex);
        }
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }
}
