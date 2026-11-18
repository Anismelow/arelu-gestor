package io.github.anismelow.arelu.materiaprima.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.anismelow.arelu.materiaprima.dto.CompraRequest;
import io.github.anismelow.arelu.materiaprima.dto.CompraResponse;
import io.github.anismelow.arelu.materiaprima.service.CompraService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping("/api/compras")
    public ResponseEntity<CompraResponse> registrar(@Valid @RequestBody CompraRequest request) {
        CompraResponse creada = compraService.registrar(request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(("/api/compras/{id}"))
                .buildAndExpand(creada.id())
                .toUri();
        return ResponseEntity.created(ubicacion).body(creada);
    }
     @GetMapping("/api/compras/{id}")
    public CompraResponse obtener(@PathVariable("id") Long id) {
        return compraService.obtener(id);
    }

    /** Historial de precios de un ingrediente. */
    @GetMapping("/api/ingredientes/{ingredienteId}/compras")
    public List<CompraResponse> historial(@PathVariable("ingredienteId") Long ingredienteId) {
        return compraService.historialDe(ingredienteId);
    }

    @DeleteMapping("/api/compras/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        compraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
