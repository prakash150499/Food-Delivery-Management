package com.example.FoodDeliveryManagement.service;

import com.example.FoodDeliveryManagement.dto.request.DeliveryPartnerRequest;
import com.example.FoodDeliveryManagement.exception.CustomerNotFoundException;
import com.example.FoodDeliveryManagement.exception.DeliveryPartnerNotFoundException;
import com.example.FoodDeliveryManagement.model.DeliveryPartner;
import com.example.FoodDeliveryManagement.repository.DeliveryPartnerRepo;
import com.example.FoodDeliveryManagement.transformer.PartnerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryPartnerService {

    final DeliveryPartnerRepo deliveryPartnerRepo;

    @Autowired
    public DeliveryPartnerService(DeliveryPartnerRepo deliveryPartnerRepo) {
        this.deliveryPartnerRepo = deliveryPartnerRepo;
    }

    public String addPartner(DeliveryPartnerRequest deliveryPartnerRequest) {

        //dto -> entity
        DeliveryPartner deliveryPartner = PartnerTransformer.PartnerRequestToDeliveryPartner(deliveryPartnerRequest);

        // save
        DeliveryPartner savedPartner = deliveryPartnerRepo.save(deliveryPartner);

        return "You have been successfully regsitered!!!";

    }

    public List<Integer> getDeliveryPartnerWithMaximumOrders() {
        List<DeliveryPartner> deliveryPartnerList=deliveryPartnerRepo.findAll();

        DeliveryPartner deliveryPartner=null;
        List<Integer>deliverPartnerIDs=new ArrayList<>();
        int maximumOrders=0;
        for(DeliveryPartner deliveryPartner1:deliveryPartnerList)
        {
            if(deliveryPartner1.getOrders().size()>maximumOrders)
            {
                maximumOrders=deliveryPartner1.getOrders().size();
            }
        }
        for(DeliveryPartner deliveryPartner1:deliveryPartnerList)
        {
            if(deliveryPartner1.getOrders().size()==maximumOrders)
            {
                deliverPartnerIDs.add(deliveryPartner1.getId());
            }
        }
        return deliverPartnerIDs;

    }

    public void deleteDeliveryPartner(int id) {
       DeliveryPartner deliveryPartner=deliveryPartnerRepo.findById(id).get();
        if(deliveryPartner==null){
            throw new DeliveryPartnerNotFoundException("Invalid mobile no!!!");
        }
        deliveryPartnerRepo.deleteById(id);
    }
}
