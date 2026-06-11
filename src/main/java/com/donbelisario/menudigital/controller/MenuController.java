package com.donbelisario.menudigital.controller;

import com.donbelisario.menudigital.model.CategoriaMenu;
import com.donbelisario.menudigital.model.MenuQr;
import com.donbelisario.menudigital.servicios.MenuService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    @GetMapping("/menu/destacados")
    public List<MenuQr> getProductosDestacados() {
        return menuService.getProductosDestacados();
    }
    // Agregar este método al MenuController.java

    @GetMapping("/qr/mesa/{mesaId}")
    @Operation(summary = "Generar código QR para una mesa específica")
    public ResponseEntity<Map<String, String>> generarQrMesa(@PathVariable UUID mesaId) {
        // Generar URL con el ID de la mesa
        String qrUrl = "https://donbelisario.com/menu?mesa=" + mesaId;
        String qrBase64 = generarCodigoQR(qrUrl); // Método auxiliar
        
        Map<String, String> response = new HashMap<>();
        response.put("mesaId", mesaId.toString());
        response.put("qrCode", qrBase64);
        response.put("url", qrUrl);
        
        return ResponseEntity.ok(response);
    }

    // Método auxiliar para generar QR (puedes usar biblioteca como ZXing)
    private String generarCodigoQR(String texto) {
        // Implementación con ZXing o similar
        // Retorna base64 de la imagen QR
        return "data:image/png;base64,...";
    }
}