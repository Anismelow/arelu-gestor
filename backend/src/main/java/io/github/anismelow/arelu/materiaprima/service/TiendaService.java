package io.github.anismelow.arelu.materiaprima.service;


import io.github.anismelow.arelu.materiaprima.dto.TiendaRequest;
import io.github.anismelow.arelu.materiaprima.dto.TiendaResponse;
import io.github.anismelow.arelu.materiaprima.model.Tienda;
import io.github.anismelow.arelu.materiaprima.repository.TiendaRepository;
import io.github.anismelow.arelu.shared.exception.ConflictoException;
import io.github.anismelow.arelu.shared.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TiendaService {

    private final TiendaRepository tiendaRepository;

    public List<TiendaResponse>listar(){
        return tiendaRepository.findAllByOrderByNombreAsc().stream()
                    .map(TiendaResponse::from)
                    .toList();
    }

        @Transactional
    public TiendaResponse crear(TiendaRequest request) {
        String nombre = request.nombre().trim();
        if (tiendaRepository.existsByNombreIgnoreCase(nombre)) {
            throw new ConflictoException("Ya existe una tienda llamada " + nombre);
        }
        return TiendaResponse.from(tiendaRepository.save(new Tienda(nombre)));
    }

    Tienda buscarPorId(Long id) {
        return tiendaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la tienda con id " + id));
    }


}
