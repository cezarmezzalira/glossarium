package com.mezzalira.model.data;

import com.mezzalira.model.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 01/12/13
 * Time: 03:38
 * To change this template use File | Settings | File Templates.
 */
public interface AreaData extends JpaRepository<Area, Integer> {
}
