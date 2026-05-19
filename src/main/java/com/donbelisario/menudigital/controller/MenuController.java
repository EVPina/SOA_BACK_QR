package com.donbelisario.menudigital.controller;

import com.donbelisario.menudigital.model.CategoriaMenu;
import com.donbelisario.menudigital.model.MenuQr;
import com.donbelisario.menudigital.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    
    @GetMapping("/categorias")
    public List<CategoriaMenu> getCategorias() {
        return menuService.getCategoriasActivas();
    }
    
    @GetMapping("/categorias/{categoriaId}/productos")
    public List<MenuQr> getProductosByCategoria(@PathVariable UUID categoriaId) {
        return menuService.getProductosByCategoria(categoriaId);
    }
    
    @GetMapping("/productos/destacados")
    public List<MenuQr> getProductosDestacados() {
        return menuService.getProductosDestacados();
    }
}