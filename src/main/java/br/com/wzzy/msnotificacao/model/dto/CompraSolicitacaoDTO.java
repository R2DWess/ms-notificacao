package br.com.wzzy.msnotificacao.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompraSolicitacaoDTO {
    private String emailCliente;
    private List<Integer> idsProdutos;
}