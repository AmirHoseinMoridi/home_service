/*
package com.example.home_service;

import com.example.home_service.dto.DutyDto;
import com.example.home_service.dto.SubDutyDto;
import com.example.home_service.entity.Duty;
import com.example.home_service.entity.SubDuty;
import com.example.home_service.service.ServiceRegistry;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManagerServicesTest {

    private final ServiceRegistry serviceRegistry;

    @Autowired
    public ManagerServicesTest(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Test
    @Order(value = 1)
    void createDutyTest() {
        DutyDto dutyDto = new DutyDto("duty name");
        Duty duty = serviceRegistry.dutyService().create(dutyDto);
        assertThat(duty.getId()).isNotNull();
    }

    @Test
    @Order(value = 2)
    void createRepetitiousDutyTest() {
        DutyDto dutyDto = new DutyDto("duty name");
        Duty duty = serviceRegistry.dutyService().create(dutyDto);
        assertThat(duty.getId()).isNull();
    }

    @Test
    @Order(value = 3)
    void createSubDuty() {
        SubDutyDto subDutyDto = new SubDutyDto("sub duty name",5D,"some description");
        DutyDto dutyDto = new DutyDto("duty name");
        SubDuty subDuty = serviceRegistry.subDutyService().create(subDutyDto, dutyDto);
        assertThat(subDuty.getId()).isNotNull();
    }

    @Test
    @Order(value = 4)
    void createRepetitiousSubDutyTest() {
        SubDutyDto subDutyDto = new SubDutyDto("sub duty name",5D,"some description");
        DutyDto dutyDto = new DutyDto("duty name");
        SubDuty subDuty = serviceRegistry.subDutyService().create(subDutyDto, dutyDto);
        assertThat(subDuty.getId()).isNull();
    }



    @Test
    @Order(value = 5)
    void removeDutyTest() {
        DutyDto dutyDto = new DutyDto("duty name two");
        serviceRegistry.dutyService().create(dutyDto);
        Long before = serviceRegistry.dutyService().count();
        serviceRegistry.dutyService().remove(dutyDto);
        Long after = serviceRegistry.dutyService().count();
        assertThat(before).isGreaterThan(after);
    }


    @Test
    @Order(value = 6)
    void editSubDutyPriceTest() {
        SubDutyDto subDutyDto = new SubDutyDto("sub duty name",10D,"some description");
        SubDuty subDuty = serviceRegistry.subDutyService().update(subDutyDto);
        assertThat(subDuty.getPrice()).isEqualTo(subDutyDto.getPrice());
    }
    @Test
    @Order(value = 6)
    void editSubDutyDescriptionTest() {
        SubDutyDto subDutyDto = new SubDutyDto("sub duty name",10D,"another description");
        SubDuty subDuty = serviceRegistry.subDutyService().update(subDutyDto);
        assertThat(subDuty.getDescription()).isEqualTo(subDutyDto.getDescription());
    }

}
*/
