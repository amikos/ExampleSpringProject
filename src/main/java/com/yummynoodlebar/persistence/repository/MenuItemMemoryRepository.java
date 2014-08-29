package com.yummynoodlebar.persistence.repository;

import com.yummynoodlebar.persistence.domain.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MenuItemMemoryRepository implements MenuItemRepository {

	private Map<String, MenuItem> items = new HashMap<String, MenuItem>();

	
	public MenuItemMemoryRepository() {
		
	}

	public MenuItemMemoryRepository(Map<String, MenuItem> items) {
		this.items = items;
	}

	public MenuItem save(MenuItem item) {
		items.put(item.getId(), item);
		return item;
	}

	public void delete(String key) {
		items.remove(key);
	}

	public MenuItem findById(String key) {
		for (MenuItem item : items.values()) {
			if (item.getId().equals(key)) {
				return item;
			}
		}
		return null;
	}

	public List<MenuItem> findAll() {
		return new ArrayList<MenuItem>(items.values());
	}
}
