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
import org.servicosGerais.bean.Servico;
import org.servicosGerais.bean.jpa.ServicoEntity;
import org.servicosGerais.bean.jpa.ClienteEntity;
import org.servicosGerais.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class ServicoServiceMapperTest {

	private ServicoServiceMapper servicoServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		servicoServiceMapper = new ServicoServiceMapper();
		servicoServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'ServicoEntity' to 'Servico'
	 * @param servicoEntity
	 */
	@Test
	public void testMapServicoEntityToServico() {
		// Given
		ServicoEntity servicoEntity = new ServicoEntity();
		servicoEntity.setDataEntrada(mockValues.nextDate());
		servicoEntity.setDataSaida(mockValues.nextDate());
		servicoEntity.setValor(mockValues.nextFloat());
		servicoEntity.setObservacao(mockValues.nextString(300));
		servicoEntity.setCliente(new ClienteEntity());
		servicoEntity.getCliente().setId(mockValues.nextInteger());
		
		// When
		Servico servico = servicoServiceMapper.mapServicoEntityToServico(servicoEntity);
		
		// Then
		assertEquals(servicoEntity.getDataEntrada(), servico.getDataEntrada());
		assertEquals(servicoEntity.getDataSaida(), servico.getDataSaida());
		assertEquals(servicoEntity.getValor(), servico.getValor());
		assertEquals(servicoEntity.getObservacao(), servico.getObservacao());
		assertEquals(servicoEntity.getCliente().getId(), servico.getClienteId());
	}
	
	/**
	 * Test : Mapping from 'Servico' to 'ServicoEntity'
	 */
	@Test
	public void testMapServicoToServicoEntity() {
		// Given
		Servico servico = new Servico();
		servico.setDataEntrada(mockValues.nextDate());
		servico.setDataSaida(mockValues.nextDate());
		servico.setValor(mockValues.nextFloat());
		servico.setObservacao(mockValues.nextString(300));
		servico.setClienteId(mockValues.nextInteger());

		ServicoEntity servicoEntity = new ServicoEntity();
		
		// When
		servicoServiceMapper.mapServicoToServicoEntity(servico, servicoEntity);
		
		// Then
		assertEquals(servico.getDataEntrada(), servicoEntity.getDataEntrada());
		assertEquals(servico.getDataSaida(), servicoEntity.getDataSaida());
		assertEquals(servico.getValor(), servicoEntity.getValor());
		assertEquals(servico.getObservacao(), servicoEntity.getObservacao());
		assertEquals(servico.getClienteId(), servicoEntity.getCliente().getId());
	}

}