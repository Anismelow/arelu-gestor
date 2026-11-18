package io.github.anismelow.arelu.materiaprima.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.anismelow.arelu.materiaprima.dto.CompraRequest;
import io.github.anismelow.arelu.materiaprima.dto.CompraResponse;
import io.github.anismelow.arelu.materiaprima.model.Compra;
import io.github.anismelow.arelu.materiaprima.model.Ingrediente;
import io.github.anismelow.arelu.materiaprima.model.Tienda;
import io.github.anismelow.arelu.materiaprima.repository.CompraRepository;
import io.github.anismelow.arelu.shared.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompraService {

    private final CompraRepository compraRepository;
    private final IngredienteService ingredienteService;
    private final TiendaService tiendaService;

    @Transactional
    public CompraResponse registrar(CompraRequest request) {
        Ingrediente ingrediente = ingredienteService.buscarPorId(request.ingredienteId());
        Tienda tienda = request.tiendaId() == null ? null : tiendaService.buscarPorId(request.tiendaId());

        // El constructor de Compra comprueba que la unidad sirve para ese ingrediente
        Compra compra = new Compra(ingrediente, tienda, request.fecha(),
                request.cantidad(), request.unidad(), request.precioTotal());

        return CompraResponse.from(compraRepository.save(compra));
    }

    public CompraResponse obtener(Long id) {
        return compraRepository.findById(id)
                .map(CompraResponse::from)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la compra con id " + id));
    }

    public List<CompraResponse> historialDe(Long ingredienteId) {
        ingredienteService.buscarPorId(ingredienteId);   // 404 si el ingrediente no existe
        return compraRepository.findByIngredienteIdOrderByFechaDescIdDesc(ingredienteId).stream()
                .map(CompraResponse::from)
                .toList();
    }

    @Transactional
    public void eliminar(Long id) {
        if (!compraRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe la compra con id " + id);
        }
        compraRepository.deleteById(id);
    }
}