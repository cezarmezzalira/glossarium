package com.mezzalira.web.controller;


import com.mezzalira.model.entity.*;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.*;
import com.mezzalira.web.comparator.SiglaComparator;
import com.mezzalira.web.framework.CrudController;
import com.mezzalira.web.model.SiglaDataModel;
import com.mezzalira.web.report.SiglaReport;
import com.mezzalira.web.report.UtilReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
    private UtilReport utilReport;

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
        getUtilReport().getReportByName("Siglas", new JRBeanCollectionDataSource(siglasReport), new HashMap<>());
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
        for (Sigla sigla : siglas) {

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

    public UtilReport getUtilReport() {
        if (utilReport == null) {
            utilReport = new UtilReport();
        }
        return utilReport;
    }

    public boolean getListaTemTermos() {
        return !(getItensAdicionados().size() > 0);
    }
}