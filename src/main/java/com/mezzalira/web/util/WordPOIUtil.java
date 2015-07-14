package com.mezzalira.web.util;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cezar on 13/07/15.
 * código retirado e aprimorado do link
 * http://www.cnblogs.com/hold/archive/2013/03/14/2959452.html
 * Disponivel em chines e códigos em inglês
 */


public class WordPOIUtil {

    // 替换word中需要替换的特殊字符
    // Substituir os caracteres especiais na palavra precisa ser substituído
    public boolean replaceAndGenerateWord(String srcPath,
                                                 String destPath, Map<String, String> map) {
        String[] sp = srcPath.split("\\.");
        String[] dp = destPath.split("\\.");
        if ((sp.length > 0) && (dp.length > 0)) {// verifica a extensão do arquivo
            // compara qual é a extensão
            if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
                return replaceOnDocx(srcPath, destPath, map);
            } else if ((sp[sp.length - 1].equalsIgnoreCase("doc"))
                    && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
                return replaceOnDoc(srcPath, destPath, map);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean replaceOnDoc(String srcPath, String destPath, Map<String, String> map) {
        HWPFDocument document = null;
        try {
            document = new HWPFDocument(new FileInputStream(srcPath));
            Range range = document.getRange();
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

    private boolean replaceOnDocx(String srcPath, String destPath, Map<String, String> map) {
        try {
            XWPFDocument document = new XWPFDocument(
                    POIXMLDocument.openPackage(srcPath));
            // 替换段落中的指定文字
            // Substituir os parágrafos de texto especificados
            Iterator<XWPFParagraph> itPara = document
                    .getParagraphsIterator();
            while (itPara.hasNext()) {
                XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                List<XWPFRun> runs = paragraph.getRuns();
                for (int i = 0; i < runs.size(); i++) {
                    String oneparaString = runs.get(i).getText(
                            runs.get(i).getTextPosition());
                    for (Map.Entry<String, String> entry : map
                            .entrySet()) {
                        oneparaString = oneparaString.replace(
                                entry.getKey(), entry.getValue());
                    }
                    runs.get(i).setText(oneparaString, 0);
                }
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
/*
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String filepathString = "D:/2.doc";
        String destpathString = "D:/2ttt.doc";
        Map<String, String> map = new HashMap<String, String>();
        map.put("${NAME}", "王五王五啊王力宏的辣味回答侯卫东拉网");
        map.put("${NsAME}", "王五王五啊王力味回答侯卫东拉网");
        map.put("${NAMaE}", "王五王五啊王力宏侯卫东拉网");
        map.put("${NArME}", "王五王五啊王力宏的辣味回答东拉网");
        map.put("${NwAME}", "王五王五啊王的辣味回答侯卫东拉网");
        map.put("${NA4ME}", "王五王五啊王力侯卫东拉网");
        map.put("${N5AME}", "王五王五辣味回答侯卫东拉网");
        map.put("${NAadwME}", "王五力宏的辣味回答侯卫东拉网");
        System.out.println(replaceAndGenerateWord(filepathString,
                destpathString, map));
    }
    */
}
