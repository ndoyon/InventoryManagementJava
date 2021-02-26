package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Class MainScreenController implements initializable and starts scene for main screen of Inventory
 * management system. 
 *
 * @author nick
 */

public class MainScreenController implements Initializable {

    private ObservableList<Part> partInv = FXCollections.observableArrayList();
    private ObservableList<Product> productInv = FXCollections.observableArrayList();
    private ObservableList<Part> partsInvSearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInvSearch = FXCollections.observableArrayList();
    
    Inventory inv;
    
    @FXML
    private TableView<Part> tableViewPart;
    @FXML
    private TableView<Product> tableViewProduct;
    
    
    @FXML
    private TextField searchPartField;
    @FXML
    private TextField searchProductField;
    @FXML
    private Button modifyProductButton;
    @FXML
    private AnchorPane clearProductButton;
    @FXML
    private Button modifyPartsButton;
    @FXML
    private Button clearPartsButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button addPartButton;
    @FXML
    private Button searchPartButton;
    @FXML
    private Button searchProductButton;
    @FXML
    private Button exitButton;
    @FXML
    private TableColumn<?, ?> partIDCol;
    @FXML
    private TableColumn<?, ?> partNameCol;
    @FXML
    private TableColumn<?, ?> partCountCol;
    @FXML
    private TableColumn<?, ?> prodIDCol;
    @FXML
    private TableColumn<?, ?> prodNameCol;
    @FXML
    private TableColumn<?, ?> prodCountCol;
    @FXML
    private Button deleteProducts;
   


    
    /**Constructor MainScreenController inventory
     *@param inv Inventory
     */
    
    public MainScreenController(Inventory inv) {
        this.inv = inv;
    
    }
    
    
    /**
     * Method initialize Initializes the controller class and generates Part table and Product Table. 
     * @param url URL
     * @param rb ResourceBundle
     */
    
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        generatePartTable();
        generateProductTable();
    }

    /** Method generatePartTable generates part table view and creates table column for price.
     */
    
    private void generatePartTable() {
        partInv.setAll(inv.getAllParts());
        TableColumn<Part, Double> costCol = formatPrice();
        tableViewPart.getColumns().addAll(costCol);
        tableViewPart.setItems(partInv);
        tableViewPart.refresh();
    }
    
    /** Method generateProductTable generates product table view and creates table column for price.
     */
    
    private void generateProductTable() {
        productInv.setAll(inv.getAllProducts());
        TableColumn<Product, Double> costCol = formatPrice();
        tableViewProduct.getColumns().addAll(costCol);
        tableViewProduct.setItems(productInv);
        tableViewProduct.refresh();
    }

/** Method modifyProductScreen scene change to Modify Product on mouse click. 
*@param event MouseEvent on click changes fxml scene
*@return product selected
*/
    
    @FXML
    private void modifyProductsScreen(MouseEvent event) {
        try {
            Product productSelected = tableViewProduct.getSelectionModel().getSelectedItem();
            if (productInv.isEmpty()) {
                errorWindow(1);
                return;
            }
            if (!productInv.isEmpty() && productSelected == null) {
                errorWindow(2);
                return;

            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
                ModifyProductController controller = new ModifyProductController(inv, productSelected);

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } catch (IOException e) {

        }
    }

    /** Method deleteProducts deletes product highlighted in search by delete button click only if its empty. 
     *@param event Mouse event on click deletes product highlighted in table view
     *@return return deleted or infowindow on part in product.
     */
    
    @FXML
    private void deleteProducts(MouseEvent event) {
        {
        boolean deleted = false;
        Product removeProduct = tableViewProduct.getSelectionModel().getSelectedItem();
        if (productInv.isEmpty()) {
            errorWindow(1);
            return;
        }
        if (!productInv.isEmpty() && removeProduct == null) {
            errorWindow(2);
            return;
        }
        if (removeProduct.getPartsListSize() > 0) {
            boolean confirm = confirmWarning(removeProduct.getProdName());
             {
                return;
            }
        }  else {
            boolean confirm = confirmationWindow(removeProduct.getProdName());
            if (!confirm) {
                return;

            }
        }
        inv.removeProduct(removeProduct.getProdID());
        productInv.remove(removeProduct);
        tableViewProduct.setItems(productInv);
        tableViewProduct.refresh();
    }
    }      
      

    /**Method modifyPartsScreen mouse click to modify parts scene. 
     *@param event MouseEvent click mouse to change scenes
     *@return part selected on scene change into text fields
     */
    
    @FXML
    private void modifyPartsScreen(MouseEvent event) {
        try {
            Part selected = tableViewPart.getSelectionModel().getSelectedItem();
            if (partInv.isEmpty()) {
                errorWindow(1);
                return;
            }
            if (!partInv.isEmpty() && selected == null) {
                errorWindow(2);
                return;
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPart.fxml"));
                ModifyPartController controller = new ModifyPartController(inv, selected);

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
               
            }
        } catch (IOException e) {

        }
    }

    /**Method clearPartsSearch deletes highlighted part in table view.
     *@param event MouseEvent click delete to remove part
     */
    
   
    @FXML
    private void clearPartsSearch(MouseEvent event) {
         Part removePart = tableViewPart.getSelectionModel().getSelectedItem();
        if (partInv.isEmpty()) {
            errorWindow(1);
            return;
        }
        if (!partInv.isEmpty() && removePart == null) {
            errorWindow(2);
            return;
        } else {
            boolean confirm = confirmationWindow(removePart.getName());
            if (!confirm) {
                return;
            }
            inv.deletePart(removePart);
            partInv.remove(removePart);
            tableViewPart.refresh();

        }
    }

    /** Method addProductsScreen changes scene to add product scene. 
     *@param event MouseEvent click the add button to change scene.
     */
    
    @FXML
    private void addProductsScreen(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddProduct.fxml"));
            AddProductController controller = new AddProductController(inv);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {

        }
       
    }

    /** Method addPartScreen changes scene to add part scene. 
     *@param event MouseEvent click the add button to change scene.
     */
    
    @FXML
    private void addPartScreen(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddPart.fxml"));
            AddPartController controller = new AddPartController(inv);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {

        }
    }

   /**Method searchPart searches for part when mouse click on button. 
    *@param event MouseEvent searches part and displays in table view
    */
    
    @FXML
    private void searchPart(MouseEvent event) {
         if (!searchPartField.getText().trim().isEmpty()) {
            tableViewPart.setItems(inv.lookUpPart(searchPartField.getText().trim()));
            tableViewPart.refresh();
        }
    }
    
    /**Method searchforProduct searches for product when mouse click on button. 
    *@param event MouseEvent searches product and displays in table view
    */
    
     @FXML
    private void searchForProduct(MouseEvent event
    ) {
        if (!searchProductField.getText().trim().isEmpty()) {
            productInvSearch.clear();
            for (Product prod : inv.getAllProducts()) {
                if (prod.getProdName().contains(searchProductField.getText().trim())) {
                    productInvSearch.add(prod);
                }
            }
            tableViewProduct.setItems(productInvSearch);
            tableViewProduct.refresh();
        }
    }
    
    /** Method clearText clears the text in the search field for both Product and Part when clicked
     *@param event MouseEvent clears text on click
     */
    
    @FXML
    void clearText(MouseEvent event
    ) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
        if (searchPartField == field) {
            if (partInv.size() != 0) {
                tableViewPart.setItems(partInv);
            }
        }
        if (searchProductField == field) {
            if (productInv.size() != 0) {
                tableViewProduct.setItems(productInv);
            }
        }
    }
    
    /**Method errorWindow alerts error when selection is empty or invalid 
     *@param code int integer code for alert
     */
    
    private void errorWindow(int code) {
        if (code == 1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Inventory!");
            alert.setContentText("There's nothing to select!");
            alert.showAndWait();
        }
        if (code == 2) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("You must select an item!");
            alert.showAndWait();
        }

    }

    /** Method confirmationWindow confirming delete part changes with alert. 
     *@param name String 
     */
    
    private boolean confirmationWindow(String name) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete part");
        alert.setHeaderText("Are you sure you want to delete: " + name);
        alert.setContentText("Click ok to confirm");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    
    }
    
    /** Method confirmationWarning confirm warning product cannot delete with part inside. 
     *@param name String 
     */
    
    private boolean confirmWarning(String name) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Delete product");
        alert.setHeaderText("You cannot delete the product: " + name + " still has parts assigned to it!");
        alert.setContentText("Click ok to confirm");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
    /** Method infoWindow information window showing confirmation or error. 
     *@param name String 
     */
    
    private void infoWindow(int code, String name) {
        if (code != 2) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmed");
            alert.setHeaderText(null);
            alert.setContentText(name + " has been deleted!");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There was an error!");
        }
    }

     /** Method formatPrice formats price for newly created table column
     */
    
    private <T> TableColumn<T, Double> formatPrice() {
        TableColumn<T, Double> costCol = new TableColumn("Price");
        costCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        costCol.setCellFactory((TableColumn<T, Double> column) -> {
            return new TableCell<T, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    if (!empty) {
                        setText("$" + String.format("%10.2f", item));
                    }
                    else{
                        setText("");
                    }
                }
            };
        });
        return costCol;
    }

    /** Method exitButtonAction exit button when clicked closes program
     *@param event MouseEvent platform.exit
     */
    
    @FXML
    private void exitButtonAction(MouseEvent event) {
        Platform.exit();
    }


}
