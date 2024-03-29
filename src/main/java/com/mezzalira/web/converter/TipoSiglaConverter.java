package com.mezzalira.web.converter;

import com.mezzalira.model.entity.TipoSigla;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.TipoSiglaService;
import com.mezzalira.web.framework.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 02/12/13
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TipoSiglaConverter extends BaseConverter<TipoSigla, Integer>{

    @Autowired private TipoSiglaService tipoSiglaService;

    @Override
    protected ICrudService<TipoSigla, Integer> getService() {
        return tipoSiglaService;
    }

    @Override
    protected String getAsString(TipoSigla value) {
        return value.getTiposiglaid().toString();
    }

    @Override
    protected Integer getAsObject(String value) {
        return new Integer(value);
    }
}
