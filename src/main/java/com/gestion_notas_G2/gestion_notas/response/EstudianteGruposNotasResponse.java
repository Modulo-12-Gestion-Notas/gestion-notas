package com.gestion_notas_G2.gestion_notas.response;

import com.gestion_notas_G2.gestion_notas.dto.EstudianteDTO;
import com.gestion_notas_G2.gestion_notas.dto.GrupoNotasByEstudianteDTO;
import java.util.List;
import lombok.Data;

@Data
public class EstudianteGruposNotasResponse {
    private EstudianteDTO estudiante;
    private List<GrupoNotasByEstudianteDTO> Grupos;
}
