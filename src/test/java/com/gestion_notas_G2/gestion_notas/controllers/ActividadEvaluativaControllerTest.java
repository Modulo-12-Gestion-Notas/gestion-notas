package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.models.ActividadEvaluativa;
import com.gestion_notas_G2.gestion_notas.services.ActividadEvaluativaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
@WebMvcTest(ActividadEvaluativaController.class)
class ActividadEvaluativaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActividadEvaluativaService actividadEvaluativaService;
    @InjectMocks
    private ActividadEvaluativaController actividadEvaluativaController;
    private List<ActividadEvaluativa> actividadEvaluativaList;

    @BeforeEach
    void setUp() {
        ActividadEvaluativa actividadEvaluativa = new ActividadEvaluativa(1L, "unidad1", 25, null, null);
        actividadEvaluativaList = Collections.singletonList(actividadEvaluativa);
    }

    @Test
    void getActividadEvaluativaListByGrupoSuccess() {
        // arrange
        when(actividadEvaluativaService.getActividadEvaluativaListByGrupo(1L)).thenReturn(actividadEvaluativaList);

        // act
        ResponseEntity<Object> response = actividadEvaluativaController.getActividadEvaluativaListByGrupo(1L);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(actividadEvaluativaList, response.getBody());
    }

    @Test
    void getActividadEvaluativaListByGrupoException() {
        // arrange
        String errorMessage = "Error al obtener las actividades evaluativas";
        when(actividadEvaluativaService.getActividadEvaluativaListByGrupo(1L)).thenThrow(new RuntimeException(errorMessage));

        // act
        ResponseEntity<Object> response = actividadEvaluativaController.getActividadEvaluativaListByGrupo(1L);

        // assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    void postActividadEvaluativaListSuccess() throws Exception {
        // Arrange
        Long codigoGrupo = 1L;
        String expectedMessage = "Las actividades evaluativas se han guardado correctamente.";

        when(actividadEvaluativaService.postActivadEvaluativaList(actividadEvaluativaList, codigoGrupo))
                .thenReturn(expectedMessage);

        // Act
        ResponseEntity<Object> response = actividadEvaluativaController.postActividadEvaluativaList(actividadEvaluativaList, codigoGrupo);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
    }

    @Test
    void postActividadEvaluativaList_InvalidArgument_Exception() throws Exception {
        // Arrange
        Long codigoGrupo = 1l;

        when(actividadEvaluativaService.postActivadEvaluativaList(actividadEvaluativaList, codigoGrupo))
                .thenThrow(new IllegalArgumentException("No se pueden guardar las actividades evaluativas porque la suma de los porcentajes supera el 100%"));

        // Act
        try {
            ResponseEntity<Object> response = actividadEvaluativaController.postActividadEvaluativaList(actividadEvaluativaList, codigoGrupo);
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Assert
            assertEquals("No se pueden guardar las actividades evaluativas porque la suma de los porcentajes supera el 100%", e.getMessage());
        }
    }

    @Test
    void postActividadEvaluativaList_InternalServerError_Exception() throws Exception {
        // Arrange
        Long codigoGrupo = 1L;

        when(actividadEvaluativaService.postActivadEvaluativaList(actividadEvaluativaList, codigoGrupo))
                .thenThrow(new Exception("Ha ocurrido un error al guardar los datos"));

        // Act
        ResponseEntity<Object> response = actividadEvaluativaController.postActividadEvaluativaList(actividadEvaluativaList, codigoGrupo);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ha ocurrido un error al guardar los datos", response.getBody());
    }

    @Test
    void handleIllegalArgumentException() {
        // Ejecutar método y verificar respuesta
        ResponseEntity<Object> response = actividadEvaluativaController.handleIllegalArgumentException(new IllegalArgumentException("Mensaje de prueba"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensaje de prueba", response.getBody());
    }

}