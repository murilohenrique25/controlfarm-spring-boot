package br.com.starkstecnologia.control_api.web.controller;

import br.com.starkstecnologia.control_api.dto.DadosEntregaDTO;
import br.com.starkstecnologia.control_api.dto.DadosFilialDTO;
import br.com.starkstecnologia.control_api.dto.DadosMatrizDTO;
import br.com.starkstecnologia.control_api.services.MatrizService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/empresa")
@Tag(name = "Entrega", description = "Operações referente as entregas")
public class MatrizEndpoint {

    @Autowired
    MatrizService matrizService;

    @PostMapping("/matriz")
    public ResponseEntity<?> salvarEmpresa(@RequestBody DadosMatrizDTO dadosMatrizDTO){
        matrizService.salvarMatriz(dadosMatrizDTO);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/filial")
    public ResponseEntity<?> salvarFilial(@RequestBody DadosFilialDTO dadosFilialDTO){
        matrizService.salvarFilial(dadosFilialDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEmpresa(@PathVariable("id") Long idMatriz){
        return ResponseEntity.ok(matrizService.buscarMatriz(idMatriz));
    }

}
