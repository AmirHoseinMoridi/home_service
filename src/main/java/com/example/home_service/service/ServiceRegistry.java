package com.example.home_service.service;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ServiceRegistry {

    DutyService dutyService;
    ImageService imageService;
    OrderService orderService;
    SubDutyService subDutyService;
    WalletService walletService;
    ExpertService expertService;
    CustomerService customerService;
    CommentService commentService;
    ProposalService proposalService;
    AssignmentRequestsService assignmentRequestsService;

    @Autowired
    @Lazy
    public ServiceRegistry(DutyService dutyService, ExpertService expertService,
                           ImageService imageService, OrderService orderService,
                           SubDutyService subDutyService, WalletService walletService,
                           CustomerService customerService, CommentService commentService, ProposalService proposalService,
                           AssignmentRequestsService assignmentRequestsService) {
        this.expertService = expertService;
        this.dutyService = dutyService;
        this.imageService = imageService;
        this.orderService = orderService;
        this.subDutyService = subDutyService;
        this.walletService = walletService;
        this.customerService = customerService;
        this.commentService = commentService;
        this.proposalService = proposalService;
        this.assignmentRequestsService = assignmentRequestsService;
    }



    public DutyService dutyService() {
        return dutyService;
    }

    public ImageService imageService() {
        return imageService;
    }

    public OrderService orderService() {
        return orderService;
    }

    public SubDutyService subDutyService() {
        return subDutyService;
    }

    public WalletService walletService() {
        return walletService;
    }

    public ExpertService expertService(){
        return expertService;
    }

    public CustomerService customerService(){
        return customerService;
    }

    public CommentService commentService() {
        return commentService;
    }

    public ProposalService proposalService() {
        return proposalService;
    }

    public AssignmentRequestsService assignmentRequestsService() {
        return assignmentRequestsService;
    }
}
