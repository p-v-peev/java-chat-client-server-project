package UserInterface;

import java.io.ByteArrayInputStream;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class AvatarMaker extends Circle{
	
	
	public AvatarMaker(byte[] image, int radius, double strokeWidth) {
		super(radius, new ImagePattern(new Image(new ByteArrayInputStream(image)), radius*2 - 2, radius, radius*4, radius*2,false));
		setStroke(Color.web("0078FF"));
		setStrokeWidth(strokeWidth);
	}
}
