package com.example.customerorder.filereader;

import com.example.customerorder.entity.Order;
import com.example.customerorder.entity.OrderDetails;
import com.example.customerorder.exception.DataValidationException;
import com.example.customerorder.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataReader {
    @Autowired
    private DataService dataService;
    private Map<String, Integer> fieldMapping;


    @Transactional
    public List<Order> readDataAndStore(MultipartFile file) {
        List<String> validationErrors = new ArrayList<>();
        List<Order> orderResults = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            Order order = null;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    fieldMapping = mapFieldIndices(line);
                    continue;
                }
                Order parsedOrder = parseOrder(line, validationErrors);
                if (parsedOrder != null) {
                    if (order == null || !order.getOrderName().equals(parsedOrder.getOrderName())) {
                        if (order != null) {
                            orderResults.add(order);
                        }
                        order = parsedOrder;
                    } else {
                        order.addOrderDetails(parsedOrder.getOrderDetails().get(0));
                    }
                }
            }

            if (validationErrors.isEmpty() && order != null) {
                orderResults.add(order);

            } else {
                for (String error : validationErrors) {
                    System.out.println("Validation Error: " + error);
                }
                System.out.println("Data validation failed. Rolling back.");
                throw new DataValidationException("Data validation failed");
                //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data validation failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderResults;
    }

    private Order parseOrder(String line, List<String> validationErrors) {
        Order order = new Order();

        if (line == null) {
            validationErrors.add("Invalid number of columns for order data");
            return null;
        }

        String[] data = line.split(",");
        if (fieldMapping.isEmpty() || data.length != fieldMapping.size()) {
            validationErrors.add("Invalid number of columns for order details data: " + line);
            return null;
        }

        order.setOrderName(data[fieldMapping.get("orderName")].trim());

        for (int i = 0; i < data.length; i += 6) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setItemName(data[i + fieldMapping.get("itemName")].trim());
            orderDetails.setItemCode(data[i + fieldMapping.get("itemCode")].trim());
            orderDetails.setUnitPrice(Double.parseDouble(data[i + fieldMapping.get("unitPrice")].trim()));
            orderDetails.setItemQuantity(Integer.parseInt(data[i + fieldMapping.get("itemQuantity")].trim()));
            orderDetails.setTotalAmount(Double.parseDouble(data[i + fieldMapping.get("totalAmount")].trim()));
            orderDetails.setItemCreatedBy("me");
            order.addOrderDetails(orderDetails);
        }

        System.out.println("Customer details: " + order);
        return order;
    }


    private Map<String, Integer> mapFieldIndices(String line) {
        Map<String, Integer> fieldMapping = new LinkedHashMap<>();
        if (line == null) {
            throw new IllegalArgumentException("Invalid header row: null");
        }
        String[] headers = line.split(",");
        for (int i = 0; i < headers.length; i++) {
            String header = headers[i].trim();
            fieldMapping.put(header, i);
        }
        return fieldMapping;
    }

}
