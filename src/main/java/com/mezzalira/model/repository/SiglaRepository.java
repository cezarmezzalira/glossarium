package com.mezzalira.model.repository;

import com.mezzalira.model.entity.Sigla;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar
 * Date: 22/01/14
 * Time: 21:31
 */
public interface SiglaRepository {

    List<Sigla> findBySiglaOrLinguaIdOrAreaIdOrSubareaIdOrTipoSiglaId(Sigla sigla);

    List<Sigla> findByDataAprovada();

    List<Sigla> findBySiglaIn(Set<String> termos);
}
