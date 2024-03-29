package com.mezzalira.model.service.impl;

import com.googlecode.ehcache.annotations.Cacheable;
import com.mezzalira.model.data.AreaData;
import com.mezzalira.model.entity.Area;
import com.mezzalira.model.framework.CrudService;
import com.mezzalira.model.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 01/12/13
 * Time: 03:17
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AreaServiceImpl extends CrudService<Area, Integer> implements AreaService{

    @Autowired private AreaData areaData;

    @Override
    protected JpaRepository<Area, Integer> getData() {
        return areaData;
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable(cacheName = "area")
    public List<Area> complete() {
        return areaData.findAll();
    }
}
