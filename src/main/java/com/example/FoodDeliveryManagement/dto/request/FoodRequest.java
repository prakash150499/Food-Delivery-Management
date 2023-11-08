package com.example.FoodDeliveryManagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequest {

    int requiredQuantity;

    String customerMobile;

    int menuItemId;
}
