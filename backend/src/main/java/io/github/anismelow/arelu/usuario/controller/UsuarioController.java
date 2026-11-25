package io.github.anismelow.arelu.usuario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.anismelow.arelu.usuario.dto.CrearUsuarioRequest;
import io.github.anismelow.arelu.usuario.dto.UsuarioResponse;
import io.github.anismelow.arelu.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse crear(@Valid @RequestBody CrearUsuarioRequest request) {
        return usuarioService.crear(request);

    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable("id") Long id) {
        usuarioService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

}
