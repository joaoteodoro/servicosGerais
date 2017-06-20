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
import org.servicosGerais.bean.ServicoRoupas;
import org.servicosGerais.bean.jpa.ServicoRoupasEntity;
import org.servicosGerais.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class ServicoRoupasServiceMapperTest {

	private ServicoRoupasServiceMapper servicoRoupasServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		servicoRoupasServiceMapper = new ServicoRoupasServiceMapper();
		servicoRoupasServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'ServicoRoupasEntity' to 'ServicoRoupas'
	 * @param servicoRoupasEntity
	 */
	@Test
	public void testMapServicoRoupasEntityToServicoRoupas() {
		// Given
		ServicoRoupasEntity servicoRoupasEntity = new ServicoRoupasEntity();
		servicoRoupasEntity.setQuantidadePecas(mockValues.nextInteger());
		
		// When
		ServicoRoupas servicoRoupas = servicoRoupasServiceMapper.mapServicoRoupasEntityToServicoRoupas(servicoRoupasEntity);
		
		// Then
		assertEquals(servicoRoupasEntity.getQuantidadePecas(), servicoRoupas.getQuantidadePecas());
	}
	
	/**
	 * Test : Mapping from 'ServicoRoupas' to 'ServicoRoupasEntity'
	 */
	@Test
	public void testMapServicoRoupasToServicoRoupasEntity() {
		// Given
		ServicoRoupas servicoRoupas = new ServicoRoupas();
		servicoRoupas.setTipoServico(mockValues.nextString(15));
		servicoRoupas.setQuantidadePecas(mockValues.nextInteger());

		ServicoRoupasEntity servicoRoupasEntity = new ServicoRoupasEntity();
		
		// When
		servicoRoupasServiceMapper.mapServicoRoupasToServicoRoupasEntity(servicoRoupas, servicoRoupasEntity);
		
		// Then
		assertEquals(servicoRoupas.getQuantidadePecas(), servicoRoupasEntity.getQuantidadePecas());
	}

}