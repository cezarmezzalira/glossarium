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

    private SiglaDataModel siglaDataModel;

    private List<Sigla> itensAdicionados = new ArrayList<>();

    private Sigla[] siglas;


    public void limparCampos() {
        entity = new Sigla();
        siglaDataModel = new SiglaDataModel(new ArrayList<>());

    }

    private void addToModel() {
        siglaDataModel = new SiglaDataModel(lsEntity);
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
        List<SiglaReport> siglasReport = new ArrayList<>();
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
        addToModel();
    }

    public void adicionarNaLista() {
        for (Sigla sigla : lsEntity) {
            if (!itensAdicionados.contains(sigla)) {
                itensAdicionados.add(sigla);
            }
        }
        //Ordeno a lista depois de inserir os dados para que a visualização do relatório fique correta.
        Collections.sort(itensAdicionados, new SiglaComparator());
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

    public List<Sigla> getItensAdicionados() {
        return itensAdicionados;
    }

    public void setItensAdicionados(List<Sigla> itensAdicionados) {
        this.itensAdicionados = itensAdicionados;
    }

    public Sigla[] getSiglas() {
        return siglas;
    }

    public void setSiglas(Sigla[] siglas) {
        this.siglas = siglas;
    }
}