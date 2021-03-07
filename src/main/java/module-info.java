module org.m946.mvvmfxsample {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.m946.mvvmfxsample to javafx.fxml;
    exports org.m946.mvvmfxsample;
}