package com.gestion_notas_G2.gestion_notas.services;

import com.gestion_notas_G2.gestion_notas.models.ActividadEvaluativa;
import com.gestion_notas_G2.gestion_notas.repositories.ActividadEvaluativaRepository;

import Interfaces.IActividadEvaluativa;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadEvaluativaService implements IActividadEvaluativa {
    private ActividadEvaluativaRepository actividadEvaluativaRepository;

    public ActividadEvaluativaService(ActividadEvaluativaRepository actividadEvaluativaRepository) {
        this.actividadEvaluativaRepository = actividadEvaluativaRepository;
    }

    /**
     * Devuelve una lista con todas las actividades evaluativas almacenadas en el
     * repositorio.
     *
     * @return Lista de objetos ActividadEvaluativa.
     */
    public List<ActividadEvaluativa> getActividadEvaluativaList() {
        return this.actividadEvaluativaRepository.findAll();
    }

    /**
     * Devuelve una lista con todas las actividades evaluativas del grupo
     * especificado por su código.
     *
     * @param codigoGrupo El código del grupo del cual se desean obtener las
     *                    actividades evaluativas.
     * @return Lista de objetos ActividadEvaluativa.
     */
    public List<ActividadEvaluativa> getActividadEvaluativaListByGrupo(Long codigoGrupo) {
        return this.actividadEvaluativaRepository.findAllByGrupo_PeriodoAcademico_VigenteAndGrupo_CodigoGrupo(true,
                codigoGrupo);
    }

    /**
     * Guarda una lista de actividades evaluativas para un grupo especificado.
     *
     * @param actividadEvaluativaList Lista de objetos ActividadEvaluativa que se
     *                                desea guardar.
     * @param codigoGrupo             El código del grupo para el cual se desean
     *                                guardar las actividades evaluativas.
     * @return Un mensaje de confirmación de la operación realizada.
     * @throws Exception
     * @throws IllegalArgumentException si la suma de los porcentajes de las
     *                                  actividades evaluativas supera el 100%.
     */
    public String postActivadEvaluativaList(List<ActividadEvaluativa> actividadEvaluativaList, Long codigoGrupo)
            throws Exception {
        List<ActividadEvaluativa> actividadEvaluativas = this.actividadEvaluativaRepository
                .findAllByGrupo_PeriodoAcademico_VigenteAndGrupo_CodigoGrupo(true, codigoGrupo);

        if (actividadEvaluativaList.stream().mapToInt(ActividadEvaluativa::getPorcentaje).sum() > 100) {
            throw new IllegalArgumentException(
                    "No se pueden guardar las actividades evaluativas porque la suma de los porcentajes supera el 100%");
        }

        if (actividadEvaluativas.isEmpty()) {
            try {
                this.actividadEvaluativaRepository.saveAll(actividadEvaluativaList);
                return "Las actividades evaluativas se han guardado correctamente.";
            } catch (Exception e) {
                throw new Exception("Ha ocurrido un error al guardar los datos");
            }
        } else {
            try {
                actividadEvaluativas.removeIf(evaluacion -> actividadEvaluativaList.contains(evaluacion));
                this.actividadEvaluativaRepository.deleteAll(actividadEvaluativas);
                this.actividadEvaluativaRepository.saveAll(actividadEvaluativaList);
                return "Las actividades evaluativas se han guardado correctamente.";
            } catch (Exception e) {
                throw new Exception("Ha ocurrido un error al guardar los datos");
            }
        }
    }
}
