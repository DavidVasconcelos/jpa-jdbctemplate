package br.com.fiap.helper;

import javax.persistence.EntityManager;

import br.com.fiap.entity.Escola;

public class EscolaHelper extends Helper<Escola> {

	public EscolaHelper(EntityManager em) {
		super(em);		
	}

}
