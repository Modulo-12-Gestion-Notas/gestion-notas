package com.gestion_notas_G2.gestion_notas.dto;
import lombok.Data;

import java.util.List;

@Data
public class GrupoNotasByEstudianteDTO {
    private CursoSimpleDTO curso;
    private PeriodoAcademicoDTO periodoAcademico;
    private String nombreGrupo;
    private ProfesorDTO profesor;
    private List<ActividadEvaluativaSimpleDTO> evaluaciones;
    private NotasByEstudianteOfGrupoDTO notas;
}
