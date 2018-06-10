package br.com.fiap.app;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.fiap.entity.Escola;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.helper.EscolaHelper;
import br.com.fiap.jdbc.JdbcEscolaAlunoDao;
import br.com.fiap.jdbc.JdbcEscolaCursoDao;
import br.com.fiap.viewmodel.EscolaAlunoViewModel;
import br.com.fiap.viewmodel.EscolaCursoViewModel;

public class AppEscola {
	
	private static final String PERSISTENCEUNIT = "jpaPU";

	public static void main(String[] args) {
//		incluirEscola();
//		listarEscolas();
//		listarAlunosMedias();
		listarQuantidadeDeCursosEMediaDeAlunos();

	}

	private static void incluirEscola() {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			while (true) {
				
				EscolaHelper helper = new EscolaHelper(new ConnectionFactory().getEntityManager(PERSISTENCEUNIT));
				
				int opcao = JOptionPane.showConfirmDialog(null, "Deseja incluir uma escola?", "Confirmação",
						JOptionPane.YES_NO_OPTION);

				if (opcao == JOptionPane.NO_OPTION)
					break;

				String descricao = JOptionPane.showInputDialog("Descrição da escola: ");
				String endereco = JOptionPane.showInputDialog("Endereco da escola: ");
				

				Escola escola = new Escola();
				escola.setDescricao(descricao);
				escola.setEndereco(endereco);
				
				helper.salvar(escola);

				JOptionPane.showMessageDialog(null, "Escola incluído com sucesso", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}

	private static void listarAlunosMedias() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<EscolaAlunoViewModel> alunos = ((JdbcEscolaAlunoDao) context.getBean("jdbcEscolaAlunoDao"))
					.listarEscolasComCursos();			
			
			for (EscolaAlunoViewModel vm : alunos) {
				System.out.print("Aluno: " + vm.getNome());
				System.out.println(", Média escolar: " + vm.getMedia());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void listarQuantidadeDeCursosEMediaDeAlunos() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<EscolaCursoViewModel> escolas = ((JdbcEscolaCursoDao) context.getBean("jdbcEscolaCursoDao"))
					.listarEscolasComCursos();
			for (EscolaCursoViewModel vm : escolas) {
				System.out.print("Quantidade de Cursos: " + vm.getQuantidadeCursos());
				System.out.println(", Quantidade média de alunos por Curso: " + vm.getQuantidadeMediaAlunos());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
