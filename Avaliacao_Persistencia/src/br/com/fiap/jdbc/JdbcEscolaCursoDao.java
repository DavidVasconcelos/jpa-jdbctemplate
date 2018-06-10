package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.mapper.EscolaCursoMapper;
import br.com.fiap.viewmodel.EscolaCursoViewModel;

public class JdbcEscolaCursoDao {

	private JdbcTemplate jdbcTemplate;

	// propriedade: dataSource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<EscolaCursoViewModel> listarEscolasComCursos() {
		List<EscolaCursoViewModel> escolas = new ArrayList<>();
		try {
			escolas = this.jdbcTemplate.query(
					"SELECT COUNT(CURSOS) QUANTIDADE, AVG(QUANTIDADE) MEDIAALUNOS FROM\r\n" +
					"(SELECT C.ID CURSOS,COUNT(CA.IDALUNO) QUANTIDADE\r\n" +
					"FROM ESCOLA E JOIN CURSO C ON E.ID = C.IDESCOLA\r\n" +
					"JOIN CURSO_ALUNO CA ON C.ID = CA.IDCURSO\r\n" +
					"GROUP BY CURSOS) NESTED\r\n",
					new EscolaCursoMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return escolas;
	}

}
