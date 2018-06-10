package br.com.fiap.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {
	
	public EntityManager getEntityManager(String persistenceUnit) {		
		
		try {
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
			return emf.createEntityManager();
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao acessar a base de dados");
		}

		
	}

}
