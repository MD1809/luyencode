module com.example.quanly_thuvien {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quanly_thuvien to javafx.fxml;
    exports com.example.quanly_thuvien;
}