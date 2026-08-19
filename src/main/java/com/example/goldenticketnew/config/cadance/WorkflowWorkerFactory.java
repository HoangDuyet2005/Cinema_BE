package com.example.goldenticketnew.config.cadance;


import com.example.goldenticketnew.workflow.BookingTicketWorkflow;
import com.example.goldenticketnew.workflow.activities.interfaces.IBookingTicketActivity;
import com.uber.cadence.DomainAlreadyExistsError;
import com.uber.cadence.RegisterDomainRequest;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import com.uber.cadence.worker.WorkerOptions;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


//@Component
@ConditionalOnProperty(name = "cadence.enabled", havingValue = "true", matchIfMissing = false)
@Slf4j
@RequiredArgsConstructor
public class WorkflowWorkerFactory implements InitializingBean {
    private final CadenceWorkflowConfig workflowConfig;

    private final WorkerFactory workerFactory;

    private final IWorkflowService workflowService;

    private final IBookingTicketActivity bookingTicketActivity;
    public void afterPropertiesSet() {
        registerDomain();
        runFactory();
    }

    @SneakyThrows
    private void registerDomain() {
        try {
            RegisterDomainRequest request = new RegisterDomainRequest();
            request.setName(workflowConfig.getDomain());
            request.setWorkflowExecutionRetentionPeriodInDays(1);
            workflowService.RegisterDomain(request);
        } catch (DomainAlreadyExistsError ignore) {
        }
    }
    private void runFactory() {
        if (!workflowConfig.getDisabledWorkers().contains(BookingTicketWorkflow.class.getSimpleName())) {
            createBookingWorker();
        }
        workerFactory.start();
    }

    private void createBookingWorker() {
        Worker worker = workerFactory.newWorker(CadenceWorkflowConfig.BOOKING_TASK,
            WorkerOptions.newBuilder()
                .setMaxConcurrentActivityExecutionSize(workflowConfig.getMaxConcurrentActivityExecutionSize())
                .setMaxConcurrentWorkflowExecutionSize(workflowConfig.getMaxConcurrentWorkflowExecutionSize())
                .build()
        );
        worker.registerWorkflowImplementationTypes(BookingTicketWorkflow.class);
        worker.registerActivitiesImplementations(bookingTicketActivity);
    }
}


