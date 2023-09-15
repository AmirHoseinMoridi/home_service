package com.example.home_service.service;

import com.example.home_service.base.service.BaseUserService;
import com.example.home_service.dto.UserSearchDto;
import com.example.home_service.entity.Expert;
import com.example.home_service.entity.Image;

import java.util.List;

public interface ExpertService extends BaseUserService<Expert> {
    void signUp(Expert expert, Image image);

    void editPassword(String username,String newPassword);
    void addingSubDutyRequest(String username, String subDutyName);

    void removingSubDutyRequest(String username, String subDutyName);

    void subtractPoint(Expert expert, int subtract);

    void addPoint(Expert expert, int point);

    void acceptExpert(Long expertId);

    Double showPoint(Long expertId);

}
