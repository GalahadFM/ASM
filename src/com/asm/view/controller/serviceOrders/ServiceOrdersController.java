package com.asm.view.controller.serviceOrders;

import com.asm.interactors.EmployeeInteractor;
import com.asm.interactors.OrderInteractor;
import com.asm.view.controller.EditEmployeeController;
import com.asm.view.controller.NewEmployeeController;
import com.asm.view.controller.properties.OrderProperty;
import com.asm.view.controller.properties.OrderProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServiceOrdersController  implements Initializable{



        private ObservableList<OrderProperty> orderData;
        private String currentUserID;
        private OrderInteractor interactor;
        private String baseURL = "http://localhost:8080";
        private ScrollPane mainScrollPane;

        // Anchor pane for forms
        @FXML
        private AnchorPane mainAnchorPane;

        // Clients table items
        @FXML private TableView<OrderProperty> clientsTable;
        @FXML private TableColumn<OrderProperty, String> columnID;
        @FXML private TableColumn<OrderProperty, String> columnClientName;
        @FXML private TableColumn<OrderProperty, String> columnAutomobileName;
        @FXML private TableColumn<OrderProperty, String> columnService;
        @FXML private TableColumn<OrderProperty, String> columnEmployee;

        @FXML private TableColumn<OrderProperty, String> columnRequiredTime;
        @FXML private TableColumn<OrderProperty, String> columnPrice;
        @FXML private TableColumn<OrderProperty, String> columnStatus;


        // Client Details labels
        @FXML private SplitPane clientDetailSplitPane;
        @FXML private AnchorPane clientDetailsPane;
        @FXML private VBox clientDetailsVBox;
        @FXML private VBox carDetailsVBox;
        @FXML private Label employeeNameDetail;
        @FXML private Label employeeEmailDetail;
        @FXML private Label employeePhoneDetail;
        private OrderProperty selectedEmployee;


        public ServiceOrdersController() {
            this.interactor = new OrderInteractor();
            try {
                this.orderData = FXCollections.observableArrayList(interactor.readAsOrderProperty());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public ObservableList<OrderProperty> getOrderData() {
            return orderData;
        }

        private void setUpTable() {
            clientsTable.setItems(getOrderData());
        }


        @Override
        public void initialize(URL location, ResourceBundle resources) {
            columnID.setCellValueFactory(cellData-> cellData.getValue().idProperty());
            columnClientName.setCellValueFactory(cellData -> cellData.getValue().clientProperty());
            columnAutomobileName.setCellValueFactory(cellData -> cellData.getValue().automobileProperty());
            columnEmployee.setCellValueFactory(cellData -> cellData.getValue().mechanicProperty());
            columnService.setCellValueFactory(cellData -> cellData.getValue().serviceProperty());
            columnRequiredTime.setCellValueFactory(cellData-> cellData.getValue().hourlyRequiredTimeProperty());
            columnPrice.setCellValueFactory(cellData-> cellData.getValue().priceProperty());
            columnStatus.setCellValueFactory(cellData-> cellData.getValue().statusProperty());
            showEmployeeDetails(null);

//        columnNSS.setCellValueFactory(cellData-> cellData.getValue().nssProperty());
            clientsTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showEmployeeDetails(newValue)
            );
            setUpTable();
        }



        public void newClientBtnOnClick(MouseEvent mouseEvent) throws IOException {
            System.out.println("New Client window!");
            newClientWindowVisible();
            //showNewClientForm(true);
        }

        private void showClientDetailsPane(boolean visible) {
            clientDetailsPane.setVisible(visible);
            clientDetailsPane.setManaged(visible);
        }

        public void showEmployeeDetails(OrderProperty OrderProperty) {
            System.out.println("Here on earth");
            if (OrderProperty != null) {
                this.selectedEmployee = OrderProperty;
                showClientDetailsPane(true);
                carDetailsVBox.getChildren().clear();
                clientDetailSplitPane.setDividerPositions(new double[]{0.5});
                employeeNameDetail.setText( OrderProperty.getClient());
                employeeEmailDetail.setText(OrderProperty.getAutomobile());
                employeePhoneDetail.setText(OrderProperty.getMechanic());
            } else {
                showClientDetailsPane(false);
                clientDetailSplitPane.setDividerPositions(new double[]{1});
                employeeNameDetail.setText("");
                employeeEmailDetail.setText("");
                employeePhoneDetail.setText("");
                currentUserID = "";
            }
        }




        public void closeClientDetails(MouseEvent mouseEvent) {
            showClientDetailsPane(false);
            clientDetailSplitPane.setDividerPositions(new double[]{1});
            employeeNameDetail.setText("");
            employeeEmailDetail.setText("");
            employeePhoneDetail.setText("");
            currentUserID = "";
        }

        public void openClientEditWindow() throws IOException {
            Node editPane = (Node)FXMLLoader.load(getClass().getResource("/fxml/client_views/edit.fxml"));
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(editPane);
        }

        public void newClientWindowVisible() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/employee_views/new.fxml"));
            Parent root = loader.load();
            NewEmployeeController newEmployeeController= loader.getController();
            newEmployeeController.init(mainScrollPane);
            this.mainScrollPane.setContent(root);
            mainScrollPane.setFitToWidth(true);
        }


        public void deleteClientOnClick(MouseEvent mouseEvent) {
//            this.interactor.deleteEmployee(selectedEmployee.getId());
//            System.out.println(currentUserID);
        }

        public void goToEditEmployee(ActionEvent actionEvent) throws IOException{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/employee_views/edit.fxml"));
//            Parent root = loader.load();
//            EditEmployeeController editEmployeeController = loader.getController();
//            editEmployeeController.init(mainScrollPane, this.selectedEmployee);
//            this.mainScrollPane.setContent(root);
//            mainScrollPane.setFitToWidth(true);

        }



    public void newOrderClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/services_views/newServiceOrder.fxml"));
        Parent root = loader.load();
        NewServiceOrderController newServiceOrderController= loader.getController();
        newServiceOrderController.init(mainScrollPane);
        this.mainScrollPane.setContent(root);
        mainScrollPane.setFitToWidth(true);
        System.out.println("New Order");
    }

    public void init(ScrollPane mainScrollPane) {
        this.mainScrollPane = mainScrollPane;
    }
}
