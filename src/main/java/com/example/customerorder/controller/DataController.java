package com.example.customerorder.controller;

import com.example.customerorder.entity.Order;
import com.example.customerorder.exception.FileNotFoundException;
import com.example.customerorder.filereader.DataReader;
import com.example.customerorder.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/customer")
public class DataController {
    @Autowired
    private DataService dataService;

    @Autowired
    private DataReader dataReader;


    @PostMapping("/orderInvoice")
    public ResponseEntity<List<Order>> orderDetails(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        if (file.isEmpty()) {
            throw new FileNotFoundException("File not found.");
        }
        List<Order> orders = dataService.storeDetails(file);
        return ResponseEntity.ok(orders);
    }

}