module com.example.katestirovanie {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.katestirovanie to javafx.fxml;
    exports com.example.katestirovanie;
}