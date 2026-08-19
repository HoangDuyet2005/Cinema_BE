package com.example.goldenticketnew.workflow.activities;

import com.example.goldenticketnew.dtos.BookingRequestDto;
import com.example.goldenticketnew.dtos.DeleteBillTicketRequest;
import com.example.goldenticketnew.service.bill.IBillService;
import com.example.goldenticketnew.utils.BeanUtils;
import com.example.goldenticketnew.workflow.activities.interfaces.IBookingTicketActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableAsync
@Slf4j
public class BookingTicketActivity implements IBookingTicketActivity {
    @Override
    public void composeBooking(BookingRequestDto request) {
        try {
            IBillService billService = BeanUtils.getBean(IBillService.class);
            DeleteBillTicketRequest request1 = new DeleteBillTicketRequest();
            request1.setBillId(request.getBillId());
            billService.removeBill(request1);
            log.info("đã chờ xong thời gian");
        }
        catch (Exception e){
            log.error(e.getMessage());
        }

    }
}