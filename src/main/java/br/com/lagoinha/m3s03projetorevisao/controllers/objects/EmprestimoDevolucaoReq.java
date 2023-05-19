package br.com.lagoinha.m3s03projetorevisao.controllers.objects;

import lombok.Data;

@Data
public class EmprestimoDevolucaoReq {

    private Long usuarioId;
    private Long livroId;

}
