package a6tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;

import a6.*;

public class A6Helper {

	public final static Pixel RED = new ColorPixel(1.0, 0.0, 0.0);
	public final static Pixel GREEN = new ColorPixel(0.0, 1.0, 0.0);
	public final static Pixel BLUE = new ColorPixel(0.0, 0.0, 1.0);
	public final static Pixel YELLOW = new ColorPixel(1.0, 1.0, 0.0);
	public final static Pixel PURPLE = new ColorPixel(0.5, 0.0, 0.5);
	public final static Pixel ORANGE = new ColorPixel(1.0, 165.0 / 255, 0.0);
	public final static Pixel PINK = new ColorPixel(1.0, 192.0 / 255, 203.0 / 255);
	public final static Pixel GRAY = new GrayPixel(0.5);
	public final static Pixel WHITE = Pixel.WHITE;
	public final static Pixel BLACK = Pixel.BLACK;
	public final static Pixel MY_FAVORITE_COLOR = new ColorPixel(14.0 / 17, 7.0 / 17, 2.0 / 17);

	final static Pixel[][] checkerboard = new Pixel[][] { 
			{ BLACK, WHITE, BLACK, WHITE },
			{ WHITE, BLACK, WHITE, BLACK }, 
			{ BLACK, WHITE, BLACK, WHITE }, 
			{ WHITE, BLACK, WHITE, BLACK } };

	Picture pic, pic1, pic2;
	
	@BeforeEach
	public void setUp() {
		System.out.println();
	}
	
	public void constructorNotFound() {
		System.out.println("Failed: no such constructor found");
		fail("No such constructor found");
	}
	
	public void checkDimensionGetters(Class c) {
		
		System.out.println("Testing " + c.getSimpleName() + " Dimension Getters...");
		try {
			c.getMethod("getWidth");
			c.getMethod("getHeight");
		} catch (Exception e) {
			System.out.println("Failed: dimension getters not found");
			fail("Dimension getters not found");
		}
	}
	
	public void checkGetPixel(Class c) {
		
		System.out.println("Testing " + c.getSimpleName() + " GetPixel()...");
		try {
			c.getMethod("getPixel", int.class, int.class);
		} catch (Exception e) {
			System.out.println("Failed: getPixel() not found");
			fail("Method not found");
		}
	}
	
	public void classPaintFail(Class c) {
		
		System.out.println("Testing " + c.getSimpleName() + " Paint Fail...");

		try { // checks to see if paint methods exist, even if they're unsupported
			c.getMethod("paint", int.class, int.class, Pixel.class);
			c.getMethod("paint", int.class, int.class, Pixel.class, double.class);
			c.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class);
			c.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class, double.class);
			c.getMethod("paint", int.class, int.class, double.class, Pixel.class);
			c.getMethod("paint", int.class, int.class, double.class, Pixel.class, double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}
	}
	
	public void testPaintFail() {
		
		try {
			pic.paint(0, 0, BLUE);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}
		
		try {
			pic.paint(3, 4, BLUE, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, 2, 2, BLUE);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, 1, 1, GRAY, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 1.0, PURPLE);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 1.0, MY_FAVORITE_COLOR, 0.4);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		System.out.println("Passed!");
	}
	
	public void checkPaint(Class c, String key) {
		
		System.out.println("Testing " + c.getSimpleName() + " Paint() - " + key + "...");

		try { // checks to see if paint methods exist
			switch (key) {
			case "Single Pixel" :
				c.getMethod("paint", int.class, int.class, Pixel.class);
				c.getMethod("paint", int.class, int.class, Pixel.class, double.class);
				break;
			case "Rectangle" :
				c.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class);
				c.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class, double.class);
				break;
			case "Circle" :
				c.getMethod("paint", int.class, int.class, double.class, Pixel.class);
				c.getMethod("paint", int.class, int.class, double.class, Pixel.class, double.class);
				break;
			}
			
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}
	}
	
	public void classFieldEncapsulation(Class c) {
		
		System.out.println("Testing " + c.getSimpleName() + " Field Encapsulation...");
		try {
			for (Field field : c.getDeclaredFields()) {

				if (field.getType().equals(Pixel[][].class) && !c.getSimpleName().equals("MutablePixelArrayPicture")) {
					throw new DisallowedFieldException(Pixel[][].class);
				}

				if (!field.toString().contains("private") && !field.toString().contains("protected")) {
					throw new ExposedAccessException(field);
				}
			}

		} catch (Exception e) {
			System.out.println("Failed: " + e.toString());
			fail(e.getMessage());
		}
		System.out.println("Passed!");
	}
	
	public void legalArgumentExceptionCatch(Exception e) {
		legalArgumentExceptionCatch(e, "");
	}
	
	public void legalArgumentExceptionCatch(Exception e, String message) {
		System.out.println("Failed: exception thrown for legal argument(s) " + message);
		e.printStackTrace();
		fail("Exception thrown for legal argument");
	}

	public void incorrectExceptionCatch(Exception e) {
		System.out.println("Failed: correct exception not thrown");
		e.printStackTrace();
		System.out.println();
		fail("Correct Exception not thrown");
	}
	
	public void unthrownUnsupportedOperationExceptionCatch() {
		unthrownExceptionCatch("unsupported operation");
	}
	
	public void illegalCoordinatesPaintCatch() {
		unthrownExceptionCatch("attempting to paint at illegal coordinates");
	}
	
	public void illegalCoordinatesGetPixelCatch() {
		unthrownExceptionCatch("attempting to get pixel at illegal coordinates");
	}
	
	public void unthrownExceptionCatch(String message) {
		System.out.println("Failed: exception not thrown for " + message);
		illegalArgFail();
	}
	
	public void illegalArgFail() {
		fail("Exception not thrown for illegal argument");
	}
	
	
	
}
