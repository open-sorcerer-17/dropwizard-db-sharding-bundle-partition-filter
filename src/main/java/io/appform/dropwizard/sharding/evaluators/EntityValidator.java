package io.appform.dropwizard.sharding.evaluators;

import io.appform.dropwizard.sharding.dao.operations.OpContext;
import io.appform.dropwizard.sharding.filters.FilterOutput;
import io.appform.dropwizard.sharding.filters.FilterResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Getter
@Slf4j
@NoArgsConstructor
public final class EntityValidator {

    private final List<io.appform.dropwizard.sharding.filters.EntityValidator> evaluator = new ArrayList<>();

    public void addFilters(final Collection<io.appform.dropwizard.sharding.filters.EntityValidator> evaluators) {
        this.evaluator.addAll(evaluators);
    }

    public <T, R> Boolean isValid(OpContext<T> context, R entity) {
        val blocks = evaluator.stream()
            .map(evaluator -> {
                try {
                    return evaluator.validate(context, entity);
                } catch (Throwable t) {
                    log.error("Error running evaluator: " + evaluator.getClass(), t);
                    return FilterResult.allow();
                }
            })
            .filter(result -> FilterOutput.BLOCK.equals(result.getOutput()))
            .map(FilterResult::getReason)
            .collect(Collectors.toList());
        return blocks.isEmpty();
    }
}
