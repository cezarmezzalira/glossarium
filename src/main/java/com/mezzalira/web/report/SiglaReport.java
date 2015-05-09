package com.mezzalira.web.report;

import com.mezzalira.model.entity.Sigla;

import java.io.Serializable;

/**
 * Created by -Cezar on 03/02/14.
 */
public class SiglaReport implements Serializable {

    private String sigla;
    private String significado;
    private String estrangeira;

    public SiglaReport() {

    }

    public SiglaReport(Sigla sigla) {
        this.sigla = sigla.getSigla();
        this.significado = sigla.getSignificado();
        this.estrangeira = String.valueOf(sigla.getLinguaid().getEstrangeira());
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getSignificado() {
        return significado;
    }

    public void setSignificado(String significado) {
        this.significado = significado;
    }

    public String getEstrangeira() {
        return estrangeira;
    }

    public void setEstrangeira(String estrangeira) {
        this.estrangeira = estrangeira;
    }
}
