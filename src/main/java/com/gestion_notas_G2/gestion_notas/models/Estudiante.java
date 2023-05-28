package com.gestion_notas_G2.gestion_notas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "persona")
public class Estudiante extends Persona {

    @Column(name = "programa_academico",nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'null'")
    private String programaAcademico;
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NotaActividad> notaActividades;
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Matricula> matriculas;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'null'")
    private String seccional;

    public Estudiante() {
    }

    public Estudiante(String programaAcademico, List<NotaActividad> notaActividades, List<Matricula> matriculas, String seccional) {
        this.programaAcademico = programaAcademico;
        this.notaActividades = notaActividades;
        this.matriculas = matriculas;
        this.seccional = seccional;
    }

    public Estudiante(Long id, String nombre, String apellido, String correo, String numDocumento, String correoInstitucional, String telefono, Usuario usuario, String programaAcademico, List<NotaActividad> notaActividades, List<Matricula> matriculas, String seccional) {
        super(id, nombre, apellido, correo, numDocumento, correoInstitucional, telefono, usuario);
        this.programaAcademico = programaAcademico;
        this.notaActividades = notaActividades;
        this.matriculas = matriculas;
        this.seccional = seccional;
    }

    public Estudiante(Long idEstudiante) {
        super(idEstudiante);
    }

    public String getProgramaAcademico() {
        return programaAcademico;
    }

    public void setProgramaAcademico(String programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    public List<NotaActividad> getNotaActividades() {
        return notaActividades;
    }

    public void setNotaActividades(List<NotaActividad> notaActividades) {
        this.notaActividades = notaActividades;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public String getSeccional() {
        return seccional;
    }

    public void setSeccional(String seccional) {
        this.seccional = seccional;
    }
}
