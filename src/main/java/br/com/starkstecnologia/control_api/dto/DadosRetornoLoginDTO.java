package br.com.starkstecnologia.control_api.dto;
import java.io.Serializable;

public class DadosRetornoLoginDTO implements Serializable {

    private Long idUser;

    private String nome;

    private String token;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
