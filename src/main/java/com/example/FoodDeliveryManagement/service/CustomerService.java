package com.example.FoodDeliveryManagement.service;

import com.example.FoodDeliveryManagement.Enum.Gender;
import com.example.FoodDeliveryManagement.dto.request.CustomerRequest;
import com.example.FoodDeliveryManagement.dto.response.CustomerResponse;
import com.example.FoodDeliveryManagement.exception.CustomerNotFoundException;
import com.example.FoodDeliveryManagement.model.Cart;
import com.example.FoodDeliveryManagement.model.Customer;
import com.example.FoodDeliveryManagement.repository.CustomerRepository;
import com.example.FoodDeliveryManagement.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        // dto -> customer model
        Customer customer = CustomerTransformer.CustomerRequestToCustomer(customerRequest);

        //allocate a cart
        Cart cart = Cart.builder()
                .cartTotal(0)
                .customer(customer)
                .build();

        customer.setCart(cart);

        // save both customer and cart
        Customer savedCustomer = customerRepository.save(customer);  // saves both customer and cart

        // prepare response Dto
        return CustomerTransformer.CustomerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse findCustomerByMobile(String mobile) {

        Customer customer = customerRepository.findByMobileNo(mobile);
        if(customer==null){
            throw new CustomerNotFoundException("Invalid mobile no!!!");
        }
        return CustomerTransformer.CustomerToCustomerResponse(customer);
    }

    public CustomerResponse getCustomerWithMostOrders() {
        List<Customer> customerList=customerRepository.findAll();

        int maximumOrders=0;
        Customer customer=null;
        for(Customer customer1:customerList)
        {
            int customerOrdersCount=customer1.getOrders().size();
            if(customerOrdersCount>maximumOrders)
            {
                maximumOrders=customerOrdersCount;
                customer=customer1;
            }
        }
        return CustomerTransformer.CustomerToCustomerResponse(customer);
    }
    public CustomerResponse getFemaleCustomerWithLeastOrders() {
        List<Customer> customerList=customerRepository.findByGender(Gender.FEMALE);
        int minimumOrders=Integer.MAX_VALUE;
        Customer customer=null;
        for(Customer customer1:customerList)
        {
            int customerOrdersCount=customer1.getOrders().size();
            if(customerOrdersCount<minimumOrders)
            {
                minimumOrders=customerOrdersCount;
                customer=customer1;
            }
        }
        return CustomerTransformer.CustomerToCustomerResponse(customer);
    }

    public CustomerResponse deleteCustomerByMobile(String mobile) {

        Customer customer = customerRepository.findByMobileNo(mobile);
        if(customer==null){
            throw new CustomerNotFoundException("Invalid mobile no!!!");
        }
        customerRepository.deleteById(customer.getId());
        return CustomerTransformer.CustomerToCustomerResponse(customer);
    }
}
