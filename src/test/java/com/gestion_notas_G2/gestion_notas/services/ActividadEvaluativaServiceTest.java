package com.gestion_notas_G2.gestion_notas.services;

import com.gestion_notas_G2.gestion_notas.models.ActividadEvaluativa;
import com.gestion_notas_G2.gestion_notas.repositories.ActividadEvaluativaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActividadEvaluativaServiceTest {
    @Mock
    private ActividadEvaluativaRepository actividadEvaluativaRepository;
    @InjectMocks
    private ActividadEvaluativaService actividadEvaluativaService;

    private List<ActividadEvaluativa> actividadEvaluativaList;
    private final Long codigoGrupo = 1L;

    @BeforeEach
    void setUp() {
        ActividadEvaluativa actividadEvaluativa = new ActividadEvaluativa(1L, "unidad1", 25, null, null);
        actividadEvaluativaList = new ArrayList<>();
        actividadEvaluativaList.add(actividadEvaluativa);
    }

    @Test
    void getActividadEvaluativaList() {
        when(actividadEvaluativaRepository.findAll()).thenReturn(actividadEvaluativaList);

        List<ActividadEvaluativa> result = actividadEvaluativaService.getActividadEvaluativaList();

        assertEquals(actividadEvaluativaList, result);
    }
//
//    @Test
//    void getActividadEvaluativaListByGrupo(){
//        when(actividadEvaluativaRepository.findAllByGrupo_PeriodoAcademico_VigenteAndGrupo_CodigoGrupo(true, codigoGrupo ))
//                .thenReturn(actividadEvaluativaList);
//
//        List<ActividadEvaluativa> result = actividadEvaluativaService.getActividadEvaluativaListByGrupo(codigoGrupo);
//
//        assertEquals(actividadEvaluativaList, result);
//    }
//
//    @Test
//    void postActividadEvaluativaList() throws Exception {
//        when(actividadEvaluativaRepository.findAllByGrupo_PeriodoAcademico_VigenteAndGrupo_CodigoGrupo(true, codigoGrupo))
//                .thenReturn(Collections.emptyList());
//
//        String result = actividadEvaluativaService.postActivadEvaluativaList(actividadEvaluativaList, codigoGrupo);
//        String expect = "Las actividades evaluativas se han guardado correctamente.";
//
//        verify(actividadEvaluativaRepository, times(1)).saveAll(actividadEvaluativaList);
//        assertEquals(expect, result);
//    }
//
//    @Test
//    void postActivadEvaluativaList_whenSumOfPorcentajesGreaterThan100_shouldThrowIllegalArgumentException() {
//        ActividadEvaluativa actividadEvaluativaAdd = new ActividadEvaluativa(1L, "unidad1", 80, null, null);
//        actividadEvaluativaList.add(actividadEvaluativaAdd);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            actividadEvaluativaService.postActivadEvaluativaList(actividadEvaluativaList, codigoGrupo);
//        });
//
//        String expectedMessage = "No se pueden guardar las actividades evaluativas porque la suma de los porcentajes supera el 100%";
//        String actualMessage = exception.getMessage();
//
//        assertEquals(actualMessage, expectedMessage);
//    }
//
//
//    @Test
//    void postActivadEvaluativaListIsEmptyException() throws Exception {
//        when(actividadEvaluativaRepository.findAllByGrupo_PeriodoAcademico_VigenteAndGrupo_CodigoGrupo(true, codigoGrupo))
//                .thenReturn(Collections.emptyList());
//        when(actividadEvaluativaRepository.saveAll(actividadEvaluativaList)).thenThrow(new RuntimeException("Ha ocurrido un error al guardar los datos"));
//
//        Exception exception = assertThrows(Exception.class, () -> {
//            actividadEvaluativaService.postActivadEvaluativaList(actividadEvaluativaList, codigoGrupo);
//        });
//
//        String expected = "Ha ocurrido un error al guardar los datos";
//
//        assertEquals(expected, exception.getMessage());
//    }
//
//    @Test
//    void postActivadEvaluativaListNotEmpty() throws Exception {
//        when(actividadEvaluativaRepository.findAllByGrupo_PeriodoAcademico_VigenteAndGrupo_CodigoGrupo(true, codigoGrupo))
//                .thenReturn(actividadEvaluativaList);
//
//        String result = actividadEvaluativaService.postActivadEvaluativaList(actividadEvaluativaList, codigoGrupo);
//        String expected = "Las actividades evaluativas se han guardado correctamente.";
//
//        verify(actividadEvaluativaRepository, times(1)).deleteAll(actividadEvaluativaList);
//        verify(actividadEvaluativaRepository, times(1)).saveAll(actividadEvaluativaList);
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    void postActivadEvaluativaListNotEmptyException() throws Exception {
//        when(actividadEvaluativaRepository.findAllByGrupo_PeriodoAcademico_VigenteAndGrupo_CodigoGrupo(true, codigoGrupo))
//                .thenReturn(actividadEvaluativaList);
//        when(actividadEvaluativaRepository.saveAll(actividadEvaluativaList)).thenThrow(new RuntimeException("Ha ocurrido un error al guardar los datos"));
//
//        Exception exception = assertThrows(Exception.class, () -> {
//            actividadEvaluativaService.postActivadEvaluativaList(actividadEvaluativaList, codigoGrupo);
//        });
//        String expected = "Ha ocurrido un error al guardar los datos";
//
//        assertEquals(expected, exception.getMessage());
//    }
//
}