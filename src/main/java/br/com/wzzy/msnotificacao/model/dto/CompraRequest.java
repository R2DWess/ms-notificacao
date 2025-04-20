package br.com.wzzy.msnotificacao.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompraRequest {

    private String emailCliente;
    private List<ProdutoDTO> produtoDTO;
}
