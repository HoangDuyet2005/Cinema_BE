package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.BillDto;
import com.example.goldenticketnew.dtos.BookingRequestDto;
import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.model.Bill;
import com.example.goldenticketnew.model.User;
import com.example.goldenticketnew.service.bill.IBillService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Test
    void createNewBill_whenValidRequest_shouldReturn200() throws Exception {
        // Given
        given(billService.bookingHandler(any(BookingRequestDto.class))).willReturn(mockBillDto);

        // When & Then
        mockMvc.perform(post("/api/bills/create-new-bill")
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
    void getBillDetail_whenValidId_shouldReturn200() throws Exception {
        // Given
        // BillDetailDto constructor is complex, so we just verify the endpoint responds
        given(billService.getBillDetail(1)).willReturn(null);

        // When & Then
        mockMvc.perform(get("/api/bills/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
