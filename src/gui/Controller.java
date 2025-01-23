package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import repository.weatherInterval;
import service.Service;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @FXML
    private Button avgButton;

    @FXML
    private Button filterButton;

    @FXML
    private Button viewAllButton;

    @FXML
    private ListView<weatherInterval> weatherListView;
    ObservableList<weatherInterval>weatherIntervalObservableList;
    public void initialize(){

       resetObservableList();


    }
    private void resetObservableList() {
        List<weatherInterval> intervals = service.getAllWeatherIntervals();
        ObservableList<weatherInterval> observableIntervals = FXCollections.observableArrayList(intervals);
        weatherListView.setItems(observableIntervals);
    }
    private void showAlert(String error ) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }
    private String showInputDialog(String title, String prompt){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setContentText(prompt);
        return dialog.showAndWait().orElse(null);
    }



    @FXML
    void avgButton(ActionEvent event) {
        String start=showInputDialog("Average Temperature","Enter start hour: ");
        String end=showInputDialog("Average Temperature","Enter end hour: ");
        if(start!=null && end != null){
            try{
                int startH=Integer.parseInt(start);
                int endH=Integer.parseInt(end);
                double avgTemo=service.avgTemp(startH,endH);
                resetObservableList();
                if(Double.isNaN(avgTemo)){
                    showAlert("No intervals found");
                }else {
                    showAlert("Average:"+avgTemo);
                }
            }catch (NumberFormatException e){
                showAlert("Invalid input");
            }
        }

    }

    @FXML
    void filterButton(ActionEvent event) {
        String maxPrep=showInputDialog("Filter","Enter max precipitation probability: ");
        String minTemp=showInputDialog("Filter","Enter min temperature: ");
        if(maxPrep!=null && minTemp!=null){
            try{
                int maxPrecip=Integer.parseInt(maxPrep);
                int minTempe=Integer.parseInt(minTemp);
                List<weatherInterval>filtered=service.filterWeatherIntervals(maxPrecip,minTempe);
                resetObservableList();
                if(filtered.isEmpty()){
                    showAlert("No intervals found");
                }else{
                    weatherIntervalObservableList.setAll(filtered);
                }
            }catch (NumberFormatException e){
                showAlert("Invalid input");
            }
        }


    }

    @FXML
    void viewAllButton(ActionEvent event) {
        List<weatherInterval> allIntervals = service.getAllWeatherIntervals();
        resetObservableList();
        if (allIntervals.isEmpty()) {
            showAlert("No weather intervals found.");
        } else {
            weatherIntervalObservableList.setAll(allIntervals);
        }


    }
}
