package com.example.home_service.repository;


import com.example.home_service.entity.Order;
import com.example.home_service.entity.Proposal;
import com.example.home_service.entity.enumaration.ProposalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ProposalRepository
        extends JpaRepository<Proposal, Long> {


    @Query(value = "select p from Proposal p where p.order.customer.id = :id and p.status = 'ACCEPTED'")
    Set<Proposal> findByCustomerId(@Param("id")Long id);
    @Query(value = "select p from Proposal p where p.expert.id = :id and p.status = 'ACCEPTED'")
    Set<Proposal> findByExpertId(@Param("id")Long id);


    Set<Proposal> findByOrderId(Long order_id);

    @Query(value = "select p from Proposal p where p.order = :order order by p.expert.point asc ")
    Set<Proposal> findByOrderOrderByExpertPoint(@Param("order") Order order);

    @Query(value = "select p from Proposal p where p.order = :order and p.status = 'ACCEPTED'")
   Optional<Proposal> findAcceptedByOrder(@Param("order") Order order);


}
