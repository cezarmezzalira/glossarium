package com.mezzalira.web.util;

import java.io.Serializable;

/**
 * Created by cezar on 05/06/15.
 */
public class Termo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String termo;
    private Integer paragrafo;
    private Integer numPalavra;

    public Termo(String termo, Integer paragrafo, Integer numPalavra) {
        this.termo = termo;
        this.paragrafo = paragrafo;
        this.numPalavra = numPalavra;
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public Integer getParagrafo() {
        return paragrafo;
    }

    public void setParagrafo(Integer paragrafo) {
        this.paragrafo = paragrafo;
    }

    public Integer getNumPalavra() {
        return numPalavra;
    }

    public void setNumPalavra(Integer numPalavra) {
        this.numPalavra = numPalavra;
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
