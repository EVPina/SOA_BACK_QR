package com.donbelisario.menudigital.controller;

import com.donbelisario.menudigital.model.CategoriaMenu;
import com.donbelisario.menudigital.model.MenuQr;
import com.donbelisario.menudigital.servicios.MenuService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/menu")
@Tag(name = "Menu Digital", description = "API para revisar menu ditital")
@CrossOrigin(origins = "${app.frontend-url:http://localhost:4200}")
public class MenuController {

    private static final int QR_SIZE = 300;

    @Autowired
    private MenuService menuService;

    @Value("${app.frontend-url:http://localhost:4200}")
    private String frontendUrl;

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
    @GetMapping("/qr/mesa/{mesaId}")
    @Operation(summary = "Generar código QR para una mesa específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Código QR generado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
    })
    public ResponseEntity<Map<String, String>> generarQrMesa(@PathVariable UUID mesaId) {
        String qrUrl = frontendUrl + "/login?mesaId=" + mesaId;
        String qrBase64;
        try {
            qrBase64 = generarCodigoQR(qrUrl);
        } catch (WriterException | IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("mesaId", mesaId.toString());
        response.put("qrCode", qrBase64);
        response.put("url", qrUrl);

        return ResponseEntity.ok(response);
    }

    private String generarCodigoQR(String texto) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        return "data:image/png;base64," + base64;
    }
}