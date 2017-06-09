package org.servicosGerais.test;

import org.servicosGerais.bean.jpa.ServicoEntity;

public class ServicoEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ServicoEntity newServicoEntity() {

		Integer id = mockValues.nextInteger();

		ServicoEntity servicoEntity = new ServicoEntity();
		servicoEntity.setId(id);
		return servicoEntity;
	}
	
}
