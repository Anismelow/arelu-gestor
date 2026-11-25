package io.github.anismelow.arelu.usuario.service;

import io.github.anismelow.arelu.shared.exception.RecursoNoEncontradoException;
import io.github.anismelow.arelu.shared.exception.ReglaNegocioException;

import java.util.List;

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
            throw new ConflictoException("Ya existe el nombre de usuario: " + nombre);
        }

        Usuario guardado = usuarioRepository.save(new Usuario(
                nombre,
                passwordEncoder.encode(request.contrasena()),
                request.rol()));

        return UsuarioResponse.from(guardado);

    }

    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAllByOrderByNombreDeUsuarioAsc().stream().map(UsuarioResponse::from).toList();
    }

    @Transactional
    public void desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException ("No existe el usuario con id " + id));

        boolean esElUltimoAdminActivo = usuario.getRol() == Rol.ADMIN
                && usuario.isActivo()
                && usuarioRepository.countByRolAndActivoTrue(Rol.ADMIN) == 1;

        if (esElUltimoAdminActivo) {
            throw new ReglaNegocioException("No se puede desactivar al último administrador activo");
        }

        usuario.desactivar();
    }

}
