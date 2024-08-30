package br.com.starkstecnologia.control_api.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "entregador")
public class Entregador implements Serializable{

	@Serial
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entregador")
    private Long idEntregador;

    private String nome;

    private String usuario;

    private boolean ativo;

    private String senha;

    public Long getIdEntregador() {
        return idEntregador;
    }

    public void setIdEntregador(Long idEntregador) {
        this.idEntregador = idEntregador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entregador that = (Entregador) o;
        return ativo == that.ativo && Objects.equals(idEntregador, that.idEntregador) && Objects.equals(nome, that.nome) && Objects.equals(usuario, that.usuario) && Objects.equals(senha, that.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEntregador, nome, usuario, ativo, senha);
    }
}