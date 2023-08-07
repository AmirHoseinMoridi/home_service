package com.example.home_service.service;


import com.example.home_service.dto.*;
import com.example.home_service.dto.result.ExpertResultDto;
import com.example.home_service.entity.enumaration.ExpertStatus;
import com.example.home_service.exception.ImageNotFoundException;

import java.util.Optional;
import java.util.Set;



public interface ExpertService {
    void signUp(ExpertRequestDto expertRequestDTO);

    void editPassword(EmailAndPasswordDto emailAndPassword, NewPasswordDto newPassword);
    void addingSubDutyRequest(EmailAndPasswordDto emailAndPassword, String subDutyName);
    void removingSubDutyRequest(EmailAndPasswordDto emailAndPassword, String subDutyName);

    Optional<WalletDto> findWallet(EmailAndPasswordDto emailAndPassword);

    Set<ExpertResultDto> findAll() throws ImageNotFoundException;

    Set<ExpertResultDto> findByStatus(ExpertStatus expertStatus);


    void acceptExpert(String expertEmail);


}
