package com.gestion_notas_G2.gestion_notas.services;

import com.gestion_notas_G2.gestion_notas.dto.EstudianteDTO;
import com.gestion_notas_G2.gestion_notas.models.Estudiante;
import com.gestion_notas_G2.gestion_notas.repositories.MatriculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private MatriculaService matriculaService;
    private List<Estudiante> estudianteList;
    private Long codigoGrupo;

    @BeforeEach
    void setUp() {
        codigoGrupo = 1L;
        Estudiante estudiante = new Estudiante("Sistemas", null, null, "Turbo");
        estudianteList = Collections.singletonList(estudiante);

    }

    @Test
    void getEstudieantesByGrupo() {
        when(matriculaRepository.findEstudiantesByGrupo_CodigoGrupo(codigoGrupo)).thenReturn(estudianteList);

        List<EstudianteDTO> actual = matriculaService.getEstudieantesByGrupo(codigoGrupo);
        List<EstudianteDTO> expected = estudianteList.stream()
                .map(estudiante -> modelMapper.map(estudiante, EstudianteDTO.class))
                .collect(Collectors.toList());

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }
}