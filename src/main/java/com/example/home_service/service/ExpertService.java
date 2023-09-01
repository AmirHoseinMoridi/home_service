package com.example.home_service.service;

import com.example.home_service.dto.result.ExpertResultDto;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.Image;
import com.example.home_service.entity.Wallet;
import com.example.home_service.entity.enumaration.ExpertStatus;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.exception.ImageNotFoundException;
import com.example.home_service.exception.WrongPasswordException;

import java.util.Optional;
import java.util.Set;



public interface ExpertService {
    void signUp(Expert expert, Image image);

    void editPassword(String email,String oldPassword, String newPassword);
    void addingSubDutyRequest(String email,String password, String subDutyName);
    void removingSubDutyRequest(String email,String password, String subDutyName);

    Optional<Wallet> findWallet(String email, String password);

    Set<Expert> findAll() throws ImageNotFoundException;

  //  Set<ExpertResultDto> findByStatus(ExpertStatus expertStatus);


    void subtractPoint(Expert expert,int subtract);

    void acceptExpert(String expertEmail);


    Expert findByEmailAndPassword(String email,String Password) throws FieldNotFoundException, WrongPasswordException;

    Long count();




}
