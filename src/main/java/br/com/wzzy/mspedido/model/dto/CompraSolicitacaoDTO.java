package br.com.wzzy.mspedido.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompraSolicitacaoDTO {
    private String emailCliente;
    private List<Integer> idsProdutos = new ArrayList<>();
}
