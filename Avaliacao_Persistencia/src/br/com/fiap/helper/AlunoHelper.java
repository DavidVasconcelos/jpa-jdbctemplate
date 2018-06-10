package br.com.fiap.helper;

import javax.persistence.EntityManager;

import br.com.fiap.entity.Aluno;

public class AlunoHelper extends Helper<Aluno> {

	public AlunoHelper(EntityManager em) {
		super(em);		
	}

}
