package br.com.starkstecnologia.control_api.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "entrega")
public class Entrega implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega")
    private Long idEntrega;

    @Column(name = "data_cadastro_entrega")
    private LocalDateTime dataCadastroEntrega;

    @Column(name = "data_assinatura_entrega")
    private LocalDateTime dataAssinaturaEntrega;

    @Column(name = "data_selecao_entrega")
    private LocalDateTime dataSelecaoEntrega;

    @Column(name = "data_finalizacao_entrega")
    private LocalDateTime dataFinalizacaoEntrega;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Usuario user;

    @ManyToOne
    @JoinColumn(name = "id_entregador")
    private Entregador entregador;

    private String latitude;

    private String longitude;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "valor_receber")
    private Double valorReceber;

    private Double troco;

    @Column(name = "cupom_orcamento")
    private String cupomOrcamento;

    @Column(name = "tipo_entrega")
    private String tipoEntrega;

    @Column(name = "forma_pagamento")
    private String formaPagamento;

    private String observacao;

    @Column(name = "status_entrega")
    private String statusEntrega;

    public Long getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Long idEntrega) {
        this.idEntrega = idEntrega;
    }

    public LocalDateTime getDataCadastroEntrega() {
        return dataCadastroEntrega;
    }

    public void setDataCadastroEntrega(LocalDateTime dataCadastroEntrega) {
        this.dataCadastroEntrega = dataCadastroEntrega;
    }

    public LocalDateTime getDataAssinaturaEntrega() {
        return dataAssinaturaEntrega;
    }

    public void setDataAssinaturaEntrega(LocalDateTime dataAssinaturaEntrega) {
        this.dataAssinaturaEntrega = dataAssinaturaEntrega;
    }

    public LocalDateTime getDataSelecaoEntrega() {
        return dataSelecaoEntrega;
    }

    public void setDataSelecaoEntrega(LocalDateTime dataSelecaoEntrega) {
        this.dataSelecaoEntrega = dataSelecaoEntrega;
    }

    public LocalDateTime getDataFinalizacaoEntrega() {
        return dataFinalizacaoEntrega;
    }

    public void setDataFinalizacaoEntrega(LocalDateTime dataFinalizacaoEntrega) {
        this.dataFinalizacaoEntrega = dataFinalizacaoEntrega;
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

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(String statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrega entrega = (Entrega) o;
        return Objects.equals(idEntrega, entrega.idEntrega) && Objects.equals(dataCadastroEntrega, entrega.dataCadastroEntrega) && Objects.equals(dataAssinaturaEntrega, entrega.dataAssinaturaEntrega) && Objects.equals(dataSelecaoEntrega, entrega.dataSelecaoEntrega) && Objects.equals(dataFinalizacaoEntrega, entrega.dataFinalizacaoEntrega) && Objects.equals(user, entrega.user) && Objects.equals(entregador, entrega.entregador) && Objects.equals(latitude, entrega.latitude) && Objects.equals(longitude, entrega.longitude) && Objects.equals(valorTotal, entrega.valorTotal) && Objects.equals(valorReceber, entrega.valorReceber) && Objects.equals(troco, entrega.troco) && Objects.equals(cupomOrcamento, entrega.cupomOrcamento) && Objects.equals(tipoEntrega, entrega.tipoEntrega) && Objects.equals(formaPagamento, entrega.formaPagamento) && Objects.equals(observacao, entrega.observacao) && Objects.equals(statusEntrega, entrega.statusEntrega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEntrega, dataCadastroEntrega, dataAssinaturaEntrega, dataSelecaoEntrega, dataFinalizacaoEntrega, user, entregador, latitude, longitude, valorTotal, valorReceber, troco, cupomOrcamento, tipoEntrega, formaPagamento, observacao, statusEntrega);
    }
}