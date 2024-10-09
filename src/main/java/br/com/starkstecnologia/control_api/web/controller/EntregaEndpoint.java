package br.com.starkstecnologia.control_api.web.controller;


import br.com.starkstecnologia.control_api.dto.DadosEntregaDTO;
import br.com.starkstecnologia.control_api.services.EntregaService;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/entrega")
@Tag(name = "Entrega", description = "Operações referente as entregas")
public class EntregaEndpoint {

    @Autowired
    EntregaService entregaService;

    @PostMapping
    public ResponseEntity<?> salvarEntrega(@RequestBody DadosEntregaDTO dadosEntregaDTO){
        entregaService.salvarEntrega(dadosEntregaDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterarEntrega(@RequestBody DadosEntregaDTO dadosEntregaDTO){
        entregaService.alterarEntrega(dadosEntregaDTO);
        return ResponseEntity.status(200).build();
    }

    @PutMapping(value = "/assinar/{idCaixa}")
    public ResponseEntity<?> assinarEntregas(@RequestBody List<DadosEntregaDTO> entregaDTOList, @PathVariable("idCaixa") Long idCaixa){
        entregaService.assinarEntregaCaixa(entregaDTOList, idCaixa);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/atribuir/{user_id}")
    public ResponseEntity<?> selecionarEntrega(@RequestBody List<DadosEntregaDTO> entregaDTOS,@PathVariable("user_id") String userId){
        entregaService.selecionarAtribuirAlterarEntrega(entregaDTOS, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/finalizar")
    public ResponseEntity<?> finalizarEntregaManual(@RequestBody List<DadosEntregaDTO> entregaDTOS){
        entregaService.finalizarEntregaManual(entregaDTOS);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/cancelar/{idEntrega}")
    public ResponseEntity<?> cancelarEntrega(@PathVariable("idEntrega") Long idEntrega){
        entregaService.cancelarEntrega(idEntrega);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> buscarEntregas(@RequestParam(required = false) Long idEntrega,
                                             @RequestParam(required = false) LocalDateTime dataInicio,
                                             @RequestParam(required = false) LocalDateTime dataTermino){
       return ResponseEntity.ok(entregaService.buscarEntregas(idEntrega, dataInicio, dataTermino));
    }



    @GetMapping("/avulsas")
    public ResponseEntity<?> buscarTodasEntregasAvulsas(){
        return ResponseEntity.ok(entregaService.buscarTodasEntregasAvulsas());
    }

}
