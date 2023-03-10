module com.kodilla.sudokujavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;

    opens com.kodilla.sudokujavafx to javafx.fxml;
    exports com.kodilla.sudokujavafx;
}