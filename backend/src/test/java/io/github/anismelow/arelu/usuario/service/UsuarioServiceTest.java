package io.github.anismelow.arelu.usuario.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.anismelow.arelu.shared.exception.ConflictoException;
import io.github.anismelow.arelu.shared.exception.RecursoNoEncontradoException;
import io.github.anismelow.arelu.shared.exception.ReglaNegocioException;
import io.github.anismelow.arelu.usuario.dto.CrearUsuarioRequest;
import io.github.anismelow.arelu.usuario.model.Rol;
import io.github.anismelow.arelu.usuario.model.Usuario;
import io.github.anismelow.arelu.usuario.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository; // repositorio falso

    @Mock
    private PasswordEncoder passwordEncoder; // encoder falso

    @InjectMocks
    private UsuarioService usuarioService; // el de verdad, con los dos mocks dentro

    @Test
    void noDesactivaAlUltimoAdminActivo() {
        Usuario admin = new Usuario("admin", "hash-falso", Rol.ADMIN);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(usuarioRepository.countByRolAndActivoTrue(Rol.ADMIN)).thenReturn(1L);

        assertThatThrownBy(() -> usuarioService.desactivar(1L)).isInstanceOf(ReglaNegocioException.class);

        assertThat(admin.isActivo()).isTrue();

    }

    @Test
    void desactivaAUnSubAdmin() {
        Usuario mama = new Usuario("mama", "hash-falso", Rol.SUB_ADMIN);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(mama));

        usuarioService.desactivar(2L);

        assertThat(mama.isActivo()).isFalse();
    }

    @Test
    void lanzaNoEncontradoSiElUsuarioNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.desactivar(99L))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    void noCreaUnUsuarioConNombreRepetido() {
        Usuario existente = new Usuario("mama", "hash-falso", Rol.SUB_ADMIN);
        when(usuarioRepository.buscarPorNombre("mama")).thenReturn(Optional.of(existente));

        CrearUsuarioRequest request = new CrearUsuarioRequest("mama", "Bizcocho2026", Rol.SUB_ADMIN);

        assertThatThrownBy(() -> usuarioService.crear(request))
                .isInstanceOf(ConflictoException.class);

        verify(usuarioRepository, never()).save(any());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void alCrearGuardaElHashYNoLaContrasena() {
        when(usuarioRepository.buscarPorNombre("mama")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("Bizcocho2026")).thenReturn("hash-falso");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        usuarioService.crear(new CrearUsuarioRequest("  mama  ", "Bizcocho2026", Rol.SUB_ADMIN));

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        Usuario guardado = captor.getValue();

        assertThat(guardado.getContrasenaHash()).isEqualTo("hash-falso");
        assertThat(guardado.getNombreDeUsuario()).isEqualTo("mama");
    }

}
