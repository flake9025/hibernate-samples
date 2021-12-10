package fr.vvlabs.hibernate.sample.mapper;

import fr.vvlabs.hibernate.sample.exception.SampleException;
import fr.vvlabs.hibernate.sample.model.BaseEntity;
import java.util.Arrays;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Mapper(componentModel = "spring")
public class JpaMapper {

  @Autowired
  private Optional<EntityManager> entityManager;

  public <T extends BaseEntity> T mapEntity(Object key, @TargetType Class<T> entityClass) throws SampleException {
    if (key == null) {
      return null;
    }
    Assert.isTrue(entityManager.isPresent(), "No EntityManager available, check JPA configuration");
    T entity = entityManager.get().find(entityClass, key);
    if (entity == null) {
      throw new SampleException(entityClass.getSimpleName(), key);
    }
    return entity;
  }

  public <T extends Enum<T>> T mapEnum(Object key, @TargetType Class<T> enumClass) throws SampleException {
    return key == null ? null : Arrays.stream(enumClass.getEnumConstants()).filter(key::equals).findFirst().orElseThrow(() ->
        new SampleException("Enum de type '" + enumClass.getSimpleName() + "': ", key));
  }
}
