package org.servicosGerais.test;

import org.servicosGerais.bean.Servico;

public class ServicoFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Servico newServico() {

		Integer id = mockValues.nextInteger();

		Servico servico = new Servico();
		servico.setId(id);
		return servico;
	}
	
}
