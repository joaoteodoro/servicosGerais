package org.servicosGerais.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.servicosGerais.bean.jpa.ServicoEntity;

/**
 * Repository : Servico.
 */
public interface ServicoJpaRepository extends PagingAndSortingRepository<ServicoEntity, Integer> {

}
