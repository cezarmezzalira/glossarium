package com.mezzalira.model.service;

import com.mezzalira.model.entity.SubArea;
import com.mezzalira.model.framework.ICrudService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 30/11/13
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 *
 */
public interface SubAreaService extends ICrudService<SubArea, Integer> {
    List<SubArea> complete();
}
