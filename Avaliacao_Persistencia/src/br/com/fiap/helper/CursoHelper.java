package br.com.fiap.helper;

import javax.persistence.EntityManager;

import br.com.fiap.entity.Curso;

public class CursoHelper extends Helper<Curso> {

	public CursoHelper(EntityManager em) {
		super(em);		
	}

}
