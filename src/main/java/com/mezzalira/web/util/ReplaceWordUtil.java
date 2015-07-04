package com.mezzalira.web.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cezar on 03/07/15.
 */
public class ReplaceWordUtil {

    private XWPFDocument xwpfDocument;

    public ReplaceWordUtil(InputStream inputStream) {
        try {
            this.xwpfDocument = new XWPFDocument(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método utilizado para substituir um conjunto de palavras no texto.
     *
     * @param wordsToReplace
     */
    public void replaceMap(HashMap<String, String> wordsToReplace, String pathFileDownload) {
        for (String searchValue : wordsToReplace.keySet()) {
            String replacement = wordsToReplace.get(searchValue);

            replaceString(searchValue, replacement);

        }
        saveFileToDownload(pathFileDownload);
    }

    private void saveFileToDownload(String pathFileDownload) {
        FileOutputStream docOut = null;
        try {
            docOut = new FileOutputStream(pathFileDownload);
            xwpfDocument.write(docOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que percorre os paragrafos
     *
     * @param searchValue - Palavra que será substituida
     * @param replacement - Nova palavra
     */
    public void replaceString(String searchValue, String replacement) {
        List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();

        for (XWPFParagraph xwpfParagraph : paragraphs) {
            replaceOnParagraph(xwpfParagraph, searchValue, replacement);
        }
    }

    /**
     * Método que substitui a palavra no paragrafo
     *
     * @param paragraph
     * @param searchValue
     * @param replacement
     */
    private void replaceOnParagraph(XWPFParagraph paragraph, String searchValue, String replacement) {
        if (hasReplaceableItem(paragraph.getText(), searchValue)) {
            String replacedText = StringUtils.replace(paragraph.getText(), searchValue, replacement);

            removeAllRuns(paragraph);

            insertReplacementRuns(paragraph, replacedText);
        }
    }


    /**
     * Método que atualiza o paragrafo no documento
     *
     * @param paragraph
     * @param replacedText
     */
    private void insertReplacementRuns(XWPFParagraph paragraph, String replacedText) {
        String[] replacementTextSplitOnCarriageReturn = StringUtils.split(replacedText, "\n");

        for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
            String part = replacementTextSplitOnCarriageReturn[j];

            XWPFRun newRun = paragraph.insertNewRun(j);
            newRun.setText(part);

            if (j + 1 < replacementTextSplitOnCarriageReturn.length) {
                newRun.addCarriageReturn();
            }
        }
    }

    private void removeAllRuns(XWPFParagraph paragraph) {
        int size = paragraph.getRuns().size();
        for (int i = 0; i < size; i++) {
            paragraph.removeRun(0);
        }
    }

    private boolean hasReplaceableItem(String runText, String searchValue) {
        return StringUtils.contains(runText, searchValue);
    }

}
