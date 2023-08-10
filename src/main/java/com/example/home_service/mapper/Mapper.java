package com.example.home_service.mapper;

import com.example.home_service.dto.*;
import com.example.home_service.dto.result.ExpertResultDto;
import com.example.home_service.dto.result.CustomerResultDto;
import com.example.home_service.entity.*;
import lombok.Getter;
import org.mapstruct.factory.Mappers;


@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    Mapper MAPPER = Mappers.getMapper(Mapper.class);

    Address dtoToAddress(AddressDto addressDto);
    AddressDto addressToDto(Address address);
    Comment dtoToComment(CommentDto commentDto);
    CommentDto commentToDto(Comment comment);
    Duty dtoToDuty(DutyDto dutyDto);
    DutyDto dutyToDto(Duty duty);
    SubDutyDto subDutyToDto(SubDuty subDuty);
    SubDuty dtoToSubDuty(SubDutyDto subDutyDto);
    Expert dtoToExpert(ExpertRequestDto expertRequestDto);
    ExpertResultDto expertToDto(Expert expert);
    OrderDto orderToDto(Order order);
    Order dtoToOrder(OrderDto orderDto);
    CustomerResultDto customerToDto(Customer customer);
    Customer dtoToCustomer(CustomerRequestDto customerRequestDto);
    ProposalDto ProposalToDto(Proposal proposal);
    Proposal dtoToProposal(ProposalDto proposalDto);
    Wallet dtoToWallet(WalletDto walletDto);
    WalletDto walletToDto(Wallet wallet);

}
