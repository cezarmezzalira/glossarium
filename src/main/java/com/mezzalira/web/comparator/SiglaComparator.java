package com.mezzalira.web.comparator;

import com.mezzalira.model.entity.Sigla;

import java.util.Comparator;

/**
 * Created by -Cezar on 14/02/14.
 */
public class SiglaComparator implements Comparator<Sigla>{
    @Override
    public int compare(Sigla sigla, Sigla outraSigla) {
        return sigla.getSigla().compareTo(outraSigla.getSigla());
    }
}
