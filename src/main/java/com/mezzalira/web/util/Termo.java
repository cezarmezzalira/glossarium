package com.mezzalira.web.util;

import java.io.Serializable;

/**
 * Created by cezar on 05/06/15.
 */
public class Termo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String termo;
    private String signficado;
    private Boolean estrangeiro;

    public Termo(String termo, String signficado, Boolean estrangeiro) {
        this.termo = termo;
        this.signficado = signficado;
        this.estrangeiro = estrangeiro;
    }

    public Termo() {
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public String getSignficado() {
        return signficado;
    }

    public void setSignficado(String signficado) {
        this.signficado = signficado;
    }

    public Boolean getEstrangeiro() {
        return estrangeiro;
    }

    public void setEstrangeiro(Boolean estrangeiro) {
        this.estrangeiro = estrangeiro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Termo termo1 = (Termo) o;

        return termo.equals(termo1.termo);

    }

    @Override
    public int hashCode() {
        return termo.hashCode();
    }
}
