module org.m946.mvvmfxsample {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.saxsys.mvvmfx;
    requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires junit;
	requires transitive java.sql;
	requires org.simpleflatmapper.jdbc;
	requires java.base;
	requires org.slf4j;
    
    opens org.m946.mvvmfxsample  to javafx.fxml, de.saxsys.mvvmfx, javafx.graphics, junit;
    opens org.m946.controllersample  to javafx.fxml, de.saxsys.mvvmfx, junit;
    exports org.m946.hanakolib.db;
    exports org.m946.hanakolib.jfx;
    exports org.m946.mvvmfxsample.db;
}