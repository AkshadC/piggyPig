module org.morons.piggypig {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens org.morons.piggypig to javafx.fxml;
    exports org.morons.piggypig;
}