package com.gestion_notas_G2.gestion_notas.dto;
import lombok.Data;

@Data
public class NotaActividadDTO {
    private Long id;
    private Double calificacion;
    private Long idEstudiante;
    private Long idActividadEvaluativa;
}
