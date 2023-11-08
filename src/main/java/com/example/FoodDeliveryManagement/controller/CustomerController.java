package com.example.FoodDeliveryManagement.controller;

import com.example.FoodDeliveryManagement.dto.request.CustomerRequest;
import com.example.FoodDeliveryManagement.dto.response.CustomerResponse;
import com.example.FoodDeliveryManagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
        return new ResponseEntity(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/find/mobile/{mobile}")
    public ResponseEntity getCustomerByMobile(@PathVariable("mobile") String mobile){
        try{
           CustomerResponse customerResponse = customerService.findCustomerByMobile(mobile);
           return new ResponseEntity(customerResponse,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

      @GetMapping("/most-orders")
        public ResponseEntity getCustomerWithMostOrders()
        {
            try{
                CustomerResponse customerResponse = customerService.getCustomerWithMostOrders();
                return new ResponseEntity(customerResponse,HttpStatus.FOUND);
            }
            catch (Exception e){
                return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }
    @GetMapping("/most-orders/female")
    public ResponseEntity getFemaleCustomerWithLeastOrders()
    {
        try{
            CustomerResponse customerResponse = customerService.getFemaleCustomerWithLeastOrders();
            return new ResponseEntity(customerResponse,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/delete-customer/{mobile}")
    public ResponseEntity deleteCustomerByMobile(@PathVariable("mobile") String mobile)
    {

        try{
            CustomerResponse customerResponse = customerService.deleteCustomerByMobile(mobile);
            return new ResponseEntity(customerResponse,HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



}
