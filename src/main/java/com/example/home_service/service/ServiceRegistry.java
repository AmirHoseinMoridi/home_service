package com.example.home_service.service;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ServiceRegistry {

    AddressService addressService;
    DutyService dutyService;
    ImageService imageService;
    OrderService orderService;
    SubDutyService subDutyService;
    WalletService walletService;
    CustomerService customerService;
    ExpertService expertService;
    CommentService commentService;
    AssignmentRequestsService assignmentRequestsService;

    @Autowired
    public ServiceRegistry(@Lazy AddressService addressService,@Lazy DutyService dutyService,
                           @Lazy ImageService imageService,@Lazy OrderService orderService,
                           @Lazy SubDutyService subDutyService,@Lazy WalletService walletService,
                           @Lazy CustomerService customerService,@Lazy ExpertService expertService,
                           @Lazy CommentService commentService,@Lazy AssignmentRequestsService assignmentRequestsService) {
        this.addressService = addressService;
        this.dutyService = dutyService;
        this.imageService = imageService;
        this.orderService = orderService;
        this.subDutyService = subDutyService;
        this.walletService = walletService;
        this.customerService = customerService;
        this.expertService = expertService;
        this.commentService = commentService;
        this.assignmentRequestsService = assignmentRequestsService;
    }

    public  AddressService addressService() {
        return addressService;
    }

    public  DutyService dutyService() {
        return dutyService;
    }

    public  ImageService imageService() {
        return imageService;
    }

    public  OrderService orderService() {
        return orderService;
    }

    public  SubDutyService subDutyService() {
        return subDutyService;
    }

    public  WalletService walletService() {
        return walletService;
    }

    public  CustomerService customerService() {
        return customerService;
    }

    public  ExpertService expertService() {
        return expertService;
    }

    public  CommentService commentService() {
        return commentService;
    }

    public  AssignmentRequestsService assignmentRequestsService() {
        return assignmentRequestsService;
    }
}
