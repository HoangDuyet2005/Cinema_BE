package com.example.goldenticketnew.workflow;

import com.example.goldenticketnew.config.cadance.CadenceWorkflowConfig;
import com.example.goldenticketnew.dtos.BookingRequestDto;
import com.example.goldenticketnew.workflow.activities.interfaces.IBookingTicketActivity;
import com.example.goldenticketnew.workflow.interfaces.IBookingTicketWorkflow;
import com.uber.cadence.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
@Slf4j
public class BookingTicketWorkflow  implements IBookingTicketWorkflow {
    /**
     * Activity stub implements activity interface and proxies calls to it to Cadence activity
     * invocations. Because activities are reentrant, only a single stub can be used for multiple
     * activity invocations.
     */

    @Override
    public void getBooking(BookingRequestDto request, CadenceWorkflowConfig config ) {
        // This is a blocking call that returns only after the activity has completed.
        IBookingTicketActivity activity = Workflow.newActivityStub(IBookingTicketActivity.class,config.getActivityOptions());
        log.info("cho 120 s cho den khi huy ve");
        Workflow.sleep(Duration.ofSeconds(240));
        activity.composeBooking(request);
    }
}