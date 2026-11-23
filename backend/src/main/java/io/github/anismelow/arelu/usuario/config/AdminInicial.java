package io.github.anismelow.arelu.usuario.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.github.anismelow.arelu.usuario.model.Rol;
import io.github.anismelow.arelu.usuario.model.Usuario;
import io.github.anismelow.arelu.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInicial implements ApplicationRunner {

    private final AdminProperties adminProperties;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (usuarioRepository.count() > 0) {
            return;
        }
        Usuario admin = new Usuario(
                adminProperties.nombreDeUsuario(),
                passwordEncoder.encode(adminProperties.contrasena()),
                Rol.ADMIN);
        usuarioRepository.save(admin);
        log.info("Creado el administrador inicial '{}'", admin.getNombreDeUsuario());
    }

}
