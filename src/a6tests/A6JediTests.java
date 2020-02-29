package a6tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import a6.*;

public class A6JediTests {
	
	public final static Pixel RED = new ColorPixel(1.0, 0.0, 0.0);
	public final static Pixel GREEN = new ColorPixel(0.0, 1.0, 0.0);
	public final static Pixel BLUE = new ColorPixel(0.0, 0.0, 1.0);
	public final static Pixel YELLOW = new ColorPixel(1.0, 1.0, 0.0);
	public final static Pixel PURPLE = new ColorPixel(1, 0.0, 0.5);
	public final static Pixel ORANGE = new ColorPixel(1.0, 165/255, 0.0);
	public final static Pixel PINK = new ColorPixel(1.0, 192/255, 203/255);
	public final static Pixel GRAY = new GrayPixel(0.5);	
	public final static Pixel WHITE = Pixel.WHITE;
	public final static Pixel BLACK = Pixel.BLACK;
	public final static Pixel MY_FAVORITE_COLOR = new ColorPixel(14/17, 7/17, 2/17);
	
	final static Pixel[][] checkerboard = new Pixel[][] { { BLACK, WHITE, BLACK, WHITE },
														{ WHITE, BLACK, WHITE, BLACK },
														{ BLACK, WHITE, BLACK, WHITE },
														{ WHITE, BLACK, WHITE, BLACK } };
																
	Picture pic;
	
	
	@Test
	public void testGradientPictureConstructor() {
		// Tests the constructor for GradientPicture
		System.out.println("Testing GradientPicture Constructor");
		try {
			GradientPicture.class.getConstructor(int.class, int.class, Pixel.class, Pixel.class, Pixel.class, Pixel.class);
		} catch (Exception e) {
			System.out.println("Failed: no such constructor found");
			fail("No such constructor found");
		}
		
		Picture pic;
		
		try {
			pic = new GradientPicture(0, 1, ORANGE, RED, RED, RED);

			System.out.println("Failed: exception not thrown for illegal width");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(1, -2, YELLOW, YELLOW, YELLOW, YELLOW);

			System.out.println("Failed: exception not thrown for illegal height");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(2, 2, null, WHITE, BLACK, BLUE);

			System.out.println("Failed: exception not thrown for illegal upper_left pixel value");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(2, 2, GRAY, null, BLACK, BLUE);

			System.out.println("Failed: exception not thrown for illegal upper_right pixel value");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(2, 2, GRAY, BLACK, null, BLUE);

			System.out.println("Failed: exception not thrown for illegal lower_left pixel value");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(2, 2, GRAY, BLACK, BLUE, null);

			System.out.println("Failed: exception not thrown for illegal lower_right pixel value");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(1, 1, RED, YELLOW, ORANGE, PINK);
			pic = new GradientPicture(1, 2, new ColorPixel(0.2, 0.2, 0), YELLOW, ORANGE, PINK);
			pic = new GradientPicture(3, 1, BLUE, new GrayPixel(0.2), GREEN, PURPLE);
			pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);
			
		}  catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}
		
		System.out.println("Passed");

	}

	@Test
	public void testGradientPictureGetPixel() {
		// Tests that the pixel returned by getPixel is the same one given to the constructor
		System.out.println("Testing GradientPicture GetPixel()");
		try {
			GradientPicture.class.getMethod("getPixel", int.class, int.class);
		} catch (Exception e) {
			System.out.println("Failed: getPixel() not found");
			fail("Method not found");
		}
		
		fail("Replace with testing code");
	}
	
	@Test
	public void testGradientPaintFail() {
		// Tests that trying to paint on a monochrome picture throws an exception of the
		// appropriate type
		System.out.println("Testing GradientPicture Paint()");
		
		try {
			GradientPicture.class.getMethod("paint", int.class, int.class, Pixel.class);
			GradientPicture.class.getMethod("paint", int.class, int.class, Pixel.class, double.class);
			GradientPicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class);
			GradientPicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class, double.class);
			GradientPicture.class.getMethod("paint", int.class, int.class, double.class, Pixel.class);
			GradientPicture.class.getMethod("paint", int.class, int.class, double.class, Pixel.class, double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}
		
		Picture pic;
		try {
			pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);
			pic.paint(0, 0, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);
			pic.paint(3, 4, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);
			pic.paint(0, 0, 2, 2, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(5, 5, BLUE, GREEN, BLACK, PINK);
			pic.paint(0, 0, 1, 1, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(3, 3, RED, YELLOW, ORANGE, PINK);
			pic.paint(1, 1, 1.0, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try {
			pic = new GradientPicture(3, 3, RED, YELLOW, ORANGE, PINK);
			pic.paint(1, 1, 1.0, GREEN, 0.4);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		System.out.println("Passed");
	}
	
	@Test
	public void testGradientPictureDimensionGetters() {
		// Tests getWidth() and getHeight()
		fail("Replace with testing code");
	}

	@Test
	public void testHorizontalStackPictureConstructor() {
		// Tests horizontal stack picture constructor
		fail("Replace with testing code");
	}	
	
	@Test
	public void testHorizontalStackPictureDimensionGetters() {
		// Tests getWidth() and getHeight()
		fail("Replace with testing code");
	}

	@Test
	public void testHorizontalStackPictureGetPixel() {
		// Creates a picture from a 2D array of pixels with different pixels in various places.
		// Tests that pixel returned from getPixel at those places returns the same pixels
		System.out.println("Testing HorizontalStackPicture GetPixel()");
		try {
			VerticalStackPicture.class.getMethod("getPixel", int.class, int.class);
		} catch (Exception e) {
			System.out.println("Failed: getPixel() not found");
			fail("Method not found");
		}
		
		fail("Replace with testing code");
	}
	
	@Test
	public void testHorizontalStackPicturePaintSinglePixel() {
		// Tests paint(x,y,p) and paint(x,y,p,f)
		fail("Replace with testing code");
	}
	
	
	@Test
	public void testHorizontalStackPicturePaintRectangle() {
		// Tests paint(ax, ay, bx, by, p) and paint(ax, ay, bx, by, p, f)
		fail("Replace with testing code");
	}
	
	@Test
	public void testHorizontalStackPicturePaintCircle() {
		// Tests paint(cx, cy, radius, p) and paint(cx, cy, radius, p, f);
		fail("Replace with testing code");
	}

	@Test
	public void testVerticalStackPictureConstructor() {
		// Tests vertical stack picture constructor
		fail("Replace with testing code");
	}	
	
	@Test
	public void testVerticalStackPictureDimensionGetters() {
		// Tests getWidth() and getHeight()
		fail("Replace with testing code");
	}

	@Test
	public void testVerticalStackPictureGetPixel() {
		// Creates a picture from a 2D array of pixels with different pixels in various places.
		// Tests that pixel returned from getPixel at those places returns the same pixels
		System.out.println("Testing VerticalStackPicture GetPixel()");
		try {
			VerticalStackPicture.class.getMethod("getPixel", int.class, int.class);
		} catch (Exception e) {
			System.out.println("Failed: getPixel() not found");
			fail("Method not found");
		}
		
		fail("Replace with testing code");
	}
	
	@Test
	public void testVerticalStackPicturePaintSinglePixel() {
		// Tests paint(x,y,p) and paint(x,y,p,f)
		fail("Replace with testing code");
	}
	
	
	@Test
	public void testVerticalStackPicturePaintRectangle() {
		// Tests paint(ax, ay, bx, by, p) and paint(ax, ay, bx, by, p, f)
		fail("Replace with testing code");
	}
	
	@Test
	public void testVerticalStackPicturePaintCircle() {
		// Tests paint(cx, cy, radius, p) and paint(cx, cy, radius, p, f);
		fail("Replace with testing code");
	}

	public void incorrectException() {
		System.out.println("Failed: correct exception not thrown");
		fail("Correct Exception not thrown");
	}
	
	public boolean isImmutable (Picture pic) {
		return isImmutable(0,0);
	}
	
	public boolean isImmutable (int x, int y) {
		try {
			pic.paint(x, y, MY_FAVORITE_COLOR);
		} catch (UnsupportedOperationException e) {
			return true;
		}
		return false;
	}

}
