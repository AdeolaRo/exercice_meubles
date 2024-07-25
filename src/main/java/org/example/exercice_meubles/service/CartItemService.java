package org.example.exercice_meubles.service;

import org.example.exercice_meubles.dao.CartItemRepository;
import org.example.exercice_meubles.entity.CartItem;
import org.example.exercice_meubles.entity.Furniture;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final FurnitureService furnitureService;

    public CartItemService(CartItemRepository cartItemRepository, FurnitureService furnitureService) {
        this.cartItemRepository = cartItemRepository;
        this.furnitureService = furnitureService;
    }

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem addToCart(int furnitureId, long quantity) {
        Furniture furniture = furnitureService.getFurnitureById(furnitureId);
        if (furniture == null) {
            throw new NullPointerException("Furniture does not exist");
        }

        if (furniture.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        furniture.setStock(furniture.getStock() - quantity);
        furnitureService.saveFurniture(furniture);

        CartItem cartItem = new CartItem();
        cartItem.setFurniture(furniture);
        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    public void removeFromCart(int cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem != null) {
            Furniture furniture = cartItem.getFurniture();
            furniture.setStock(furniture.getStock() + cartItem.getQuantity());
            furnitureService.saveFurniture(furniture);
            cartItemRepository.delete(cartItem);
        }
    }

    public void clearCart() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        for (CartItem cartItem : cartItems) {
            Furniture furniture = cartItem.getFurniture();
            furniture.setStock(furniture.getStock() + cartItem.getQuantity());
            furnitureService.saveFurniture(furniture);
        }
        cartItemRepository.deleteAll();
    }
}
