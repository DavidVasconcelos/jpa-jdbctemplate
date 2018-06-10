package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.mapper.CursoAlunoMapper;
import br.com.fiap.viewmodel.CursoAlunoViewModel;

public class JdbcCursoAlunoDao {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<CursoAlunoViewModel> listarAlunosENotas() {
		List<CursoAlunoViewModel> alunos = new ArrayList<>();
		try {
			alunos = this.jdbcTemplate.query(
					"SELECT A.NOME, CA.NOTA\r\n" +
					"FROM CURSO C JOIN CURSO_ALUNO CA ON C.ID = CA.IDCURSO\r\n" +
					"JOIN ALUNO A ON A.ID = CA.IDALUNO\r\n",
					new CursoAlunoMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alunos;
	}	

}
