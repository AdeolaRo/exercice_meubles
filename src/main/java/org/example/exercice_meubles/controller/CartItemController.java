package org.example.exercice_meubles.controller;

import org.example.exercice_meubles.entity.CartItem;
import org.example.exercice_meubles.service.CartItemService;
import org.example.exercice_meubles.service.FurnitureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartItemController {

    private final CartItemService cartItemService;
    private final FurnitureService furnitureService;

    public CartItemController(CartItemService cartItemService, FurnitureService furnitureService) {
        this.cartItemService = cartItemService;
        this.furnitureService = furnitureService;
    }

    @GetMapping("/list")
    public String getAllCartItems(Model model) {
        model.addAttribute("cartItems", cartItemService.getAllCartItems());
        return "cart/list";
    }

    @GetMapping("/add/{furnitureId}")
    public String addToCart(@PathVariable int furnitureId, Model model) {
        model.addAttribute("furnitureId", furnitureId);
        model.addAttribute("quantity", furnitureService.getFurnitureById(furnitureId).getStock());
        model.addAttribute("stock", furnitureService.getFurnitureById(furnitureId).getStock());
        model.addAttribute("cartItem", new CartItem());
        return "cart/add";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam int furnitureId, @RequestParam long quantity) {
        try {
            cartItemService.addToCart(furnitureId, quantity);
        }
        catch (NullPointerException e){
            System.out.println("Pas de furniture");
        }
        catch (IllegalArgumentException e){
            System.out.println("Not enough furniture");
        }

        return "redirect:/cart/list";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable int id) {
        cartItemService.removeFromCart(id);
        return "redirect:/cart/list";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartItemService.clearCart();
        return "redirect:/cart/list";
    }
}
