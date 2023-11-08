package com.example.FoodDeliveryManagement.dto.request;

import com.example.FoodDeliveryManagement.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerRequest {

    String name;

    String mobileNo;

    Gender gender;
}
