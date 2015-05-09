package com.mezzalira.model.repository;

import com.mezzalira.model.entity.Usuario;

/**
 * Created by -Cezar on 02/02/14.
 */
public interface UsuarioRepository {
    Usuario login(Usuario usuario);
}
