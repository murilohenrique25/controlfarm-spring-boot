package br.com.starkstecnologia.control_api.services;

import br.com.starkstecnologia.control_api.dto.DadosFilialDTO;
import br.com.starkstecnologia.control_api.dto.DadosMatrizDTO;
import br.com.starkstecnologia.control_api.entity.Matriz;
import br.com.starkstecnologia.control_api.exception.ServiceException;
import br.com.starkstecnologia.control_api.repository.MatrizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatrizService {

    @Autowired
    MatrizRepository matrizRepository;

    public void salvarMatriz(DadosMatrizDTO matrizDTO){
        Matriz matriz = converterDTOToEntity(matrizDTO);
        matrizRepository.save(matriz);
    }
    public void salvarFilial(DadosFilialDTO matrizDTO){
        Matriz matriz = converterDTOToEntityFilial(matrizDTO);
        matrizRepository.save(matriz);
    }

    public Matriz buscarMatriz(Long idMatriz){
        return matrizRepository.findById(idMatriz).orElseThrow(()-> new ServiceException("Matriz não encontrada"));
    }

    private static Matriz converterDTOToEntity(DadosMatrizDTO dadosMatrizDTO){
        Matriz matriz = new Matriz();
        matriz.setCnpj(dadosMatrizDTO.getCnpj());
        matriz.setEmail(dadosMatrizDTO.getEmail());
        matriz.setLogradouro(dadosMatrizDTO.getLogradouro());
        matriz.setNome(dadosMatrizDTO.getNome());
        matriz.setTelefone(dadosMatrizDTO.getTelefone());
        return matriz;
    }

    private Matriz converterDTOToEntityFilial(DadosFilialDTO dadosMatrizDTO){
        Matriz matrizFilial = new Matriz();
        matrizFilial.setCnpj(dadosMatrizDTO.getCnpj());
        matrizFilial.setEmail(dadosMatrizDTO.getEmail());
        matrizFilial.setLogradouro(dadosMatrizDTO.getLogradouro());
        matrizFilial.setNome(dadosMatrizDTO.getNome());
        matrizFilial.setTelefone(dadosMatrizDTO.getTelefone());
        Matriz matriz = matrizRepository.findById(dadosMatrizDTO.getIdMatriz()).orElseThrow(()-> new ServiceException("Matriz não encontrada"));
        matrizFilial.setIdMatrizPrincipal(matriz.getIdMatriz());
        return matrizFilial;
    }

}
