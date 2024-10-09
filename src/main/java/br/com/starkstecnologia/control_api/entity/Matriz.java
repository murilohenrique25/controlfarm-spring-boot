package br.com.starkstecnologia.control_api.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(schema = "sistema",name = "matriz")
public class Matriz implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matriz_seq")
    @SequenceGenerator(name = "matriz_seq", sequenceName = "sistema.matriz_seq", allocationSize = 1)
    @Column(name = "id_matriz")
    private Long idMatriz;

    private String nome;

    private String logradouro;

    private String telefone;

    private String cnpj;

    private String email;

    @Column(name = "id_matriz_principal")
    private Long idMatrizPrincipal;

    public Long getIdMatriz() {
        return idMatriz;
    }


    public void setIdMatriz(Long idMatriz) {
        this.idMatriz = idMatriz;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdMatrizPrincipal() {
        return idMatrizPrincipal;
    }

    public void setIdMatrizPrincipal(Long idMatrizPrincipal) {
        this.idMatrizPrincipal = idMatrizPrincipal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matriz matriz = (Matriz) o;
        return Objects.equals(idMatriz, matriz.idMatriz) && Objects.equals(nome, matriz.nome) && Objects.equals(logradouro, matriz.logradouro) && Objects.equals(telefone, matriz.telefone) && Objects.equals(cnpj, matriz.cnpj) && Objects.equals(email, matriz.email) && Objects.equals(idMatrizPrincipal, matriz.idMatrizPrincipal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMatriz, nome, logradouro, telefone, cnpj, email, idMatrizPrincipal);
    }
}
