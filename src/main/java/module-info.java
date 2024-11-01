module org.example.otp2_w3_homeassignment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires dotenv.java;

    opens org.example.otp2_w3_homeassignment to javafx.fxml;
    exports org.example.otp2_w3_homeassignment;
}