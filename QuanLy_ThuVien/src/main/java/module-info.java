module com.example.quanly_thuvien {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.quanly_thuvien to javafx.fxml;
    exports com.example.quanly_thuvien;
    exports database;
    opens database to javafx.fxml;
    exports lop;
    opens lop to javafx.fxml;
}