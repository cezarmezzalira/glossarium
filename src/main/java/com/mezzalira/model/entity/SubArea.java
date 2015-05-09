/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mezzalira.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 *
 * @author Cezar Mezzalira
 */
@Entity
@Table(name = "subareas", catalog = "glossarium", schema = "")
public class SubArea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUBAREAID", nullable = false)
    private Integer subareaid;
    @Basic(optional = false)
    @Column(name = "DESCRICAO", nullable = false, length = 45)
    private String descricao;
    @OneToMany(mappedBy = "subareaid")
    private List<Sigla> siglaList;

    public SubArea() {
    }

    public SubArea(Integer subareaid) {
        this.subareaid = subareaid;
    }

    public SubArea(Integer subareaid, String descricao) {
        this.subareaid = subareaid;
        this.descricao = descricao;
    }

    public Integer getSubareaid() {
        return subareaid;
    }

    public void setSubareaid(Integer subareaid) {
        this.subareaid = subareaid;
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
        hash += (subareaid != null ? subareaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubArea)) {
            return false;
        }
        SubArea other = (SubArea) object;
        if ((this.subareaid == null && other.subareaid != null) || (this.subareaid != null && !this.subareaid.equals(other.subareaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SubArea[ subareaid=" + subareaid + " ]";
    }
    
}
