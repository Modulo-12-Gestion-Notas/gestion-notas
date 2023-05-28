package com.gestion_notas_G2.gestion_notas.dto;
import lombok.Data;

@Data
public class NotaActividadDetalladaDTO {
    private Long id;
    private double calificacion;
    private ActividadEvaluativaSimpleDTO evaluacion;
}
