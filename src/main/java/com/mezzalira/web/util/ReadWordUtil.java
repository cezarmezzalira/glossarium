package com.mezzalira.web.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cezar Mezzalira on 08/01/2015.
 */
public class ReadWordUtil {

    public static final String EXTENSION_FILE_DOC = ".doc";
    public static final String EXTENSION_FILE_DOCX = ".docx";
    public static final String EXTENSION_FILE_ODT = ".odt";

    private List<String> terms;


    public List<String> readDocFile(InputStream inputStream) {
        List<String> listTerms = new ArrayList<>();
        try {
            HWPFDocument hwpfDocument = new HWPFDocument(inputStream);
            Range range = hwpfDocument.getRange();

            int numParagraphs = 0;

            for (int x = 0; x < range.numSections(); x++) {
                Section s = range.getSection(x);
                for (int y = 0; y < s.numParagraphs(); y++) {
                    Paragraph paragraph = s.getParagraph(y);
                    numParagraphs++;
                    listTerms.add(paragraph.text());
                }
            }
            System.out.println("Total no of paragraph " + numParagraphs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listTerms;
    }

    public List<String> readDocxFile(InputStream inputStream) {
        List<String> listTerms = null;
        try {
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);

            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();

            System.out.println("Total no of paragraph " + paragraphs.size());
            listTerms = new ArrayList<>();

            for (XWPFParagraph para : paragraphs) {

                listTerms.add(para.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listTerms;
    }

    public List<String> findTerms(List<String> paragraphs) {
        terms = new ArrayList<>();
        int countWords = 0;
        //percorre a lista de paragrafos e transforma eles em listas de palavras...

        for (int i = 0; i < paragraphs.size(); i++) {
            String paragraph = paragraphs.get(i);
            //separa as palavras em
            String[] words = paragraph.split(" ");


            for (int j = 0; j < words.length; j++) {
                String word = words[j];
                countWords++;

                //remove os pontos em caso de palavras que estão em finais de paragrafos.
                word = word.replace('.', ' ');
                //remove os virgulas em caso de palavras que estão em finais de orações.
                word = word.replace(',', ' ');

                //elimina os espaços
                word = word.trim();

                if (!word.isEmpty()) {

                    //Se o primeiro caractere for maiusculo e o ultimo for fecha parenteses...
                    if ((Character.isUpperCase(word.charAt(0))) &&
                            (Character.compare(word.charAt(word.length() - 1), ')') == 0)) {
                        addTerm(word);
                    } //Se o primeiro caractere for abre parenteses e o ultimo for fecha parenteses...
                    else if ((Character.compare(word.charAt(0), '(') == 0) &&
                            (Character.compare(word.charAt(word.length() - 1), ')') == 0)) {
                        addTerm(word);
                    }
                }
            }
        }
        return terms;
    }

    private void addTerm(String word) {
        if (validTerm(word)) {
            if (!terms.contains(removeParenthesis(word))) {
                terms.add(removeParenthesis(word));
            }
        }
    }


    private boolean validTerm(String word) {
        //converte a palavra para array
        char[] letters = word.toCharArray();
        //termina em -1 para verificar se a palavra é inteira maiuscula
        boolean isTerm = true;

        for (int j = 1; j < letters.length - 1; j++) {
            if (!Character.isUpperCase(letters[j])) {
                isTerm = false;
                break; //termino o laço para evitar processamento desnecessario
            }
        }

        return isTerm;
    }


    private String removeParenthesis(String word) {
        return word.replace('(', ' ').replace(')', ' ').trim();
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }
}
