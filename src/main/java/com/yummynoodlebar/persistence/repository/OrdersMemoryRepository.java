package com.yummynoodlebar.persistence.repository;

import com.yummynoodlebar.persistence.domain.Order;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdersMemoryRepository implements OrdersRepository {

  private Map<UUID, Order> orders;

  @Autowired(required=false)
  public OrdersMemoryRepository(final Map<UUID, Order> orders) {
    this.orders = Collections.unmodifiableMap(orders);
  }

  public synchronized Order save(Order order) {

    Map<UUID, Order> modifiableOrders = new HashMap<UUID, Order>(orders);
    modifiableOrders.put(order.getKey(), order);
    this.orders = Collections.unmodifiableMap(modifiableOrders);

    return order;
  }

  public synchronized void delete(UUID key) {
    if (orders.containsKey(key)) {
      Map<UUID, Order> modifiableOrders = new HashMap<UUID, Order>(orders);
      modifiableOrders.remove(key);
      this.orders = Collections.unmodifiableMap(modifiableOrders);
    }
  }

  public Order findById(UUID key) {
    return orders.get(key);
  }

  public List<Order> findAll() {
    return Collections.unmodifiableList(new ArrayList<Order>(orders.values()));
  }
}
