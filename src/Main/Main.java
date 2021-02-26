
package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *This class initiates the Inventory Management Program, Run time error that occurred was getting the scene to load on compile due to the getResource (controller) already being called prior to
     *  This left the the compiler failing and not loading the program
     *  This behavior also happened when changing scenes
     *  Correction to this was to ensure in Scene builder that no controller is called and the correct controller path was in getResources. 
 * @author nick
 */

public class Main extends Application{

    /**
     * This is the main method.
     * @param args launches args
     */
    
    public static void main(String[] args) {
        
        launch(args);
        
    }
    
/**   Method start  Stage for scene builder to initiate.   
     * @param stage starts the stage
     * @throws java.lang.Exception  throws exception
 */
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Inventory inv = new Inventory();
        addTestData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /** Method that adds the Test Data into the Scene.
     * @param inv adds test data to inventory
     */
    
    void addTestData(Inventory inv) {
        
        //Add InHouse Parts
        
        Part a1 = new InHouse(1, "Part A1", 6.99, 10, 5, 100, 101);
        Part a2 = new InHouse(3, "Part A2", 8.99, 11, 5, 100, 103);
        Part c = new InHouse(2, "Part C1", 4.99, 9, 5, 100, 102);
        inv.addPart(a1);
        inv.addPart(c);
        inv.addPart(a2);
        inv.addPart(new InHouse(4, "Part A5", 9.99, 15, 5, 100, 104));
        inv.addPart(new InHouse(5, "Part A7", 2.99, 5, 5, 100, 105));
        
        
        //Add OutSourced Parts
        
        Part f1 = new Outsourced(6, "Part F1", 7.99, 10, 5, 100, "Jive Co.");
        Part x1 = new Outsourced(7, "Part X1", 1.99, 9, 5, 100, "Lumen LLC.");
        Part n = new Outsourced(8, "Part N7", 8.99, 10, 5, 100, "NVState Co.");
        inv.addPart(f1);
        inv.addPart(x1);
        inv.addPart(n);
        inv.addPart(new Outsourced(9, "Part L", 4.99, 10, 5, 100, "Lumen Co."));
        inv.addPart(new Outsourced(10, "Part F2", 8.99, 10, 5, 100, "Xibo Co."));
        
        
        //Add allProducts
        
        Product prod1 = new Product(100, "Product 1", 11.99, 20, 5, 100);
        prod1.addAssociatedPart(a1);
        prod1.addAssociatedPart(f1);
        inv.addProduct(prod1);
        Product prod2 = new Product(200, "Product 2", 12.99, 29, 5, 100);
        prod2.addAssociatedPart(a2);
        prod2.addAssociatedPart(x1);
        inv.addProduct(prod2);
        Product prod3 = new Product(300, "Product 3", 5.99, 30, 5, 100);
        prod3.addAssociatedPart(c);
        prod3.addAssociatedPart(n);
        inv.addProduct(prod3);
        Product prod4 = new Product(400, "Product 4", 15.99, 20, 5, 100);
        inv.addProduct(prod4);
        inv.addProduct(new Product(500, "Product 5", 49.99, 9, 5, 100));

    }
    
    
    
}
