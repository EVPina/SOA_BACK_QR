package com.donbelisario.menudigital.repositorio;

import com.donbelisario.menudigital.model.MenuQr;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MenuQrRepository extends JpaRepository<MenuQr, UUID> {
    List<MenuQr> findByCategoriaIdAndDestacadoTrue(UUID categoriaId);
    List<MenuQr> findByCategoriaId(UUID categoriaId);
}