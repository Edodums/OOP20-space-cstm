module space.cstm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jackson.annotations;
    requires jackson.databind;
    requires eventbus;
    requires com.fasterxml.jackson.dataformat.yaml;

    exports space.cstm;
}