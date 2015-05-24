package com.mezzalira.web.report;

import net.sf.jasperreports.engine.*;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by cezar on 24/05/15.
 */
public class UtilReport {
    private final static String PATH_REPORTS = "WEB-INF/relatorios/";

    public void getReportByName(String name, JRDataSource dataSource, HashMap<String, Object> properties){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        //caminho arquivo relatorio
        String file = servletContext.getRealPath("WEB-INF/relatorios/" + name + ".jasper");

        //contexto
        FacesContext context = FacesContext.getCurrentInstance();
        //input stream do arquivo .jasper

        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        try {
            JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance())
                    .setProperty("net.sf.jasperreports.default.font.name", "Arial Sans");

            JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance())
                    .setProperty("net.sf.jasperreports.default.pdf.embedded", "true");

            JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance())
                    .setProperty("net.sf.jasperreports.default.pdf.font.name", "Arial Sans");

            ServletOutputStream servletOutputStream = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"" + name + ".pdf\"");
            JasperPrint impressao = JasperFillManager.fillReport(file, properties, dataSource);
            JasperExportManager.exportReportToPdfStream(impressao, response.getOutputStream());

            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (JRException | IOException e) {
            e.printStackTrace();
        } finally{
            context.renderResponse();
            context.responseComplete();
        }
    }
}
