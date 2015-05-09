package com.mezzalira.web.converter;

import com.mezzalira.model.entity.Linguagem;
import com.mezzalira.model.framework.ICrudService;
import com.mezzalira.model.service.LinguagemService;
import com.mezzalira.web.framework.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 02/12/13
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
@Component
public class LinguagemConverter extends BaseConverter<Linguagem, Integer> {

    @Autowired
    private LinguagemService linguagemService;

    @Override
    protected ICrudService<Linguagem, Integer> getService() {
        return linguagemService;
    }

    @Override
    protected String getAsString(Linguagem value) {
        return value.getLinguaid().toString();
    }

    @Override
    protected Integer getAsObject(String value) {
        return new Integer(value);
    }
}
