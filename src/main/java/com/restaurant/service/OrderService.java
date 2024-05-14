package com.restaurant.service;

import com.restaurant.dto.OrderDTO;
import com.restaurant.dto.OrderDisplayDTO;
import com.restaurant.dto.OrderReceiveDTO;
import com.restaurant.entity.Menu;
import com.restaurant.entity.Order;
import com.restaurant.mapper.OrderMapper;
import com.restaurant.repository.ClientRepository;
import com.restaurant.repository.MenuRepository;
import com.restaurant.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final ClientRepository clientRepository;
    private final OrderMapper mapper = OrderMapper.INSTANCE;

    public List<OrderDisplayDTO> getAllOrders() {
        return toOrderDisplayDTOList(orderRepository.findAll());
    }

    public List<OrderDisplayDTO> getOrdersFilteredByStatus(String statusStr) {
        Optional<Order.StatusOrder> status = checkStatus(statusStr);
        if (status.isEmpty()) return null;
        return toOrderDisplayDTOList(orderRepository.findAllByStatus(status.get()));
//        return toOrderDisplayDTOList(orderDAO.readOrders(status.get(), null));
    }

    public List<OrderDisplayDTO> getOrdersFilteredByStatusAndId(String statusStr, Long clientId) {
        Optional<Order.StatusOrder> status = checkStatus(statusStr);
        if (status.isEmpty()) return null;
        return toOrderDisplayDTOList(orderRepository.findAllByStatusAndClientId(status.get(), clientId));
    }

    public List<OrderDTO> createOrders(OrderReceiveDTO orders, Long clientId) {
        List<Order> orderList = fromOrderReceiveDTO(orders, clientId);
        List<Order> createdOrders = orderRepository.saveAll(orderList);
//        if (createdOrders == null) return null;
        return createdOrders.stream().map(mapper::toOrderDTO).toList();
    }

    public int updateOrderStatus(List<Long> orderIds, String statusStr) {
        Optional<Order.StatusOrder> status = checkStatus(statusStr);
        if (status.isEmpty()) return -1;
        List<Order> ordersToUpdate = orderRepository.findAllById(orderIds);
        for (var order : ordersToUpdate) {
            order.setStatus(status.get());
        }
        List<Order> ordersUpdated = orderRepository.saveAll(ordersToUpdate);
        return ordersUpdated.size();
//        return orderDAO.updateOrderStatus(orderIds, status.get());
    }

    private List<OrderDisplayDTO> toOrderDisplayDTOList(List<Order> orders) {
        if (orders == null) return null;
        List<OrderDisplayDTO> orderDisplay = new ArrayList<>();
        for (Order order : orders) {
            Optional<Menu> orderedItem = menuRepository.findById(order.getMenuId());
            Optional<String> clientName = clientRepository.findEmailAddressById(order.getClientId());
            if (orderedItem.isEmpty() || clientName.isEmpty()) {
                continue;
            }
            orderDisplay.add(
                    OrderDisplayDTO.builder()
                            .id(order.getId())
                            .clientName(clientName.get())
                            .menuItemName(orderedItem.get().getName())
                            .amount(order.getAmount())
                            .status(order.getStatus())
                            .cost(order.getAmount() * orderedItem.get().getCost())
                            .build()
            );
        }
        return orderDisplay;
    }

    private List<Order> fromOrderReceiveDTO(OrderReceiveDTO orders, Long clientId) {
        List<Order> orderList = new ArrayList<>();
        for (String menuId : orders.getMenuAmts().keySet()) {
            Order orderToAdd = new Order();
            orderToAdd.setClientId(clientId);
            orderToAdd.setMenuId(Long.parseLong(menuId));
            orderToAdd.setAmount(orders.getMenuAmts().get(menuId));
            orderToAdd.setStatus(Order.StatusOrder.ORDERED);

            orderList.add(orderToAdd);
        }
        return orderList;
    }

    private Optional<Order.StatusOrder> checkStatus(String statusStr) {
        try {
            Order.StatusOrder status = Order.StatusOrder.valueOf(statusStr);
            return Optional.of(status);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
