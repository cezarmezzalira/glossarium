package com.mezzalira.model.data;

import com.mezzalira.model.entity.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 30/11/13
 * Time: 01:37
 * To change this template use File | Settings | File Templates.
 */
public interface SubAreaData extends JpaRepository<SubArea, Integer> {
}
