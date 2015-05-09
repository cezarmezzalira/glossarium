package com.mezzalira.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * @author Cezar Mezzalira
 */
@Entity
@Table(name = "areas", catalog = "glossarium", schema = "")
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AREASID", nullable = false)
    private Integer areasid;
    @Basic(optional = false)
    @Column(name = "DESCRICAO", nullable = false, length = 100)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "areaid")
    private List<Sigla> siglaList;

    public Area() {
    }

    public Area(Integer areasid) {
        this.areasid = areasid;
    }

    public Area(Integer areasid, String descricao) {
        this.areasid = areasid;
        this.descricao = descricao;
    }

    public Integer getAreasid() {
        return areasid;
    }

    public void setAreasid(Integer areasid) {
        this.areasid = areasid;
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
        hash += (areasid != null ? areasid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.areasid == null && other.areasid != null) || (this.areasid != null && !this.areasid.equals(other.areasid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Area[ areasid=" + areasid + " ]";
    }

}
