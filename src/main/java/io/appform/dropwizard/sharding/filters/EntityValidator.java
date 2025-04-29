package io.appform.dropwizard.sharding.filters;

import io.appform.dropwizard.sharding.dao.operations.OpContext;

public interface EntityValidator {

    <T, R> FilterResult validate(final OpContext<T> context, final R entity);
}
