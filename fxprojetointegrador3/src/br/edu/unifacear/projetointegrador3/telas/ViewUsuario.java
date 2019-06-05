package br.edu.unifacear.projetointegrador3.telas;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.unifacear.projetointegrador3.dao.Dao;
import br.edu.unifacear.projetointegrador3.dao.GenericDao;
import br.edu.unifacear.projetointegrador3.entidade.Usuario;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewUsuario extends Application implements Initializable {

	@FXML TableView<Usuario> tabelaUsuario;
	@FXML TableColumn<Usuario,Integer> id;
	@FXML TableColumn<Usuario,String> nome;
	@FXML TableColumn<Usuario,String> login;

	@FXML Button btnSalvar;
	@FXML TextField txtId;
	@FXML TextField txtNome;
	@FXML TextField txtLogin;
	@FXML TextField txtSenha;
	
	@FXML Button carregarArquivo;
	
	@FXML Label txtNomeDoArquivo;

	private Usuario usuario;
	private byte[] arquivo;
	private String nomeDoArquivo;
	
	@FXML public void download() {
		try {
			if (arquivo != null && arquivo.length > 0) {
				File file = new File("c:\\temp\\" + nomeDoArquivo);
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(arquivo);

				Desktop.getDesktop().open(file);
				fos.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@FXML public void carregarArquivo(ActionEvent event) {
		
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Abrir");
			File file = fileChooser.showOpenDialog(new Stage());
			arquivo = new byte[(int) file.length()];
			nomeDoArquivo = file.getName();
			txtNomeDoArquivo.setText(nomeDoArquivo);
			
			FileInputStream fis = new FileInputStream(file);
			fis.read(arquivo);
			fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
	
	@FXML public void salvar(ActionEvent event) {
		Usuario usuario = new Usuario();
		usuario.setNome(txtNome.getText());
		usuario.setLogin(txtLogin.getText());
		usuario.setSenha(txtSenha.getText());
		usuario.setNomeDoArquivo(nomeDoArquivo);
		usuario.setArquivo(arquivo);

		Dao<Usuario> usuarioDao = new GenericDao<Usuario>();
		
		if (txtId.getText().isEmpty()) {
			usuarioDao.inserir(usuario);
		} else {
			usuario.setId(Integer.valueOf(txtId.getText()));
			usuarioDao.alterar(usuario);
		}

		List<Usuario> usuarios = new GenericDao<Usuario>().listarTodos(Usuario.class);
		ObservableList<Usuario> data = FXCollections.observableArrayList(usuarios);
		tabelaUsuario.setItems(data);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		try {
			FXMLLoader loader = new 
					FXMLLoader(getClass().
							getResource("/br/edu/unifacear/projetointegrador3/view/ViewUsuario.fxml"));

			Scene scene = new Scene(loader.load());

			arg0.setScene(scene);
			arg0.show();
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		id.setCellValueFactory(
				new PropertyValueFactory<>("id"));
		nome.setCellValueFactory(
				new PropertyValueFactory<>("nome"));
		login.setCellValueFactory(
				new PropertyValueFactory<>("login"));

		List<Usuario> usuarios = new GenericDao<Usuario>().listarTodos(Usuario.class);
		ObservableList<Usuario> data = FXCollections.observableArrayList(usuarios);
		tabelaUsuario.setItems(data);	

		tabelaUsuario.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY) && 
						event.getClickCount() == 2){
					usuario = tabelaUsuario.getSelectionModel().getSelectedItem();
					
					txtId.setText(usuario.getId().toString());
					txtNome.setText(usuario.getNome());
					txtLogin.setText(usuario.getLogin());	
					txtNomeDoArquivo.setText(usuario.getNomeDoArquivo());
					arquivo = usuario.getArquivo();
					nomeDoArquivo = usuario.getNomeDoArquivo();
				}

			}
		});



	}

}
