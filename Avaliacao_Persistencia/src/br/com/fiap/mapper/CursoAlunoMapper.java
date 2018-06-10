package br.com.fiap.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.fiap.viewmodel.CursoAlunoViewModel;

public class CursoAlunoMapper implements RowMapper<CursoAlunoViewModel> {

	@Override
	public CursoAlunoViewModel mapRow(ResultSet rs, int arg1) throws SQLException {
		CursoAlunoViewModel vm = new CursoAlunoViewModel();
		vm.setNome(rs.getString("NOME"));
		vm.setNota(rs.getDouble("NOTA"));		
		return vm;
	}

}
