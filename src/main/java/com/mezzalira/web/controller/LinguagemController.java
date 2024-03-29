package com.mezzalira.web.controller;

import com.mezzalira.model.entity.Linguagem;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.LinguagemService;
import com.mezzalira.web.framework.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 01/12/13
 * Time: 02:00
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("view")
public class LinguagemController  extends CrudController<Linguagem, Integer> {

    @Autowired private LinguagemService linguagemService;

    @Override
    protected ICrudService<Linguagem, Integer> getService() {
        return linguagemService;
    }

    @Override
    protected String getUrlFormPage() {
        return "/pages/admin/linguagemForm.xhtml?faces-redirect=true";
    }
}
