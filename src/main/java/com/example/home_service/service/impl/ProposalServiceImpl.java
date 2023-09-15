package com.example.home_service.service.impl;


import com.example.home_service.dto.CreditCardDto;
import com.example.home_service.dto.ProposalDto;
import com.example.home_service.entity.*;
import com.example.home_service.entity.enumaration.OrderStatus;
import com.example.home_service.entity.enumaration.ProposalStatus;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.exception.InsufficientInventoryException;
import com.example.home_service.exception.NotValidException;
import com.example.home_service.exception.OrderOutOfRange;
import com.example.home_service.repository.ProposalRepository;
import com.example.home_service.service.ProposalService;
import com.example.home_service.service.ServiceRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {
    private final ProposalRepository repository;
    private final ServiceRegistry serviceRegistry;

    @Transactional
    @Override
    public void add(String username, ProposalDto proposalDto) {

        Order order = serviceRegistry.orderService().findById(proposalDto.getOrderId());
        if (order.getSuggestedPriceByCustomer() >= proposalDto.getSuggestedPriceByExpert()) {
            throw new NotValidException("Suggested-Price-By-Expert must to be higher than Suggested-Price-By-Customer");
        }

        Expert expert = serviceRegistry.expertService().findByUsername(username)
                .orElseThrow(() -> new FieldNotFoundException("expert not found !"));

        Set<SubDuty> subDuties = serviceRegistry.subDutyService().findByExpert(expert);
        if (!subDuties.contains(order.getSubDuty())) {
            throw new OrderOutOfRange("this order is not in expert's duty range !");
        }
        order.setStatus(OrderStatus.WAITING_FOR_EXPERT_SELECTION);
        serviceRegistry.orderService().update(order);
        Proposal proposal = Proposal.builder()
                .dateOfCreate(LocalDate.now())
                .suggestedPriceByExpert(proposalDto.getSuggestedPriceByExpert())
                .suggestedDate(proposalDto.getSuggestedDate())
                .durationOfWork(proposalDto.getDurationOfWork())
                .status(ProposalStatus.WAITING)
                .order(order)
                .expert(expert)
                .build();
        repository.save(proposal);

    }

    @Override
    public Set<Proposal> findByOrderId(Long order_id) {
        return repository.findByOrderId(order_id);
    }


    @Transactional
    @Override
    public void select(Long proposalId) {

        Proposal proposal = findById(proposalId);
        if (!proposal.getOrder().getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SELECTION)) {
            throw new FieldNotFoundException("this order can not select any proposal !");
        }
        proposal.setStatus(ProposalStatus.ACCEPTED);
        Order order = proposal.getOrder();
        order.setStatus(OrderStatus.WAITING_FOR_THE_EXPERT_TO_COME);
        serviceRegistry.orderService().update(order);
        repository.save(proposal);
    }

    @Override
    public Proposal findAcceptedByOrder(Order order) {
        return repository.findAcceptedByOrder(order).orElseThrow(
                () -> new FieldNotFoundException("accepted proposal is not exists for this order !")
        );
    }

    @Override
    public void paymentWithWallet(Long proposalId) {
        Proposal proposal = findById(proposalId);
        Wallet customerWallet = proposal.getOrder().getCustomer().getWallet();
        Wallet expertWallet = proposal.getExpert().getWallet();
        Double balance = customerWallet.getBalance();
        if (balance < proposal.getSuggestedPriceByExpert()) {
            throw new InsufficientInventoryException("Insufficient is inventory");
        }
        proposal.getOrder().getCustomer().getWallet().setBalance(
                balance - proposal.getSuggestedPriceByExpert()
        );

        double add = proposal.getSuggestedPriceByExpert() * 0.7;
        Double expertWalletBalance = expertWallet.getBalance();
        expertWallet.setBalance(expertWalletBalance + add);
        serviceRegistry.walletService().update(customerWallet);
        serviceRegistry.walletService().update(expertWallet);

    }

    @Override
    public void paymentWithCreditCard(CreditCardDto creditCardDto) {
        Proposal proposal = findById(creditCardDto.getProposalId());
        Wallet wallet = proposal.getExpert().getWallet();
        Double balance = wallet.getBalance();
        wallet.setBalance(balance + proposal.getSuggestedPriceByExpert());
        serviceRegistry.walletService().update(wallet);
    }

    @Override
    public Set<Proposal> findByCustomerId(Long id) {
        return repository.findByCustomerId(id);
    }

    @Override
    public Set<Proposal> findByExpert(Long id) {
        return repository.findByExpertId(id);
    }

    private Proposal findById(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new FieldNotFoundException("proposal is not exists !")
        );
    }
}
