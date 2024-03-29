package com.mezzalira.model.service;

import com.mezzalira.model.entity.TipoSigla;
import com.mezzalira.model.framework.ICrudService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 30/11/13
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public interface TipoSiglaService extends ICrudService<TipoSigla, Integer> {
    List<TipoSigla> complete();
}
