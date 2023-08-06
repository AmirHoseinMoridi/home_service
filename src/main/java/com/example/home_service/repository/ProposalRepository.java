package com.example.home_service.repository;


import com.example.home_service.entity.Expert;
import com.example.home_service.entity.Order;
import com.example.home_service.entity.Proposal;
import com.example.home_service.entity.enumaration.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Set;

public interface ProposalRepository extends JpaRepository<Proposal,Long> {

    Set<Proposal> findByDateOFCreate(LocalDate dateOFCreate);

    Set<Proposal> findByStatus (ProposalStatus status);

    Set<Proposal> findByOrder(Order order);

    Set<Proposal> findByExpert(Expert expert);

}
