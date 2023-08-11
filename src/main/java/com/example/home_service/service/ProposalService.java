package com.example.home_service.service;

import com.example.home_service.dto.EmailAndPasswordDto;
import com.example.home_service.dto.ProposalDto;
import com.example.home_service.entity.Order;
import com.example.home_service.entity.Proposal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProposalService {
    Proposal add(EmailAndPasswordDto emailAndPasswordDto, ProposalDto proposalDto);

    Set<Proposal> findByOrderOrderBySuggestedPriceByExpertDesc(Order order);


    Set<Proposal> findByOrderOrderByExpertPoint(Order order);

    Proposal select(Long ProposalId);

}
