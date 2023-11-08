package com.example.FoodDeliveryManagement.service;

import com.example.FoodDeliveryManagement.dto.request.FoodRequest;
import com.example.FoodDeliveryManagement.dto.response.CartStatusResponse;
import com.example.FoodDeliveryManagement.dto.response.FoodResponse;
import com.example.FoodDeliveryManagement.exception.CustomerNotFoundException;
import com.example.FoodDeliveryManagement.exception.MenuItemNotFoundException;
import com.example.FoodDeliveryManagement.model.*;
import com.example.FoodDeliveryManagement.repository.CartRepository;
import com.example.FoodDeliveryManagement.repository.CustomerRepository;
import com.example.FoodDeliveryManagement.repository.FoodRepo;
import com.example.FoodDeliveryManagement.repository.MenuRepository;
import com.example.FoodDeliveryManagement.transformer.FoodTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    final CustomerRepository customerRepository;
    final MenuRepository menuRepository;
    final CartRepository cartRepository;
    final FoodRepo foodRepo;

    @Autowired
    public CartService(CustomerRepository customerRepository,
                       MenuRepository menuRepository,
                       CartRepository cartRepository,
                       FoodRepo foodRepo) {
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
        this.cartRepository = cartRepository;
        this.foodRepo = foodRepo;
    }

    public CartStatusResponse addFoodItemToCart(FoodRequest foodRequest) {

        Customer customer = customerRepository.findByMobileNo(foodRequest.getCustomerMobile());
        if(customer==null){
            throw new CustomerNotFoundException("Customer doesn't exisit");
        }

        Optional<MenuItem> menuItemOptional = menuRepository.findById(foodRequest.getMenuItemId());
        if(menuItemOptional.isEmpty()){
            throw new MenuItemNotFoundException("Item not available in the restaurant!!!");
        }

        MenuItem menuItem = menuItemOptional.get();
        if(!menuItem.getRestaurant().isOpened() || !menuItem.isAvailable()) {
            throw new MenuItemNotFoundException("Given dish is out of stock for now!!!");
        }

        Cart cart = customer.getCart();

        // having item from same restaurant
        if(cart.getFoodItems().size()!=0){
          Restaurant currRestaurant = cart.getFoodItems().get(0).getMenuItem().getRestaurant();
          Restaurant newRestaurant = menuItem.getRestaurant();

        if(!currRestaurant.equals(newRestaurant)){
            List<FoodItem> foodItems = cart.getFoodItems();
            for(FoodItem foodItem: foodItems) {
                foodItem.setCart(null);
                foodItem.setOrder(null);
                foodItem.setMenuItem(null);
            }
            cart.setCartTotal(0);
            cart.getFoodItems().clear();
            foodRepo.deleteAll(foodItems);
          }
        }

        boolean alreadyExists = false;
        FoodItem savedFoodItem = null;
        if(cart.getFoodItems().size()!=0){
            for(FoodItem foodItem: cart.getFoodItems()){
                if(foodItem.getMenuItem().getId()==menuItem.getId()){
                    savedFoodItem = foodItem;
                    int curr = foodItem.getRequiredQuantity();
                    foodItem.setRequiredQuantity(curr+foodRequest.getRequiredQuantity());
                    alreadyExists = true;
                    break;
                }
            }
        }

        if(!alreadyExists){
          FoodItem foodItem = FoodItem.builder()
                  .cart(cart)
                .menuItem(menuItem)
                .requiredQuantity(foodRequest.getRequiredQuantity())
                .totalCost(foodRequest.getRequiredQuantity()*menuItem.getPrice())
                .build();

           savedFoodItem = foodRepo.save(foodItem);
           cart.getFoodItems().add(savedFoodItem);
           menuItem.getFoodItems().add(savedFoodItem);
           savedFoodItem.setCart(cart);
        }

        double cartTotal = 0;
        for(FoodItem food: cart.getFoodItems()){
            cartTotal += food.getRequiredQuantity()*food.getMenuItem().getPrice();
        }

        cart.setCartTotal(cartTotal);
        // save
        Cart savedCart = cartRepository.save(cart);
        MenuItem savedMenuItem = menuRepository.save(menuItem);

        // prepare
        List<FoodResponse> foodResponseList = new ArrayList<>();
        for(FoodItem food: cart.getFoodItems()){
            foodResponseList.add(FoodTransformer.FoodToFoodResponse(food));
        }

        return CartStatusResponse.builder()
                .customerName(savedCart.getCustomer().getName())
                .customerMobile(savedCart.getCustomer().getMobileNo())
                .customerAddress(savedCart.getCustomer().getAddress())
                .foodList(foodResponseList)
                .restaurantName(savedMenuItem.getRestaurant().getName())
                .cartTotal(cartTotal)
                .build();

    }
}
