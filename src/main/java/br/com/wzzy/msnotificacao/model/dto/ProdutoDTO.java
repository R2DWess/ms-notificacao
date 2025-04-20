package br.com.wzzy.msnotificacao.model.dto;

import lombok.Data;

@Data
public class ProdutoDTO {
    private int id;
    private String title;
    private Double price;
    private String category;
}
