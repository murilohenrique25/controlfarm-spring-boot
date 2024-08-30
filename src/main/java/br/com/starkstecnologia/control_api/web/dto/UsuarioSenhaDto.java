package br.com.starkstecnologia.control_api.web.dto;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioSenhaDto implements Serializable {

    private String senhaAtual;
    private String novaSenha;
    private String confirmacaoSenha;

    public UsuarioSenhaDto(String senhaAtual, String novaSenha, String confirmacaoSenha) {
        this.senhaAtual = senhaAtual;
        this.novaSenha = novaSenha;
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioSenhaDto that = (UsuarioSenhaDto) o;
        return Objects.equals(senhaAtual, that.senhaAtual) && Objects.equals(novaSenha, that.novaSenha) && Objects.equals(confirmacaoSenha, that.confirmacaoSenha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senhaAtual, novaSenha, confirmacaoSenha);
    }
}
