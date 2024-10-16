package br.com.starkstecnologia.control_api.services;

import br.com.starkstecnologia.control_api.dto.DadosFilialDTO;
import br.com.starkstecnologia.control_api.dto.DadosMatrizDTO;
import br.com.starkstecnologia.control_api.entity.Matriz;
import br.com.starkstecnologia.control_api.exception.ServiceException;
import br.com.starkstecnologia.control_api.repository.MatrizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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

    public List<DadosMatrizDTO> buscarTodasMatriz() {
        List<Matriz> matrizes = matrizRepository.findByMatrizes();
        return converterEntityMatrizToDTO(matrizes);
    }

    public List<DadosFilialDTO> buscarTodasFiliais(Long idMatriz) {
        List<Matriz> matrizes = matrizRepository.findFiliaisByIdMatriz(idMatriz);
        return converterEntityFilialToDTO(matrizes);
    }

    private List<DadosFilialDTO> converterEntityFilialToDTO(List<Matriz> matrizes) {
        List<DadosFilialDTO> dadosFilialDTOS = new ArrayList<>();

        for(Matriz matriz : matrizes){
            DadosFilialDTO dadosFilialDTO = new DadosFilialDTO();
            dadosFilialDTO.setIdFilial(matriz.getIdMatriz());
            dadosFilialDTO.setNome(matriz.getNome());
            dadosFilialDTO.setIdMatriz(matriz.getIdMatrizPrincipal());
            dadosFilialDTO.setCnpj(matriz.getCnpj());
            dadosFilialDTO.setEmail(matriz.getEmail());
            dadosFilialDTO.setLogradouro(matriz.getLogradouro());
            dadosFilialDTO.setTelefone(matriz.getTelefone());
            dadosFilialDTOS.add(dadosFilialDTO);
        }

        return dadosFilialDTOS;
    }

    private List<DadosMatrizDTO> converterEntityMatrizToDTO(List<Matriz> matrizes) {
        List<DadosMatrizDTO> dadosMatrizDTOS = new ArrayList<>();

        for(Matriz matriz : matrizes){
            DadosMatrizDTO dadosMatrizDTO = new DadosMatrizDTO();
            dadosMatrizDTO.setIdMatriz(matriz.getIdMatriz());
            dadosMatrizDTO.setCnpj(matriz.getCnpj());
            dadosMatrizDTO.setEmail(matriz.getEmail());
            dadosMatrizDTO.setLogradouro(matriz.getLogradouro());
            dadosMatrizDTO.setTelefone(matriz.getTelefone());
            dadosMatrizDTO.setNome(matriz.getNome());
            dadosMatrizDTOS.add(dadosMatrizDTO);
        }

        return dadosMatrizDTOS;
    }
}
