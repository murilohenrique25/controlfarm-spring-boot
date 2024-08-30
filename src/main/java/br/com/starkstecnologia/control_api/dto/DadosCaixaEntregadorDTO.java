package br.com.starkstecnologia.control_api.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class DadosCaixaEntregadorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

	private Long key;
    private String nome;
    private String usuario;
    private String senha;
    private boolean ativo;

 

    public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(ativo, key, nome, senha, usuario);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DadosCaixaEntregadorDTO other = (DadosCaixaEntregadorDTO) obj;
		return ativo == other.ativo && Objects.equals(key, other.key) && Objects.equals(nome, other.nome)
				&& Objects.equals(senha, other.senha) && Objects.equals(usuario, other.usuario);
	}
    
    
}
