package com.mezzalira.web.util;

import com.mezzalira.model.entity.Sigla;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by cezar on 13/07/15.
 * código retirado e aprimorado do link
 * http://www.cnblogs.com/hold/archive/2013/03/14/2959452.html
 * Disponivel em chines e códigos em inglês
 */


public class WordPOIUtil {

    // 替换word中需要替换的特殊字符
    // Substituir os caracteres especiais na palavra precisa ser substituído
    public boolean replaceAndGenerateWord(String srcPath, String destPath,
                                          String sections, List<Sigla> lsEntity) {
        String[] sp = srcPath.split("\\.");
        String[] dp = destPath.split("\\.");
        if ((sp.length > 0) && (dp.length > 0)) {// verifica a extensão do arquivo
            // compara qual é a extensão
            if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
                return replaceOnDocx(srcPath, destPath, sections, lsEntity);
            } else if ((sp[sp.length - 1].equalsIgnoreCase("doc"))
                    && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
                return replaceOnDoc(srcPath, destPath, lsEntity);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean replaceOnDoc(String srcPath, String destPath, List<Sigla> lsEntity) {
        HWPFDocument document = null;
        try {
            document = new HWPFDocument(new FileInputStream(srcPath));

            document = replaceTermsOnDoc(document, getListToReplace(lsEntity));

            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private HWPFDocument replaceTermsOnDoc(HWPFDocument document, List<Termo> terms) {
        HWPFDocument docChanged = document;
        Range range = docChanged.getRange();
        List<Termo> replacedTerms = new ArrayList<>();

        for (int x = 0; x < range.numSections(); x++) {
            Section s = range.getSection(x);
            for (int y = 0; y < s.numParagraphs(); y++) {
                Paragraph paragraph = s.getParagraph(y);

                String textParag = paragraph.text();
                for (int z = 0; z < paragraph.numCharacterRuns(); z++) {
                    CharacterRun run = paragraph.getCharacterRun(z);
                    String text = run.text();
                    for (Termo termo : terms) {
                        //verifico se no range existe o termo e se o termo não foi substituido
                        //para evitar que ele substitua mais de uma vez o termo
                        if ((text.contains(termo.getTermo())) &&
                                (!replacedTerms.contains(termo))) {
                            //String replacedText = text.replaceFirst(termo.getTermo(), termo.getSignficado());

                            run.replaceText(termo.getTermo(), termo.getSignficado());

                            //run.replaceText();
                            replacedTerms.add(termo);
                        }
                    }
                }


            }
        }

        return docChanged;
    }


    private boolean replaceOnDocx(String srcPath, String destPath,
                                  String sectionName, List<Sigla> lsEntity) {
        try {
            XWPFDocument document = new XWPFDocument(
                    POIXMLDocument.openPackage(srcPath));
            // 替换段落中的指定文字
            // Substituir os parágrafos de texto especificados
            document = replaceTerms(document, getListToReplace(lsEntity));

            if (document == null) {
                return false;
            } else {
                //subtitui a seção
                document = replaceSectionListOnDocx(document, sectionName, lsEntity);
            }

            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Substitui os termos em um arquivo DOCX
     *
     * @param document
     * @param terms
     * @return
     */
    private XWPFDocument replaceTerms(XWPFDocument document, List<Termo> terms) {

        XWPFDocument docChanged = document;

        try {

            Iterator<XWPFParagraph> itPara = docChanged
                    .getParagraphsIterator();
            List<String> replacedTerms = new ArrayList<>();

            while (itPara.hasNext()) {
                XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                List<XWPFRun> runs = paragraph.getRuns();
                for (int i = 0; i < runs.size(); i++) {
                    String oneparaString = runs.get(i).getText(
                            runs.get(i).getTextPosition());

                    //se não foi marcado nenhum termo, então
                    if (terms == null) {
                        break;
                    } else if (oneparaString != null) {
                        //retira todos os parenteses para substituir apenas o termo
                        oneparaString = oneparaString.replace("(", "");
                        oneparaString = oneparaString.replace(")", "");

                        for (Termo termo : terms) {
                            //se não estiver na lista de substituidos e conter o termo na string
                            if ((!replacedTerms.contains(termo.getTermo())) &&
                                    (oneparaString.contains(termo.getTermo()))) {
                                String term = termo.getTermo();


                                //valido a forma como a sigla aparece no trecho (run[i]) do paragrafo
                            /*if (oneparaString.contains("("+termo.getTermo()+")")){
                                term = "("+termo.getTermo()+")";
                            } else if (oneparaString.contains("("+termo.getTermo())){
                                term = "[(]"+termo.getTermo();
                            } else if (oneparaString.contains(termo.getTermo()+")")){
                                term = termo.getTermo()+")";
                            }*/

                                //crio um string builder para facilitar a criação da string que substituirá o termo
                                StringBuilder significado = new StringBuilder();
                                significado.append(termo.getSignficado()).append("(").append(termo.getTermo()).append(")");
                                //substitui o termo na primeira vez que aparece
                                oneparaString = oneparaString.replaceFirst(term, significado.toString());
                                //atualiza o texto do run
                                runs.get(i).setText(oneparaString, 0);
                                //adiciono para uma lista de itens já substituidos
                                replacedTerms.add(termo.getTermo());
                            }
                        }
                    }
                }
            }
            return docChanged;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * * * método utilizado para substituir um determinado parametro em um paragrafo e criar uma lista
     * em um arquivo .DOC
     *
     * @param document
     * @param sectionName
     * @param terms
     * @return
     */
    private XWPFDocument replaceSectionListOnDocx(XWPFDocument document, String sectionName, List<Sigla> terms) {
        XWPFDocument docChanged = document;
        Iterator<XWPFParagraph> itPara = docChanged
                .getParagraphsIterator();
        //int countParagraph = 0;
        while (itPara.hasNext()) {
            XWPFParagraph paragraph = itPara.next();
            List<XWPFRun> runs = paragraph.getRuns();
            boolean findSection = false;
            int posRun = 0;
            for (int i = 0; i < runs.size(); i++) {
                String oneparaString = runs.get(i).getText(
                        runs.get(i).getTextPosition());
                if ((oneparaString != null) &&
                        (oneparaString.contains(sectionName))) {
                    findSection = true;
                    posRun = i;
                    break;
                }
            }
            //se encontrar a seção, vai remove-la e adicionar novas com as siglas
            if (findSection) {
                paragraph.removeRun(posRun);
                //crio uma nova linha
                for (Sigla term : terms) {
                    XWPFRun tempRun = paragraph.createRun();
                    StringBuilder termText = new StringBuilder();

                    termText.append(term.getSigla().toUpperCase()).append("\t\t").append(term.getSignificado());
                    tempRun.setText(termText.toString());
                    boolean isItalic = term.getLinguaid().getEstrangeira() == 1;
                    tempRun.setItalic(isItalic);
                    tempRun.addCarriageReturn();
                }

            }

        }
        return docChanged;
    }


    /**
     * método utilizado para substituir um determinado parametro em um paragrafo e criar uma lista
     * em um arquivo .DOC
     *
     * @param srcPath
     * @param destPath
     * @param map
     * @return true se criou o arquivo | false se houve algum problema
     */
    private boolean replaceListOnDoc(String srcPath, String destPath, Map<String, String> map) {
        HWPFDocument document = null;

        try {
            document = new HWPFDocument(new FileInputStream(srcPath));
            Range range = document.getRange();
            for (int x = 0; x < range.numSections(); x++) {
                Section s = range.getSection(x);
                for (int y = 0; y < s.numParagraphs(); y++) {
                    Paragraph paragraph = s.getParagraph(y);


                }
            }

            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<Termo> getListToReplace(List<Sigla> lsEntity) {

        List<Termo> words = new ArrayList<>();
        for (Sigla sigla : lsEntity) {
            Termo termo = new Termo();
            //concateno parenteses, porque é como a sigla aparece em um paragrafo.
            StringBuilder word = new StringBuilder();
            //word.append("(").append(sigla.getSigla()).append(")");
            word.append(sigla.getSigla());

            //A sigla sera substituida pelo seu significado e na sequencia
            // por ela mesma entre parenteses
            StringBuilder replaceTo = new StringBuilder();
            replaceTo.append(sigla.getSignificado()).append(" - ");

            termo.setTermo(word.toString());
            termo.setSignficado(replaceTo.toString());
            termo.setEstrangeiro(sigla.getLinguaid().getEstrangeira() == 1);


            if (!words.contains(termo)) {
                words.add(termo);
            }
        }

        return words;
    }
}
