package com.manager.controller;

import com.manager.services.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/estoque")
    public String estoque() {
        return estoqueService.consultarProduto();
    }
}
