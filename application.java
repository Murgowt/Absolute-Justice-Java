import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.cell.PropertyValueFactory;

public class JavaFXApplication2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Button button1 = new Button();
        button1.setText("Open a New Window");
        
        primaryStage.setTitle("Police Login");
        GridPane gridPane = FormPane();
        startPage(gridPane, primaryStage);
        Scene scene = new Scene(gridPane, 800, 500);	
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

   private void startPage(GridPane gridPane,Stage primaryStage) {
        Button polButton = new Button("Police");
        polButton.setPrefHeight(100);
        polButton.setDefaultButton(true);
        polButton.setPrefWidth(800);
        gridPane.add(polButton,1, 1);
        GridPane.setHalignment(polButton, HPos.CENTER);
        GridPane.setMargin(polButton, new Insets(20, 0,20,0));
        polButton.setOnAction(new EventHandler<ActionEvent>() {
 
         @Override
         public void handle(ActionEvent event) {
 
            Stage newWindow = new Stage();
            newWindow.setTitle("HomePage");
            GridPane gridPane = FormPane();
            addUIControls(gridPane,primaryStage);
            Scene secondscene = new Scene(gridPane, 800, 500);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(primaryStage);
            newWindow.setScene(secondscene);
 
            newWindow.show();
         }
      });
        Button cizButton = new Button("Citizen");
        cizButton.setPrefHeight(100);
        cizButton.setDefaultButton(true);
        cizButton.setPrefWidth(800);
        cizButton.setOnAction(new EventHandler<ActionEvent>() {
 
         @Override
         public void handle(ActionEvent event) {
 
            Stage newWindow = new Stage();
            newWindow.setTitle("Citizen Portal");
            GridPane gridPane = FormPane();
            CitizenMenu(gridPane,primaryStage);
            Scene secondscene = new Scene(gridPane, 800, 500);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(primaryStage);
            newWindow.setScene(secondscene);
 
            newWindow.show();
         }
      });
        gridPane.add(cizButton, 1, 2);
        GridPane.setHalignment(cizButton, HPos.CENTER);
        GridPane.setMargin(cizButton, new Insets(20, 0,20,0));
   }
    private GridPane FormPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        return gridPane;
    }
    
   
    private void PoliceArea(GridPane gridPane,Stage primaryStage){
        Label headerLabel = new Label("Police in Your Area");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Label nameLabel = new Label("Area: ");
        gridPane.add(nameLabel, 0,1);
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1,1);
        
        Button polButton = new Button("Submit");
        polButton.setPrefHeight(40);
        polButton.setDefaultButton(true);
        polButton.setPrefWidth(100);
        gridPane.add(polButton, 0, 4, 2, 1);
        GridPane.setHalignment(polButton, HPos.CENTER);
        GridPane.setMargin(polButton, new Insets(20, 0,20,0));

        polButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(nameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter ID");
                    return;
                }
               Stage newWindow = new Stage();
               
            newWindow.setTitle("Police In Area");
            GridPane gridPane = FormPane();
            PoliceAreaTable(gridPane,nameField.getText());
            Scene secondscene = new Scene(gridPane, 800, 500);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(primaryStage);
            newWindow.setScene(secondscene);
 
            newWindow.show();
            }
        });
    }
    private void PoliceAreaTable(GridPane gridPane,String area){
        
        Label nameLabel = new Label("Name");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
        gridPane.add(nameLabel, 0,1);
        Label noLabel = new Label("No.");
        
        GridPane.setHalignment(noLabel, HPos.CENTER);
        noLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
        gridPane.add(noLabel, 1,1);
        Label areaLabel = new Label("Area");
        areaLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
        gridPane.add(areaLabel, 2,1);
        
         try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java?zeroDateTimeBehavior=convertToNull", "root", "") ;
                    Statement stmt=con.createStatement();  
                    String sql="select * from police Where area= '"+area+"'";
                    ResultSet rs=stmt.executeQuery(sql);  
                    int i=1;
                    while(rs.next())  
                    {
                       nameLabel = new Label(rs.getString(4));
                        gridPane.add(nameLabel, 0,1+i);
                         noLabel = new Label(rs.getString(5));

                        GridPane.setHalignment(noLabel, HPos.CENTER);
                        gridPane.add(noLabel, 1,1+i);
                       areaLabel = new Label(rs.getString(3));
                        gridPane.add(areaLabel, 2,1+i);
                        i++;
                    }
                    con.close();  
                }
                catch(Exception e){ System.out.println(e);}
       
       
    }
     private void CitizenMenu(GridPane gridPane,Stage primaryStage){
        Button CRButton = new Button("Complaint Register");
        CRButton.setPrefHeight(100);
        CRButton.setDefaultButton(true);
        CRButton.setPrefWidth(800);
        CRButton.setOnAction(new EventHandler<ActionEvent>() {
 
         @Override
         public void handle(ActionEvent event) {
 
            Stage newWindow = new Stage();
            newWindow.setTitle("Complaint Register");
            GridPane gridPane = FormPane();
            ComplaintRegister(gridPane);
            Scene secondscene = new Scene(gridPane, 800, 500);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(primaryStage);
            newWindow.setScene(secondscene);
 
            newWindow.show();
         }
      });
        gridPane.add(CRButton,1, 1);
        GridPane.setHalignment(CRButton, HPos.CENTER);
        GridPane.setMargin(CRButton, new Insets(20, 0,20,0));
        
        Button CSButton = new Button("Complaint Status");
        CSButton.setPrefHeight(100);
        CSButton.setDefaultButton(true);
        CSButton.setPrefWidth(800);
        CSButton.setOnAction(new EventHandler<ActionEvent>() {
 
         @Override
         public void handle(ActionEvent event) {
 
            Stage newWindow = new Stage();
            newWindow.setTitle("Complaint Status");
            GridPane gridPane = FormPane();
            ComplaintStatus(gridPane);
            Scene secondscene = new Scene(gridPane, 800, 500);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(primaryStage);
            newWindow.setScene(secondscene);
 
            newWindow.show();
         }
      });
        gridPane.add(CSButton,1, 2);
        GridPane.setHalignment(CSButton, HPos.CENTER);
        GridPane.setMargin(CSButton, new Insets(20, 0,20,0));
        
        Button PAButton = new Button("Police Details by Area");
        PAButton.setPrefHeight(100);
        PAButton.setDefaultButton(true);
        PAButton.setPrefWidth(800);
        
        PAButton.setOnAction(new EventHandler<ActionEvent>() {
 
         @Override
         public void handle(ActionEvent event) {
 
            Stage newWindow = new Stage();
            newWindow.setTitle("PoliceMen Information");
            GridPane gridPane = FormPane();
            PoliceArea(gridPane,primaryStage);
            Scene secondscene = new Scene(gridPane, 800, 500);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(primaryStage);
            newWindow.setScene(secondscene);
 
            newWindow.show();
         }
      });
        
        gridPane.add(PAButton, 1,3);
        GridPane.setHalignment(PAButton, HPos.CENTER);
        GridPane.setMargin(PAButton, new Insets(20, 0,20,0));
    }
      private void ComplaintStatus(GridPane gridPane){
        Label headerLabel = new Label("Complaint Status");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Label nameLabel = new Label(" ID: ");
        gridPane.add(nameLabel, 0,1);
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1,1);
        
        Button polButton = new Button("Submit");
        polButton.setPrefHeight(40);
        polButton.setDefaultButton(true);
        polButton.setPrefWidth(100);
        gridPane.add(polButton, 0, 4, 2, 1);
        GridPane.setHalignment(polButton, HPos.CENTER);
        GridPane.setMargin(polButton, new Insets(20, 0,20,0));

        polButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(nameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter ID");
                    return;
                }
                 try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java?zeroDateTimeBehavior=convertToNull", "root", "") ;
                    Statement stmt=con.createStatement();  
                    String id=nameField.getText();
                    String sql="select status from complaint Where CID= '"+id+"'";
                    ResultSet rs=stmt.executeQuery(sql); 
                    rs.next();
                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), rs.getString(1), rs.getString(1));

                    con.close();  
                }
                catch(Exception e){ System.out.println(e);}
            }
        });
    }
      
    private void ComplaintRegister(GridPane gridPane){
        Label headerLabel = new Label("Complaint Registration");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Label nameLabel = new Label("Police Station: ");
        gridPane.add(nameLabel, 0,1);
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1,1);
        Label desLabel = new Label("Description: ");
        gridPane.add(desLabel, 0, 2);
        TextField desField = new TextField();
        desField.setPrefHeight(40);
        gridPane.add(desField, 1, 2);
         Label IDLabel = new Label("ID: ");
        gridPane.add(IDLabel, 0,3);
        TextField IDField = new TextField();
        IDField.setPrefHeight(40);
        gridPane.add(IDField, 1,3);
        Button polButton = new Button("Submit");
        polButton.setPrefHeight(40);
        polButton.setDefaultButton(true);
        polButton.setPrefWidth(100);
        gridPane.add(polButton, 0, 4, 3, 1);
        GridPane.setHalignment(polButton, HPos.CENTER);
        GridPane.setMargin(polButton, new Insets(20, 0,20,0));

        polButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(nameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter Area");
                    return;
                }
               
                if(desField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a Description");
                    return;
                }
                
                try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java?zeroDateTimeBehavior=convertToNull", "root", "") ;
                    Statement stmt=con.createStatement();  
                    String area=nameField.getText();
                    String status="Registered";
                    String description=desField.getText();
                    String ID=IDField.getText();
                    String sql="insert into complaint (area,status,description,cid) values (?,?,?,?)";
                    PreparedStatement pstmt = con.prepareStatement(sql);   
                    pstmt.setString(1,area);
                    pstmt.setString(2,status);
                    pstmt.setString(3,description);
                    pstmt.setString(4,ID);
                    pstmt.executeUpdate();
                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Successful!", "Complaint Registered");
                    con.close();  
                }
                catch(Exception e){ System.out.println(e);}
                
                
            }
        });
    }
    private void addUIControls(GridPane gridPane,Stage primaryStage) {
        Label headerLabel = new Label("Login");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        Label nameLabel = new Label("Unique ID : ");
        gridPane.add(nameLabel, 0,1);
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1,1);
        Label passwordLabel = new Label("Password : ");
        gridPane.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 2);
        Button polButton = new Button("Submit");
        polButton.setPrefHeight(40);
        polButton.setDefaultButton(true);
        polButton.setPrefWidth(100);
        gridPane.add(polButton, 0, 4, 2, 1);
        GridPane.setHalignment(polButton, HPos.CENTER);
        GridPane.setMargin(polButton, new Insets(20, 0,20,0));

        polButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(nameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter ID");
                    return;
                }
               
                if(passwordField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a password");
                    return;
                }
                try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java?zeroDateTimeBehavior=convertToNull", "root", "") ;
                    Statement stmt=con.createStatement();  
                    String id=nameField.getText();
                    String password=passwordField.getText();
                    String sql="select * from police Where ID= '"+id+"'";
                    ResultSet rs=stmt.executeQuery(sql);  
                    while(rs.next())  
                    {
                        if(password.equals(rs.getString(2)))
                        {
                            
                            Stage newWindow = new Stage();
                            newWindow.setTitle("Police Portal");
                            GridPane gridPane = FormPane();
                            PoliceMenu(gridPane,primaryStage);
                            Scene secondscene = new Scene(gridPane, 800, 500);
                            newWindow.initModality(Modality.WINDOW_MODAL);
                            newWindow.initOwner(primaryStage);
                            newWindow.setScene(secondscene);

                            newWindow.show();
                        }
                        else
                        {
                            System.out.println(rs.getString(2));
                            System.out.println(password);
                            showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Form Error!!", "Incorrect Credentials");
                        }
                    }
                    con.close();  
                }
                catch(Exception e){ System.out.println(e);}
                
            }
        });
    }
 private void PoliceMenu(GridPane gridPane,Stage primaryStage){
        Button CRButton = new Button("Complaints View");
        CRButton.setPrefHeight(100);
        CRButton.setDefaultButton(true);
        CRButton.setPrefWidth(800);
        CRButton.setOnAction(new EventHandler<ActionEvent>() {
 
         @Override
         public void handle(ActionEvent event) {
 
            Stage newWindow = new Stage();
            newWindow.setTitle("Complaint Register");
            GridPane gridPane = FormPane();
                   Label nameLabel = new Label("Area");
            nameLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
            gridPane.add(nameLabel, 0,1);
            Label noLabel = new Label("Status");

            GridPane.setHalignment(noLabel, HPos.CENTER);
            noLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
            gridPane.add(noLabel, 1,1);
            Label areaLabel = new Label("Description");
            areaLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
            gridPane.add(areaLabel, 2,1);
            Label cidLabel = new Label("CID");
            cidLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
            gridPane.add(cidLabel, 3,1);

             try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java?zeroDateTimeBehavior=convertToNull", "root", "") ;
                        Statement stmt=con.createStatement();  
                        String sql="select * from complaint";
                        ResultSet rs=stmt.executeQuery(sql);  
                        int i=1;
                        while(rs.next())  
                        {
                           nameLabel = new Label(rs.getString(1));
                            gridPane.add(nameLabel, 0,1+i);
                             noLabel = new Label(rs.getString(2));

                            GridPane.setHalignment(noLabel, HPos.CENTER);
                            gridPane.add(noLabel, 1,1+i);
                           areaLabel = new Label(rs.getString(3));
                            gridPane.add(areaLabel, 2,1+i);
                            cidLabel = new Label(rs.getString(4));
                              gridPane.add(cidLabel, 3,1+i);
                        i++;
                    }
                    con.close();  
                }
                catch(Exception e){ System.out.println(e);} 
            Scene secondscene = new Scene(gridPane, 800, 500);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(primaryStage);
            newWindow.setScene(secondscene);
 
            newWindow.show();
         }
      });
        gridPane.add(CRButton,1, 1);
        GridPane.setHalignment(CRButton, HPos.CENTER);
        GridPane.setMargin(CRButton, new Insets(20, 0,20,0));
        
       
        
        Button PAButton = new Button("Police Details by Area");
        PAButton.setPrefHeight(100);
        PAButton.setDefaultButton(true);
        PAButton.setPrefWidth(800);
        
        PAButton.setOnAction(new EventHandler<ActionEvent>() {
 
         @Override
         public void handle(ActionEvent event) {
 
            Stage newWindow = new Stage();
            newWindow.setTitle("PoliceMen Information");
            GridPane gridPane = FormPane();
            PoliceArea(gridPane,primaryStage);
            Scene secondscene = new Scene(gridPane, 800, 500);
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initOwner(primaryStage);
            newWindow.setScene(secondscene);
 
            newWindow.show();
         }
      });
        
        gridPane.add(PAButton, 1,2);
        GridPane.setHalignment(PAButton, HPos.CENTER);
        GridPane.setMargin(PAButton, new Insets(20, 0,20,0));
    }
    
    
    
    
    
    
    
    
    
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    
   
    public static void main(String[] args) {
        launch(args);
    }
}
