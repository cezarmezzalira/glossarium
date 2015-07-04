package com.mezzalira.model.service.impl;

import com.mezzalira.model.data.SiglaData;
import com.mezzalira.model.entity.Sigla;
import com.mezzalira.model.entity.Usuario;
import com.mezzalira.model.framework.CrudService;
import com.mezzalira.model.repository.SiglaRepository;
import com.mezzalira.model.service.SiglaService;
import com.mezzalira.web.util.JsfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 14/12/13
 * Time: 17:27
 */
@Service
public class SiglaServiceImpl extends CrudService<Sigla, Integer> implements SiglaService {

    @Autowired
    private SiglaData siglaData;

    @Autowired
    private SiglaRepository siglaRepository;

    @Override
    protected JpaRepository<Sigla, Integer> getData() {
        return siglaData;
    }

    @Override
    public List<Sigla> findBySigla(String sigla) {
        if (sigla != null) {
            return siglaData.findBySigla(sigla);
        } else {
            return siglaData.findAll();
        }
    }

    @Override
    public Sigla preProcessorSave(Sigla entity) {
        //Cezar Mezzalira - 10/02/2014
        //Feita validação para não mudar a data toda  a vez que salva, mas sim somente na primeira vez.
        if (entity.getDtcadastro() == null) {
            entity.setDtcadastro(new Date(Calendar.getInstance().getTimeInMillis()));
        }

        //Cezar Mezzalira 10/02/2014
        //Feita validação para que quando o registro já esteja persistido e o usuario seja administrador
        //o registro é aprovado automaticamente.
        if ((entity.getSiglaid() != null) &&
                (entity.getDtaprovada() == null) &&
                (entity.getUsuarioidaprov() == null)) {
            Usuario usuario = (Usuario) JsfUtil.getAttributeSession("usuario");
            entity.setDtaprovada(new Date(Calendar.getInstance().getTimeInMillis()));
            entity.setUsuarioidaprov(usuario);
        }

        //Cezar Mezzalira - 21/01/2014
        //Irá salvar todas as siglas como maiusculas...
        entity.setSigla(entity.getSigla().toUpperCase());
        return entity;
    }

    @Override
    public List<Sigla> findBySiglaOrLinguaIdOrAreaIdOrSubareaIdOrTipoSiglaId(Sigla sigla) {
        return siglaRepository.findBySiglaOrLinguaIdOrAreaIdOrSubareaIdOrTipoSiglaId(sigla);
    }

    @Override
    public List<Sigla> findByDataAprovada() {
        return siglaRepository.findByDataAprovada();
    }

    @Override
    public List<Sigla> findBySiglaIn(Set<String> termos) {
        return siglaRepository.findBySiglaIn(termos);
    }
}
