package Interfaces;

import java.util.List;

import com.gestion_notas_G2.gestion_notas.dto.GrupoSimpleDTO;
import com.gestion_notas_G2.gestion_notas.dto.ProfesorDTO;
import com.gestion_notas_G2.gestion_notas.models.Grupo;

public interface IGrupoService {
    public List<Grupo> getGruposByProfesor(Long idProfesor);

    public GrupoSimpleDTO getGrupoByCodigoGrupo(Long codigoGrupo);

    public ProfesorDTO getProfesorByGrupo(Long codigoGrupo);
}
