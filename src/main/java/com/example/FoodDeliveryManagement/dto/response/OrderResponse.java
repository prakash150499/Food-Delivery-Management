package com.example.FoodDeliveryManagement.dto.response;

import com.example.FoodDeliveryManagement.model.Customer;
import com.example.FoodDeliveryManagement.model.DeliveryPartner;
import com.example.FoodDeliveryManagement.model.FoodItem;
import com.example.FoodDeliveryManagement.model.Restaurant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    String orderId;  // UUID

    double orderTotal;

    Date orderTime;

    String customerName;

    String customerMobile;

    String deliveryPartnerName;

    String deliveryPartnerMobile;

    String restaurantName;

    List<FoodResponse> foodResponses;
}
