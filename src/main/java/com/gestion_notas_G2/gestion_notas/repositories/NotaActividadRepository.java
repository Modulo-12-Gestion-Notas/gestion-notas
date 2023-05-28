package com.gestion_notas_G2.gestion_notas.repositories;

import com.gestion_notas_G2.gestion_notas.dto.NotaActividadDTO;
import com.gestion_notas_G2.gestion_notas.models.NotaActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface NotaActividadRepository extends JpaRepository<NotaActividad, Long> {

    @Query("SELECT n FROM NotaActividad n WHERE n.actividadEvaluativa.grupo.codigoGrupo = :codigoGrupo")
    List<NotaActividad> findNotaActividadByActividadEvaluativa_Grupo(@Param("codigoGrupo") Long codigoGrupo);

    @Query("SELECT n FROM NotaActividad n WHERE n.actividadEvaluativa.grupo.codigoGrupo = :codigoGrupo AND n.estudiante.id = :idEstudiante")
    List<NotaActividad> findNotaActividadByEstudianteAndGrupo(@Param("codigoGrupo") Long codigoGrupo, @Param("idEstudiante") Long idEstudiante);
}
