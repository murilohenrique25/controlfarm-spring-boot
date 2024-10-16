package br.com.starkstecnologia.control_api.types;

public enum StatusEntregaEnum {

    ABERTA("Aberta"),
    EM_ROTA("Em rota"),
    CANCELADA("Cancelada"),
    FINALIZADA("Finalizada"),
    FINALIZADA_MANUAL("Finalizada Manualmente"),
    FINALIZADA_ASSINADA("Finalizada e Assinada");

    private final String descricao;

    StatusEntregaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
