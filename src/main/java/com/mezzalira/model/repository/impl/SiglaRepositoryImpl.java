package com.mezzalira.model.repository.impl;

import com.mezzalira.model.entity.Sigla;
import com.mezzalira.model.framework.BaseRepository;
import com.mezzalira.model.repository.SiglaRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Path;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar
 * Date: 24/01/14
 * Time: 18:18
 */
@Repository
public class SiglaRepositoryImpl extends BaseRepository<Sigla> implements SiglaRepository{

    @Override
    public List<Sigla> findBySiglaOrLinguaIdOrAreaIdOrSubareaIdOrTipoSiglaId(Sigla sigla) {
        initialize();

        Path<String> columnSigla = root.get("sigla");
        query.select(root).orderBy(criterio.asc(columnSigla));

        if (StringUtils.isNotBlank(sigla.getSigla())) {
            and(criterio.like(criterio.lower(columnSigla), containing(sigla.getSigla())));
        }

        if (sigla.getLinguaid() != null) {
            Path<Integer> columnLinguaId = root.get("linguaid").get("linguaid");
            and(criterio.equal(columnLinguaId, sigla.getLinguaid().getLinguaid()));
        }

        if (sigla.getAreaid() != null) {
            Path<Integer> columnAreaId = root.get("areaid").get("areasid");
            and(criterio.equal(columnAreaId, sigla.getAreaid().getAreasid()));
        }

        if (sigla.getSubareaid() != null) {
            Path<Integer> columnSubareaId = root.get("subareaid").get("subareaid");
            and(criterio.equal(columnSubareaId, sigla.getSubareaid().getSubareaid()));
        }

        if (sigla.getTiposiglaid() != null) {
            Path<Integer> columnTipoSiglaId = root.get("tiposiglaid").get("tiposiglaid");
            and(criterio.equal(columnTipoSiglaId, sigla.getTiposiglaid().getTiposiglaid()));
        }

        //Cezar 10/02/2014
        //Trazer somente siglas aprovadas para pesquisa
        Path<String> columnDtAprovada = root.get("dtaprovada");
        and(criterio.isNotNull(columnDtAprovada));

        return entityManager.createQuery(query)/*.setMaxResults()*/.getResultList();
    }

    @Override
    public List<Sigla> findByDataAprovada() {
        initialize();
        Path<String> columnDtAprovada = root.get("dtaprovada");

        Path<String> columnSigla = root.get("sigla");
        query.select(root).orderBy(criterio.asc(columnSigla));
        and(criterio.isNull(columnDtAprovada));

        return entityManager.createQuery(query).getResultList();

    }
}