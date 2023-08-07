package com.example.home_service.service;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceRegistry {
    static AddressService addressService;
    static DutyService dutyService;
    static ImageService imageService;
    static OrderService orderService;
    static SubDutyService subDutyService;
    static WalletService walletService;
    static CustomerService customerService;
    static ExpertService expertService;
    static CommentService commentService;
    static AssignmentRequestsService assignmentRequestsService;




    public static AddressService addressService() {
        return addressService;
    }

    public static DutyService dutyService() {
        return dutyService;
    }

    public static ImageService imageService() {
        return imageService;
    }

    public static OrderService orderService() {
        return orderService;
    }

    public static SubDutyService subDutyService() {
        return subDutyService;
    }

    public static WalletService walletService() {
        return walletService;
    }

    public static CustomerService customerService() {
        return customerService;
    }

    public static ExpertService expertService() {
        return expertService;
    }

    public static CommentService commentService() {
        return commentService;
    }

    public static AssignmentRequestsService assignmentRequestsService() {
        return assignmentRequestsService;
    }
}
