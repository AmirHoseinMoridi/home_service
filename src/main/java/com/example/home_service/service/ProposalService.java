package com.example.home_service.service;

import com.example.home_service.dto.CreditCardDto;
import com.example.home_service.dto.ProposalDto;
import com.example.home_service.entity.Order;
import com.example.home_service.entity.Proposal;

import java.util.Set;

public interface ProposalService {
    void add(String email,String password, ProposalDto proposalDto);

  //  Set<Proposal> findByOrderOrderBySuggestedPriceByExpertDesc(Order order);


    Set<Proposal> findByOrderOrderByExpertPoint(Order order);

    void select(Long proposalId);

   Proposal findAcceptedByOrder(Order order);

    void paymentWithWallet(Long proposalId);

    void paymentWithCreditCard(CreditCardDto creditCardDto);
}
