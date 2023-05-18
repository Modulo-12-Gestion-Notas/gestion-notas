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
}
