package com.example.home_service.controller;

import com.example.home_service.dto.EmailAndPasswordDto;
import com.example.home_service.dto.ExpertRequestDto;
import com.example.home_service.dto.NewPasswordDto;
import com.example.home_service.dto.Parrent.AddProposalDto;
import com.example.home_service.dto.Parrent.AddSbDutyDto;
import com.example.home_service.dto.WalletDto;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.Image;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final ServiceRegistry serviceRegistry;
    private final Mapper mapper;

    public ExpertController(@Validated @RequestBody ServiceRegistry serviceRegistry, Mapper mapper) {
        this.serviceRegistry = serviceRegistry;
        this.mapper = mapper;
    }

    @PostMapping("/signUp")
    public void signUp(@Validated @RequestBody ExpertRequestDto expertRequestDto){
        Expert expert = mapper.dtoToExpert(expertRequestDto);
        Image image = serviceRegistry.imageService().save(expertRequestDto.getImageDTO());
        serviceRegistry.expertService().signUp(expert,image);
    }

    @PutMapping("/editPassword")
    public void editPassword(@Validated @RequestBody NewPasswordDto newPasswordDto){
        serviceRegistry.expertService().editPassword(newPasswordDto.getEmail(),
                newPasswordDto.getPassword(),newPasswordDto.getNewPassword());
    }

    @GetMapping("/findWallet")
    public WalletDto findWallet(@Validated @RequestBody EmailAndPasswordDto request){
        return serviceRegistry.expertService()
                .findWallet(request.getEmail(), request.getPassword())
                .map(mapper::walletToDto)
                .orElse(new WalletDto());
    }

    @PostMapping("/add/subDuty")
    public void addSbDutyRequest(@Validated @RequestBody AddSbDutyDto request){
        serviceRegistry.expertService().addingSubDutyRequest(
                request.getEmail(),request.getPassword(),request.getSubDutyName()
        );
    }

    @PostMapping("/remove/subDuty")
    public void removeSbDutyRequest(@Validated @RequestBody AddSbDutyDto request){
        serviceRegistry.expertService().removingSubDutyRequest(
                request.getEmail(),request.getPassword(),request.getSubDutyName()
        );
    }

    @PostMapping("/add/proposal")
    public void addProposal(AddProposalDto addProposalDto){

        serviceRegistry.proposalService().add(addProposalDto.getEmail(),
                addProposalDto.getPassword(), addProposalDto.getProposalDto());
    }
}
