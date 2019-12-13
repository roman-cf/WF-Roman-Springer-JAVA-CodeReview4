package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.TextArea;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("CodeReview Java 4");
        // Objekte bauen
        Product product1 = new Product("Pfeffer",
                "1 Stück",
                "Schwarzer Pfeffer verleiht Ihren Speisen eine pikante Schärfe, besonders wenn er länger mitgekocht wird. ",
                "pfeffer__600x600.jpg",
                3.49,
                2.79);
        Product product2 = new Product("Schafmilchkäse",
                "200 Gramm Packung",
                "Hier gibt es keine Beschreibung, weil unsere Handelskette kennst sich nur bedingt damit aus, wie man eine Werbebeschreibung schreibt.",
                "cheese_salakis__600x600.jpg",
                2.59,
                1.99);
        Product product3 = new Product("Vöslauer",
                "1.5 Liter Flasche",
                "Spritziges Vöslauer Mineralwasser.",
                "voslauer__600x600.jpg",
                0.75,
                0.49);
        Product product4 = new Product("Zucker",
                "500 Gramm Packet",
                "Natürliches Gelieren wird durch Apfelpektin unterstützt, welches im richtigen Verhältnis mit Zitronensäure und Kristallzucker abgemischt wurde.",
                "zucker__600x600.jpg",
                1.39,
                0.89);

        Label lblHeadlineProdDetail = new Label("Product Details");
            lblHeadlineProdDetail.setStyle("-fx-font-weight: bold");
            lblHeadlineProdDetail.setStyle("-fx-font-size: 22px");
        Label lblHeadlineProdList = new Label("List of Products");
            lblHeadlineProdList.setStyle("-fx-font-weight: bold");
            lblHeadlineProdList.setStyle("-fx-font-size: 22px");

        Label lblProductName = new Label("Prudukt Name:");
            lblProductName.setMinWidth(150);
        TextField txtName = new TextField();
        Label lblProductQuant = new Label("Produktmenge:");
            lblProductQuant.setMinWidth(150);
        TextField txtQuant = new TextField();
        Label lblOldPrice = new Label("alter Preis");
            lblOldPrice.setMinWidth(150);
        TextField txtOldPrice = new TextField();
        Label lblNewPrices = new Label("Aktionspreis:");
            lblNewPrices.setMinWidth(150);
        TextField txtNewPrice = new TextField();
        Label lblDescHead = new Label("Beschreibung");
            lblDescHead.setStyle("-fx-font-weight: bold");
            lblDescHead.setStyle("-fx-font-size: 22px");
        Label lblProductDesc = new Label();
        lblProductDesc.setPrefWidth(250);
        lblProductDesc.setWrapText(true);

        Button btnUpdate = new Button("Update");
        Button btnClear = new Button("Clear fields");
        Button btnAdd = new Button("Add");
        Button btnDelete = new Button("Delete");
        Button btnFile = new Button("Create file");

        InputStream input = this.getClass().getResourceAsStream("/images/default.png" );
        Image image = new Image(input);
        ImageView imageViewBox = new  ImageView(image);
        imageViewBox.setFitHeight(200);
        imageViewBox.setFitWidth(200);


// Elemente zusammenbauen
        ObservableList<Product> productItems = FXCollections.observableArrayList(product1,product2,product3,product4);
        ListView<Product> productList = new ListView<>();
            productList.getItems().addAll(productItems);
            productList.setMinWidth(340);
// Actions ------------------------------------------------------
        productList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            int selIdx = productList.getSelectionModel().getSelectedIndex();
            if (selIdx!=-1){
                txtName.setText(newValue.getProductName());
                txtQuant.setText(newValue.getProductAmount()); // +" "+newValue.getProductAmountType()
                txtOldPrice.setText(String.valueOf((newValue.getProductPriceOld())));
                txtNewPrice.setText(String.valueOf((newValue.getProductPriceNew())));
                lblProductDesc.setText(newValue.getProductDescription());

                InputStream in = this.getClass().getResourceAsStream("/images/"+ newValue.getProductPicPath() );
                Image imageNew = new Image(in);
                imageViewBox.setImage(imageNew);

                txtName.setDisable(true);
                txtQuant.setDisable(true);
                btnUpdate.setDisable(false);
            }
        });

        btnUpdate.setOnAction(actionEvent -> {
            int selIdx = productList.getSelectionModel().getSelectedIndex();
            if (selIdx != -1){
                double oldprice = Double.parseDouble(txtOldPrice.getText());
                productList.getItems().get(selIdx).setProductPriceOld(oldprice);
                productList.refresh();
                double newprice = Double.parseDouble(txtNewPrice.getText());
                productList.getItems().get(selIdx).setProductPriceNew(newprice);
                productList.refresh();
            }
        });

        btnClear.setOnAction(actionEvent -> {
            txtName.setText("");
            txtName.setDisable(false);
            txtQuant.setText("");
            txtQuant.setDisable(false);
            txtOldPrice.setText("");
            txtNewPrice.setText("");
            lblProductDesc.setText("");
            btnUpdate.setDisable(true);
        });

        btnAdd.setOnAction(actionEvent -> {
            double oldP = Double.parseDouble(txtOldPrice.getText());
            double newP = Double.parseDouble(txtNewPrice.getText());
            if (txtName.getText()!="" && txtQuant.getText() != "" ){
                productList.getItems().add(new Product( txtName.getText(), txtQuant.getText(),"Beschreibung folgt","default.png", oldP,newP));
            }else{
                txtName.setText("Bitte ausfüllen");
            }
        });

        btnFile.setOnAction(actionEvent -> {
            String report = "-------Report----------\n";
            for (int i = 0; i < productList.getItems().size(); i++) {
                String productAmount = productList.getItems().get(i).getProductAmount();
                String productName = productList.getItems().get(i).getProductName();
                report += productAmount + " " + productName + "\n";
            }
            try{
                FileWriter fstream = new FileWriter("report.txt");
                BufferedWriter output = new BufferedWriter(fstream);
                output.write(report);
                output.close();
            }catch (Exception e){
                System.err.println("Error: " + e.getMessage());
            }

        });


// Scene Zusammenbauen ---------------------------------------------------------------------------
        HBox namenBox = new HBox(lblProductName,txtName);
            namenBox.setPadding(new Insets(10,10,10,10));
        HBox quantBox = new HBox(lblProductQuant,txtQuant);
            quantBox.setPadding(new Insets(10,10,10,10));
        HBox oldPrBox = new HBox(lblOldPrice,txtOldPrice);
            oldPrBox.setPadding(new Insets(10,10,10,10));
        HBox newPrBox = new HBox(lblNewPrices,txtNewPrice);
            newPrBox.setPadding(new Insets(10,10,10,10));

        HBox btnBox = new HBox(btnUpdate,btnClear,btnAdd);
        VBox productDetails = new VBox(lblHeadlineProdDetail,namenBox,quantBox,oldPrBox,newPrBox,imageViewBox,lblDescHead,lblProductDesc);
            productDetails.setMinWidth(450);
            productDetails.setPadding(new Insets(10,10,10,10));
        VBox productListBox = new VBox(lblHeadlineProdList,productList,btnBox,btnFile);
        HBox mainBox = new HBox(productDetails,productListBox);
        Scene scene = new Scene(mainBox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
