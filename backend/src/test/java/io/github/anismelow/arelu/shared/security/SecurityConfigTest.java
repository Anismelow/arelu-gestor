package io.github.anismelow.arelu.shared.security;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.github.anismelow.arelu.auth.controller.AuthController;
import io.github.anismelow.arelu.auth.service.TokenService;
import io.github.anismelow.arelu.materiaprima.controller.IngredienteController;
import io.github.anismelow.arelu.materiaprima.service.IngredienteService;
import io.github.anismelow.arelu.usuario.controller.UsuarioController;
import io.github.anismelow.arelu.usuario.service.UsuarioService;

@WebMvcTest({ UsuarioController.class, IngredienteController.class, AuthController.class })
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    // 🎭 Figurantes que piden los CONTROLADORES
    @MockitoBean
    private UsuarioService usuarioService;
    @MockitoBean
    private IngredienteService ingredienteService;
    @MockitoBean
    private TokenService tokenService;
    @MockitoBean
    private JwtProperties jwtProperties;

    // 🎭 Figurantes que pide TU SecurityConfig
    @MockitoBean
    private UserDetailsService userDetailsService;
    @MockitoBean
    private PasswordEncoder passwordEncoder;
    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void sinTokenDevuelve401() throws Exception {
        mockMvc.perform(get("/api/ingredientes"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().string("WWW-Authenticate", containsString("Bearer")));
    }

    @Test
    void subAdminPuedeVerIngredientes() throws Exception {
        mockMvc.perform(get("/api/ingredientes")
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_SUB_ADMIN"))))
                .andExpect(status().isOk());
    }

    @Test
    void subAdminNoPuedeVerUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios")
                .with(jwt().authorities((new SimpleGrantedAuthority("ROLE_SUB_ADMIN")))))
                .andExpect(status().isForbidden());
    }

    @Test
    void subAdminNoPuedeDesactivarUsuarios() throws Exception {
        mockMvc.perform(patch("/api/usuarios/5/desactivar")
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_SUB_ADMIN"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminPuedeVerUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios").with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))).
        andExpect(status().isOk());
    }

    @Test
    void elLoginEsPublico() throws Exception {
        mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest());
    }

}
