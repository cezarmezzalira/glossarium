package com.mezzalira.model.service.impl;

import com.googlecode.ehcache.annotations.Cacheable;
import com.mezzalira.model.data.LinguagemData;
import com.mezzalira.model.entity.Linguagem;
import com.mezzalira.model.framework.CrudService;
import com.mezzalira.model.service.LinguagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 01/12/13
 * Time: 02:06
 * To change this template use File | Settings | File Templates.
 */
@Service
public class LinguagemServiceImpl extends CrudService<Linguagem, Integer> implements LinguagemService{

    @Autowired private LinguagemData linguagemData;

    @Override
    protected JpaRepository<Linguagem, Integer> getData() {
        return linguagemData;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheName = "linguagem")
    public List<Linguagem> complete() {
        return linguagemData.findAll();
    }
}
