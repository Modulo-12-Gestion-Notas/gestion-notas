package com.gestion_notas_G2.gestion_notas.repositories;

import com.gestion_notas_G2.gestion_notas.dto.ActividadEvaluativaSimpleDTO;
import com.gestion_notas_G2.gestion_notas.models.ActividadEvaluativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadEvaluativaRepository extends JpaRepository<ActividadEvaluativa, Long> {
    public List<ActividadEvaluativa> findAllByGrupo_CodigoGrupo(String codigoGrupo);
    public List<ActividadEvaluativa> findAllByGrupo_PeriodoAcademico_VigenteAndGrupo_CodigoGrupo(boolean vigente, Long codigoGrupo);

    @Query("SELECT av FROM ActividadEvaluativa  av WHERE av.grupo.codigoGrupo = :codigoGrupo")
    List<ActividadEvaluativa> findActividadEvaluativaByGrupo(@Param("codigoGrupo") Long codigoGrupo);
}
