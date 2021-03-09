module org.m946.mvvmfxsample {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.saxsys.mvvmfx;
    requires transitive javafx.base;
	requires org.jdbi.v3.core;
    
    opens org.m946.mvvmfxsample to javafx.fxml, de.saxsys.mvvmfx;
    exports org.m946.mvvmfxsample;
}