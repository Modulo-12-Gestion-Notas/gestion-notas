package com.gestion_notas_G2.gestion_notas.dto;

import java.util.List;
import lombok.Data;

@Data
public class NotasByEstudianteOfGrupoDTO {
    private List<NotaActividadDetalladaDTO> calificaciones;
    private int porcentajeEvaluado;
    private Float notaAcumulada;
    private Float notaDefinitiva;
}
