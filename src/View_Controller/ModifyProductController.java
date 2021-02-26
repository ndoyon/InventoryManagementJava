
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
import javafx.stage.Stage;

/**
 * Class ModifyProductController implements initializable and starts Modify Product scene. 
 *
 * @author nick
 */
public class ModifyProductController implements Initializable {
    
    private ObservableList<Part> partInv = FXCollections.observableArrayList();
    private ObservableList<Part> partsInvSearch = FXCollections.observableArrayList();
    private ObservableList<Part> assocPartList = FXCollections.observableArrayList();

    
    @FXML
    private TextField prodMin;
    @FXML
    private TextField prodMax;
    @FXML
    private TextField prodCost;
    @FXML
    private TextField prodCount;
    @FXML
    private TextField prodName;
    @FXML
    private TextField prodID;
    @FXML
    private TextField modPartSearch;
    
    @FXML
    private TableView<Part> tableViewModifyPart;
    @FXML
    private TableView<Part> tableViewDeletePart;
  
   
    

    Inventory inv;
    Product product;
    @FXML
    private Button savemodifyProduct;
    @FXML
    private Button cancelModifyProduct;
    @FXML
    private TableColumn<?, ?> modifypartIDCol;
    @FXML
    private TableColumn<?, ?> modifypartNameCol;
    @FXML
    private TableColumn<?, ?> modifypartInvlvlCol;
    @FXML
    private TableColumn<?, ?> deletepartIDCol;
    @FXML
    private TableColumn<?, ?> deletepartNameCol;
    @FXML
    private TableColumn<?, ?> deletepartInvlvlCol;
    @FXML
    private Button searchForPartButton;
    
    
    
    
    /** Controller ModifyProductController controller for ModifyProduct class. 
     *@param inv Inventory
     *@param product Product
     */
    
     public ModifyProductController(Inventory inv, Product product) {
        this.inv = inv;
        this.product = product;
     }
     
    /**
     * Method initialize Initializes and populates search table and sets data. 
     * @param url URL
     * @param rb ResourceBundle
     */
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateSearchTable();
        setData();

    }  
    
    /** Method populateSearchTable populates the search table using allParts from inventory. 
     * 
     */
    
    private void populateSearchTable() {
        partInv.setAll(inv.getAllParts());
        TableColumn<Part, Double> costCol = formatPrice();
        tableViewModifyPart.getColumns().addAll(costCol);
        tableViewModifyPart.setItems(partInv);
        tableViewModifyPart.refresh();
    }
    
    /** Method populateSearchTable populates the search table using allParts from inventory. 
     * 
     */
    
    @FXML
    private void searchPart(MouseEvent event) {
        if (!modPartSearch.getText().trim().isEmpty()) {
            tableViewModifyPart.setItems(inv.lookUpPart(modPartSearch.getText().trim()));
            tableViewModifyPart.refresh();
        }
    }
   
    
    /** Method saveChanges saves the changes made to product on mouse click and returns to MainScreen.  
     *@param event mouse event saves changes or reports alert/error 
     */
    
    @FXML
    private void saveModify(MouseEvent event) {
        
        boolean end = false;
        TextField[] fieldCount = {prodCount, prodCost, prodMin, prodMax};
        double minCost = 0;
        for (int i = 0; i < assocPartList.size(); i++) {
            minCost += assocPartList.get(i).getPrice();
        }
        if (prodName.getText().trim().isEmpty() || prodName.getText().trim().toLowerCase().equals("part name")) {
            Alerts.errorProduct(4, prodName);
            return;
        }
        for (int i = 0; i < fieldCount.length; i++) {
            boolean valueError = checkValue(fieldCount[i]);
            if (valueError) {
                end = true;
                break;
            }
            boolean typeError = checkType(fieldCount[i]);
            if (typeError) {
                end = true;
                break;
            }
        }
        if (Integer.parseInt(prodMin.getText().trim()) > Integer.parseInt(prodMax.getText().trim())) {
            Alerts.errorProduct(10, prodMin);
            return;
        }
        if (Integer.parseInt(prodCount.getText().trim()) < Integer.parseInt(prodMin.getText().trim())) {
            Alerts.errorProduct(8, prodCount);
            return;
        }
        if (Integer.parseInt(prodCount.getText().trim()) > Integer.parseInt(prodMax.getText().trim())) {
            Alerts.errorProduct(9, prodCount);
            return;
        }
        if (Double.parseDouble(prodCost.getText().trim()) < minCost) {
            Alerts.errorProduct(6, prodCost);
            return;
        }
        if (assocPartList.size() == 0) {
            Alerts.errorProduct(7, null);
            return;
        }

        saveProduct();
        mainScreen(event);

    }

    /** Method cancelModify mouse event to cancel modifying product and return to main screen.
     *@param event mouse event alerts cancel and return to main screen
     */
    
    @FXML
    private void cancelModify(MouseEvent event) {
         boolean cancel = Alerts.cancel();
        if (cancel) {
            mainScreen(event);
        } else {
            return;
        }
    }

    /** Method removePart from associated parts of product. 
     *@param event mouse event click to remove part with alerts
     */
    @FXML
    private void removePart(MouseEvent event) {
        Part removePart = tableViewDeletePart.getSelectionModel().getSelectedItem();
        boolean deleted = false;
        if (removePart != null) {
            boolean remove = confirmationWindow(removePart.getName());
            if (remove) {
                deleted = product.removeAssociatedPart(removePart.getId());
                assocPartList.remove(removePart);
                tableViewDeletePart.refresh();
            }
        } else {
            return;
        }
        if (deleted) {
            Alerts.infoWindow(1, removePart.getName());
        } else {
            Alerts.infoWindow(2, "");
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
 
     /** Method addPart adds part on mouse event to associated parts from search table. 
     *@param event mouse event for adding part to associated parts
     * @return null
     */
 @FXML
    private void addPart(MouseEvent event) {
        Part addPart = tableViewModifyPart.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;

        if (addPart == null) {
            return;
        } else {
            int id = addPart.getId();
            for (int i = 0; i < assocPartList.size(); i++) {
                if (assocPartList.get(i).getId() == id) {
                    Alerts.errorProduct(2, null);
                    repeatedItem = true;
                }
            }

            if (!repeatedItem) {
                assocPartList.add(addPart);
            }
            tableViewDeletePart.setItems(assocPartList);
        }
    }
    
    /** Method modifyProductSearch search button for search field clicked searches for part in table view. 
     *@param event MouseEvent  button click for part search */
    private void modifyProductSearch(MouseEvent event) {
        if (modPartSearch.getText() != null && modPartSearch.getText().trim().length() != 0) {
            partsInvSearch.clear();
            for (Part part : inv.getAllParts()) {
                if (part.getName().contains(modPartSearch.getText().trim())) {
                    partsInvSearch.add(part);
                }
            }
            tableViewModifyPart.setItems(partsInvSearch);
        }
    }
    
    /** Method saveProduct saves product to inv and adds any associated parts. 
     */
    
    private void saveProduct() {
        Product product = new Product(Integer.parseInt(prodID.getText().trim()), prodName.getText().trim(), Double.parseDouble(prodCost.getText().trim()),
                Integer.parseInt(prodCount.getText().trim()), Integer.parseInt(prodMin.getText().trim()), Integer.parseInt(prodMax.getText().trim()));
        for (int i = 0; i < assocPartList.size(); i++) {
            product.addAssociatedPart(assocPartList.get(i));
        }

        inv.updateProduct(product);
    }
    
    /**Method setData setsdata for product using associated parts. 
     */
    
     private void setData() {
        for (int i = 0; i < 1000; i++) {
            Part part = product.lookupAssociatedPart(i);
            if (part != null) {
                assocPartList.add(part);
            }
        }

        TableColumn<Part, Double> costCol = formatPrice();
        tableViewDeletePart.getColumns().addAll(costCol);
        tableViewDeletePart.setItems(assocPartList);
        this.prodName.setText(product.getProdName());
        this.prodID.setText((Integer.toString(product.getProdID())));
        this.prodCount.setText((Integer.toString(product.getInStock())));
        this.prodCost.setText((Double.toString(product.getPrice())));
        this.prodMin.setText((Integer.toString(product.getMin())));
        this.prodMax.setText((Integer.toString(product.getMax())));

    }

     /** Method clearText clears the text field of search. 
     *@param event Mouse event on click sets text to empty
     */
     
     @FXML
    void clearText(MouseEvent event) {
     Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
        if (modPartSearch == field) {
            if (partInv.size() != 0) {
                tableViewModifyPart.setItems(partInv);
            }
        }
    }
    
    /** 
     * Method mainScreen changes scene back to inventory management MainScreen. 
     *@param event event click
     */
    
    private void mainScreen(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            MainScreenController controller = new MainScreenController(inv);

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

    /** Method checkValue  checks value entered in field and alerts if not correct price. 
     *@param field checks field
     *@return error
     *@exception exception
     */
    
    private boolean checkValue(TextField field) {
        boolean error = false;
        try {
            if (field.getText().trim().isEmpty() || field.getText().trim() == null) {
                Alerts.errorProduct(1, field);
                return true;
            }
            if (field == prodCost && Double.parseDouble(field.getText().trim()) < 0) {
                Alerts.errorProduct(5, field);
                error = true;
            }
        } catch (Exception e) {
            error = true;
            Alerts.errorProduct(3, field);
            System.out.println(e);

        }
        return error;
    }

    /** Method checkType checks type entered in field and alerts if price is not correct. 
     *@param field checks field for price
     *@return true or false 
     */
    
    private boolean checkType(TextField field) {

        if (field == prodCost & !field.getText().trim().matches("\\d+(\\.\\d+)?")) {
            Alerts.errorProduct(3, field);
            return true;
        }
        if (field != prodCost & !field.getText().trim().matches("[0-9]*")) {
            Alerts.errorProduct(3, field);
            return true;
        }
        return false;

    }
    
    /** Method formatPrice formats price for newly created table column
     */
    
    private <T> TableColumn<T, Double> formatPrice() {
        TableColumn<T, Double> costCol = new TableColumn("Price");
        costCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        // Format as currency
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

}
