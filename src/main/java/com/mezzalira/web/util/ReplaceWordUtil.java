package com.mezzalira.web.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private long replaceParagraphsHash(HashMap<String, String> replacements, List<XWPFParagraph> xwpfParagraphs) {
        long count = 0;
        for (XWPFParagraph paragraph : xwpfParagraphs) {
            List<XWPFRun> runs = paragraph.getRuns();

            for (Map.Entry<String, String> replPair : replacements.entrySet()) {
                String find = replPair.getKey();
                String repl = replPair.getValue();
                TextSegement found = paragraph.searchText(find, new PositionInParagraph());
                //se encontrar a sigla em alguns trechos
                if (found != null) {
                    count++;
                    // whole search string is in one Run
                    XWPFRun run = runs.get(found.getBeginRun());
                    String runText = run.getText(run.getTextPosition());
                    String replaced = runText.replace(find, repl);
                    run.setText(replaced, 0);
                }
            }
        }
        return count;
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
        //System.out.printf(runText);

        return StringUtils.contains(runText, searchValue);
    }

    /**
     * Código retirado de
     * http://stackoverflow.com/questions/8398259/replace-table-column-value-in-apache-poi?rq=1
     *
     * @param replacements
     * @param xwpfParagraphs
     * @return

    private long replaceInParagraphs(Map<String, String> replacements, List<XWPFParagraph> xwpfParagraphs) {
        long count = 0;
        for (XWPFParagraph paragraph : xwpfParagraphs) {
            List<XWPFRun> runs = paragraph.getRuns();

            for (Map.Entry<String, String> replPair : replacements.entrySet()) {
                String find = replPair.getKey();
                String repl = replPair.getValue();
                TextSegement found = paragraph.searchText(find, new PositionInParagraph());
                //se encontrar a sigla em alguns trechos
                if (found != null) {
                    count++;
                    if (found.getBeginRun() == found.getEndRun()) {
                        // whole search string is in one Run
                        XWPFRun run = runs.get(found.getBeginRun());
                        String runText = run.getText(run.getTextPosition());
                        String replaced = runText.replace(find, repl);
                        run.setText(replaced, 0);
                    } else {
                        // The search string spans over more than one Run
                        // Put the Strings together
                        StringBuilder b = new StringBuilder();
                        for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
                            XWPFRun run = runs.get(runPos);
                            b.append(run.getText(run.getTextPosition()));
                        }
                        String connectedRuns = b.toString();
                        String replaced = connectedRuns.replace(find, repl);

                        // The first Run receives the replaced String of all connected Runs
                        XWPFRun partOne = runs.get(found.getBeginRun());
                        partOne.setText(replaced, 0);
                        // Removing the text in the other Runs.
                        for (int runPos = found.getBeginRun() + 1; runPos <= found.getEndRun(); runPos++) {
                            XWPFRun partNext = runs.get(runPos);
                            partNext.setText("", 0);
                        }
                    }
                }
            }
        }
        return count;
    }
     */


}
