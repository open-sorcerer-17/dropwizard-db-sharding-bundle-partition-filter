package io.appform.dropwizard.sharding.dao.filters;

import io.appform.dropwizard.sharding.execution.TransactionExecutionContext;
import io.appform.dropwizard.sharding.filters.TransactionFilter;
import io.appform.dropwizard.sharding.filters.FilterResult;

/**
 *
 */
public class AlwaysFailFilter implements TransactionFilter {
    @Override
    public FilterResult evaluate(TransactionExecutionContext context) {
        return FilterResult.block("This always fails");
    }
}
