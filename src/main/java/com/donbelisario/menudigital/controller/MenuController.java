package com.donbelisario.menudigital.controller;

import com.donbelisario.menudigital.model.CategoriaMenu;
import com.donbelisario.menudigital.model.MenuQr;
import com.donbelisario.menudigital.servicios.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu")
@Tag(name = "Menu Digital", description = "API para revisar menu ditital")
@CrossOrigin(origins = "*")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    
    @GetMapping("/categorias")
    @Operation(summary = "Obtener todas las categorías de menú")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorías obtenidas exitosamente")
    })
    public List<CategoriaMenu> getCategorias() {
        return menuService.getCategoriasActivas();
    }
    
    @GetMapping("/categorias/{categoriaId}/productos")
    @Operation(summary = "Obtener productos por categoría")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos obtenidos exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public List<MenuQr> getProductosByCategoria(@PathVariable UUID categoriaId) {
        return menuService.getProductosByCategoria(categoriaId);
    }
    
    @GetMapping("/menu/destacados")
    @Operation(summary = "Obtener productos destacados del menú")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos destacados obtenidos exitosamente")
    })
    public List<MenuQr> getProductosDestacados() {
        return menuService.getProductosDestacados();
    }
    // Agregar este método al MenuController.java

    @GetMapping("/qr/mesa/{mesaId}")
    @Operation(summary = "Generar código QR para una mesa específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Código QR generado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
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