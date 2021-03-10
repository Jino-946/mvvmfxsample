package mvvmfxsample.jfxtestrunner;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.m946.mvvmfxsample.JfxTestRunner;

import javafx.scene.control.TextField;

@RunWith(JfxTestRunner.class)
public class TestTextField {

	@Test
	public void test() {
		TextField textField = new TextField();
		textField.textProperty().setValue("Japan");
		
		assertEquals("Japan", textField.textProperty().getValue());
	
	}

}
