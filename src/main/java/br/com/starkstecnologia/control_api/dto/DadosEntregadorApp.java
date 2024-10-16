package br.com.starkstecnologia.control_api.dto;

public class DadosEntregadorApp {

    private Long quantidadeEntrega;

    private String mediaTotal;

    public String getMediaTotal() {
        return mediaTotal;
    }

    public void setMediaTotal(String mediaTotal) {
        this.mediaTotal = mediaTotal;
    }

    public Long getQuantidadeEntrega() {
        return quantidadeEntrega;
    }

    public void setQuantidadeEntrega(Long quantidadeEntrega) {
        this.quantidadeEntrega = quantidadeEntrega;
    }
}
