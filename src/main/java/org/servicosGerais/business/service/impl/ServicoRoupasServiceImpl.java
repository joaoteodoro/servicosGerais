/*
 * Created on 7 jun 2017 ( Time 11:29:14 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.servicosGerais.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.servicosGerais.bean.ServicoRoupas;
import org.servicosGerais.bean.jpa.ServicoRoupasEntity;
import org.servicosGerais.business.service.ServicoRoupasService;
import org.servicosGerais.business.service.mapping.ServicoRoupasServiceMapper;
import org.servicosGerais.data.repository.jpa.ServicoRoupasJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of ServicoRoupasService
 */
@Component
@Transactional
public class ServicoRoupasServiceImpl implements ServicoRoupasService {

	@Resource
	private ServicoRoupasJpaRepository servicoRoupasJpaRepository;

	@Resource
	private ServicoRoupasServiceMapper servicoRoupasServiceMapper;
	
	@Override
	public ServicoRoupas findById(Integer id) {
		ServicoRoupasEntity servicoRoupasEntity = servicoRoupasJpaRepository.findOne(id);
		return servicoRoupasServiceMapper.mapServicoRoupasEntityToServicoRoupas(servicoRoupasEntity);
	}

	@Override
	public List<ServicoRoupas> findAll() {
		Iterable<ServicoRoupasEntity> entities = servicoRoupasJpaRepository.findAll();
		List<ServicoRoupas> beans = new ArrayList<ServicoRoupas>();
		for(ServicoRoupasEntity servicoRoupasEntity : entities) {
			beans.add(servicoRoupasServiceMapper.mapServicoRoupasEntityToServicoRoupas(servicoRoupasEntity));
		}
		return beans;
	}

	@Override
	public ServicoRoupas save(ServicoRoupas servicoRoupas) {
		return update(servicoRoupas) ;
	}

	@Override
	public ServicoRoupas create(ServicoRoupas servicoRoupas) {
		ServicoRoupasEntity servicoRoupasEntity = servicoRoupasJpaRepository.findOne(servicoRoupas.getId());
		if( servicoRoupasEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		servicoRoupasEntity = new ServicoRoupasEntity();
		servicoRoupasServiceMapper.mapServicoRoupasToServicoRoupasEntity(servicoRoupas, servicoRoupasEntity);
		ServicoRoupasEntity servicoRoupasEntitySaved = servicoRoupasJpaRepository.save(servicoRoupasEntity);
		return servicoRoupasServiceMapper.mapServicoRoupasEntityToServicoRoupas(servicoRoupasEntitySaved);
	}

	@Override
	public ServicoRoupas update(ServicoRoupas servicoRoupas) {
		ServicoRoupasEntity servicoRoupasEntity = servicoRoupasJpaRepository.findOne(servicoRoupas.getId());
		servicoRoupasServiceMapper.mapServicoRoupasToServicoRoupasEntity(servicoRoupas, servicoRoupasEntity);
		ServicoRoupasEntity servicoRoupasEntitySaved = servicoRoupasJpaRepository.save(servicoRoupasEntity);
		return servicoRoupasServiceMapper.mapServicoRoupasEntityToServicoRoupas(servicoRoupasEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		servicoRoupasJpaRepository.delete(id);
	}

	public ServicoRoupasJpaRepository getServicoRoupasJpaRepository() {
		return servicoRoupasJpaRepository;
	}

	public void setServicoRoupasJpaRepository(ServicoRoupasJpaRepository servicoRoupasJpaRepository) {
		this.servicoRoupasJpaRepository = servicoRoupasJpaRepository;
	}

	public ServicoRoupasServiceMapper getServicoRoupasServiceMapper() {
		return servicoRoupasServiceMapper;
	}

	public void setServicoRoupasServiceMapper(ServicoRoupasServiceMapper servicoRoupasServiceMapper) {
		this.servicoRoupasServiceMapper = servicoRoupasServiceMapper;
	}

}
