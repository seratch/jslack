package com.slack.api.audit.metrics;

import com.slack.api.audit.AuditApiResponse;
import com.slack.api.audit.impl.AsyncExecutionSupplier;
import com.slack.api.audit.impl.AsyncRateLimitQueue;
import com.slack.api.rate_limits.metrics.impl.BaseMemoryMetricsDatastore;

public class MemoryMetricsDatastore extends BaseMemoryMetricsDatastore<
        AsyncExecutionSupplier<? extends AuditApiResponse>, AsyncRateLimitQueue.AuditMessage> {

    public MemoryMetricsDatastore(int numberOfNodes) {
        super(numberOfNodes);
    }

    @Override
    protected String getMetricsType() {
        return "AUDIT_LOGS";
    }

    @Override
    public AsyncRateLimitQueue getRateLimitQueue(String executorName, String teamId) {
        return AsyncRateLimitQueue.get(executorName, teamId);
    }

}
