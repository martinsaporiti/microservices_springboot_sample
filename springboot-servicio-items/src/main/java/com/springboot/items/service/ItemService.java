package com.springboot.items.service;

import java.util.List;

import com.springboot.items.model.Item;

public interface ItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);
}
