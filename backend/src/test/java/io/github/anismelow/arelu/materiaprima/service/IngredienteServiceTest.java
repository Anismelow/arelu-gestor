package io.github.anismelow.arelu.materiaprima.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.anismelow.arelu.materiaprima.dto.IngredienteRequest;
import io.github.anismelow.arelu.materiaprima.dto.IngredienteResponse;
import io.github.anismelow.arelu.materiaprima.model.Ingrediente;
import io.github.anismelow.arelu.materiaprima.model.TipoMedida;
import io.github.anismelow.arelu.materiaprima.repository.IngredienteRepository;
import io.github.anismelow.arelu.shared.exception.ConflictoException;

@ExtendWith(MockitoExtension.class)
class IngredienteServiceTest {

    @Mock
    private IngredienteRepository ingredienteRepository;

    @InjectMocks
    private IngredienteService ingredienteService;

    @Test
    void creaElIngredienteQuitandoLosEspacios() {
        when(ingredienteRepository.existsByNombreIgnoreCase("Harina")).thenReturn(false);
        when(ingredienteRepository.save(any(Ingrediente.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        IngredienteResponse respuesta = ingredienteService.crear(new IngredienteRequest("  Harina  ", TipoMedida.PESO));

        assertThat(respuesta.nombre()).isEqualTo("Harina");
        assertThat(respuesta.tipoMedida()).isEqualTo(TipoMedida.PESO);
    }

    @Test
    void noCreaUnIngredienteRepetido() {
        when(ingredienteRepository.existsByNombreIgnoreCase("Harina")).thenReturn(true);

        assertThatThrownBy(() -> ingredienteService.crear(new IngredienteRequest("Harina", TipoMedida.PESO)))
                .isInstanceOf(ConflictoException.class);

        verify(ingredienteRepository, never()).save(any());
    }
}