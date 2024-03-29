package com.mezzalira.model.service.impl;

import com.mezzalira.model.data.UsuarioData;
import com.mezzalira.model.entity.Usuario;
import com.mezzalira.model.framework.CrudService;
import com.mezzalira.model.repository.UsuarioRepository;
import com.mezzalira.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 30/11/13
 * Time: 02:01
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UsuarioServiceImpl extends CrudService<Usuario, Integer> implements UsuarioService {

    @Autowired
    private UsuarioData usuarioData;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    protected JpaRepository<Usuario, Integer> getData() {
        return usuarioData;
    }


    @Override
    public Usuario findByMatricula(int matricula) {
        return usuarioData.findByMatricula(matricula);
    }

    @Override
    public Usuario login(Usuario usuario) {
        return usuarioRepository.login(usuario);
    }
}
