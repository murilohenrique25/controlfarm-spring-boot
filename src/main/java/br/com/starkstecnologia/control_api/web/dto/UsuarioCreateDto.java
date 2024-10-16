package br.com.starkstecnologia.control_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioCreateDto implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 4, max = 12)
    private String password;

    private String nome;

    private boolean ativo;

    private String turno;

    private String telefone;

    private String cpf;

    public UsuarioCreateDto(){}
    public UsuarioCreateDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioCreateDto that = (UsuarioCreateDto) o;
        return ativo == that.ativo && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(nome, that.nome) && Objects.equals(turno, that.turno) && Objects.equals(telefone, that.telefone) && Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, nome, ativo, turno, telefone, cpf);
    }
}
