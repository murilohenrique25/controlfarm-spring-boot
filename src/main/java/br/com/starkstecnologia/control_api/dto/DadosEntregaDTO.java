package br.com.starkstecnologia.control_api.dto;



import java.io.Serializable;
import java.time.LocalDateTime;

public class DadosEntregaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
	private Long idEntrega;
    private String dataCadastroEntrega;
    private String dataAssinaturaEntrega;
    private String dataSelecaoEntrega;
    private String dataFinalizacaoEntrega;
    private Long idUser;
    private Long idEntregador;
    private String latitude;
    private String longitude;
    private Double valorTotal;
    private Double valorReceber;
    private Double troco;
    private String cupomOrcamento;
    private String tipoEntrega;
    private String formaPagamento;
    private String observacao;

    public Long getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Long idEntrega) {
        this.idEntrega = idEntrega;
    }

    public String getDataCadastroEntrega() {
        return dataCadastroEntrega;
    }

    public void setDataCadastroEntrega(String dataCadastroEntrega) {
        this.dataCadastroEntrega = dataCadastroEntrega;
    }

    public String getDataAssinaturaEntrega() {
        return dataAssinaturaEntrega;
    }

    public void setDataAssinaturaEntrega(String dataAssinaturaEntrega) {
        this.dataAssinaturaEntrega = dataAssinaturaEntrega;
    }

    public String getDataSelecaoEntrega() {
        return dataSelecaoEntrega;
    }

    public void setDataSelecaoEntrega(String dataSelecaoEntrega) {
        this.dataSelecaoEntrega = dataSelecaoEntrega;
    }

    public String getDataFinalizacaoEntrega() {
        return dataFinalizacaoEntrega;
    }

    public void setDataFinalizacaoEntrega(String dataFinalizacaoEntrega) {
        this.dataFinalizacaoEntrega = dataFinalizacaoEntrega;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdEntregador() {
        return idEntregador;
    }

    public void setIdEntregador(Long idEntregador) {
        this.idEntregador = idEntregador;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorReceber() {
        return valorReceber;
    }

    public void setValorReceber(Double valorReceber) {
        this.valorReceber = valorReceber;
    }

    public Double getTroco() {
        return troco;
    }

    public void setTroco(Double troco) {
        this.troco = troco;
    }

    public String getCupomOrcamento() {
        return cupomOrcamento;
    }

    public void setCupomOrcamento(String cupomOrcamento) {
        this.cupomOrcamento = cupomOrcamento;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
