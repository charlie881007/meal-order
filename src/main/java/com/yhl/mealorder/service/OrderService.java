package com.yhl.mealorder.service;

import com.yhl.mealorder.DTO.OrderCreationDTO;
import com.yhl.mealorder.DTO.OrderDTO;
import com.yhl.mealorder.entity.Item;
import com.yhl.mealorder.entity.Order;
import com.yhl.mealorder.entity.OrderItem;
import com.yhl.mealorder.entity.UserAccount;
import com.yhl.mealorder.exception.InvalidArgumentException;
import com.yhl.mealorder.repository.ItemRepository;
import com.yhl.mealorder.repository.OrderRepository;
import com.yhl.mealorder.repository.UserAccountRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserAccountRepository userAccountRepository;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, UserAccountRepository userAccountRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userAccountRepository = userAccountRepository;
    }

    public List<OrderDTO> getAllOrders(Pageable pageRequest) {
        return orderRepository.findAll(pageRequest).stream().map(Order::toDTO).toList();
    }

    public List<OrderDTO> getOrdersByParams(Order.Status status, LocalDateTime startTime, Pageable pageRequest) {
        //
        if (null != status && null != startTime) {
            return orderRepository.findByStatusAndCreateTimeAfter(status, startTime, pageRequest).stream().map(Order::toDTO).toList();
        }

        if (null != status) {
            return orderRepository.findByStatus(status, pageRequest).stream().map(Order::toDTO).toList();
        }


        return orderRepository.findByCreateTimeAfter(startTime, pageRequest).stream().map(Order::toDTO).toList();
    }

    public OrderDTO getById(Long id) {
        return orderRepository.findById(id).orElseThrow(InvalidArgumentException::new).toDTO();
    }

    public OrderDTO changeStatusToFinish(Long id) {
        Order order = orderRepository.findByIdAndStatus(id, Order.Status.IN_PROGRESS).orElseThrow(InvalidArgumentException::new);
        order.setStatus(Order.Status.COMPLETED);
        return order.toDTO();
    }

    public OrderDTO createOrder(UserAccount userAccount, OrderCreationDTO orderCreationDTO) {
        // 檢查userId參數跟JWT中的userId是不是同一人
        if (!userAccount.getId().equals(orderCreationDTO.getUserId())) {
            throw new InvalidArgumentException();
        }

        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        Item item;


        // 建構orderItems
        for (OrderCreationDTO.OrderItemDTO itemDTO : orderCreationDTO.getOrderItems()) {
            item = itemRepository.findById(itemDTO.getItemId()).orElseThrow(InvalidArgumentException::new);
            orderItems.add(new OrderItem(order, item, itemDTO.getQuantity()));
        }

        order.setOrderItems(orderItems);
        order.setPhoneNum(orderCreationDTO.getPhoneNum());
        order.setUserAccount(userAccountRepository.findById(orderCreationDTO.getUserId()).orElseThrow(InvalidArgumentException::new));
        order.setCreateTime(LocalDateTime.now(ZoneId.systemDefault()));
        order.setStatus(Order.Status.IN_PROGRESS);
        orderRepository.save(order);

        return order.toDTO();
    }
}
