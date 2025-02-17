package io.appform.dropwizard.sharding.filters;

import lombok.Value;

/**
 * Output for the filter operation. If {@link FilterOutput#BLOCK} is returned, a reason should be returned
 */
@Value
public class FilterResult {
    FilterOutput output;
    String reason;

    public static FilterResult allow() {
        return new FilterResult(FilterOutput.PROCEED, null);
    }

    public static FilterResult block(final String reason) {
        return new FilterResult(FilterOutput.BLOCK, reason);
    }
}
