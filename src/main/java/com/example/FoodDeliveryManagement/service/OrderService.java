package com.example.FoodDeliveryManagement.service;

import com.example.FoodDeliveryManagement.dto.response.FoodResponse;
import com.example.FoodDeliveryManagement.dto.response.OrderResponse;
import com.example.FoodDeliveryManagement.exception.CustomerNotFoundException;
import com.example.FoodDeliveryManagement.exception.EmptyCartException;
import com.example.FoodDeliveryManagement.model.*;
import com.example.FoodDeliveryManagement.repository.CustomerRepository;
import com.example.FoodDeliveryManagement.repository.DeliveryPartnerRepo;
import com.example.FoodDeliveryManagement.repository.OrderEntityRepo;
import com.example.FoodDeliveryManagement.repository.RestaurantRespository;
import com.example.FoodDeliveryManagement.transformer.OrderTransformer;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    final CustomerRepository customerRepository;
    final OrderEntityRepo orderEntityRepo;


    final DeliveryPartnerRepo deliveryPartnerRepo;
    private final RestaurantRespository restaurantRespository;

    @Autowired
    public OrderService(CustomerRepository customerRepository,
                        OrderEntityRepo orderEntityRepo,
                        DeliveryPartnerRepo deliveryPartnerRepo,
                        RestaurantRespository restaurantRespository) {
        this.customerRepository = customerRepository;
        this.orderEntityRepo = orderEntityRepo;
        this.deliveryPartnerRepo = deliveryPartnerRepo;
        this.restaurantRespository = restaurantRespository;
    }
    @Autowired
    JavaMailSender javaMailSender;
    public OrderResponse placeOrder(String customerMobile) {

        // verify the customer
        Customer customer = customerRepository.findByMobileNo(customerMobile);
        if(customer == null){
            throw new CustomerNotFoundException("Invalid mobile number!!!");
        }

        // verify if cart is empty or not
        Cart cart = customer.getCart();
        if(cart.getFoodItems().size()==0){
            throw new EmptyCartException("Sorry! Your cart is empty!!!");
        }

        // find a delivery partner to deliver. Randomly
        DeliveryPartner partner = deliveryPartnerRepo.findRandomDeliveryPartner();
        Restaurant restaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();

        // prepare the order entity
        OrderEntity order = OrderTransformer.prepareOrderEntity(cart);

        OrderEntity savedOrder = orderEntityRepo.save(order);

        order.setCustomer(customer);
        order.setDeliveryPartner(partner);
        order.setRestaurant(restaurant);
        order.setFoodItems(cart.getFoodItems());

        customer.getOrders().add(savedOrder);
        partner.getOrders().add(savedOrder);
        restaurant.getOrders().add(savedOrder);

        for(FoodItem foodItem: cart.getFoodItems()){
            foodItem.setCart(null);
            foodItem.setOrder(savedOrder);
        }
        clearCart(cart);

        customerRepository.save(customer);
        deliveryPartnerRepo.save(partner);
        restaurantRespository.save(restaurant);

        // prepare orderresponse
        OrderResponse orderResponse =OrderTransformer.OrderToOrderResponse(savedOrder);

        //prepare an email of confirmation
        String customerName = customer.getName();
        String customerMo = customer.getMobileNo();
        String customerAddress = customer.getAddress();
        String restaurantName = orderResponse.getRestaurantName();
        String DeliveryBoy = orderResponse.getDeliveryPartnerName();
        String DeliveryBoyMo=orderResponse.getDeliveryPartnerMobile();
        String orderId = order.getOrderId();


        String foodItemString = "";
        for(FoodResponse foodResponse : orderResponse.getFoodResponses()){
            foodItemString += foodResponse.getDishName()+" :₹"+foodResponse.getPrice()+" \n ";
        }

        String msgForCustomer1 = "Dear,"+customerName+"\n"+"We're thrilled to inform you that your food delivery order has been successfully placed and is on its way to you. Your order details are as follows:\n" +
                "OrderId :#"+orderId +"\n"
                +"Delivery Address :"+customerAddress+"\n"+
                "Contact Number :"+customerMo+"\n"+"\n"+
                "ORDER DETAILS:"
                +"\n"+
                "Restaurant :"+restaurantName+"\n"+
                "Delivery Partner :"+DeliveryBoy +"\n"+
                "Delivery Partner Contact :"+DeliveryBoyMo +"\n"+
                "Order Date :"+orderResponse.getOrderTime()+"\n"+"\n"+

                "ORDER SUMMARY :"
                +"\n"+foodItemString+"\n"+
                "Order Total :₹"+orderResponse.getOrderTotal()+"\n \n \n"+
                "              THANK YOU         ";


        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("rahulkumardewangan380@gmail.com");
        simpleMailMessage.setTo(customer.getEmail());
        simpleMailMessage.setSubject("SWIGGATO ORDER PLACED");
        simpleMailMessage.setText(msgForCustomer1);
        javaMailSender.send(simpleMailMessage);
        return orderResponse;

    }

    private void clearCart(Cart cart) {
        cart.setCartTotal(0);
        cart.setFoodItems(new ArrayList<>());
    }

    public List<OrderResponse> getAllOrders() {

        List<OrderEntity>orderEntityList=orderEntityRepo.findAll();
        List<OrderResponse>orderResponseList=new ArrayList<>();
        for(OrderEntity order:orderEntityList)
        {
            orderResponseList.add(OrderTransformer.OrderToOrderResponse(order));
        }
        return orderResponseList;
    }
}
