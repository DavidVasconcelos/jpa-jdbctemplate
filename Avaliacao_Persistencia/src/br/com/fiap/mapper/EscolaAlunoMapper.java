package br.com.fiap.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.fiap.viewmodel.EscolaAlunoViewModel;


public class EscolaAlunoMapper implements RowMapper<EscolaAlunoViewModel> {

	@Override
	public EscolaAlunoViewModel mapRow(ResultSet rs, int arg1) throws SQLException {
		EscolaAlunoViewModel vm = new EscolaAlunoViewModel();
		vm.setNome(rs.getString("NOME"));
		vm.setMedia(rs.getDouble("NOTA"));
		return vm;
	}

}
