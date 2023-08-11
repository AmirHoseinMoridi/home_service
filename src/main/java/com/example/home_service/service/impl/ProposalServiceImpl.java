package com.example.home_service.service.impl;

import com.example.home_service.dto.EmailAndPasswordDto;
import com.example.home_service.dto.ProposalDto;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.Order;
import com.example.home_service.entity.Proposal;
import com.example.home_service.entity.SubDuty;
import com.example.home_service.entity.enumaration.OrderStatus;
import com.example.home_service.entity.enumaration.ProposalStatus;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.exception.NotValidException;
import com.example.home_service.exception.OrderOutOfRange;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.repository.ProposalRepository;
import com.example.home_service.service.ProposalService;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.util.Checker;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
@Component
public class ProposalServiceImpl implements ProposalService {
    private final ProposalRepository repository;
    private final ServiceRegistry serviceRegistry;
    private final Mapper mapper;

    public ProposalServiceImpl(ProposalRepository repository, ServiceRegistry serviceRegistry, Mapper mapper) {
        this.repository = repository;
        this.serviceRegistry = serviceRegistry;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Proposal add(EmailAndPasswordDto emailAndPasswordDto, ProposalDto proposalDto) {
        try {
            Checker.checkValidation(emailAndPasswordDto);
            Checker.checkValidation(proposalDto);

            Optional<Order> orderOptional = serviceRegistry.orderService()
                    .findById(proposalDto.getOrderId());
            if (orderOptional.isEmpty()) {
                throw new FieldNotFoundException("this order is not exists !");
            }
            Order order = orderOptional.get();
            Expert expert = serviceRegistry.expertService().findByEmailAndPassword(emailAndPasswordDto);
            Checker.checkExpertsAccess(expert);
            Set<SubDuty> subDuties = serviceRegistry.subDutyService().findByExpert(expert);
            if (!subDuties.contains(order.getSubDuty())) {
                throw new OrderOutOfRange("this order is not in expert's duty range !");
            }

            if (order.getSuggestedPriceByCustomer() > proposalDto.getSuggestedPriceByExpert()) {
                throw new NotValidException("Suggested-Price-By-Expert must to be higher than Suggested-Price-By-Customer");
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
            return repository.save(proposal);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new Proposal();
        }
    }

    @Override
    public Set<Proposal> findByOrderOrderBySuggestedPriceByExpertDesc(Order order) {
        return repository.findByOrderOrderBySuggestedPriceByExpertDesc(order);
    }

    @Override
    public Set<Proposal> findByOrderOrderByExpertPoint(Order order) {
        return repository.findByOrderOrderByExpertPoint(order);
    }

    @Transactional
    @Override
    public Proposal select(Long proposalId) {
        Optional<Proposal> optional = repository.findById(proposalId);
        if (optional.isEmpty()){
            throw new FieldNotFoundException("proposal is not exists !");
        }
        Proposal proposal = optional.get();
        proposal.setStatus(ProposalStatus.ACCEPTED);
        Order order = proposal.getOrder();
        order.setStatus(OrderStatus.WAITING_FOR_THE_EXPERT_TO_COME);
        serviceRegistry.orderService().update(order);
        return repository.save(proposal);
    }

}
