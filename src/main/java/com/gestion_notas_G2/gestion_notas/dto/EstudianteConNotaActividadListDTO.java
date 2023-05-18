package com.gestion_notas_G2.gestion_notas.dto;
import lombok.Data;

import java.util.List;

@Data
public class EstudianteConNotaActividadListDTO extends EstudianteSimpleDTO{
    private List<NotaActividadDTO> notas;
}
