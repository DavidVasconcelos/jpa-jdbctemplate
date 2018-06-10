package br.com.fiap.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import br.com.fiap.entity.Aluno;
import br.com.fiap.entity.Curso;
import br.com.fiap.mapper.CursoNotaMapper;
import br.com.fiap.viewmodel.CursoNotaViewModel;

public class JdbcCursoNotaDao {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<CursoNotaViewModel> listarCursosEMedias() {
		List<CursoNotaViewModel> cursos = new ArrayList<>();
		try {
			cursos = this.jdbcTemplate.query(
					"SELECT C.DESCRICAO,AVG(CA.NOTA) NOTA\r\n" +
					"FROM CURSO C JOIN CURSO_ALUNO CA ON C.ID = CA.IDCURSO\r\n" +
					"GROUP BY DESCRICAO\r\n",
					new CursoNotaMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursos;
	}
	
	// método para incluir uma escola
	public void incluirNota(Curso curso, Aluno aluno, double nota) throws Exception {
		try {
			String sql = "UPDATE CURSO_ALUNO SET NOTA = ? WHERE IDCURSO = ? AND IDALUNO = ?";
			this.jdbcTemplate.update(sql, nota, curso.getId(), aluno.getId());
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<CursoNotaViewModel> listarCursosENota(int alunoId) {
		List<CursoNotaViewModel> cursos = new ArrayList<>();
		try {
			cursos = this.jdbcTemplate.query(
					"SELECT C.DESCRICAO, CA.NOTA\r\n" +
					"FROM CURSO C JOIN CURSO_ALUNO CA ON C.ID = CA.IDCURSO\r\n" +
					"WHERE CA.IDALUNO = ?\r\n", new Integer[] { alunoId },
					new CursoNotaMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursos;
	}

}
