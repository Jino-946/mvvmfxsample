module org.m946.mvvmfxsample {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.saxsys.mvvmfx;
    requires transitive javafx.base;
	requires javafx.graphics;
	requires junit;
	requires java.sql;
	requires org.simpleflatmapper.jdbc;
    
    opens org.m946.mvvmfxsample to javafx.fxml, de.saxsys.mvvmfx;
    exports org.m946.mvvmfxsample;
    exports org.m946.mvvmfxsample.db;
    
}