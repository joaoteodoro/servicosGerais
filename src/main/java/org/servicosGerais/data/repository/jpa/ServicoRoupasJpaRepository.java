package org.servicosGerais.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.servicosGerais.bean.jpa.ServicoRoupasEntity;

/**
 * Repository : ServicoRoupas.
 */
public interface ServicoRoupasJpaRepository extends PagingAndSortingRepository<ServicoRoupasEntity, Integer> {

}
