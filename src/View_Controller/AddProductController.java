
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Class AddProductController implements initalizable for AddPRoduct scene. 
 *
 * @author nick
 */
public class AddProductController implements Initializable {
    
    private ObservableList<Part> partsInv = FXCollections.observableArrayList();
    private ObservableList<Part> partsInvSearch = FXCollections.observableArrayList();
    private ObservableList<Part> assocPartList = FXCollections.observableArrayList();

    Inventory inv;
    @FXML
    private TableColumn<?, ?> addpartIDCol;
    @FXML
    private TableColumn<?, ?> addpartNameCol;
    @FXML
    private TableColumn<?, ?> addpartInvlvlCol;
    @FXML
    private TableColumn<?, ?> deletepartIDCol;
    @FXML
    private TableColumn<?, ?> deletepartNameCol;
    @FXML
    private TableColumn<?, ?> deletepartInvlvlCol;
    
    public AddProductController(Inventory inv) {
        this.inv = inv;
    }
    
    
    @FXML
    private TextField min;
    @FXML
    private TextField max;
    @FXML
    private TextField Price;
    @FXML
    private TextField Count;
    @FXML
    private TextField Name;
    @FXML
    private TextField ID;
    
    
    
    @FXML
    private TableView<Part> partSearchTable;
    @FXML
    private TableView<Part> removeAPartSearch;
    
    
    @FXML
    private TextField productSearch;

    
    /**
     * Method Initialize initializes the controller class and generates ProductID and populates search table. 
     * @param url URL
     * @param rb Resource Bundle
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        generateProductID();
        populateSearchTable();
    }    

    /** Method generateProductID generates random number for Product ID. 
     */
    
    private void generateProductID() {
        boolean match;
        Random randomNum = new Random();
        Integer num = randomNum.nextInt(1000);
    }
    
    /**Method populateSearchTable populates parts search table with parts and adds Price column.  
     */
    private void populateSearchTable() {
        partsInv.setAll(inv.getAllParts());

        TableColumn<Part, Double> costCol = formatPrice();
        partSearchTable.getColumns().addAll(costCol);

        partSearchTable.setItems(partsInv);
        partSearchTable.refresh();
    }
     
    /** Method saveChanges saves the changes made to product on mouse click and returns to MainScreen.  
     *@param event mouse event saves changes or reports alert/error 
     */
    
    @FXML
    private void saveChanges(MouseEvent event) {
        boolean end = false;
        TextField[] fieldCount = {Count, Price, min, max};
        double minCost = 0;
        for (int i = 0; i < assocPartList.size(); i++) {
            minCost += assocPartList.get(i).getPrice();
        }
        if (Name.getText().trim().isEmpty() || Name.getText().trim().toLowerCase().equals("part name")) {
            Alerts.errorProduct(4, Name);
            return;
        }
        for (TextField fieldCount1 : fieldCount) {
            boolean valueError = checkValue(fieldCount1);
            if (valueError) {
                end = true;
                break;
            }
            boolean typeError = checkType(fieldCount1);
            if (typeError) {
                end = true;
                break;
            }
        }
        if (Integer.parseInt(min.getText().trim()) > Integer.parseInt(max.getText().trim())) {
            Alerts.errorProduct(10, min);
            return;
        }
        if (Integer.parseInt(Count.getText().trim()) < Integer.parseInt(min.getText().trim())) {
            Alerts.errorProduct(8, Count);
            return;
        }
        if (Integer.parseInt(Count.getText().trim()) > Integer.parseInt(max.getText().trim())) {
            Alerts.errorProduct(9, Count);
            return;
        }
        if (Double.parseDouble(Price.getText().trim()) < minCost) {
            Alerts.errorProduct(6, Price);
            return;
        }
        if (assocPartList.isEmpty()) {
            Alerts.errorProduct(7, null);
            return;
        }

        saveProduct();
        mainScreen(event);

    }

    /** Method fieldError field display of red border when field is empty. 
     @param field Text field
     */
    
    private void fieldError(TextField field) {
        if (field != null) {
            field.setStyle("-fx-border-color: red");
        }
    }

    /** Method saveProduct saves product to inv and adds any associated parts. 
     */
    
    private void saveProduct() {
        Product product = new Product(Integer.parseInt(ID.getText().trim()), Name.getText().trim(), Double.parseDouble(Price.getText().trim()),
                Integer.parseInt(Count.getText().trim()), Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()));
        for (int i = 0; i < assocPartList.size(); i++) {
            product.addAssociatedPart(assocPartList.get(i));
        }

        inv.addProduct(product);

    }

    
    /** Method cancelButton mouse event to cancel adding product and return to main screen.
     *@param event mouse event alerts cancel and return to main screen
     */
    @FXML
    private void cancelButton(MouseEvent event) {
         boolean cancel = Alerts.cancel();
        if (cancel) {
            mainScreen(event);
        }
    }

    /** Method addPart adds part on mouse event to associated parts from search table. 
     *@param event mouse event for adding part to associated parts
     * @return null
     */
    
    @FXML
    private void addPart(MouseEvent event) {
         Part addPart = partSearchTable.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;

        if (addPart != null) {
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

            TableColumn<Part, Double> costCol = formatPrice();
            removeAPartSearch.getColumns().addAll(costCol);

            removeAPartSearch.setItems(assocPartList);
        }
    }

    /** Method removePart from associated parts of product. 
     *@param event mouse event click to remove part with alerts
     */
    
    @FXML
    private void removePart(MouseEvent event) {
         Part removePart = removeAPartSearch.getSelectionModel().getSelectedItem();
        boolean deleted = false;
        if (removePart != null) {
            boolean remove = Alerts.confirmationWindow(removePart.getName());
            if (remove) {
                assocPartList.remove(removePart);
                removeAPartSearch.refresh();
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
    
    /** Method clearField clears the text field of search. 
     *@param event Mouse event on click sets text to empty
     */
    
    void clearField(MouseEvent event) {
        productSearch.setText("");
        if (!partsInv.isEmpty()) {
            partSearchTable.setItems(partsInv);
        }

    }
    
    /** Method searchPart search for part button for search field. 
     *@param event Mouse event on click searches for part in inv and displays in table view
     */
    
    @FXML
    private void searchPart(MouseEvent event) {
        if (!productSearch.getText().trim().isEmpty()) {
            partsInv.clear();
            partSearchTable.setItems(inv.lookUpPart(productSearch.getText().trim()));
            partSearchTable.refresh();
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
            if (field == Price && Double.parseDouble(field.getText().trim()) < 0) {
                Alerts.errorProduct(5, field);
                error = true;
            }
        } catch (NumberFormatException e) {
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

        if (field == Price & !field.getText().trim().matches("\\d+(\\.\\d+)?")) {
            Alerts.errorProduct(3, field);
            return true;
        }
        if (field != Price & !field.getText().trim().matches("[0-9]*")) {
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

    @FXML
    private void deleteButtonPushed(ActionEvent event) {
    }

}
