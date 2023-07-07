package com.example.customerorder.service;

import com.example.customerorder.entity.Order;
import com.example.customerorder.entity.OrderDetails;
import com.example.customerorder.exception.DataValidationException;
import com.example.customerorder.filereader.DataReader;
import com.example.customerorder.repository.OrderDetailsRepository;
import com.example.customerorder.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DataReader dataReader;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;


    public List<Order> storeDetails(MultipartFile file) {
        try {
            List<Order> orders = dataReader.readDataAndStore(file);
            return storeCustomerAndOrderDetails(orders);
        } catch (DataValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data validation failed", ex);
        }
    }

    public List<Order> storeCustomerAndOrderDetails(List<Order> orders) {
        List<Order> savedOrders = new ArrayList<>();

        for (Order order : orders) {
            order.setOrderDeliveryDate(LocalDate.now().plusDays(1));
            Order savedOrder = orderRepository.save(order);
            for (OrderDetails orderDetails : order.getOrderDetails()) {
                orderDetails.setOrder(savedOrder);
                orderDetailsRepository.save(orderDetails);
            }
            savedOrders.add(savedOrder);
        }

        return savedOrders;
    }

}
