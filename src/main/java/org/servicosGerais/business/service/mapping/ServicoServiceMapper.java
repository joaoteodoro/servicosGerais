/*
 * Created on 7 jun 2017 ( Time 11:29:14 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.servicosGerais.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.servicosGerais.bean.Servico;
import org.servicosGerais.bean.jpa.ServicoEntity;
import org.servicosGerais.bean.jpa.ClienteEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class ServicoServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public ServicoServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'ServicoEntity' to 'Servico'
	 * @param servicoEntity
	 */
	public Servico mapServicoEntityToServico(ServicoEntity servicoEntity) {
		if(servicoEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Servico servico = map(servicoEntity, Servico.class);

		//--- Link mapping ( link to Cliente )
		if(servicoEntity.getCliente() != null) {
			servico.setClienteId(servicoEntity.getCliente().getId());
		}
		return servico;
	}
	
	/**
	 * Mapping from 'Servico' to 'ServicoEntity'
	 * @param servico
	 * @param servicoEntity
	 */
	public void mapServicoToServicoEntity(Servico servico, ServicoEntity servicoEntity) {
		if(servico == null) {
			return;
		}

		//--- Generic mapping 
		map(servico, servicoEntity);

		//--- Link mapping ( link : servico )
		if( hasLinkToCliente(servico) ) {
			ClienteEntity cliente1 = new ClienteEntity();
			cliente1.setId( servico.getClienteId() );
			servicoEntity.setCliente( cliente1 );
		} else {
			servicoEntity.setCliente( null );
		}

	}
	
	/**
	 * Verify that Cliente id is valid.
	 * @param Cliente Cliente
	 * @return boolean
	 */
	private boolean hasLinkToCliente(Servico servico) {
		if(servico.getClienteId() != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}