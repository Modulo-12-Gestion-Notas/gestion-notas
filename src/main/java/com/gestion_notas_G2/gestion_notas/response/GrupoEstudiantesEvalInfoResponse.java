package com.gestion_notas_G2.gestion_notas.response;

import com.gestion_notas_G2.gestion_notas.dto.*;
import java.util.List;
import lombok.Data;

@Data
public class GrupoEstudiantesEvalInfoResponse {
    private ProfesorDTO profesor;
    private GrupoSimpleDTO grupo;
    private List<EstudianteConNotaActividadListDTO> estudiantes;
    private List<ActividadEvaluativaSimpleDTO> evaluaciones;
}
