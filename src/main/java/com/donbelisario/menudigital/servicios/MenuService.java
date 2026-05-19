package com.donbelisario.menudigital.service;

import com.donbelisario.menudigital.model.CategoriaMenu;
import com.donbelisario.menudigital.model.MenuQr;
import com.donbelisario.menudigital.repository.CategoriaMenuRepository;
import com.donbelisario.menudigital.repository.MenuQrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class MenuService {
    
    @Autowired
    private CategoriaMenuRepository categoriaRepository;
    
    @Autowired
    private MenuQrRepository menuRepository;
    
    public List<CategoriaMenu> getCategoriasActivas() {
        return categoriaRepository.findByActivoTrueOrderByOrdenAsc();
    }
    
    public List<MenuQr> getProductosByCategoria(UUID categoriaId) {
        return menuRepository.findByCategoriaId(categoriaId);
    }
    
    public List<MenuQr> getProductosDestacados() {
        return menuRepository.findByCategoriaIdAndDestacadoTrue(null);
    }
}