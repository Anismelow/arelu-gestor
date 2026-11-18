package io.github.anismelow.arelu.materiaprima.controller;

import io.github.anismelow.arelu.materiaprima.dto.TiendaResponse;
import io.github.anismelow.arelu.materiaprima.service.TiendaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TiendaController.class)
class TiendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TiendaService tiendaService;

    @Test
    void leeElNombreDelBodyYCreaLaTienda() throws Exception {
        when(tiendaService.crear(any())).thenReturn(new TiendaResponse(1L, "Makro"));

        mockMvc.perform(post("/api/tiendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre": "Makro"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Makro"));
    }
}