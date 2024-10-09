package br.com.starkstecnologia.control_api.web.controller.app;


import br.com.starkstecnologia.control_api.dto.DadosEntregaAvulsaDTO;
import br.com.starkstecnologia.control_api.dto.DadosEntregaDTO;
import br.com.starkstecnologia.control_api.dto.DadosEntregaFinalizacaoDTO;
import br.com.starkstecnologia.control_api.services.EntregaService;
import br.com.starkstecnologia.control_api.services.EntregadorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app/v1/entrega")
@Tag(name = "Entrega App", description = "Operações referente as entregas no APP")
public class EntregaAppEndpoint {

    @Autowired
    EntregaService entregaService;

    @Autowired
    EntregadorService entregadorService;

    @GetMapping
    public ResponseEntity<?> entregasDisponiveis(){
        return ResponseEntity.ok(entregaService.entregasDisponiveis());
    }

    @PutMapping("/selecionar/{userId}")
    public ResponseEntity<?> selecionarEntrega(@RequestBody List<DadosEntregaDTO> entregaDTOS, @PathVariable("userId") String userId){
        entregaService.selecionarAtribuirAlterarEntrega(entregaDTOS, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/entregador/{userId}")
    public ResponseEntity<?> entregasByIdEntregador(@PathVariable("userId") String userId){
        return ResponseEntity.ok(entregaService.entregasByIdEntregador(userId));
    }

    @PutMapping("/finalizar")
    public ResponseEntity<?> finalizarEntrega(@RequestBody DadosEntregaFinalizacaoDTO entregaDTOS){
        entregaService.finalizarEntrega(entregaDTOS);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/avulsa")
    public ResponseEntity<?> finalizarEntrega(@RequestBody DadosEntregaAvulsaDTO entregaDTO){
        entregaService.salvarEntregaAvulsa(entregaDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/quantidade/{user_id}")
    public ResponseEntity<?> quantiadadeEntregas(@PathVariable("user_id") String userId){
        return ResponseEntity.ok(String.valueOf(entregadorService.getQuantidadeEntregas(userId)));
    }

}
