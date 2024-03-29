package com.mezzalira.web.converter;

import com.mezzalira.model.entity.Area;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.AreaService;
import com.mezzalira.web.framework.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 02/12/13
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AreaConverter extends BaseConverter<Area, Integer> {

    @Autowired
    private AreaService areaService;

    @Override
    protected ICrudService<Area, Integer> getService() {
        return areaService;
    }

    @Override
    protected String getAsString(Area value) {
        return value.getAreasid().toString();
    }

    @Override
    protected Integer getAsObject(String value) {
        return new Integer(value);
    }
}

