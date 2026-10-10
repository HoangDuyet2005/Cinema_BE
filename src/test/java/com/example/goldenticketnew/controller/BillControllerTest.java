package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.BillDetailDto;
import com.example.goldenticketnew.dtos.BillDto;
import com.example.goldenticketnew.dtos.BookingRequestDto;
import com.example.goldenticketnew.payload.UserProfile;
import com.example.goldenticketnew.security.UserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.model.Bill;
import com.example.goldenticketnew.model.User;
import com.example.goldenticketnew.service.bill.IBillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.goldenticketnew.security.CustomUserDetailsService;
import com.example.goldenticketnew.security.JwtAuthenticationEntryPoint;
import com.example.goldenticketnew.security.JwtTokenProvider;

@WebMvcTest(BillController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IBillService billService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JpaMetamodelMappingContext jpaMappingContext;

    private BillDto mockBillDto;
    private BookingRequestDto validBookingRequest;

    @BeforeEach
    void setUp() {
        // Tạo mock BillDto (cần Bill entity vì constructor yêu cầu)
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setUsername("testuser");

        Bill bill = new Bill();
        bill.setId(1);
        bill.setUser(user);
        bill.setStatus(BillStatus.SUCCESS);
        bill.setPrice(80000.0);
        bill.setCreatedTime(LocalDateTime.now());
        mockBillDto = new BillDto(bill);

        // Tạo request đặt vé hợp lệ
        validBookingRequest = new BookingRequestDto();
        validBookingRequest.setUserId(1L);
        validBookingRequest.setScheduleId(1);
        validBookingRequest.setListSeatIds(Arrays.asList(1, 2));
    }

    // Khách đăng nhập id=1 (controller cần UserPrincipal để kiểm tra quyền sở hữu hóa đơn). Test tắt filter
    // bảo mật (addFilters=false) nên gán thẳng SecurityContextHolder - MockMvc chạy cùng luồng với controller.
    private RequestPostProcessor asCustomer() {
        UserPrincipal principal = new UserPrincipal(1L, "Test User", "testuser", "test@example.com", "x", null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return request -> {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));
            return request;
        };
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createNewBill_whenValidRequest_shouldReturn200() throws Exception {
        // Given
        given(billService.bookingHandler(any(BookingRequestDto.class))).willReturn(mockBillDto);

        // When & Then
        mockMvc.perform(post("/api/bills/create-new-bill").with(asCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.status").value("SUCCESS"));
    }

    @Test
    void createNewBill_whenMissingUserId_shouldReturn400() throws Exception {
        // Given - Thiếu userId (null)
        validBookingRequest.setUserId(null);

        // When & Then
        mockMvc.perform(post("/api/bills/create-new-bill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookingRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewBill_whenMissingScheduleId_shouldReturn400() throws Exception {
        // Given - Thiếu scheduleId (null)
        validBookingRequest.setScheduleId(null);

        // When & Then
        mockMvc.perform(post("/api/bills/create-new-bill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookingRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewBill_whenMissingSeatIds_shouldReturn400() throws Exception {
        // Given - Danh sách ghế rỗng
        validBookingRequest.setListSeatIds(Arrays.asList());

        // When & Then
        mockMvc.perform(post("/api/bills/create-new-bill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookingRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewBill_whenBookingForAnotherUser_shouldBeDenied() throws Exception {
        // Khách id=1 không được đặt vé dưới tên user id=2
        validBookingRequest.setUserId(2L);

        mockMvc.perform(post("/api/bills/create-new-bill").with(asCustomer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookingRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getBillDetail_whenOwnBill_shouldReturn200() throws Exception {
        given(billService.getBillDetail(1)).willReturn(billDetailOwnedBy(1L));

        mockMvc.perform(get("/api/bills/1").with(asCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBillDetail_whenAnotherUsersBill_shouldBeDenied() throws Exception {
        given(billService.getBillDetail(1)).willReturn(billDetailOwnedBy(2L));

        mockMvc.perform(get("/api/bills/1").with(asCustomer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    private BillDetailDto billDetailOwnedBy(Long userId) {
        BillDetailDto detail = new BillDetailDto();
        detail.setId(1);
        detail.setUser(new UserProfile(userId, "owner", "owner@example.com", "Owner", null));
        return detail;
    }
}
