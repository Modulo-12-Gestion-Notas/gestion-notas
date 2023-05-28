package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.models.Profesor;
import com.gestion_notas_G2.gestion_notas.services.ProfesorService;
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
@WebMvcTest(ProfesorController.class)
class ProfesorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfesorService profesorService;
    @InjectMocks
    private ProfesorController profesorController;

    private Profesor profesor;
    private List<Profesor> profesorList;

    @BeforeEach
    void setUp() {
        profesor = new Profesor(1L, "Pedro", "Paternina", "mail@profesor.edu.co",
                "856932145", "mail@institucional.com", "3259684751", null, null);
        profesorList = Collections.singletonList(profesor);
    }

    @Test
    void getProfesores() throws Exception {
        // Arrange
        when(profesorService.getProfesores()).thenReturn(profesorList);

        // Act
        ResponseEntity<Object> response = profesorController.getProfesores();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profesorList, response.getBody());
        verify(profesorService, times(1)).getProfesores();
        verifyNoMoreInteractions(profesorService);
    }

    @Test
    void getProfesoresException() {
        // Configurar el servicio mock para que arroje una excepción
        when(profesorService.getProfesores()).thenThrow(new RuntimeException("Error forzado para activar catch"));

        // Ejecutar el método del controlador y verificar la respuesta
        ResponseEntity<Object> response = profesorController.getProfesores();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Error forzado para activar catch"));
    }

    @Test
    void postProfesorSuccess() throws Exception {
        String expected = "Profesor creado exitosamente";
        when(profesorService.postProfesor(profesor)).thenReturn(expected);

        ResponseEntity<Object> response = profesorController.postProfesor(profesor);

        assertEquals(expected, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void postProfesorInvalidArgument() throws Exception {
        String errorMessage = "Error al crear el profesor";
        when(profesorService.postProfesor(profesor)).thenThrow(new IllegalArgumentException(errorMessage));

        assertThrows(IllegalArgumentException.class, () -> profesorController.postProfesor(profesor));
    }

    @Test
    void postProfesorInternalServerError() throws Exception {
        String errorMessage = "Error interno del servidor";
        when(profesorService.postProfesor(profesor)).thenThrow(new Exception(errorMessage));

        ResponseEntity<Object> response = profesorController.postProfesor(profesor);

        assertEquals(errorMessage, response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void handleIllegalArgumentException() {
        // Ejecutar método y verificar respuesta
        ResponseEntity<Object> response = profesorController.handleIllegalArgumentException(new IllegalArgumentException("Mensaje de prueba"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensaje de prueba", response.getBody());
    }
}