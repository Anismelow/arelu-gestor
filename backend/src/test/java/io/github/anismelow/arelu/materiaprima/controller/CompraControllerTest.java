package io.github.anismelow.arelu.materiaprima.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.github.anismelow.arelu.materiaprima.dto.CompraResponse;
import io.github.anismelow.arelu.materiaprima.model.UnidadMedida;
import io.github.anismelow.arelu.materiaprima.service.CompraService;
import io.github.anismelow.arelu.shared.exception.RecursoNoEncontradoException;

@WebMvcTest(CompraController.class)
@AutoConfigureMockMvc(addFilters = false)
class CompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CompraService compraService;

    @Test
    void registraUnaCompraYDevuelve201() throws Exception {
        when(compraService.registrar(any())).thenReturn(new CompraResponse(
                1L, 1L, "Harina", null, null, LocalDate.of(2026, 11, 17),
                new BigDecimal("5"), UnidadMedida.KILOGRAMO, new BigDecimal("4.80"), new BigDecimal("0.000960")));

        mockMvc.perform(post("/api/compras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"ingredienteId": 1, "fecha": "2026-11-17", "cantidad": 5,
                                 "unidad": "KILOGRAMO", "precioTotal": 4.80}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ingrediente").value("Harina"));
    }

    @Test
    void devuelve404SiElIngredienteDelHistorialNoExiste() throws Exception {
        when(compraService.historialDe(99L))
                .thenThrow(new RecursoNoEncontradoException("No existe el ingrediente con id 99"));

        mockMvc.perform(get("/api/ingredientes/99/compras"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("No existe el ingrediente con id 99"));
    }
}