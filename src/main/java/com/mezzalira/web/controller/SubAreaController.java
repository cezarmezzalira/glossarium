package com.mezzalira.web.controller;

import com.mezzalira.model.entity.SubArea;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.SubAreaService;
import com.mezzalira.web.framework.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 01/12/13
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("view")
public class SubAreaController extends CrudController<SubArea, Integer>{

    @Autowired private SubAreaService subAreaService;

    @Override
    protected ICrudService<SubArea, Integer> getService() {
        return subAreaService;
    }

    @Override
    protected String getUrlFormPage() {
        return "/pages/admin/subAreaForm.xhtml?faces-redirect=true";
    }
}
