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
@Table(name = "tiposigla", catalog = "glossarium", schema = "")
public class TipoSigla implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TIPOSIGLAID", nullable = false)
    private Integer tiposiglaid;
    @Basic(optional = false)
    @Column(name = "DESCRICAO", nullable = false, length = 45)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiposiglaid")
    private List<Sigla> siglaList;

    public TipoSigla() {
    }

    public TipoSigla(Integer tiposiglaid) {
        this.tiposiglaid = tiposiglaid;
    }

    public TipoSigla(Integer tiposiglaid, String descricao) {
        this.tiposiglaid = tiposiglaid;
        this.descricao = descricao;
    }

    public Integer getTiposiglaid() {
        return tiposiglaid;
    }

    public void setTiposiglaid(Integer tiposiglaid) {
        this.tiposiglaid = tiposiglaid;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        hash += (tiposiglaid != null ? tiposiglaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoSigla)) {
            return false;
        }
        TipoSigla other = (TipoSigla) object;
        if ((this.tiposiglaid == null && other.tiposiglaid != null) || (this.tiposiglaid != null && !this.tiposiglaid.equals(other.tiposiglaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.TipoSigla[ tiposiglaid=" + tiposiglaid + " ]";
    }

}
