package com.nagarro.devops.controller;


import com.nagarro.devops.model.Item;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final List<Item> items = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public List<Item> getAllItems() {
        return items;
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        item.setId(counter.incrementAndGet());
        items.add(item);
        return item;
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    @GetMapping("/status")
    public String checkApiStatus() {
        return "The Spring Boot API is working!";
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Item item = getItemById(id);
        if (item != null) {
            item.setName(updatedItem.getName());
            item.setDescription(updatedItem.getDescription());
        }
        return item;
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        items.removeIf(item -> item.getId().equals(id));
    }
}
