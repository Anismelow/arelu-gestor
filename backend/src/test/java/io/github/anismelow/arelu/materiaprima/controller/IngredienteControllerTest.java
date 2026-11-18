package io.github.anismelow.arelu.materiaprima.controller;

import io.github.anismelow.arelu.materiaprima.dto.IngredienteResponse;
import io.github.anismelow.arelu.materiaprima.model.TipoMedida;
import io.github.anismelow.arelu.materiaprima.service.IngredienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IngredienteController.class)
class IngredienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredienteService ingredienteService;

    @Test
    void creaUnIngredienteYDevuelve201ConLocation() throws Exception {
        when(ingredienteService.crear(any()))
                .thenReturn(new IngredienteResponse(1L, "Harina", TipoMedida.PESO));

        mockMvc.perform(post("/api/ingredientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre": "Harina", "tipoMedida": "PESO"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/ingredientes/1")))
                .andExpect(jsonPath("$.nombre").value("Harina"));
    }

    @Test
    void devuelve400SiFaltaElNombre() throws Exception {
        mockMvc.perform(post("/api/ingredientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"tipoMedida": "PESO"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.nombre").value("El nombre es obligatorio"));
    }

    @Test
    void devuelve400SiElTipoDeMedidaNoExiste() throws Exception {
        mockMvc.perform(post("/api/ingredientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre": "Harina", "tipoMedida": "kg"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}