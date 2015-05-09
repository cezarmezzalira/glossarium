package com.mezzalira.web.controller;

import com.mezzalira.model.entity.Usuario;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.UsuarioService;
import com.mezzalira.web.framework.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 30/11/13
 * Time: 02:18
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("view")
public class UsuarioController extends CrudController<Usuario, Integer> {

    @Autowired private UsuarioService usuarioService;

    @Override
    protected ICrudService<Usuario, Integer> getService() {
        return usuarioService;
    }

    @Override
    protected String getUrlFormPage() {
        return "/pages/admin/usuarioForm.xhtml?faces-redirect=true";
    }
}
