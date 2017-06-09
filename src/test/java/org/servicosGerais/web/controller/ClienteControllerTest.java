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
import org.servicosGerais.bean.Cliente;
import org.servicosGerais.test.ClienteFactoryForTest;

//--- Services 
import org.servicosGerais.business.service.ClienteService;


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
public class ClienteControllerTest {
	
	@InjectMocks
	private ClienteController clienteController;
	@Mock
	private ClienteService clienteService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private ClienteFactoryForTest clienteFactoryForTest = new ClienteFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Cliente> list = new ArrayList<Cliente>();
		when(clienteService.findAll()).thenReturn(list);
		
		// When
		String viewName = clienteController.list(model);
		
		// Then
		assertEquals("cliente/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = clienteController.formForCreate(model);
		
		// Then
		assertEquals("cliente/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Cliente)modelMap.get("cliente")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/cliente/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Cliente cliente = clienteFactoryForTest.newCliente();
		Integer id = cliente.getId();
		when(clienteService.findById(id)).thenReturn(cliente);
		
		// When
		String viewName = clienteController.formForUpdate(model, id);
		
		// Then
		assertEquals("cliente/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(cliente, (Cliente) modelMap.get("cliente"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/cliente/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Cliente cliente = clienteFactoryForTest.newCliente();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Cliente clienteCreated = new Cliente();
		when(clienteService.create(cliente)).thenReturn(clienteCreated); 
		
		// When
		String viewName = clienteController.create(cliente, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/cliente/form/"+cliente.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(clienteCreated, (Cliente) modelMap.get("cliente"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Cliente cliente = clienteFactoryForTest.newCliente();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = clienteController.create(cliente, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("cliente/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(cliente, (Cliente) modelMap.get("cliente"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/cliente/create", modelMap.get("saveAction"));
		
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

		Cliente cliente = clienteFactoryForTest.newCliente();
		
		Exception exception = new RuntimeException("test exception");
		when(clienteService.create(cliente)).thenThrow(exception);
		
		// When
		String viewName = clienteController.create(cliente, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("cliente/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(cliente, (Cliente) modelMap.get("cliente"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/cliente/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "cliente.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Cliente cliente = clienteFactoryForTest.newCliente();
		Integer id = cliente.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Cliente clienteSaved = new Cliente();
		clienteSaved.setId(id);
		when(clienteService.update(cliente)).thenReturn(clienteSaved); 
		
		// When
		String viewName = clienteController.update(cliente, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/cliente/form/"+cliente.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(clienteSaved, (Cliente) modelMap.get("cliente"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Cliente cliente = clienteFactoryForTest.newCliente();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = clienteController.update(cliente, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("cliente/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(cliente, (Cliente) modelMap.get("cliente"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/cliente/update", modelMap.get("saveAction"));
		
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

		Cliente cliente = clienteFactoryForTest.newCliente();
		
		Exception exception = new RuntimeException("test exception");
		when(clienteService.update(cliente)).thenThrow(exception);
		
		// When
		String viewName = clienteController.update(cliente, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("cliente/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(cliente, (Cliente) modelMap.get("cliente"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/cliente/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "cliente.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Cliente cliente = clienteFactoryForTest.newCliente();
		Integer id = cliente.getId();
		
		// When
		String viewName = clienteController.delete(redirectAttributes, id);
		
		// Then
		verify(clienteService).delete(id);
		assertEquals("redirect:/cliente", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Cliente cliente = clienteFactoryForTest.newCliente();
		Integer id = cliente.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(clienteService).delete(id);
		
		// When
		String viewName = clienteController.delete(redirectAttributes, id);
		
		// Then
		verify(clienteService).delete(id);
		assertEquals("redirect:/cliente", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "cliente.error.delete", exception);
	}
	
	
}
