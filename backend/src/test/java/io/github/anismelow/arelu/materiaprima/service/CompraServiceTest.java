package io.github.anismelow.arelu.materiaprima.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.anismelow.arelu.materiaprima.dto.CompraRequest;
import io.github.anismelow.arelu.materiaprima.dto.CompraResponse;
import io.github.anismelow.arelu.materiaprima.model.Compra;
import io.github.anismelow.arelu.materiaprima.model.Ingrediente;
import io.github.anismelow.arelu.materiaprima.model.TipoMedida;
import io.github.anismelow.arelu.materiaprima.model.UnidadMedida;
import io.github.anismelow.arelu.materiaprima.repository.CompraRepository;
import io.github.anismelow.arelu.shared.exception.RecursoNoEncontradoException;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private IngredienteService ingredienteService;

    @Mock
    private TiendaService tiendaService;

    @InjectMocks
    private CompraService compraService;

    private final CompraRequest sacoDeHarina = new CompraRequest(1L, null, LocalDate.of(2026, 11, 17),
            new BigDecimal("5"), UnidadMedida.KILOGRAMO, new BigDecimal("4.80"));

    @Test
    void registraUnaCompraSinTienda() {
        when(ingredienteService.buscarPorId(1L)).thenReturn(new Ingrediente("Harina", TipoMedida.PESO));
        when(compraRepository.save(any(Compra.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        CompraResponse respuesta = compraService.registrar(sacoDeHarina);

        assertThat(respuesta.ingrediente()).isEqualTo("Harina");
        assertThat(respuesta.tienda()).isNull();
        assertThat(respuesta.precioPorUnidadBase()).isEqualByComparingTo("0.00096");
        verifyNoInteractions(tiendaService);
    }

    @Test
    void noRegistraLaCompraSiElIngredienteNoExiste() {
        when(ingredienteService.buscarPorId(1L))
                .thenThrow(new RecursoNoEncontradoException("No existe el ingrediente con id 1"));

        assertThatThrownBy(() -> compraService.registrar(sacoDeHarina))
                .isInstanceOf(RecursoNoEncontradoException.class);

        verify(compraRepository, never()).save(any());
    }
}