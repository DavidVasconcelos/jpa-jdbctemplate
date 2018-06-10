package br.com.fiap.app;

import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.fiap.entity.Aluno;
import br.com.fiap.entity.Curso;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.helper.AlunoHelper;
import br.com.fiap.helper.CursoHelper;
import br.com.fiap.jdbc.JdbcCursoNotaDao;
import br.com.fiap.jdbc.JdbcEscolaCursoDao;
import br.com.fiap.viewmodel.CursoNotaViewModel;
import br.com.fiap.viewmodel.EscolaCursoViewModel;

public class AppAluno {

	private static final String PERSISTENCEUNIT = "jpaPU";

	public static void main(String[] args) {
//		incluirAluno();
//		listarAlunos();
//		incluirAlunoEmCurso();
//		incluirNotaAluno();
		listarCursosENotas();

	}

	private static void incluirAluno() {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			while (true) {

				AlunoHelper helper = new AlunoHelper(new ConnectionFactory().getEntityManager(PERSISTENCEUNIT));

				int opcao = JOptionPane.showConfirmDialog(null, "Deseja incluir um aluno?", "Confirmação",
						JOptionPane.YES_NO_OPTION);

				if (opcao == JOptionPane.NO_OPTION)
					break;

				String nome = JOptionPane.showInputDialog("Nome do aluno: ");

				Aluno aluno = new Aluno();
				aluno.setNome(nome);
				aluno.setMatricula(new Random().nextInt(1000000));

				helper.salvar(aluno);

				JOptionPane.showMessageDialog(null, "Aluno incluído com sucesso", "Mensagem",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}

	private static void listarAlunos() {

		AlunoHelper helper = new AlunoHelper(new ConnectionFactory().getEntityManager(PERSISTENCEUNIT));

		List<Aluno> listarTodos = helper.listarTodos();

		for (Aluno aluno : listarTodos) {
			System.out.print(aluno.getId() + " ");
			System.out.println(aluno.getNome() + " matricula: " + aluno.getMatricula());
		}

	}

	private static void incluirAlunoEmCurso() {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			while (true) {

				AlunoHelper helper = new AlunoHelper(new ConnectionFactory().getEntityManager(PERSISTENCEUNIT));
				CursoHelper helperCurso = new CursoHelper(new ConnectionFactory().getEntityManager(PERSISTENCEUNIT));

				int opcao = JOptionPane.showConfirmDialog(null, "Deseja adicionar o aluno em um Curso?", "Confirmação",
						JOptionPane.YES_NO_OPTION);

				if (opcao == JOptionPane.NO_OPTION)
					break;

				List<Aluno> listaAluno = helper.listarTodos();

				Aluno aluno = (Aluno) JOptionPane.showInputDialog(null, "Selecione um aluno", "Alunos",
						JOptionPane.DEFAULT_OPTION, null, listaAluno.toArray(), null);

				List<Curso> listaCursos = helperCurso.listarTodos();

				for (Curso cursoAluno : aluno.getCursos()) {

					if (listaCursos.contains(cursoAluno))
						listaCursos.remove(cursoAluno);
				}

				if (listaCursos.stream().count() == 0) {
					JOptionPane.showMessageDialog(null, "O Aluno já está matriculado em todos os cursos", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					break;
				}

				Curso curso = (Curso) JOptionPane.showInputDialog(null, "Selecione um curso", "Cursos",
						JOptionPane.DEFAULT_OPTION, null, listaCursos.toArray(), null);

				curso.getAlunos().add(aluno);
				aluno.getCursos().add(curso);
				helperCurso.atualizar(curso);

				JOptionPane.showMessageDialog(null, "Aluno incluído no curso com sucesso", "Mensagem",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}

	private static void incluirNotaAluno() {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			while (true) {

				AlunoHelper helper = new AlunoHelper(new ConnectionFactory().getEntityManager(PERSISTENCEUNIT));
				double nota = 0d;

				int opcao = JOptionPane.showConfirmDialog(null, "Deseja dar a nota de um aluno para um Curso?",
						"Confirmação", JOptionPane.YES_NO_OPTION);

				if (opcao == JOptionPane.NO_OPTION)
					break;

				List<Aluno> listaAluno = helper.listarTodos();

				Aluno aluno = (Aluno) JOptionPane.showInputDialog(null, "Selecione um aluno", "Alunos",
						JOptionPane.DEFAULT_OPTION, null, listaAluno.toArray(), null);

				Set<Curso> setDeCursos = aluno.getCursos();
				
				if (setDeCursos.stream().count() == 0) {
					JOptionPane.showMessageDialog(null, "O Aluno não está matriculado em nenhum curso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					continue;
				}

				Curso curso = (Curso) JOptionPane.showInputDialog(null, "Selecione um curso", "Cursos",
						JOptionPane.DEFAULT_OPTION, null, setDeCursos.toArray(), null);

				try {

					nota = Double.parseDouble(JOptionPane.showInputDialog("Digite a nota do aluno: "));

					if (nota <= 0 || nota > 10)
						throw new IllegalArgumentException("As notas podem ser apenas de 1 a 10");

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "ERRO: " + "Somente é permitido números", "Erro",
							JOptionPane.ERROR_MESSAGE);
					continue;
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					continue;
				}
				
				ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
				
				((JdbcCursoNotaDao) context.getBean("jdbcCursoNotaDao")).incluirNota(curso, aluno, nota);

				JOptionPane.showMessageDialog(null, "Nota do aluno incluída com sucesso", "Mensagem",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	private static void listarCursosENotas() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("beanJdbc.xml");
			List<CursoNotaViewModel> cursos = ((JdbcCursoNotaDao) context.getBean("jdbcCursoNotaDao"))
					.listarCursosENota(1);
			for (CursoNotaViewModel vm : cursos) {
				System.out.print("Nome do Curso: " + vm.getDescricao());
				System.out.println(", Nota : " + vm.getNota());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
