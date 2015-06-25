/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mezzalira.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
/**
 *
 * @author Cezar Mezzalira
 */
@Entity
@Table(name = "usuarios", catalog = "glossarium", schema = "")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USUARIOSID", nullable = false)
    private Integer usuariosid;
    
    @Basic(optional = false)
    @Column(name = "NOME", nullable = false, length = 200)
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "MATRICULA", nullable = false)
    private int matricula;
    
    @Basic(optional = false)
    @Column(name = "SENHA", nullable = false, length = 200)
    private String senha;
    
// @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @Column(name = "EMAIL", nullable = false, length = 200)
    private String email;

    @Basic(optional = false)
    @Column(name = "TIPO", nullable = false)
    private short tipo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioidaprov")
    private List<Sigla> siglaDetalheList;
    
    public Usuario() {
    }

    public Usuario(Integer matricula, String senha) {
        this.matricula = matricula;
        this.senha = senha;
    }

    public Usuario(Integer usuariosid) {
        this.usuariosid = usuariosid;
    }

    public Usuario(Integer usuariosid, String nome, int matricula, String senha, String email, short tipo) {
        this.usuariosid = usuariosid;
        this.nome = nome;
        this.matricula = matricula;
        this.senha = senha;
        this.email = email;
        this.tipo = tipo;
    }

    public Integer getUsuariosid() {
        return usuariosid;
    }

    public void setUsuariosid(Integer usuariosid) {
        this.usuariosid = usuariosid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public short getTipo() {
        return tipo;
    }

    public void setTipo(short tipo) {
        this.tipo = tipo;
    }

    public List<Sigla> getSiglaDetalheList() {
        return siglaDetalheList;
    }

    public void setSiglaDetalheList(List<Sigla> siglaDetalheList) {
        this.siglaDetalheList = siglaDetalheList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuariosid != null ? usuariosid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuariosid == null && other.usuariosid != null) || (this.usuariosid != null && !this.usuariosid.equals(other.usuariosid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Usuario[ usuariosid=" + usuariosid + " ]";
    }

}
