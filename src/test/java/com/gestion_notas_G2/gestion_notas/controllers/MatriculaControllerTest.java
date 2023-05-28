package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.dto.EstudianteDTO;
import com.gestion_notas_G2.gestion_notas.dto.GrupoSimpleDTO;
import com.gestion_notas_G2.gestion_notas.dto.ProfesorDTO;
import com.gestion_notas_G2.gestion_notas.models.Estudiante;
import com.gestion_notas_G2.gestion_notas.response.GrupoEstudiantesResponse;
import com.gestion_notas_G2.gestion_notas.services.GrupoService;
import com.gestion_notas_G2.gestion_notas.services.MatriculaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
@WebMvcTest(GrupoController.class)
class MatriculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatriculaService matriculaService;
    @MockBean
    private GrupoService grupoService;
    @InjectMocks
    private MatriculaController matriculaController;
    private List<EstudianteDTO> estudianteDTOList;
    private List<Estudiante> estudianteList;
    private EstudianteDTO estudianteDTO;
    private Long codigoGrupo;
    private ProfesorDTO profesor;
    private GrupoSimpleDTO grupo;
    ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        codigoGrupo = 1L;
        Estudiante estudiante = new Estudiante("Sistemas", null, null, "Turbo");
        estudianteList = Collections.singletonList(estudiante);
        estudianteDTOList = estudianteList.stream()
                .map(estudiantes -> modelMapper.map(estudiantes, EstudianteDTO.class))
                .collect(Collectors.toList());
        profesor = new ProfesorDTO();
        grupo = new GrupoSimpleDTO();
    }

    @Test
    void getEstudieantesByGrupo() {
        // Crear instancias simuladas de los servicios necesarios
        when(matriculaService.getEstudieantesByGrupo(codigoGrupo)).thenReturn(estudianteDTOList);
        when(grupoService.getProfesorByGrupo(codigoGrupo)).thenReturn(profesor);
        when(grupoService.getGrupoByCodigoGrupo(codigoGrupo)).thenReturn(grupo);

        // Llamar al método y verificar la respuesta
        ResponseEntity<Object> response = matriculaController.getEstudieantesByGrupo(codigoGrupo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof GrupoEstudiantesResponse);
        GrupoEstudiantesResponse grupoEstudiantesResponse = (GrupoEstudiantesResponse) response.getBody();
        assertEquals(profesor, grupoEstudiantesResponse.getProfesor());
        assertEquals(grupo, grupoEstudiantesResponse.getGrupo());
        assertEquals(estudianteDTOList, grupoEstudiantesResponse.getEstudianteList());

        // Verificar comportamiento con código de grupo no válido
        response = matriculaController.getEstudieantesByGrupo(456L);

        // Verificar comportamiento con excepción lanzada por los servicios
        when(matriculaService.getEstudieantesByGrupo(codigoGrupo)).thenThrow(new RuntimeException("Error"));
        response = matriculaController.getEstudieantesByGrupo(codigoGrupo);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void handleIllegalArgumentException() {
        // Ejecutar método y verificar respuesta
        ResponseEntity<Object> response = matriculaController.handleIllegalArgumentException(new IllegalArgumentException("Mensaje de prueba"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensaje de prueba", response.getBody());
    }
}