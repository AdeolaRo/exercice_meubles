package org.example.exercice_meubles.dao;

import org.example.exercice_meubles.entity.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FurnitureRepository extends JpaRepository<Furniture, Integer> {
    Furniture findByName(String name);
}
