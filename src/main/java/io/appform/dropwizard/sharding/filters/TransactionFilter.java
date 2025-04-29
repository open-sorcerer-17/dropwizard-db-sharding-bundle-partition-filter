package io.appform.dropwizard.sharding.filters;

import io.appform.dropwizard.sharding.execution.TransactionExecutionContext;

/**
 *
 */
public interface TransactionFilter {

    FilterResult evaluate(final TransactionExecutionContext context);
}
