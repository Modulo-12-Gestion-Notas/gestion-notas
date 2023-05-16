package com.gestion_notas_G2.gestion_notas.response;

import com.gestion_notas_G2.gestion_notas.dto.ActividadEvaluativaSimpleDTO;
import com.gestion_notas_G2.gestion_notas.dto.EstudianteDTO;
import com.gestion_notas_G2.gestion_notas.dto.GrupoSimpleDTO;
import com.gestion_notas_G2.gestion_notas.dto.ProfesorDTO;

import java.util.List;

public class GrupoEstudianteListAndActividadEvaluativaListResponse {
    private ProfesorDTO profesor;

    private GrupoSimpleDTO grupo;
    private List<EstudianteDTO> estudianteList;

    public GrupoEstudianteListAndActividadEvaluativaListResponse(ProfesorDTO profesor, GrupoSimpleDTO grupo, List<EstudianteDTO> estudianteList, List<ActividadEvaluativaSimpleDTO> actividadEvaluativaList) {
        this.profesor = profesor;
        this.grupo = grupo;
        this.estudianteList = estudianteList;
        this.actividadEvaluativaList = actividadEvaluativaList;
    }

    public GrupoEstudianteListAndActividadEvaluativaListResponse() {
    }

    public List<ActividadEvaluativaSimpleDTO> getActividadEvaluativaList() {
        return actividadEvaluativaList;
    }

    public void setActividadEvaluativaList(List<ActividadEvaluativaSimpleDTO> actividadEvaluativaList) {
        this.actividadEvaluativaList = actividadEvaluativaList;
    }

    private List<ActividadEvaluativaSimpleDTO> actividadEvaluativaList;



    public ProfesorDTO getProfesor() {
        return profesor;
    }

    public void setProfesor(ProfesorDTO profesor) {
        this.profesor = profesor;
    }

    public List<EstudianteDTO> getEstudianteList() {
        return estudianteList;
    }

    public void setEstudianteList(List<EstudianteDTO> estudianteList) {
        this.estudianteList = estudianteList;
    }

    public GrupoSimpleDTO getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoSimpleDTO grupo) {
        this.grupo = grupo;
    }
}
