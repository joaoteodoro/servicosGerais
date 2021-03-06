/*
 * Created on 7 jun 2017 ( Time 11:29:14 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.servicosGerais.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.servicosGerais.bean.Servico;
import org.servicosGerais.bean.jpa.ServicoEntity;
import java.util.Date;
import java.util.List;
import org.servicosGerais.business.service.mapping.ServicoServiceMapper;
import org.servicosGerais.data.repository.jpa.ServicoJpaRepository;
import org.servicosGerais.test.ServicoFactoryForTest;
import org.servicosGerais.test.ServicoEntityFactoryForTest;
import org.servicosGerais.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of ServicoService
 */
@RunWith(MockitoJUnitRunner.class)
public class ServicoServiceImplTest {

	@InjectMocks
	private ServicoServiceImpl servicoService;
	@Mock
	private ServicoJpaRepository servicoJpaRepository;
	@Mock
	private ServicoServiceMapper servicoServiceMapper;
	
	private ServicoFactoryForTest servicoFactoryForTest = new ServicoFactoryForTest();

	private ServicoEntityFactoryForTest servicoEntityFactoryForTest = new ServicoEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		ServicoEntity servicoEntity = servicoJpaRepository.findOne(id);
		
		Servico servico = servicoFactoryForTest.newServico();
		when(servicoServiceMapper.mapServicoEntityToServico(servicoEntity)).thenReturn(servico);

		// When
		Servico servicoFound = servicoService.findById(id);

		// Then
		assertEquals(servico.getId(),servicoFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<ServicoEntity> servicoEntitys = new ArrayList<ServicoEntity>();
		ServicoEntity servicoEntity1 = servicoEntityFactoryForTest.newServicoEntity();
		servicoEntitys.add(servicoEntity1);
		ServicoEntity servicoEntity2 = servicoEntityFactoryForTest.newServicoEntity();
		servicoEntitys.add(servicoEntity2);
		when(servicoJpaRepository.findAll()).thenReturn(servicoEntitys);
		
		Servico servico1 = servicoFactoryForTest.newServico();
		when(servicoServiceMapper.mapServicoEntityToServico(servicoEntity1)).thenReturn(servico1);
		Servico servico2 = servicoFactoryForTest.newServico();
		when(servicoServiceMapper.mapServicoEntityToServico(servicoEntity2)).thenReturn(servico2);

		// When
		List<Servico> servicosFounds = servicoService.findAll();

		// Then
		assertTrue(servico1 == servicosFounds.get(0));
		assertTrue(servico2 == servicosFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Servico servico = servicoFactoryForTest.newServico();

		ServicoEntity servicoEntity = servicoEntityFactoryForTest.newServicoEntity();
		when(servicoJpaRepository.findOne(servico.getId())).thenReturn(null);
		
		servicoEntity = new ServicoEntity();
		servicoServiceMapper.mapServicoToServicoEntity(servico, servicoEntity);
		ServicoEntity servicoEntitySaved = servicoJpaRepository.save(servicoEntity);
		
		Servico servicoSaved = servicoFactoryForTest.newServico();
		when(servicoServiceMapper.mapServicoEntityToServico(servicoEntitySaved)).thenReturn(servicoSaved);

		// When
		Servico servicoResult = servicoService.create(servico);

		// Then
		assertTrue(servicoResult == servicoSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Servico servico = servicoFactoryForTest.newServico();

		ServicoEntity servicoEntity = servicoEntityFactoryForTest.newServicoEntity();
		when(servicoJpaRepository.findOne(servico.getId())).thenReturn(servicoEntity);

		// When
		Exception exception = null;
		try {
			servicoService.create(servico);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		Servico servico = servicoFactoryForTest.newServico();

		ServicoEntity servicoEntity = servicoEntityFactoryForTest.newServicoEntity();
		when(servicoJpaRepository.findOne(servico.getId())).thenReturn(servicoEntity);
		
		ServicoEntity servicoEntitySaved = servicoEntityFactoryForTest.newServicoEntity();
		when(servicoJpaRepository.save(servicoEntity)).thenReturn(servicoEntitySaved);
		
		Servico servicoSaved = servicoFactoryForTest.newServico();
		when(servicoServiceMapper.mapServicoEntityToServico(servicoEntitySaved)).thenReturn(servicoSaved);

		// When
		Servico servicoResult = servicoService.update(servico);

		// Then
		verify(servicoServiceMapper).mapServicoToServicoEntity(servico, servicoEntity);
		assertTrue(servicoResult == servicoSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		servicoService.delete(id);

		// Then
		verify(servicoJpaRepository).delete(id);
		
	}

}
