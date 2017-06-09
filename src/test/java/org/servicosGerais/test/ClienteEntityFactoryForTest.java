package org.servicosGerais.test;

import org.servicosGerais.bean.jpa.ClienteEntity;

public class ClienteEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ClienteEntity newClienteEntity() {

		Integer id = mockValues.nextInteger();

		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setId(id);
		return clienteEntity;
	}
	
}
