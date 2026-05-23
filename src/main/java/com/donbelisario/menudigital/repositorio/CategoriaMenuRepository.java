package com.donbelisario.menudigital.repositorio;

import com.donbelisario.menudigital.model.CategoriaMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CategoriaMenuRepository extends JpaRepository<CategoriaMenu, UUID> {
    List<CategoriaMenu> findByActivoTrueOrderByOrdenAsc();
}