package com.yummynoodlebar.persistence.repository;

import com.yummynoodlebar.persistence.domain.OrderStatus;

import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class OrderStatusMemoryRepository implements OrderStatusRepository {

  private Map<UUID, OrderStatus> orderStatuses = new HashMap<UUID, OrderStatus>();

  public OrderStatus save(OrderStatus order) {
    orderStatuses.put(order.getId(), order);
    return order;
  }

  public void delete(UUID key) {
    orderStatuses.remove(key);
  }

  public OrderStatus findLatestById(UUID key) {
    for(OrderStatus item: orderStatuses.values()) {
      if (item.getOrderId().equals(key)) {
        return item;
      }
    }
    return null;
  }

  public List<OrderStatus> findAll() {
    return new ArrayList<OrderStatus>(orderStatuses.values());
  }
}
