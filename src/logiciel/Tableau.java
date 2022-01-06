package logiciel;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Tableau{

    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;

  

    //CONNECTION DATABASE
    public TableView buildData(int z){
    	TableView tableview = new TableView();
    	String url = "jdbc:mysql://localhost/projet?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
    	String login = "root";
    	String password = "";
    	Connection conn = null;
          data = FXCollections.observableArrayList();
          try{
        	Class.forName("com.mysql.cj.jdbc.Driver");
  			conn = DriverManager.getConnection(url, login, password);
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from mesure_caractere WHERE idPage=" + z +";";
            //ResultSet
            ResultSet rs = conn.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                tableview.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
          }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
          }
          return tableview;
      }


//      @Override
//      public void start(Stage stage) {
//        //TableView
//        tableview = new TableView();
//        buildData();
//
//        //Main Scene
//        Scene scene = new Scene(tableview);        
//
//        stage.setScene(scene);
//        stage.show();
//      }
}
