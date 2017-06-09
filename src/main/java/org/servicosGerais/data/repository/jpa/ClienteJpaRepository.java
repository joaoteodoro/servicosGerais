package org.servicosGerais.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.servicosGerais.bean.jpa.ClienteEntity;

/**
 * Repository : Cliente.
 */
public interface ClienteJpaRepository extends PagingAndSortingRepository<ClienteEntity, Integer> {

}
