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
import org.servicosGerais.bean.Servico;
import org.servicosGerais.bean.Cliente;
import org.servicosGerais.test.ServicoFactoryForTest;
import org.servicosGerais.test.ClienteFactoryForTest;

//--- Services 
import org.servicosGerais.business.service.ServicoService;
import org.servicosGerais.business.service.ClienteService;

//--- List Items 
import org.servicosGerais.web.listitem.ClienteListItem;

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
public class ServicoControllerTest {
	
	@InjectMocks
	private ServicoController servicoController;
	@Mock
	private ServicoService servicoService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ClienteService clienteService; // Injected by Spring

	private ServicoFactoryForTest servicoFactoryForTest = new ServicoFactoryForTest();
	private ClienteFactoryForTest clienteFactoryForTest = new ClienteFactoryForTest();

	List<Cliente> clientes = new ArrayList<Cliente>();

	private void givenPopulateModel() {
		Cliente cliente1 = clienteFactoryForTest.newCliente();
		Cliente cliente2 = clienteFactoryForTest.newCliente();
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes.add(cliente1);
		clientes.add(cliente2);
		when(clienteService.findAll()).thenReturn(clientes);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Servico> list = new ArrayList<Servico>();
		when(servicoService.findAll()).thenReturn(list);
		
		// When
		String viewName = servicoController.list(model);
		
		// Then
		assertEquals("servico/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = servicoController.formForCreate(model);
		
		// Then
		assertEquals("servico/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Servico)modelMap.get("servico")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/servico/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ClienteListItem> clienteListItems = (List<ClienteListItem>) modelMap.get("listOfClienteItems");
		assertEquals(2, clienteListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Servico servico = servicoFactoryForTest.newServico();
		Integer id = servico.getId();
		when(servicoService.findById(id)).thenReturn(servico);
		
		// When
		String viewName = servicoController.formForUpdate(model, id);
		
		// Then
		assertEquals("servico/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servico, (Servico) modelMap.get("servico"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/servico/update", modelMap.get("saveAction"));
		
		List<ClienteListItem> clienteListItems = (List<ClienteListItem>) modelMap.get("listOfClienteItems");
		assertEquals(2, clienteListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Servico servico = servicoFactoryForTest.newServico();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Servico servicoCreated = new Servico();
		when(servicoService.create(servico)).thenReturn(servicoCreated); 
		
		// When
		String viewName = servicoController.create(servico, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/servico/form/"+servico.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servicoCreated, (Servico) modelMap.get("servico"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Servico servico = servicoFactoryForTest.newServico();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = servicoController.create(servico, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("servico/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servico, (Servico) modelMap.get("servico"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/servico/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ClienteListItem> clienteListItems = (List<ClienteListItem>) modelMap.get("listOfClienteItems");
		assertEquals(2, clienteListItems.size());
		
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

		Servico servico = servicoFactoryForTest.newServico();
		
		Exception exception = new RuntimeException("test exception");
		when(servicoService.create(servico)).thenThrow(exception);
		
		// When
		String viewName = servicoController.create(servico, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("servico/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servico, (Servico) modelMap.get("servico"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/servico/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "servico.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ClienteListItem> clienteListItems = (List<ClienteListItem>) modelMap.get("listOfClienteItems");
		assertEquals(2, clienteListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Servico servico = servicoFactoryForTest.newServico();
		Integer id = servico.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Servico servicoSaved = new Servico();
		servicoSaved.setId(id);
		when(servicoService.update(servico)).thenReturn(servicoSaved); 
		
		// When
		String viewName = servicoController.update(servico, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/servico/form/"+servico.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servicoSaved, (Servico) modelMap.get("servico"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Servico servico = servicoFactoryForTest.newServico();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = servicoController.update(servico, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("servico/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servico, (Servico) modelMap.get("servico"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/servico/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ClienteListItem> clienteListItems = (List<ClienteListItem>) modelMap.get("listOfClienteItems");
		assertEquals(2, clienteListItems.size());
		
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

		Servico servico = servicoFactoryForTest.newServico();
		
		Exception exception = new RuntimeException("test exception");
		when(servicoService.update(servico)).thenThrow(exception);
		
		// When
		String viewName = servicoController.update(servico, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("servico/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(servico, (Servico) modelMap.get("servico"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/servico/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "servico.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ClienteListItem> clienteListItems = (List<ClienteListItem>) modelMap.get("listOfClienteItems");
		assertEquals(2, clienteListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Servico servico = servicoFactoryForTest.newServico();
		Integer id = servico.getId();
		
		// When
		String viewName = servicoController.delete(redirectAttributes, id);
		
		// Then
		verify(servicoService).delete(id);
		assertEquals("redirect:/servico", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Servico servico = servicoFactoryForTest.newServico();
		Integer id = servico.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(servicoService).delete(id);
		
		// When
		String viewName = servicoController.delete(redirectAttributes, id);
		
		// Then
		verify(servicoService).delete(id);
		assertEquals("redirect:/servico", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "servico.error.delete", exception);
	}
	
	
}
