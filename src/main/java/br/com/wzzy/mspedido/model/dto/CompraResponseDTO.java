package br.com.wzzy.mspedido.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompraResponseDTO {

    private String mensagem;
    private List<ProdutoDTO> produtos;
}
