package project_testing.demo.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import project_testing.demo.model.Item;
import project_testing.demo.model.ItemListResponse;



@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final List<Item> items = new ArrayList<>();
    // private final List<String> categories = new ArrayList();

    @GetMapping(params =  "category")
    public ItemListResponse readItemsByCategory(
        @RequestParam(name = "category") String categoryName
    ) {
        String filterCategory = categoryName.toLowerCase();
        
        List<Item> filteredList = items.stream()
            .filter(item -> item.getCategories() != null && 
            item.getCategories().stream()
            .map(String::toLowerCase)
            .anyMatch(cat -> cat.equals(filterCategory)))
            .collect(Collectors.toList());

        int totalCount = filteredList.size();

        return new ItemListResponse(totalCount, filteredList);
    }

    @GetMapping
    public ItemListResponse readAllItems(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size
    ) {
        // List<Item> itemList = items;
        // int totalCount = items.size();
        // return new ItemListResponse(totalCount, itemList);

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, items.size());

        if (startIndex >= items.size() && !items.isEmpty()) {
            return new ItemListResponse(items.size(), Collections.emptyList());
        } else if (items.isEmpty()) {
            return new ItemListResponse(0, Collections.emptyList());
        }

        List<Item> paginatedList = items.subList(startIndex, endIndex);
        int totalCount = items.size();
        return new ItemListResponse(totalCount, paginatedList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> readOneItem(@PathVariable String id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return new ResponseEntity<>(item, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createItem(@RequestBody Item newItem) {
        items.add(newItem);
        return new ResponseEntity<>("Berhasil input data: " + newItem, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody Item updateItem) {
        for (int i = 0; i < items.size(); i++) {
            Item existingItem = items.get(i);
            if (existingItem.getId().equals(id)) {
                updateItem.setId(id);
                items.set(i, updateItem);
                return new ResponseEntity<>(items.get(i), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                Item deleteItem = items.remove(i);
                return new ResponseEntity<>(deleteItem, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
