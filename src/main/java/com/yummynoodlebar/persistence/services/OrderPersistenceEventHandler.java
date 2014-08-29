package com.yummynoodlebar.persistence.services;

import com.yummynoodlebar.events.orders.*;
import com.yummynoodlebar.persistence.domain.Order;
import com.yummynoodlebar.persistence.domain.OrderStatus;
import com.yummynoodlebar.persistence.repository.OrderStatusRepository;
import com.yummynoodlebar.persistence.repository.OrdersRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderPersistenceEventHandler implements OrderPersistenceService {

  private final OrdersRepository orderRepository;
  private final OrderStatusRepository orderStatusRepository;

  @Autowired
  public OrderPersistenceEventHandler(
      final OrdersRepository orderRepository,
      final OrderStatusRepository orderStatusRepository) {
    this.orderRepository = orderRepository;
    this.orderStatusRepository = orderStatusRepository;
  }

  public OrderStatusEvent setOrderStatus(SetOrderStatusEvent event) {

    OrderStatus status = OrderStatus.fromStatusDetails(event.getOrderStatus());

    status = orderStatusRepository.save(status);

    return new OrderStatusEvent(status.getId(), status.toStatusDetails());
  }

  public OrderCreatedEvent createOrder(CreateOrderEvent createOrderEvent) {
    Order order = Order.fromOrderDetails(createOrderEvent.getDetails());

    order = orderRepository.save(order);

    return new OrderCreatedEvent(order.getKey(), order.toOrderDetails());
  }

  public AllOrdersEvent requestAllOrders(RequestAllOrdersEvent requestAllCurrentOrdersEvent) {
    List<OrderDetails> generatedDetails = new ArrayList<OrderDetails>();
    for (Order order : orderRepository.findAll()) {
      generatedDetails.add(order.toOrderDetails());
    }
    return new AllOrdersEvent(generatedDetails);
  }

  public OrderDetailsEvent requestOrderDetails(RequestOrderDetailsEvent requestOrderDetailsEvent) {

    Order order = orderRepository.findById(requestOrderDetailsEvent.getKey());

    if (order == null) {
      return OrderDetailsEvent.notFound(requestOrderDetailsEvent.getKey());
    }

    return new OrderDetailsEvent(
            requestOrderDetailsEvent.getKey(),
            order.toOrderDetails());
  }

  public OrderUpdatedEvent setOrderPayment(SetOrderPaymentEvent setOrderPaymentEvent) {
    Order order = orderRepository.findById(setOrderPaymentEvent.getKey());

    if(order == null) {
      return OrderUpdatedEvent.notFound(setOrderPaymentEvent.getKey());
    }

    //TODO, handling payment details...

    return new OrderUpdatedEvent(order.getKey(), order.toOrderDetails());
  }

  public OrderDeletedEvent deleteOrder(DeleteOrderEvent deleteOrderEvent) {

    Order order = orderRepository.findById(deleteOrderEvent.getKey());

    if (order == null) {
      return OrderDeletedEvent.notFound(deleteOrderEvent.getKey());
    }

    orderRepository.delete(deleteOrderEvent.getKey());

    return new OrderDeletedEvent(deleteOrderEvent.getKey(), order.toOrderDetails());
  }

  public OrderStatusEvent requestOrderStatus(RequestOrderStatusEvent requestOrderDetailsEvent) {
    
    OrderStatus orderStatus = orderStatusRepository.findLatestById(requestOrderDetailsEvent.getKey());

    if (orderStatus == null) {
      return OrderStatusEvent.notFound(requestOrderDetailsEvent.getKey());
    }

    return new OrderStatusEvent(requestOrderDetailsEvent.getKey(), orderStatus.toStatusDetails());
  }
}
