package io.github.anismelow.arelu.materiaprima.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.anismelow.arelu.materiaprima.dto.IngredienteRequest;
import io.github.anismelow.arelu.materiaprima.dto.IngredienteResponse;
import io.github.anismelow.arelu.materiaprima.model.Ingrediente;
import io.github.anismelow.arelu.materiaprima.repository.IngredienteRepository;
import io.github.anismelow.arelu.shared.exception.ConflictoException;
import io.github.anismelow.arelu.shared.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    public List<IngredienteResponse> listar() {
        return ingredienteRepository.findAllByOrderByNombreAsc().stream()
                .map(IngredienteResponse::from)
                .toList();
    }

    public IngredienteResponse obtener(Long id) {
        return IngredienteResponse.from(buscarPorId(id));
    }

    @Transactional
    public IngredienteResponse crear(IngredienteRequest request) {
        String nombre = request.nombre().trim();
        if (ingredienteRepository.existsByNombreIgnoreCase(nombre)) {
            throw new ConflictoException("Ya existe un ingrediente llamado " + nombre);
        }
        Ingrediente guardado = ingredienteRepository.save(new Ingrediente(nombre, request.tipoMedida()));
        return IngredienteResponse.from(guardado);
    }

    /**
     * Lo usa CompraService (mismo paquete): una sola regla de "no existe" para todo
     * el módulo.
     */
    Ingrediente buscarPorId(Long id) {
        return ingredienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el ingrediente con id " + id));
    }

}
