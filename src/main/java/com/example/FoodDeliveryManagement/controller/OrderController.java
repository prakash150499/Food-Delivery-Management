package com.example.FoodDeliveryManagement.controller;

import com.example.FoodDeliveryManagement.dto.response.OrderResponse;
import com.example.FoodDeliveryManagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place/mobile/{mobile}")
    public ResponseEntity placeOrder(@PathVariable("mobile") String customerMobile){

        try{
        OrderResponse orderResponse = orderService.placeOrder(customerMobile);
        return new ResponseEntity(orderResponse,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("all-orders")
    public ResponseEntity getAllOrders()
    {
        List<OrderResponse> orderResponseList=orderService.getAllOrders();
        return new ResponseEntity(orderResponseList,HttpStatus.FOUND);

    }

}
