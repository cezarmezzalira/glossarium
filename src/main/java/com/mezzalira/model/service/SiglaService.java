package com.mezzalira.model.service;

import com.mezzalira.model.entity.Sigla;
import com.mezzalira.model.framework.ICrudService;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 14/12/13
 * Time: 17:14
 */
public interface SiglaService extends ICrudService<Sigla, Integer> {
    List<Sigla> findBySigla(String sigla);

    List<Sigla> findBySiglaOrLinguaIdOrAreaIdOrSubareaIdOrTipoSiglaId(Sigla sigla);

    List<Sigla> findByDataAprovada();

    public List<Sigla> findBySiglaIn(Set<String> termos);

}
