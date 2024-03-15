package com.mezzalira.web.util;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine .JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

/**
 * Created by -Cezar on 04/02/14.
 */

public class ReportUtil {
    public void gerarRelatorio(Collection<?> collection, final String nomeRelatorio) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        String arquivo = servletContext.getRealPath("WEB-INF/relatorios/"+nomeRelatorio+".jasper");
        JRDataSource jrds = new JRBeanCollectionDataSource(collection);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        try {
            ServletOutputStream servletOutputStream = response.getOutputStream();
            JasperRunManager.runReportToPdfStream(new FileInputStream(new File(arquivo)), response.getOutputStream(), null, jrds);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","attachment; filename=\""+nomeRelatorio+".pdf\"");
            servletOutputStream.flush();
            servletOutputStream.close();
            context.renderResponse();
            context.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
