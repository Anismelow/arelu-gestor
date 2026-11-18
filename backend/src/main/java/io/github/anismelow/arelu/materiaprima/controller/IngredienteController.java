package io.github.anismelow.arelu.materiaprima.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.anismelow.arelu.materiaprima.dto.IngredienteRequest;
import io.github.anismelow.arelu.materiaprima.dto.IngredienteResponse;
import io.github.anismelow.arelu.materiaprima.service.IngredienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/ingredientes")
@RequiredArgsConstructor 
public class IngredienteController {
    private final IngredienteService ingredienteService;

    @GetMapping
    public List<IngredienteResponse> listar(){
        return ingredienteService.listar();
    }

    @GetMapping("/{id}")
    public IngredienteResponse obtener(@PathVariable("id") Long id){
        return ingredienteService.obtener(id);
    }

    @PostMapping("path")
    public ResponseEntity<IngredienteResponse> crear(@Valid @RequestBody IngredienteRequest request){
        IngredienteResponse creado = ingredienteService.crear(request);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();
        return ResponseEntity.created(ubicacion).body(creado);
    }
    











}
