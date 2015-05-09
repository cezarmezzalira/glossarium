package com.mezzalira.web.controller;


import com.mezzalira.model.entity.*;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.*;
import com.mezzalira.web.comparator.SiglaComparator;
import com.mezzalira.web.framework.CrudController;
import com.mezzalira.web.model.SiglaDataModel;
import com.mezzalira.web.report.SiglaReport;
import com.mezzalira.web.util.ReadWordUtil;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 30/11/13
 * Time: 20:37
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("view")
public class PesquisaTermoController extends CrudController<Sigla, Integer> {

    @Autowired
    private SiglaService siglaService;

    //demais objetos para auto-complete
    @Autowired
    private LinguagemService linguagemService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private SubAreaService subAreaService;

    @Autowired
    private TipoSiglaService tipoSiglaService;


    private ReadWordUtil readWordUtil;

    private SiglaDataModel siglaDataModel;

    private Sigla[] siglas;

    private Part arquivoDoc;

    private String destination = "";


    private List<Sigla> itensAdicionados = new ArrayList<Sigla>();
    private List<String> terms = new ArrayList<>();

    public PesquisaTermoController() {
        this.destination = "/home/cezar/dev/temp/";
    }

    @Override
    protected ICrudService<Sigla, Integer> getService() {
        return siglaService;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected String getUrlFormPage() {
        return "index.xhtml";
    }

    public void gerarRelatorio() {
        //Crio a lista com os itens da lista final
        List<SiglaReport> siglasReport = new ArrayList<SiglaReport>();
        for (Sigla itensAdicionado : itensAdicionados) {
            siglasReport.add(new SiglaReport(itensAdicionado));
        }


        String nomeRelatorio = "Siglas";

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        String arquivo = servletContext.getRealPath("WEB-INF/relatorios/" + nomeRelatorio + ".jasper");
        JRDataSource jrds = new JRBeanCollectionDataSource(siglasReport);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        try {
            DefaultJasperReportsContext jContext = DefaultJasperReportsContext.getInstance();
            JRPropertiesUtil.getInstance(jContext).setProperty("net.sf.jasperreports.default.font.name", "Arial Sans");
            JRPropertiesUtil.getInstance(jContext).setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
            JRPropertiesUtil.getInstance(jContext).setProperty("net.sf.jasperreports.default.pdf.font.name", "Arial Sans");

            ServletOutputStream servletOutputStream = response.getOutputStream();
            JasperRunManager.runReportToPdfStream(new FileInputStream(new File(arquivo)), servletOutputStream, null, jrds);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nomeRelatorio + ".pdf\"");

            servletOutputStream.flush();
            servletOutputStream.close();
            context.renderResponse();
            context.responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Este metodo é responsavel por efetuar a pesquisa, de acordo com os parametros informados pelo usuario.
    *
    * */
    public void pesquisar() {
        lsEntity = siglaService.findBySiglaOrLinguaIdOrAreaIdOrSubareaIdOrTipoSiglaId(entity);
        siglaDataModel = new SiglaDataModel(lsEntity);
    }

    public void adicionarNaLista() {
        for (Sigla sigla : siglas) {
            if (!itensAdicionados.contains(sigla)) {
                itensAdicionados.add(sigla);
            }
        }
        //Ordeno a lista depois de inserir os dados para que a visualização do relatório fique correta.
        Collections.sort(itensAdicionados, new SiglaComparator());
    }

    public String adicionaNovoTermo() {
        return "cadsiglasuser";
    }


    public void readArquivo(InputStream inputStream, String fileExtension) {

        //"C:\\multi\\TCC_CezarAugustoMezzalira.docx"
        readWordUtil = new ReadWordUtil();

        List<String> paragrafos = new ArrayList<>();

        //Verifica a extensão do arquivo e executa o método correto.
        if (readWordUtil.EXTENSION_FILE_DOCX.equalsIgnoreCase(fileExtension)) {
            paragrafos = readWordUtil.readDocxFile(inputStream);
        } else if (readWordUtil.EXTENSION_FILE_DOC.equalsIgnoreCase(fileExtension)) {
            paragrafos = readWordUtil.readDocFile(inputStream);
        }


        if (paragrafos.size() > 0) {
            terms = readWordUtil.findTerms(paragrafos);
            StringBuilder builder = new StringBuilder("Termos: ");

            terms.sort(Comparator.<String>naturalOrder());

            for (String term : terms) {
                builder.append(term).append(" - ");
            }

            System.out.println(builder.toString());
        }else{
            addMessage(new FacesMessage("Nenhum termo foi encontrado no documento carregado."));
        }

    }


    public void upload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        // Do what you want with the file
        try {
            //copia para pasta do sistema
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());

            //faz analise do arquivo que foi recebido
            String fileName = event.getFile().getFileName();

            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            String filePath = destination + fileName;
            readArquivo(event.getFile().getInputstream(), fileExtension);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMessage(new FacesMessage("Uploaded!"));
    }


    public void copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            addMessage(new FacesMessage("Upload feito com sucesso!"));
            //System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //completes para objetos
    public List<Area> completeArea() {
        return areaService.complete();
    }

    public List<SubArea> completeSubArea() {
        return subAreaService.complete();
    }

    public List<Linguagem> completeLinguagem() {
        return linguagemService.complete();
    }

    public List<TipoSigla> completeTipoSigla() {
        return tipoSiglaService.complete();
    }

    public SiglaDataModel getSiglaDataModel() {
        return siglaDataModel;
    }

    public void setSiglaDataModel(SiglaDataModel siglaDataModel) {
        this.siglaDataModel = siglaDataModel;
    }

    public Sigla[] getSiglas() {
        return siglas;
    }

    public void setSiglas(Sigla[] siglas) {
        this.siglas = siglas;
    }

    public List<Sigla> getItensAdicionados() {
        return itensAdicionados;
    }

    public ReadWordUtil getReadWordUtil() {
        return readWordUtil;
    }

    public void setReadWordUtil(ReadWordUtil readWordUtil) {
        this.readWordUtil = readWordUtil;
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public Part getArquivoDoc() {
        return arquivoDoc;
    }

    public void setArquivoDoc(Part arquivoDoc) {
        this.arquivoDoc = arquivoDoc;
    }
}