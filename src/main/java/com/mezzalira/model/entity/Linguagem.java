/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mezzalira.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Cezar Mezzalira
 */
@Entity
@Table(name = "linguagens", catalog = "glossarium", schema = "")
public class Linguagem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LINGUAID", nullable = false)
    private Integer linguaid;
    @Basic(optional = false)
    @Column(name = "DESCRICAO", nullable = false, length = 100)
    private String descricao;
    @Basic(optional = false)
    @Column(name = "ESTRANGEIRA", nullable = false)
    private short estrangeira;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "linguaid")
    private List<Sigla> siglaList;

    public Linguagem() {
    }

    public Linguagem(Integer linguaid) {
        this.linguaid = linguaid;
    }

    public Linguagem(Integer linguaid, String descricao, short estrangeira) {
        this.linguaid = linguaid;
        this.descricao = descricao;
        this.estrangeira = estrangeira;
    }

    public Integer getLinguaid() {
        return linguaid;
    }

    public void setLinguaid(Integer linguaid) {
        this.linguaid = linguaid;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public short getEstrangeira() {
        return estrangeira;
    }

    public void setEstrangeira(short estrangeira) {
        this.estrangeira = estrangeira;
    }

    public List<Sigla> getSiglaList() {
        return siglaList;
    }

    public void setSiglaList(List<Sigla> siglaList) {
        this.siglaList = siglaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (linguaid != null ? linguaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Linguagem)) {
            return false;
        }
        Linguagem other = (Linguagem) object;
        if ((this.linguaid == null && other.linguaid != null) || (this.linguaid != null && !this.linguaid.equals(other.linguaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Linguagem[ linguaid=" + linguaid + " ]";
    }

}
