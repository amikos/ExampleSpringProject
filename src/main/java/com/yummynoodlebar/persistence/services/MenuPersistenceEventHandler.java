package com.yummynoodlebar.persistence.services;

import com.yummynoodlebar.events.menu.*;
import com.yummynoodlebar.persistence.domain.MenuItem;
import com.yummynoodlebar.persistence.repository.MenuItemRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuPersistenceEventHandler implements MenuPersistenceService {

  private MenuItemRepository menuItemRepository;

  @Autowired
  public MenuPersistenceEventHandler(MenuItemRepository menuItemRepository) {
    this.menuItemRepository = menuItemRepository;
  }

  public AllMenuItemsEvent requestAllMenuItems(RequestAllMenuItemsEvent requestAllMenuItemsEvent) {
    Iterable<MenuItem> menuItems = menuItemRepository.findAll();

    List<MenuItemDetails> details = new ArrayList<MenuItemDetails>();

    for(MenuItem item: menuItems) {
      details.add(item.toStatusDetails());
    }

    return new AllMenuItemsEvent(details);
  }

  public MenuItemDetailsEvent requestMenuItemDetails(RequestMenuItemDetailsEvent requestMenuItemDetailsEvent) {
    MenuItem item = menuItemRepository.findById(requestMenuItemDetailsEvent.getId());

    if (item == null) {
      return MenuItemDetailsEvent.notFound(requestMenuItemDetailsEvent.getId());
    }

    return new MenuItemDetailsEvent(
        requestMenuItemDetailsEvent.getId(),
        item.toStatusDetails());
  }

  public MenuItemDetailsEvent createMenuItem(CreateMenuItemEvent createMenuItemEvent) {
    MenuItem item = menuItemRepository.save(
        MenuItem.fromStatusDetails(createMenuItemEvent.getDetails()));

    return new MenuItemDetailsEvent(
        item.getId(),
        item.toStatusDetails());
  }
}
