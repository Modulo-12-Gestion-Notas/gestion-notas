package Interfaces;

import java.util.List;

import com.gestion_notas_G2.gestion_notas.dto.EstudianteDTO;

public interface IMatricula {

    public List<EstudianteDTO> getEstudieantesByGrupo(Long codigoGrupo);
}
