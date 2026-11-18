package io.github.anismelow.arelu.materiaprima.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.anismelow.arelu.materiaprima.dto.TiendaRequest;
import io.github.anismelow.arelu.materiaprima.dto.TiendaResponse;
import io.github.anismelow.arelu.materiaprima.service.TiendaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/tiendas")
@RequiredArgsConstructor 
public class TiendaController {

    private final TiendaService tiendaService;

    @GetMapping List<TiendaResponse> listar(){
        return tiendaService.listar();
    }

    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    public TiendaResponse crear(@Valid @RequestBody TiendaRequest request){
        return tiendaService.crear(request);
    }
}
