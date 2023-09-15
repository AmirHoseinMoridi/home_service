package com.example.home_service.service;

import com.example.home_service.dto.CreditCardDto;
import com.example.home_service.dto.ProposalDto;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.Order;
import com.example.home_service.entity.Proposal;
import com.example.home_service.entity.enumaration.ProposalStatus;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProposalService {
    void add(String username, ProposalDto proposalDto);

  //  Set<Proposal> findByOrderOrderBySuggestedPriceByExpertDesc(Order order);

    Set<Proposal> findByOrderId(Long order_id);
    Set<Proposal> findByOrderOrderByExpertPoint(Order order);

    void select(Long proposalId);

   Proposal findAcceptedByOrder(Order order);

    void paymentWithWallet(Long proposalId);

    void paymentWithCreditCard(CreditCardDto creditCardDto);
    Set<Proposal> findByCustomerId(Long id);

    Set<Proposal> findByExpert(Long id);
}
