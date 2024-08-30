package br.com.starkstecnologia.control_api.web.dto;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioResponseDto implements Serializable {

    private Long id;
    private String username;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioResponseDto that = (UsuarioResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role);
    }
}
