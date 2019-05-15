package br.edu.unifacear.projetointegrador3.telas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.unifacear.projetointegrador3.dao.GenericDao;
import br.edu.unifacear.projetointegrador3.entidade.Usuario;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewUsuario extends Application implements Initializable {

	@FXML TableView<Usuario> tabelaUsuario;
	@FXML TableColumn<Usuario,Integer> id;
	@FXML TableColumn<Usuario,String> nome;
	@FXML TableColumn<Usuario,String> login;

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
	}

}
