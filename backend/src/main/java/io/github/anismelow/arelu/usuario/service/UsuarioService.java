package io.github.anismelow.arelu.usuario.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.anismelow.arelu.shared.exception.ConflictoException;
import io.github.anismelow.arelu.usuario.dto.CrearUsuarioRequest;
import io.github.anismelow.arelu.usuario.dto.UsuarioResponse;
import io.github.anismelow.arelu.usuario.model.Rol;
import io.github.anismelow.arelu.usuario.model.Usuario;
import io.github.anismelow.arelu.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponse crear(CrearUsuarioRequest request) {
        String nombre = request.nombreDeUsuario().trim();

        if (usuarioRepository.buscarPorNombre(nombre).isPresent()) {
            throw new ConflictoException("Ya existe el nombre de usuario" + nombre);
        }

        Usuario guardado = usuarioRepository.save(new Usuario(
                nombre,
                passwordEncoder.encode(request.contrasena()),
                request.rol()));

        return UsuarioResponse.from(guardado);

    }

}
