package com.mezzalira.web.controller;

import com.mezzalira.model.entity.*;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.*;
import com.mezzalira.web.framework.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 14/12/13
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("view")
public class SiglaController extends CrudController<Sigla, Integer> {

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

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected ICrudService<Sigla, Integer> getService() {
        return siglaService;
    }

    @Override
    protected String getUrlFormPage() {
        return "/pages/admin/siglaForm.xhtml?faces-redirect=true";
    }

    @Override
    public void find() {
        if (entity.getSigla() != "")
            lsEntity = siglaService.findBySigla(entity.getSigla());
        else
            lsEntity = siglaService.findAll();
    }

    public void findNaoAprovados(){
        lsEntity = siglaService.findByDataAprovada();
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
}
