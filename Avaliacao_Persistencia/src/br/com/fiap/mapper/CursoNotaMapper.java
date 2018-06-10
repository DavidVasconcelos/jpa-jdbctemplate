package br.com.fiap.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.fiap.viewmodel.CursoNotaViewModel;

public class CursoNotaMapper implements RowMapper<CursoNotaViewModel> {

	@Override
	public CursoNotaViewModel mapRow(ResultSet rs, int arg1) throws SQLException {
		CursoNotaViewModel vm = new CursoNotaViewModel();
		vm.setDescricao(rs.getString("DESCRICAO"));
		vm.setNota(rs.getDouble("NOTA"));
		return vm;
	}

}
