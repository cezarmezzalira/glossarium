package com.mezzalira.model.service.impl;

import com.googlecode.ehcache.annotations.Cacheable;
import com.mezzalira.model.data.SubAreaData;
import com.mezzalira.model.entity.SubArea;
import com.mezzalira.model.framework.CrudService;
import com.mezzalira.model.service.SubAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 01/12/13
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SubAreaImpl extends CrudService<SubArea, Integer> implements SubAreaService{
    @Autowired private SubAreaData subAreaData;

    @Override
    protected JpaRepository<SubArea, Integer> getData() {
        return subAreaData;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheName = "subarea")
    public List<SubArea> complete() {
        return subAreaData.findAll();
    }
}
