package org.example.exercice_meubles.controller;

import org.example.exercice_meubles.entity.Furniture;
import org.example.exercice_meubles.service.FurnitureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FurnitureController {

    private final FurnitureService furnitureService;

    public FurnitureController(FurnitureService furnitureService) {
        this.furnitureService = furnitureService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/furniture/list")
    public String listFurnitures(Model model) {
        model.addAttribute("furnitures", furnitureService.getAllFurniture());
        return "furniture/list";
    }

    @GetMapping("/furniture/add")
    public String addFurniture(Model model) {
        model.addAttribute("furniture", new Furniture());
        return "furniture/form";
    }

    @GetMapping("/furniture/update/{id}")
    public String updateFurniture(@PathVariable int id, Model model) {
        Furniture furniture = furnitureService.getFurnitureById(id);
        if (furniture != null) {
            model.addAttribute("furniture", furniture);
            return "furniture/form";
        } else {
            return "redirect:/furniture/list";
        }
    }

    @PostMapping("/furniture/save")
    public String submitFurniture(@ModelAttribute("furniture") Furniture furniture) {
        furnitureService.saveFurniture(furniture);
        return "redirect:/furniture/list";
    }

    @GetMapping("/furniture/delete/{id}")
    public String deleteFurniture(@PathVariable int id) {
        furnitureService.deleteFurniture(id);
        return "redirect:/furniture/list";
    }
}
