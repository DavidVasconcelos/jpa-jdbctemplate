package br.com.fiap.app;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.fiap.entity.Curso;
import br.com.fiap.entity.Escola;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.helper.CursoHelper;
import br.com.fiap.helper.EscolaHelper;
import br.com.fiap.jdbc.JdbcCursoAlunoDao;
import br.com.fiap.jdbc.JdbcCursoNotaDao;
import br.com.fiap.viewmodel.CursoAlunoViewModel;
import br.com.fiap.viewmodel.CursoNotaViewModel;

public class AppCurso {
	
	private static final String PERSISTENCEUNIT = "jpaPU";

	public static void main(String[] args) {
//		incluirCurso();
//		listarCursos();
//		listarAlunosESuasNotas();
		listarCursosENotasMedias();

	}

	private static void incluirCurso() {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			while (true) {
				
				EscolaHelper helper = new EscolaHelper(new ConnectionFactory().getEntityManager(PERSISTENCEUNIT));				
				
				int opcao = JOptionPane.showConfirmDialog(null, "Deseja incluir um Curso?", "Confirmação",
						JOptionPane.YES_NO_OPTION);

				if (opcao == JOptionPane.NO_OPTION)
					break;

				String descricao = JOptionPane.showInputDialog("Descrição do Curso: ");				
								
				List<Escola> listaEscola = helper.listarTodos();

				Escola escola = (Escola) JOptionPane.showInputDialog(null, "Selecione uma escola", "Escolas",
						JOptionPane.DEFAULT_OPTION, null, listaEscola.toArray(), null);
				
				
				Curso curso = new Curso();
				curso.setDescricao(descricao);	
				curso.setEscola(escola);
				
				escola.getCursos().add(curso);				
				helper.salvar(escola);

				JOptionPane.showMessageDialog(null, "Curso incluído com sucesso", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}

	private static void listarAlunosESuasNotas() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<CursoAlunoViewModel> alunos = ((JdbcCursoAlunoDao) context.getBean("jdbcCursoAlunoDao"))
					.listarAlunosENotas();
			for (CursoAlunoViewModel vm : alunos) {
				System.out.print("Nome do Aluno: " + vm.getNome());
				System.out.println(", Nota: " + vm.getNota());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void listarCursosENotasMedias() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<CursoNotaViewModel> cursos = ((JdbcCursoNotaDao) context.getBean("jdbcCursoNotaDao"))
					.listarCursosEMedias();
			for (CursoNotaViewModel vm : cursos) {
				System.out.print("Nome do Curso: " + vm.getDescricao());
				System.out.println(", Nota média da sala: " + vm.getNota());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
