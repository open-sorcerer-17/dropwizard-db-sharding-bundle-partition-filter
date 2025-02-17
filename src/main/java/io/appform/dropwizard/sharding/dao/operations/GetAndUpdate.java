package io.appform.dropwizard.sharding.dao.operations;

import io.appform.dropwizard.sharding.evaluators.EntityValidator;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Get an entity from DB, mutate it and persist it back to DB. All in same hibernate
 * session.
 *
 * @param <T> Type of entity to get and update.
 */
@Data
@Builder
public class GetAndUpdate<T> extends OpContext<Boolean> {

  @NonNull
  private DetachedCriteria criteria;
  @NonNull
  private Function<DetachedCriteria, T> getter;
  @Builder.Default
  private Function<T, T> mutator = t->t;
  private BiConsumer<T, T> updater;
  private EntityValidator entityValidator;

  @Override
  public Boolean apply(Session session) {
    T entity = getter.apply(criteria);
    //todo: error throw here
    if (null == entity || !entityValidator.isValid(this, entity)) {
      return false;
    }
    T newEntity = mutator.apply(entity);
    if (null == newEntity) {
      return false;
    }
    updater.accept(entity, newEntity);
    return true;
  }

  @Override
  public OpType getOpType() {
    return OpType.GET_AND_UPDATE;
  }

  @Override
  public <R> R visit(OpContextVisitor<R> visitor) {
    return visitor.visit(this);
  }
}
