package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.mapper.EscolaAlunoMapper;
import br.com.fiap.viewmodel.EscolaAlunoViewModel;

public class JdbcEscolaAlunoDao {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<EscolaAlunoViewModel> listarEscolasComCursos() {
		List<EscolaAlunoViewModel> alunos = new ArrayList<>();
		try {
			alunos = this.jdbcTemplate.query(
					"SELECT A.NOME, AVG(CA.NOTA) MEDIA\r\n" +
					"FROM ESCOLA E JOIN CURSO C ON E.ID = C.IDESCOLA\r\n" +
					"JOIN CURSO_ALUNO CA ON C.ID = CA.IDCURSO\r\n" +
					"JOIN ALUNO A ON CA.IDALUNO = A.ID\r\n" +
					"GROUP BY NOME\r\n",
					new EscolaAlunoMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alunos;
	}

}
