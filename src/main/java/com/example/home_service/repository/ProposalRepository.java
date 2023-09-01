package com.example.home_service.repository;


import com.example.home_service.entity.Expert;
import com.example.home_service.entity.Order;
import com.example.home_service.entity.Proposal;
import com.example.home_service.entity.enumaration.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    Set<Proposal> findByDateOfCreate(LocalDate dateOFCreate);

    Set<Proposal> findByStatus(ProposalStatus status);

    //Set<Proposal> findByOrderBySuggestedPriceByExpertDesc(Order order);

    @Query(value = "select p from Proposal p where p.order = :order order by p.expert.point asc ")
    Set<Proposal> findByOrderOrderByExpertPoint(@Param("order") Order order);

    Set<Proposal> findByExpert(Expert expert);

    @Query(value = "select p from Proposal p where p.order = :order and p.status = 'ACCEPTED'")
   Optional<Proposal> findAcceptedByOrder(@Param("order") Order order);


}
