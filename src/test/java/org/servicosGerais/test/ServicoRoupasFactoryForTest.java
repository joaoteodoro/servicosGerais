package org.servicosGerais.test;

import org.servicosGerais.bean.ServicoRoupas;

public class ServicoRoupasFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ServicoRoupas newServicoRoupas() {

		Integer id = mockValues.nextInteger();

		ServicoRoupas servicoRoupas = new ServicoRoupas();
		servicoRoupas.setId(id);
		return servicoRoupas;
	}
	
}
