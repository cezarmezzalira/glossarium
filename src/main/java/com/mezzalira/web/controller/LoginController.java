package com.mezzalira.web.controller;

import com.mezzalira.model.entity.Usuario;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.UsuarioService;
import com.mezzalira.web.framework.CrudController;
import com.mezzalira.web.util.JsfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Created by -Cezar on 02/02/14.
 */
@Controller
@Scope("view")
public class LoginController extends CrudController<Usuario, Integer>{

    @Autowired
    private UsuarioService usuarioService;

    private Integer matricula;
    private String senha;

    @Override
    protected ICrudService<Usuario, Integer> getService() {
        return usuarioService;
    }

    @Override
    protected String getUrlFormPage() {
        return "/pages/admin/login.xhtml";
    }

    public void logout() {
        JsfUtil.removeAttributeSession("usuario");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/glossarium/pages/login.xhtml?faces-redirect=true");
        } catch (IOException e) {
            addMessage("Erro ao direcionar usuário", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void login(){
        Usuario usuario = usuarioService.login(new Usuario(matricula, senha));
        JsfUtil.setAttributeSession("usuario", usuario);
        if (usuario != null) {
            try {
                //FacesContext.getCurrentInstance().getExternalContext().redirect("/glossarium/pages/admin/principal/principal.xhtml?faces-redirect=true");
                FacesContext.getCurrentInstance().getExternalContext().redirect("/glossarium/pages/admin/professor/siglaAprovacaoSearch.xhtml?faces-redirect=true");
            } catch (IOException e) {
                addMessage("Erro ao direcionar usuário", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            addMessage("Usuário ou Senha inválidos", FacesMessage.SEVERITY_ERROR);
        }
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
