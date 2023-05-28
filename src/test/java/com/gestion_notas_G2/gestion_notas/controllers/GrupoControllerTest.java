package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.models.Grupo;
import com.gestion_notas_G2.gestion_notas.services.GrupoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(MockitoExtension.class)
@WebMvcTest(GrupoController.class)
class GrupoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GrupoService grupoService;
    @InjectMocks
    private GrupoController grupoController;

    private List<Grupo> grupoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Grupo grupo = new Grupo(1l, "LM 20-22", "Ude@", "25", null, null, null, null, null);
        grupoList = Collections.singletonList(grupo);
    }

    @Test
    void getGruposByProfesor() throws Exception{
        // Arrange
        when(grupoService.getGruposByProfesor(1L)).thenReturn(grupoList);

        // Act
        ResponseEntity<Object> response = grupoController.getGruposByProfesor(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(grupoList, response.getBody());
        verify(grupoService, Mockito.times(1)).getGruposByProfesor(1L);
        verifyNoMoreInteractions(grupoService);
    }

    @Test
    void getGruposByProfesorException() throws Exception {
        // Configurar el servicio mock para que arroje una excepción
        when(grupoService.getGruposByProfesor(1L)).thenThrow(new RuntimeException("Error forzado para activar catch"));

        // Ejecutar el método del controlador y verificar la respuesta
        ResponseEntity<Object> response = grupoController.getGruposByProfesor(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Error forzado para activar catch"));
    }

    @Test
    void handleIllegalArgumentException() {
        // Ejecutar método y verificar respuesta
        ResponseEntity<Object> response = grupoController.handleIllegalArgumentException(new IllegalArgumentException("Mensaje de prueba"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensaje de prueba", response.getBody());
    }
}