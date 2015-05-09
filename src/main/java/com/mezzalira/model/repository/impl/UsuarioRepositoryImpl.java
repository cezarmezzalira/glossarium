package com.mezzalira.model.repository.impl;

import com.mezzalira.model.entity.Usuario;
import com.mezzalira.model.framework.BaseRepository;
import com.mezzalira.model.repository.UsuarioRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Path;

/**
 * Created by -Cezar on 02/02/14.
 */
@Repository
public class UsuarioRepositoryImpl extends BaseRepository<Usuario> implements UsuarioRepository {
    @Override
    public Usuario login(Usuario usuario) {
        initialize();
        Path<String> columnUsuario = root.get("matricula");
        //select from
        query.select(root);
        //and matricula
        and(criterio.equal(columnUsuario, usuario.getMatricula()));

        //and senha
        Path<String> columnSenha = root.get("senha");
        and(criterio.equal(columnSenha, usuario.getSenha()));

        //retorno
        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (Exception e){
            return null;
        }

    }
}
