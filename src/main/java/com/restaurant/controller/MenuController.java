package com.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.dto.MenuCreateDTO;
import com.restaurant.dto.MenuDTO;
import com.restaurant.service.MenuService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

//    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getMenu() throws IOException {
        List<MenuDTO> menu = menuService.getMenu();
//        resp.setContentType("application/json");
//        if (menu == null)
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        else {
//            resp.setStatus(HttpServletResponse.SC_OK);
//            resp.getWriter().write(objectMapper.writeValueAsString(menu));
        return ResponseEntity.ok(menu);
//        }
    }

    @PostMapping
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuCreateDTO menuToCreate) {
        //        Client client = objectMapper.readValue(
//                req.getAttribute("client").toString(),
//                Client.class
//        );
//        if(!client.isAdmin()) {
//            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
        MenuDTO createdMenu = menuService.createMenu(menuToCreate);
        if (createdMenu == null) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ResponseEntity.badRequest().build();
        }
//            resp.setStatus(HttpServletResponse.SC_OK);
//            resp.getWriter().write(objectMapper.writeValueAsString(createdMenu));
        return ResponseEntity.ok(createdMenu);

    }

    @PutMapping
    public ResponseEntity<Void> updateMenu(@RequestBody MenuDTO menuToUpdate) {
        //        Client client = objectMapper.readValue(
//                req.getAttribute("client").toString(),
//                Client.class
//        );
//        if(!client.isAdmin()) {
//            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
        int updatedRows = menuService.updateMenu(menuToUpdate);
        if (updatedRows < 0) {
            return ResponseEntity.badRequest().build();
        } else if (updatedRows == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMenu(@RequestBody Long menuIdToDelete) {
        //        Client client = objectMapper.readValue(
//                req.getAttribute("client").toString(),
//                Client.class
//        );
//        if(!client.isAdmin()) {
//            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
        boolean deleted = menuService.deleteMenu(menuIdToDelete);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
