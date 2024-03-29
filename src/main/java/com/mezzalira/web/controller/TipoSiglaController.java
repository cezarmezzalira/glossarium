package com.mezzalira.web.controller;

import com.mezzalira.model.entity.TipoSigla;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.TipoSiglaService;
import com.mezzalira.web.framework.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 01/12/13
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("view")
public class TipoSiglaController extends CrudController<TipoSigla, Integer> {

    @Autowired private TipoSiglaService tipoSiglaService;

    @Override
    protected ICrudService<TipoSigla, Integer> getService() {
        return tipoSiglaService;
    }

    @Override
    protected String getUrlFormPage() {
        return "/pages/admin/tipoSiglaForm.xhtml?faces-redirect=true";
    }
}
