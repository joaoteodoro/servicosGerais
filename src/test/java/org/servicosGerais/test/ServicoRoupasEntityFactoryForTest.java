package org.servicosGerais.test;

import org.servicosGerais.bean.jpa.ServicoRoupasEntity;

public class ServicoRoupasEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ServicoRoupasEntity newServicoRoupasEntity() {

		Integer id = mockValues.nextInteger();

		ServicoRoupasEntity servicoRoupasEntity = new ServicoRoupasEntity();
		servicoRoupasEntity.setId(id);
		return servicoRoupasEntity;
	}
	
}
