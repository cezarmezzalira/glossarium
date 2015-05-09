package com.mezzalira.model.service.impl;

import com.googlecode.ehcache.annotations.Cacheable;
import com.mezzalira.model.data.TipoSiglaData;
import com.mezzalira.model.entity.TipoSigla;
import com.mezzalira.model.framework.CrudService;
import com.mezzalira.model.service.TipoSiglaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 01/12/13
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TipoServiceImpl extends CrudService<TipoSigla, Integer> implements TipoSiglaService{

    @Autowired private TipoSiglaData tipoSiglaData;

    @Override
    protected JpaRepository<TipoSigla, Integer> getData() {
        return tipoSiglaData;
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheName = "tiposigla")
    public List<TipoSigla> complete() {
        return tipoSiglaData.findAll();
    }
}
