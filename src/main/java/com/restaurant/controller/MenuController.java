package com.restaurant.controller;

import com.restaurant.dto.MenuCreateDTO;
import com.restaurant.dto.MenuDTO;
import com.restaurant.entity.Client;
import com.restaurant.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getMenu() {
        List<MenuDTO> menu = menuService.getMenu();
        if (menu == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(menu);
    }

    @PostMapping
    public ResponseEntity<MenuDTO> createMenu(
            @RequestBody MenuCreateDTO menuToCreate,
            @RequestAttribute(name = "client") Client client
    ) {
        if (!client.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        MenuDTO createdMenu = menuService.createMenu(menuToCreate);
        if (createdMenu == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdMenu);
    }

    @PutMapping
    public ResponseEntity<Void> updateMenu(
            @RequestBody MenuDTO menuToUpdate,
            @RequestAttribute(name = "client") Client client
    ) {
        if (!client.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        int updatedRows = menuService.updateMenu(menuToUpdate);
        if (updatedRows < 0) {
            return ResponseEntity.badRequest().build();
        } else if (updatedRows == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMenu(
            @RequestBody Long menuIdToDelete,
                                           @RequestAttribute(name = "client") Client client
    ) {
        if(!client.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        boolean deleted = menuService.deleteMenu(menuIdToDelete);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
