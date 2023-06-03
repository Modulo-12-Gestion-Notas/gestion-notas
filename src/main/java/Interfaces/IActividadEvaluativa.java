package Interfaces;

import java.util.List;

import com.gestion_notas_G2.gestion_notas.models.ActividadEvaluativa;

public interface IActividadEvaluativa {
    public List<ActividadEvaluativa> getActividadEvaluativaList();

    public List<ActividadEvaluativa> getActividadEvaluativaListByGrupo(Long codigoGrupo);

    public String postActivadEvaluativaList(List<ActividadEvaluativa> actividadEvaluativaList, Long codigoGrupo)
            throws Exception;

}