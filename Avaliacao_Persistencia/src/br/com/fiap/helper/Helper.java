package br.com.fiap.helper;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public abstract class Helper<T> {

	private EntityManager em;

	public Helper(EntityManager em) {
		this.em = em;
	}

	public void salvar(T entidade) throws Exception {
		try {
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			em.close();
		}
	}

	public void deletar(T entidade) throws Exception {
		try {
			em.getTransaction().begin();
			em.remove(entidade);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			em.close();
		}
	}

	public void atualizar(T entidade) throws Exception {
		try {
			em.getTransaction().begin();
			em.merge(entidade);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> listarTodos() {
		Class<? extends T> clazz = (Class<? extends T>) this.getClass();		
		Query query = em.createNamedQuery(clazz.getSimpleName().replaceAll("Helper", "") + ".findAll");
		return query.getResultList();
	}

}
