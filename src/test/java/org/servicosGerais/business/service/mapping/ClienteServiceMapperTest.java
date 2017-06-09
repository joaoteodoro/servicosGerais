/*
 * Created on 7 jun 2017 ( Time 11:29:14 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.servicosGerais.business.service.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.servicosGerais.bean.Cliente;
import org.servicosGerais.bean.jpa.ClienteEntity;
import org.servicosGerais.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class ClienteServiceMapperTest {

	private ClienteServiceMapper clienteServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		clienteServiceMapper = new ClienteServiceMapper();
		clienteServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'ClienteEntity' to 'Cliente'
	 * @param clienteEntity
	 */
	@Test
	public void testMapClienteEntityToCliente() {
		// Given
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setNome(mockValues.nextString(255));
		clienteEntity.setTelefone(mockValues.nextString(12));
		
		// When
		Cliente cliente = clienteServiceMapper.mapClienteEntityToCliente(clienteEntity);
		
		// Then
		assertEquals(clienteEntity.getNome(), cliente.getNome());
		assertEquals(clienteEntity.getTelefone(), cliente.getTelefone());
	}
	
	/**
	 * Test : Mapping from 'Cliente' to 'ClienteEntity'
	 */
	@Test
	public void testMapClienteToClienteEntity() {
		// Given
		Cliente cliente = new Cliente();
		cliente.setNome(mockValues.nextString(255));
		cliente.setTelefone(mockValues.nextString(12));

		ClienteEntity clienteEntity = new ClienteEntity();
		
		// When
		clienteServiceMapper.mapClienteToClienteEntity(cliente, clienteEntity);
		
		// Then
		assertEquals(cliente.getNome(), clienteEntity.getNome());
		assertEquals(cliente.getTelefone(), clienteEntity.getTelefone());
	}

}