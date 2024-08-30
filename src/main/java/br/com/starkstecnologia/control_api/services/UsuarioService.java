package br.com.starkstecnologia.control_api.services;

import br.com.starkstecnologia.control_api.entity.Usuario;
import br.com.starkstecnologia.control_api.exception.EntityNotFoundException;
import br.com.starkstecnologia.control_api.exception.PasswordInvalidException;
import br.com.starkstecnologia.control_api.exception.UsernameUniqueViolationException;
import br.com.starkstecnologia.control_api.repository.UsuarioRepository;

import br.com.starkstecnologia.control_api.types.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id)));
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha){
        if(!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }
        Usuario user = buscarPorId(id);
        if(!passwordEncoder.matches(senhaAtual, user.getPassword())){
            throw new PasswordInvalidException("Sua senha não confere.");
        }
        user.setPassword(passwordEncoder.encode(novaSenha));
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(String.format("Usuário com username %s não encontrado", username)));
    }

    @Transactional(readOnly = true)
    public Role buscarRolePorUsername(String username){
        return usuarioRepository.findRoleByUsername(username);
    }
}
