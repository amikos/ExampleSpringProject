package com.yummynoodlebar.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yummynoodlebar.events.menu.*;
import com.yummynoodlebar.persistence.services.MenuPersistenceService;

@Service
public class MenuEventHandler implements MenuService {

  private MenuPersistenceService menuPersistenceService;

  @Autowired
  public MenuEventHandler(MenuPersistenceService menuPersistenceService) {
    this.menuPersistenceService = menuPersistenceService;
  }

  public AllMenuItemsEvent requestAllMenuItems(RequestAllMenuItemsEvent requestAllMenuItemsEvent) {
    return menuPersistenceService.requestAllMenuItems(requestAllMenuItemsEvent);
  }

  public MenuItemDetailsEvent requestMenuItemDetails(RequestMenuItemDetailsEvent requestMenuItemDetailsEvent) {
    return menuPersistenceService.requestMenuItemDetails(requestMenuItemDetailsEvent);
  }

  public MenuItemDetailsEvent createMenuItem(CreateMenuItemEvent createMenuItemEvent) {
    return menuPersistenceService.createMenuItem(createMenuItemEvent);
  }
}
