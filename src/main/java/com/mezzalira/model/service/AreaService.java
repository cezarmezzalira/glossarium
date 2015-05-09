package com.mezzalira.model.service;

import com.mezzalira.model.entity.Area;
import com.mezzalira.model.framework.ICrudService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 30/11/13
 * Time: 21:00
 * To change this template use File | Settings | File Templates.
 */
public interface AreaService extends ICrudService<Area, Integer>{
    List<Area> complete();
}
