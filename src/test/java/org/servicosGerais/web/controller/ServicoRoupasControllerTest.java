package org.servicosGerais.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import org.servicosGerais.bean.ServicoRoupas;
import org.servicosGerais.bean.Servico;
import org.servicosGerais.test.ServicoRoupasFactoryForTest;
import org.servicosGerais.test.ServicoFactoryForTest;

//--- Services 
import org.servicosGerais.business.service.ServicoRoupasService;
import org.servicosGerais.business.service.ServicoService;

//--- List Items 
import org.servicosGerais.web.listitem.ServicoListItem;

import org.servicosGerais.web.common.Message;
import org.servicosGerais.web.common.MessageHelper;
import org.servicosGerais.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class ServicoRoupasControllerTest {
	
	@InjectMocks
	private ServicoRoupasController servicoRoupasController;
	@Mock
	private ServicoRoupasService servicoRoupasService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ServicoService servicoService; // Injected by Spring

	private ServicoRoupasFactoryForTest servicoRoupasFactoryForTest = new ServicoRoupasFactoryForTest();
	private ServicoFactoryForTest servicoFactoryForTest = new ServicoFactoryForTest();

	List<Servico> servicos = new ArrayList<Servico>();

	private void givenPopulateModel() {
		Servico servico1 = servicoFactoryForTest.newServico();
		Servico servico2 = servicoFactoryForTest.newServico();
		List<Servico> servicos = new ArrayList<Servico>();
		servicos.add(servico1);
		servicos.add(servico2);
		when(servicoService.findAll()).thenReturn(servicos);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<ServicoRoupas> list = new ArrayList<ServicoRoupas>();
		when(servicoRoupasService.findAll()).thenReturn(list);
		
		// When
		String viewName = servicoRoupasController.list(model);
		
		// Then
		assertEquals("servicoRoupas/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = servicoRoupasController.formForCreate(model);
		
		// Then
		assertEquals("servicoRoupas/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((ServicoRoupas)modelMap.get("servicoRoupas")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/servicoRoupas/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ServicoListItem> servicoListItems = (List<ServicoListItem>) modelMap.get("listOfServicoItems");
		assertEquals(2, servicoListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ServicoRoupas servicoRoupas = servicoRoupasFactoryForTest.newServicoRoupas();
		Integer id = servicoRoupas.getId();
		when(servicoRoupasService.findById(id)).thenReturn(servicoRoupas);
		
		// When
		String viewName = servicoRoupasController.formForUpdate(model, id);
		
		// Then
		assertEquals("servicoRoupas/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servicoRoupas, (ServicoRoupas) modelMap.get("servicoRoupas"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/servicoRoupas/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ServicoRoupas servicoRoupas = servicoRoupasFactoryForTest.newServicoRoupas();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ServicoRoupas servicoRoupasCreated = new ServicoRoupas();
		when(servicoRoupasService.create(servicoRoupas)).thenReturn(servicoRoupasCreated); 
		
		// When
		String viewName = servicoRoupasController.create(servicoRoupas, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/servicoRoupas/form/"+servicoRoupas.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servicoRoupasCreated, (ServicoRoupas) modelMap.get("servicoRoupas"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ServicoRoupas servicoRoupas = servicoRoupasFactoryForTest.newServicoRoupas();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = servicoRoupasController.create(servicoRoupas, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("servicoRoupas/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servicoRoupas, (ServicoRoupas) modelMap.get("servicoRoupas"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/servicoRoupas/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ServicoListItem> servicoListItems = (List<ServicoListItem>) modelMap.get("listOfServicoItems");
		assertEquals(2, servicoListItems.size());
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		ServicoRoupas servicoRoupas = servicoRoupasFactoryForTest.newServicoRoupas();
		
		Exception exception = new RuntimeException("test exception");
		when(servicoRoupasService.create(servicoRoupas)).thenThrow(exception);
		
		// When
		String viewName = servicoRoupasController.create(servicoRoupas, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("servicoRoupas/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servicoRoupas, (ServicoRoupas) modelMap.get("servicoRoupas"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/servicoRoupas/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "servicoRoupas.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ServicoListItem> servicoListItems = (List<ServicoListItem>) modelMap.get("listOfServicoItems");
		assertEquals(2, servicoListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ServicoRoupas servicoRoupas = servicoRoupasFactoryForTest.newServicoRoupas();
		Integer id = servicoRoupas.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ServicoRoupas servicoRoupasSaved = new ServicoRoupas();
		servicoRoupasSaved.setId(id);
		when(servicoRoupasService.update(servicoRoupas)).thenReturn(servicoRoupasSaved); 
		
		// When
		String viewName = servicoRoupasController.update(servicoRoupas, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/servicoRoupas/form/"+servicoRoupas.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servicoRoupasSaved, (ServicoRoupas) modelMap.get("servicoRoupas"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ServicoRoupas servicoRoupas = servicoRoupasFactoryForTest.newServicoRoupas();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = servicoRoupasController.update(servicoRoupas, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("servicoRoupas/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servicoRoupas, (ServicoRoupas) modelMap.get("servicoRoupas"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/servicoRoupas/update", modelMap.get("saveAction"));
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		ServicoRoupas servicoRoupas = servicoRoupasFactoryForTest.newServicoRoupas();
		
		Exception exception = new RuntimeException("test exception");
		when(servicoRoupasService.update(servicoRoupas)).thenThrow(exception);
		
		// When
		String viewName = servicoRoupasController.update(servicoRoupas, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("servicoRoupas/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servicoRoupas, (ServicoRoupas) modelMap.get("servicoRoupas"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/servicoRoupas/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "servicoRoupas.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ServicoRoupas servicoRoupas = servicoRoupasFactoryForTest.newServicoRoupas();
		Integer id = servicoRoupas.getId();
		
		// When
		String viewName = servicoRoupasController.delete(redirectAttributes, id);
		
		// Then
		verify(servicoRoupasService).delete(id);
		assertEquals("redirect:/servicoRoupas", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ServicoRoupas servicoRoupas = servicoRoupasFactoryForTest.newServicoRoupas();
		Integer id = servicoRoupas.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(servicoRoupasService).delete(id);
		
		// When
		String viewName = servicoRoupasController.delete(redirectAttributes, id);
		
		// Then
		verify(servicoRoupasService).delete(id);
		assertEquals("redirect:/servicoRoupas", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "servicoRoupas.error.delete", exception);
	}
	
	
}
