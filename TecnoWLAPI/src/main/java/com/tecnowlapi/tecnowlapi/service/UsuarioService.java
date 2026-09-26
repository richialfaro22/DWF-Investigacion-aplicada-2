package com.tecnowlapi.tecnowlapi.service;

import com.tecnowlapi.tecnowlapi.exception.RecursoDuplicadoException;
import com.tecnowlapi.tecnowlapi.exception.RecursoNoEncontradoException;
import com.tecnowlapi.tecnowlapi.model.Usuario;
import com.tecnowlapi.tecnowlapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Usuario no encontrado con ID: " + id
                        )
                );
    }

    public Usuario guardar(Usuario usuario) {

        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RecursoDuplicadoException(
                    "Ya existe un usuario registrado con el correo: "
                            + usuario.getCorreo()
            );
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario datosActualizados) {

        Usuario usuario = obtenerPorId(id);

        usuario.setNombre(datosActualizados.getNombre());
        usuario.setCorreo(datosActualizados.getCorreo());
        usuario.setRol(datosActualizados.getRol());
        usuario.setActivo(datosActualizados.getActivo());

        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuarioRepository.delete(usuario);
    }
}