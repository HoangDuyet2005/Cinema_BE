package com.example.goldenticketnew.config.cadance;



import com.uber.cadence.WorkflowIdReusePolicy;
import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.common.RetryOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.IWorkflowService;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.WorkerFactory;
import com.uber.cadence.worker.WorkerFactoryOptions;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConditionalOnProperty(name = "cadence.enabled", havingValue = "true", matchIfMissing = false)
@Slf4j
public class CadenceWorkflowConfig implements InitializingBean, Cloneable {


    public static final String BOOKING_TASK = "BookingTask";

    @Value("${cadence.host}")
    private String cadenceHost;

    @Value("${cadence.port}")
    private int cadencePort;

    @Value("${cadence.domain}")
    private String domain;

    @Value("${cadence.retry.workflow.execution-start-to-close-timeout}")
    private Integer executionStartToCloseTimeout;

    @Value("${cadence.retry.workflow.medium-execution-start-to-close-timeout}")
    public Integer mediumExecutionStartToCloseTimeout;

    @Value("${cadence.retry.workflow.activity.schedule-to-close-timeout}")
    private Integer activityScheduleToCloseTimeout;

    @Value("${cadence.retry.workflow.activity.initial-interval}")
    private Integer activityInitialInterval;

    @Value("${cadence.retry.workflow.activity.expiration}")
    private Integer activityExpiration;

    @Value("${cadence.retry.workflow.activity.maximum-attempts}")
    private Integer activityMaximumAttempts;

    @Value("${cadence.workerfactory.max-workflow-thread-count}")
    private Integer maxWorkflowThreadCount;

    @Value("${cadence.workerfactory.sticky-cache-size}")
    private Integer stickyCacheSize;

    @Value("${cadence.workerfactory.disable-sticky-execution}")
    private Boolean disableStickyExecution;

    @Value("${cadence.worker.max-concurrent-activity-execution-size}")
    private Integer maxConcurrentActivityExecutionSize;

    @Value("${cadence.worker.max-concurrent-workflow-execution-size}")
    private Integer maxConcurrentWorkflowExecutionSize;

    @Value("#{'${cadence.worker.disabled}'.split(',')}")
    private List<Object> disabledWorkers = List.of();


    final WorkflowOptionMap workflowOptionMap = new WorkflowOptionMap();

    ActivityOptions activityOptions;

    @Override
    public void afterPropertiesSet() {
        log.debug("Activity default options: {} {} {} {}", activityScheduleToCloseTimeout, activityInitialInterval, activityExpiration, activityMaximumAttempts);
        activityOptions = new ActivityOptions.Builder()
            .setScheduleToCloseTimeout(Duration.ofSeconds(activityScheduleToCloseTimeout))
            .setRetryOptions(
                new RetryOptions.Builder()
                    .setInitialInterval(Duration.ofSeconds(activityInitialInterval))
                    .setExpiration(Duration.ofSeconds(activityExpiration))
                    .setMaximumAttempts(activityMaximumAttempts)
                    .setBackoffCoefficient(1)
//                                .setDoNotRetry(InternalException.class)
                    .build()
            ).build();


    }


    @Bean
    public IWorkflowService workflowService() {
        return new WorkflowServiceTChannel(ClientOptions.newBuilder()
            .setHost(cadenceHost)
            .setPort(cadencePort)
            .build());
    }

    @Bean
    public WorkflowClient workflowClient(IWorkflowService workflowService) {
        return WorkflowClient.newInstance(
            workflowService,
            WorkflowClientOptions.newBuilder()
                .setDomain(domain)
                .build());
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
        return WorkerFactory.newInstance(
            workflowClient,
            WorkerFactoryOptions.newBuilder()
                .setMaxWorkflowThreadCount(maxWorkflowThreadCount)
                .setStickyCacheSize(stickyCacheSize)
                .setDisableStickyExecution(disableStickyExecution)
                .build());
    }


    public class WorkflowOptionMap {
        private final Map<String, WorkflowOptions> internalMap = new HashMap<>();
        public WorkflowOptions get(String taskList, String workFlowId) {
            return getOrDefault(taskList,workFlowId);
        }
        public WorkflowOptions get(String taskList) {
            return getOrDefault(taskList);
        }

        private WorkflowOptions getOrDefault(String taskList) {
            if (internalMap.get(taskList) == null) {
                WorkflowOptions defaultOptions = new WorkflowOptions.Builder()
                    .setTaskList(taskList)
                    .setExecutionStartToCloseTimeout(Duration.ofSeconds(getExecutionStartToCloseTimeout())
                    ).build();
                internalMap.put(taskList, defaultOptions);
            }
            return internalMap.get(taskList);
        }
        private WorkflowOptions getOrDefault(String taskList, String workFlowId) {
            if (internalMap.get(taskList) == null) {
                WorkflowOptions defaultOptions = new WorkflowOptions.Builder()
                    .setTaskList(taskList)
                    .setWorkflowId(workFlowId)
                    .setWorkflowIdReusePolicy(WorkflowIdReusePolicy.TerminateIfRunning)
                    .setExecutionStartToCloseTimeout(Duration.ofSeconds(getExecutionStartToCloseTimeout())
                    ).build();
                internalMap.put(taskList, defaultOptions);
            }
            return internalMap.get(taskList);
        }

        public void put(String taskList, WorkflowOptions options) {
            internalMap.put(taskList, options);
        }
    }

    @Override
    @SneakyThrows
    public CadenceWorkflowConfig clone() {
        CadenceWorkflowConfig config = new CadenceWorkflowConfig();
        config.activityOptions = activityOptions;
        config.cadenceHost = cadenceHost;
        config.cadencePort = cadencePort;
        config.domain = domain;
        config.executionStartToCloseTimeout = executionStartToCloseTimeout;
        config.activityScheduleToCloseTimeout = activityScheduleToCloseTimeout;
        config.activityInitialInterval = activityInitialInterval;
        config.activityExpiration = activityExpiration;
        config.activityMaximumAttempts = activityMaximumAttempts;
        config.maxWorkflowThreadCount = maxWorkflowThreadCount;
        config.stickyCacheSize = stickyCacheSize;
        config.disableStickyExecution = disableStickyExecution;
        config.maxConcurrentActivityExecutionSize = maxConcurrentActivityExecutionSize;
        config.maxConcurrentWorkflowExecutionSize = maxConcurrentWorkflowExecutionSize;
        config.disabledWorkers = disabledWorkers;
        return config;
    }
}
