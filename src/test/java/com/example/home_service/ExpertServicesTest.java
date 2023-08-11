package com.example.home_service;

import com.example.home_service.dto.*;
import com.example.home_service.entity.*;
import com.example.home_service.entity.enumaration.ExpertStatus;
import com.example.home_service.entity.enumaration.OrderStatus;
import com.example.home_service.service.ServiceRegistry;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpertServicesTest {

    private final ServiceRegistry serviceRegistry;

    @Autowired
    public ExpertServicesTest(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }


    private final AddressDto addressDto = new AddressDto("a", "b", "c", "d");
    private final ImageDto imageDto = new ImageDto("F:/im.jpg");
    private final ExpertRequestDto expertRequestDto = new ExpertRequestDto("firs naem", "last name",
            "a@email.com", "1234abcd", addressDto, imageDto);

    private final DutyDto dutyDto = new DutyDto("duty");
    private final SubDutyDto subDutyDto = new SubDutyDto("sub duty", 5D, "something");

    private final CustomerRequestDto customerRequestDto = new CustomerRequestDto("firs naem", "last name",
            "b@email.com", "1234abcd", addressDto);

    @Test
    @Order(value = 1)
    void signUpWhitNotValidEmailTest() {

        ExpertRequestDto notValid = this.expertRequestDto;
        notValid.setEmail("asdfa.sdf");

        Expert expert = serviceRegistry.expertService().signUp(notValid);
        assertThat(expert.getId()).isNull();
    }

    @Test
    @Order(value = 1)
    void signUpWhitNotValidPasswordTest() {
        ExpertRequestDto notValid = this.expertRequestDto;
        notValid.setPassword("asdfa.sdf");

        Expert expert = serviceRegistry.expertService().signUp(notValid);
        assertThat(expert.getId()).isNull();
    }

    @Test
    @Order(value = 1)
    void signUpWhitNotExistsImageTest() {
        ExpertRequestDto notValid = this.expertRequestDto;
        notValid.setImageDTO(new ImageDto("F:/im2.jpg"));

        Expert expert = serviceRegistry.expertService().signUp(notValid);
        assertThat(expert.getId()).isNull();
    }

    @Test
    @Order(value = 1)
    void signUpWhitNotValidImageSizeTest() {
        ExpertRequestDto notValid = this.expertRequestDto;
        notValid.setImageDTO(new ImageDto("F:/g.jpg"));

        Expert expert = serviceRegistry.expertService().signUp(notValid);
        assertThat(expert.getId()).isNull();
    }

    @Test
    @Order(value = 1)
    void signUpWhitNotValidImageFileTest() {
        ExpertRequestDto notValid = this.expertRequestDto;
        notValid.setImageDTO(new ImageDto("F:/t.txt"));

        Expert expert = serviceRegistry.expertService().signUp(notValid);
        assertThat(expert.getId()).isNull();
    }

    @Test
    @Order(value = 1)
    void signUpTest() {
        Expert expert = serviceRegistry.expertService().signUp(expertRequestDto);
        assertThat(expert.getId()).isNotNull();
    }


    @Test
    @Order(value = 2)
    void acceptExpert() {
        Expert expert = serviceRegistry.expertService().acceptExpert(expertRequestDto.getEmail());
        assertThat(expert.getStatus()).isEqualTo(ExpertStatus.ACCEPTED);
    }

    @Test
    @Order(value = 3)
    void addingSubDutyRequest() {
        EmailAndPasswordDto emailAndPasswordDto = new EmailAndPasswordDto(
                expertRequestDto.getEmail(), expertRequestDto.getPassword());

        serviceRegistry.dutyService().create(dutyDto);
        serviceRegistry.subDutyService().create(subDutyDto, dutyDto);
        Long before = serviceRegistry.assignmentRequestsService().count();
        serviceRegistry.expertService().addingSubDutyRequest(emailAndPasswordDto, subDutyDto.getName());
        Long after = serviceRegistry.assignmentRequestsService().count();
        assertThat(before).isLessThan(after);
    }

    @Test
    @Order(value = 4)
    void addExpertToSubDuty() {
        Optional<AssignmentRequests> optional = serviceRegistry.assignmentRequestsService().findById(1L);
        AssignmentRequests request = optional.get();
        boolean b = serviceRegistry.subDutyService().addExpertToSubDuty(1L);
        assertThat(b).isTrue();
    }

    @Test
    @Order(value = 5)
    void removingSubDutyRequest() {
        EmailAndPasswordDto emailAndPasswordDto = new EmailAndPasswordDto(
                expertRequestDto.getEmail(), expertRequestDto.getPassword());

        Long before = serviceRegistry.assignmentRequestsService().count();
        serviceRegistry.expertService().removingSubDutyRequest(emailAndPasswordDto, subDutyDto.getName());
        Long after = serviceRegistry.assignmentRequestsService().count();
        boolean b = before <= after;
        assertThat(b).isTrue();
    }

    @Test
    @Order(value = 6)
    void removeExpertFromSubDuty() {
        Optional<AssignmentRequests> optional = serviceRegistry.assignmentRequestsService().findById(2L);
        AssignmentRequests request = optional.get();
        boolean b = serviceRegistry.subDutyService().removeExpertFromSubDuty(2L);
        assertThat(b).isTrue();
    }


/*
    @Test
    @Order(value = 7)
    void editPasswordTest() {

        NewPasswordDto newPasswordDto = new NewPasswordDto("1111aaaa");
        EmailAndPasswordDto emailAndPasswordDto = new EmailAndPasswordDto(
                expertRequestDto.getEmail(), expertRequestDto.getPassword());

        Expert edited = serviceRegistry.expertService().editPassword(emailAndPasswordDto, newPasswordDto);
        assertThat(edited.getPassword()).isEqualTo(newPasswordDto.getPassword());
    }
*/

    @Test
    @Order(value = 7)
    void signup() {
        Customer customer = serviceRegistry.customerService().signUp(customerRequestDto);
        assertThat(customer.getId()).isNotNull();
    }
    @Test
    @Order(value = 8)
    void addOrder(){
        OrderDto orderDto = new OrderDto(customerRequestDto.getEmail(),subDutyDto.getName(),"ad",
                20D, LocalDate.now().plusDays(10),addressDto);

        com.example.home_service.entity.Order order = serviceRegistry.orderService().addOrder(orderDto);
        assertThat(order.getId()).isNotNull();
    }


    @Test
    @Order(value = 9)
    void addProposalWhitNotValidPrice(){
        EmailAndPasswordDto emailAndPasswordDto = new EmailAndPasswordDto(
                expertRequestDto.getEmail(), expertRequestDto.getPassword());
        ProposalDto proposalDto = new ProposalDto(5D, LocalDate.now().plusDays(40)
                , LocalTime.MIN,1L);
        Proposal add = serviceRegistry.proposalService().add(emailAndPasswordDto, proposalDto);
        assertThat(add.getId()).isNull();
    }

    @Test
    @Order(value = 9)
    void addProposalWhitNotValidDate(){
        EmailAndPasswordDto emailAndPasswordDto = new EmailAndPasswordDto(
                expertRequestDto.getEmail(), expertRequestDto.getPassword());
        ProposalDto proposalDto = new ProposalDto(50D, LocalDate.now().plusDays(2)
                , LocalTime.MIN,1L);
        Proposal add = serviceRegistry.proposalService().add(emailAndPasswordDto, proposalDto);
        assertThat(add.getId()).isNull();
    }

    @Test
    @Order(value = 10)
    void start(){
        com.example.home_service.entity.Order start = serviceRegistry.orderService().start(1L);
        assertThat(start.getStatus()).isEqualTo(OrderStatus.STARTED);
    }

    @Test
    @Order(value = 11)
    void done(){
        com.example.home_service.entity.Order start = serviceRegistry.orderService().done(1L);
        assertThat(start.getStatus()).isEqualTo(OrderStatus.DONE);
    }
}
