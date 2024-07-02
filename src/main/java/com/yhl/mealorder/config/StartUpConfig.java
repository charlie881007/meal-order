package com.yhl.mealorder.config;

import com.yhl.mealorder.entity.*;
import com.yhl.mealorder.repository.ItemRepository;
import com.yhl.mealorder.repository.OrderRepository;
import com.yhl.mealorder.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class StartUpConfig {
    @Bean
    public CommandLineRunner initStore(ItemRepository itemRepository, OrderRepository orderRepository, PasswordEncoder encoder, UserAccountRepository userAccountRepository) {
        return args -> {
            ItemType type_tea = new ItemType("茶");
            ItemType type_coffee = new ItemType("咖啡");

            Item tea1 = new Item("柳橙綠茶", type_tea, new BigDecimal("60"), "沁涼風味");
            Item tea2 = new Item("伯爵紅茶", type_tea, new BigDecimal("40"), "經典紅茶");

            Item coffee1 = new Item("拿鐵", type_coffee, new BigDecimal("70"), "香醇可口");
            Item coffee2 = new Item("黑咖啡", type_coffee, new BigDecimal("50"), "濃韻回甘");

            UserAccount userAccount = new UserAccount("user", encoder.encode("user"), "USER");
            userAccountRepository.save(userAccount);

            Order order = new Order();
            order.setPhoneNum("0912345678");
            order.setUserAccount(userAccount);
            order.setOrderItems(List.of(new OrderItem(order, tea1, 2), new OrderItem(order, coffee2, 1)));
            order.setCreateTime(LocalDateTime.now(ZoneId.systemDefault()));
            order.setStatus(Order.Status.IN_PROGRESS);

            itemRepository.saveAll(List.of(tea1, tea2, coffee1, coffee2));
            orderRepository.save(order);
        };
    }

    @Bean
    public CommandLineRunner initAdmin(UserAccountRepository userAccountRepository, PasswordEncoder encoder) {
        return args -> {
            userAccountRepository.save(new UserAccount("admin", encoder.encode("password"), "ROLE_ADMIN"));
        };
    }
}
