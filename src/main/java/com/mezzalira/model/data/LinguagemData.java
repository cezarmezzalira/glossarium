package com.mezzalira.model.data;

import com.mezzalira.model.entity.Linguagem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 30/11/13
 * Time: 01:33
 * To change this template use File | Settings | File Templates.
 */
public interface LinguagemData extends JpaRepository<Linguagem, Integer> {
}
