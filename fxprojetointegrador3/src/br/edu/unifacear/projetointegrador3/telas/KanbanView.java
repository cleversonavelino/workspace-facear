package br.edu.unifacear.projetointegrador3.telas;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.unifacear.projetointegrador3.entidade.Fase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KanbanView extends Application implements Initializable {
	
	private List<Fase> fases = new ArrayList<Fase>();	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {				
	}
	
	@SuppressWarnings("unchecked")
	@FXML private void mover(ActionEvent event) {
		ComboBox<Fase> comboBox = (ComboBox<Fase>) event.getSource();
		Fase fase = comboBox.getSelectionModel().getSelectedItem();
		
		//EM TASK (PANE) O COMBOBOX ESTÁ
		Pane task = (Pane) comboBox.getParent(); 	
		
		//EM QUAL VBOX (BACKLOG, DONE ETC) A TASK (PANE) ESTÁ
		VBox vbox = (VBox) task.getParent();
		vbox.getChildren().remove(task);

		//HBOX PRINCIPAL ONDE CONTÉM TODOS AS FASES (VBOX)
		HBox principal = (HBox) vbox.getParent();
		
		//FASE ONDE A TASK ESTÁ
		if (fase.getId() == 1) {
			//ADICIONAR A TASK A FASE CORRETA (VBOX)			
			((VBox)principal.getChildren().get(0)).getChildren().add(task);
		}
		
		if (fase.getId() == 2) {
			((VBox)principal.getChildren().get(1)).getChildren().add(task);
		}
		
		if (fase.getId() == 3) {
			((VBox)principal.getChildren().get(2)).getChildren().add(task);
		}
		
		if (fase.getId() == 4) {
			((VBox)principal.getChildren().get(3)).getChildren().add(task);
		}		
	}

	@SuppressWarnings("unchecked")
	private void criarTask(Pane principal) {		
		FXMLLoader loader = new FXMLLoader(getClass().
				getResource("/br/edu/unifacear/projetointegrador3/view/task.fxml"));		
		Pane task;
		try {
			task = (Pane)loader.load();
			ObservableList<Fase> data = FXCollections.observableArrayList(fases);
			((ComboBox<Fase>)task.getChildren().get(1)).setItems(data);			
			
			((VBox)principal.getChildren().get(0)).getChildren().add(task);		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void criarFases() {
		Fase faseBackLog = new Fase();
		faseBackLog.setId(1);
		faseBackLog.setNome("BackLog");
		
		Fase faseDoing = new Fase();
		faseDoing.setId(2);
		faseDoing.setNome("Doing");
		
		Fase faseTest = new Fase();
		faseTest.setId(3);
		faseTest.setNome("Test");	
		
		Fase faseDone = new Fase();
		faseDone.setId(4);
		faseDone.setNome("Done");
		
		fases.add(faseBackLog);
		fases.add(faseDoing);
		fases.add(faseTest);
		fases.add(faseDone);
	}	

	@Override
	public void start(Stage arg0) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/br/edu/unifacear/projetointegrador3/view/Kanban.fxml"));

			Pane principal = loader.load();
			Scene scene = new Scene(principal);

			arg0.setScene(scene);
			arg0.show();			
			
			criarFases();	
			criarTask((HBox)principal.getChildren().get(0));
			criarTask((HBox)principal.getChildren().get(0));
			criarTask((HBox)principal.getChildren().get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
