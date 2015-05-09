package com.mezzalira.web.controller;

import com.mezzalira.model.entity.Area;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.AreaService;
import com.mezzalira.web.framework.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 01/12/13
 * Time: 03:44
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("view")
public class AreaController extends CrudController<Area, Integer>{

    @Autowired private AreaService areaService;

    @Override
    protected ICrudService<Area, Integer> getService() {
        return areaService;
    }

    @Override
    protected String getUrlFormPage() {
        return "/pages/admin/area/areaForm.xhtml?faces-redirect=true";
    }
}
