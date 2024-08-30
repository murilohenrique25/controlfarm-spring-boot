package br.com.starkstecnologia.control_api.dto;

public class DadosUsuarioLogadoDTO {

    private String usuario;
    private int quantidadeEntregas;


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getQuantidadeEntregas() {
        return quantidadeEntregas;
    }

    public void setQuantidadeEntregas(int quantidadeEntregas) {
        this.quantidadeEntregas = quantidadeEntregas;
    }
}
