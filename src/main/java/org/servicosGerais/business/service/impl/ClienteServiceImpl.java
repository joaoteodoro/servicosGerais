/*
 * Created on 7 jun 2017 ( Time 11:29:14 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.servicosGerais.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.servicosGerais.bean.Cliente;
import org.servicosGerais.bean.jpa.ClienteEntity;
import java.util.List;
import org.servicosGerais.business.service.ClienteService;
import org.servicosGerais.business.service.mapping.ClienteServiceMapper;
import org.servicosGerais.data.repository.jpa.ClienteJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of ClienteService
 */
@Component
@Transactional
public class ClienteServiceImpl implements ClienteService {

	@Resource
	private ClienteJpaRepository clienteJpaRepository;

	@Resource
	private ClienteServiceMapper clienteServiceMapper;
	
	@Override
	public Cliente findById(Integer id) {
		ClienteEntity clienteEntity = clienteJpaRepository.findOne(id);
		return clienteServiceMapper.mapClienteEntityToCliente(clienteEntity);
	}

	@Override
	public List<Cliente> findAll() {
		Iterable<ClienteEntity> entities = clienteJpaRepository.findAll();
		List<Cliente> beans = new ArrayList<Cliente>();
		for(ClienteEntity clienteEntity : entities) {
			beans.add(clienteServiceMapper.mapClienteEntityToCliente(clienteEntity));
		}
		return beans;
	}

	@Override
	public Cliente save(Cliente cliente) {
		return update(cliente) ;
	}

	@Override
	public Cliente create(Cliente cliente) {
		ClienteEntity clienteEntity = clienteJpaRepository.findOne(cliente.getId());
		if( clienteEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		clienteEntity = new ClienteEntity();
		clienteServiceMapper.mapClienteToClienteEntity(cliente, clienteEntity);
		ClienteEntity clienteEntitySaved = clienteJpaRepository.save(clienteEntity);
		return clienteServiceMapper.mapClienteEntityToCliente(clienteEntitySaved);
	}

	@Override
	public Cliente update(Cliente cliente) {
		ClienteEntity clienteEntity = clienteJpaRepository.findOne(cliente.getId());
		clienteServiceMapper.mapClienteToClienteEntity(cliente, clienteEntity);
		ClienteEntity clienteEntitySaved = clienteJpaRepository.save(clienteEntity);
		return clienteServiceMapper.mapClienteEntityToCliente(clienteEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		clienteJpaRepository.delete(id);
	}

	public ClienteJpaRepository getClienteJpaRepository() {
		return clienteJpaRepository;
	}

	public void setClienteJpaRepository(ClienteJpaRepository clienteJpaRepository) {
		this.clienteJpaRepository = clienteJpaRepository;
	}

	public ClienteServiceMapper getClienteServiceMapper() {
		return clienteServiceMapper;
	}

	public void setClienteServiceMapper(ClienteServiceMapper clienteServiceMapper) {
		this.clienteServiceMapper = clienteServiceMapper;
	}

}
