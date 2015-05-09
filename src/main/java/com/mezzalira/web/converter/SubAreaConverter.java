package com.mezzalira.web.converter;

import com.mezzalira.model.entity.SubArea;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.SubAreaService;
import com.mezzalira.web.framework.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 02/12/13
 * Time: 19:56
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SubAreaConverter extends BaseConverter<SubArea, Integer> {

    @Autowired
    private SubAreaService subAreaService;

    @Override
    protected ICrudService<SubArea, Integer> getService() {
        return subAreaService;
    }

    @Override
    protected String getAsString(SubArea value) {
        return value.getSubareaid().toString();
    }

    @Override
    protected Integer getAsObject(String value) {
        return new Integer(value);
    }
}
