package org.study.gift.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.study.gift.core.entity.SampleEntity;

public interface SampleEntityRepository extends JpaRepository<SampleEntity, Long> {

}
