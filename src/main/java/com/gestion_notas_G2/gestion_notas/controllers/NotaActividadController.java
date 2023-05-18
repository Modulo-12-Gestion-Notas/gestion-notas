package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.dto.NotaActividadDTO;
import com.gestion_notas_G2.gestion_notas.services.NotaActividadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotaActividadController {
    private NotaActividadService notaActividadService;
    public  NotaActividadController(NotaActividadService notaActividadService){
        this.notaActividadService = notaActividadService;
    }

}
