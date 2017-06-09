package org.servicosGerais.test;

import org.servicosGerais.bean.Cliente;

public class ClienteFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Cliente newCliente() {

		Integer id = mockValues.nextInteger();

		Cliente cliente = new Cliente();
		cliente.setId(id);
		return cliente;
	}
	
}
