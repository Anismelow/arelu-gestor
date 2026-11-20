package io.github.anismelow.arelu.usuario.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
    private final 

    AdminInicial(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    @Override 
    public void run(ApplicationRunner args){
        if (usuarioRepository.count()> 0){
            return;
        }

        usuarioRepository.save(new Usuario(
            ${app.admin.nombre-de-usuario}
            

        ))
    }

}
