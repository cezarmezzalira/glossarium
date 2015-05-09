package com.mezzalira.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Cezar Mezzalira
 * Date: 10/12/13
 * Time: 23:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "siglas", catalog = "glossarium", schema = "")
public class Sigla implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SIGLAID", nullable = false)
    private Integer siglaid;
    @Basic(optional = false)
    @Column(name = "SIGLA", nullable = false, length = 20)
    private String sigla;
    @Basic(optional = false)
    @Column(name = "SIGNIFICADO", nullable = false, length = 200)
    private String significado;
    @Basic(optional = false)
    @Column(name = "DTCADASTRO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtcadastro;
    @Basic(optional = false)
    @Column(name = "DTAPROVADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtaprovada;
    @JoinColumn(name = "TIPOSIGLAID", referencedColumnName = "TIPOSIGLAID", nullable = false)
    @ManyToOne(optional = false)
    private TipoSigla tiposiglaid;
    @JoinColumn(name = "SUBAREAID", referencedColumnName = "SUBAREAID")
    @ManyToOne
    private SubArea subareaid;
    @JoinColumn(name = "AREAID", referencedColumnName = "AREASID", nullable = false)
    @ManyToOne(optional = false)
    private Area areaid;
    @JoinColumn(name = "LINGUAID", referencedColumnName = "LINGUAID", nullable = false)
    @ManyToOne(optional = false)
    private Linguagem linguaid;
    @JoinColumn(name = "USUARIOIDAPROV", referencedColumnName = "USUARIOSID")
    @ManyToOne(optional = false)
    private Usuario usuarioidaprov;

    public Sigla() {
    }

    public Integer getSiglaid() {
        return siglaid;
    }

    public void setSiglaid(Integer siglaid) {
        this.siglaid = siglaid;
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

    public Date getDtcadastro() {
        return dtcadastro;
    }

    public void setDtcadastro(Date dtcadastro) {
        this.dtcadastro = dtcadastro;
    }

    public Date getDtaprovada() {
        return dtaprovada;
    }

    public void setDtaprovada(Date dtaprovada) {
        this.dtaprovada = dtaprovada;
    }

    public TipoSigla getTiposiglaid() {
        return tiposiglaid;
    }

    public void setTiposiglaid(TipoSigla tiposiglaid) {
        this.tiposiglaid = tiposiglaid;
    }

    public SubArea getSubareaid() {
        return subareaid;
    }

    public void setSubareaid(SubArea subareaid) {
        this.subareaid = subareaid;
    }

    public Area getAreaid() {
        return areaid;
    }

    public void setAreaid(Area areaid) {
        this.areaid = areaid;
    }

    public Linguagem getLinguaid() {
        return linguaid;
    }

    public void setLinguaid(Linguagem linguaid) {
        this.linguaid = linguaid;
    }

    public Usuario getUsuarioidaprov() {
        return usuarioidaprov;
    }

    public void setUsuarioidaprov(Usuario usuarioidaprov) {
        this.usuarioidaprov = usuarioidaprov;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (siglaid != null ? siglaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sigla)) {
            return false;
        }
        Sigla other = (Sigla) object;
        if ((this.siglaid == null && other.siglaid != null) || (this.siglaid != null && !this.siglaid.equals(other.siglaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.SiglaBase[ siglaid=" + siglaid + " ]";
    }
}
