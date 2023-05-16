package com.gestion_notas_G2.gestion_notas.dto;

import lombok.Data;

@Data
public class EstudianteDTO extends EstudianteSimpleDTO {
    private String correoInstitucional;
    private String seccional;
}
