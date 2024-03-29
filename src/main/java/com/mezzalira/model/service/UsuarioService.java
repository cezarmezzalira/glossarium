package com.mezzalira.model.service;

import com.mezzalira.model.entity.Usuario;
import com.mezzalira.model.framework.ICrudService;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 30/11/13
 * Time: 01:51
 * To change this template use File | Settings | File Templates.
 */
public interface UsuarioService extends ICrudService<Usuario, Integer> {

    /**
     * Obtem o usuario pela matricula
     *
     * @param matricula #return
     */

    Usuario findByMatricula(int matricula);

    Usuario login(Usuario usuario);
}
