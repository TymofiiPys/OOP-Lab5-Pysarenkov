package com.restaurant.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.restaurant.dto.OrderDTO;
import com.restaurant.dto.OrderDisplayDTO;
import com.restaurant.dto.OrderReceiveDTO;
import com.restaurant.entity.Client;
import com.restaurant.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDisplayDTO>> getOrders(
            @RequestParam(name = "which", required = false) String which,
            @RequestParam(name = "admin", required = false) String admin,
            @RequestAttribute(name = "client") Client client
    ) {
        List<OrderDisplayDTO> orders;
        if (which == null) {
            orders = orderService.getAllOrders();
        } else {
//            Client client = (Client) req.getAttribute("client");
            if (admin != null) {
//                if (!client.isAdmin()) {
//                    resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                    return;
//                }
                orders = orderService.getOrdersFilteredByStatus(which);
            } else {
                orders = orderService.getOrdersFilteredByStatusAndId(which, client.getId());
            }
        }
        if (orders == null) {
            return ResponseEntity.badRequest().build();
        } else if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @PostMapping
    public ResponseEntity<List<OrderDTO>> createOrders(
            @RequestBody Map<String, Integer> menuAmts,
            @RequestAttribute(name = "client") Client client
    ) {
        Long clientId = client.getId();
        OrderReceiveDTO orders = new OrderReceiveDTO();
        orders.setMenuAmts(menuAmts);
        List<OrderDTO> createdOrders = orderService.createOrders(orders, clientId);
        if (createdOrders == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdOrders);
    }

    @PutMapping
    public ResponseEntity<Void> updateOrderStatus(
            @RequestBody List<Long> orderIdsToUpdate,
            @RequestParam(name = "status") String status
    ) {
        if (status.equals("ISSUED_FOR_PAYMENT")) {
//            Client client = (Client) req.getAttribute("client");
//            if (!client.isAdmin()) {
//                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                return;
//            }
        }
        int updatedRows = orderService.updateOrderStatus(orderIdsToUpdate, status);
        if (updatedRows < 0) {
            return ResponseEntity.badRequest().build();
        } else if (updatedRows == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        List<Integer> orderIdsToIssue = Arrays.asList(
//                objectMapper.readValue(
//                        req.getReader().lines().collect(Collectors.joining()),
//                        Integer[].class
//                )
//        );
//        if (req.getParameter("status").equals("ISSUED_FOR_PAYMENT")) {
//            Client client = (Client) req.getAttribute("client");
//            if (!client.isAdmin()) {
//                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                return;
//            }
//        }
//        int updatedRows = orderService.updateOrderStatus(orderIdsToIssue, req.getParameter("status"));
//        if (updatedRows < 0) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        } else if (updatedRows == 0) {
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//        } else {
//            resp.setStatus(HttpServletResponse.SC_OK);
//        }
//    }
}
