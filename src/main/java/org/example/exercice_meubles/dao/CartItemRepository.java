package org.example.exercice_meubles.dao;

import org.example.exercice_meubles.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
