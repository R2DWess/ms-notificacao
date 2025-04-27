package br.com.wzzy.mspedido.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraResponseDTO {

    private String mensagem;
    private List<ProdutoDTO> produtos;
}
