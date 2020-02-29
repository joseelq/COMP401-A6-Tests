package a6tests;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;

import a6.*;

// MPAP = MutablePixelArrayPicture

public class A6AdeptTests {

	public final static Pixel RED = new ColorPixel(1.0, 0.0, 0.0);
	public final static Pixel GREEN = new ColorPixel(0.0, 1.0, 0.0);
	public final static Pixel BLUE = new ColorPixel(0.0, 0.0, 1.0);
	public final static Pixel YELLOW = new ColorPixel(1.0, 1.0, 0.0);
	public final static Pixel PURPLE = new ColorPixel(0.5, 0.0, 0.5);
	public final static Pixel ORANGE = new ColorPixel(1.0, 165 / 255, 0.0);
	public final static Pixel PINK = new ColorPixel(1.0, 192 / 255, 203 / 255);
	public final static Pixel GRAY = new GrayPixel(0.5);
	public final static Pixel WHITE = Pixel.WHITE;
	public final static Pixel BLACK = Pixel.BLACK;
	public final static Pixel MY_FAVORITE_COLOR = new ColorPixel(14 / 17, 7 / 17, 2 / 17);

	Picture pic;

	@Test
	public void testMPAPConstructors() {
		// Tests for the existence of MPAP constructors

		System.out.println("Testing MPAP Constructors");

		Constructor[] constructors = MutablePixelArrayPicture.class.getConstructors();

		assertEquals(3, constructors.length); // assumes no additional constructors have been made besides required

		for (Constructor cons : constructors) {

			switch (cons.getParameterCount()) {
			case 1: // checks for MPAP(Pixel[][] pixel_array)
				try {
					assertEquals(Pixel[][].class, cons.getParameterTypes()[0]);
				} catch (AssertionError e) {
					System.out.println("Failed: MPAP constructor should pass (Pixel[][]) as an argument");
					fail();
				}
				break;

			case 2: // checks for MPAP(int width, int height)
				try {
					assertEquals(int.class, cons.getParameterTypes()[0]);
					assertEquals(int.class, cons.getParameterTypes()[1]);
				} catch (AssertionError e) {
					System.out.println("Failed: MPAP constructor should pass (int, int) as an argument");
					fail();
				}
				break;

			case 3: // checks for MPAP(int width, int height, Pixel initial_value)
				try {
					assertEquals(int.class, cons.getParameterTypes()[0]);
					assertEquals(int.class, cons.getParameterTypes()[1]);
					assertEquals(Pixel.class, cons.getParameterTypes()[2]);
				} catch (AssertionError e) {
					System.out.println("Failed: MPAP constructor should pass (int, int, Pixel) as an argument");
					fail();
				}
				break;
			}
		}

		System.out.println("Passed");
	}

	@Test
	public void testMPAPArrayConstructor() {
		// Tests the array form of the constructor.

		System.out.println("Testing MPAP(Pixel[][] pixel_array) Constructor");
		try {
			MutablePixelArrayPicture.class.getConstructor(Pixel[][].class);
		} catch (Exception e) {
			System.out.println("Failed: no such constructor found");
			fail("No such constructor found");
		}

		Pixel[][] pixels;
		try {
			pixels = null;
			pic = new MutablePixelArrayPicture(pixels);

			System.out.println("Failed: exception not thrown for null argument");
			fail("Exception not thrown for illegal argument");

		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pixels = new Pixel[][] {};
			pic = new MutablePixelArrayPicture(pixels);

			System.out.println("Failed: exception not thrown for empty argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pixels = new Pixel[][] { { RED }, {} };
			pic = new MutablePixelArrayPicture(pixels);

			System.out.println("Failed: exception not thrown for empty array in argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pixels = new Pixel[][] { {}, { RED, RED } };
			pic = new MutablePixelArrayPicture(pixels);

			System.out.println("Failed: exception not thrown for empty array in argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pixels = new Pixel[][] { { RED, RED }, { RED, null } };
			pic = new MutablePixelArrayPicture(pixels);

			System.out.println("Failed: exception not thrown for null element in argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pixels = new Pixel[][] { { null, RED }, { RED, RED } };
			pic = new MutablePixelArrayPicture(pixels);

			System.out.println("Failed: exception not thrown for null element in argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pixels = new Pixel[][] { { RED, RED }, { RED } };
			pic = new MutablePixelArrayPicture(pixels);

			System.out.println("Failed: exception not thrown for inconsistent second dimension in argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pixels = new Pixel[][] { { RED }, { RED, RED } };
			pic = new MutablePixelArrayPicture(pixels);

			System.out.println("Failed: exception not thrown for inconsistent second dimension in argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pixels = new Pixel[][] { { RED } };
			pic = new MutablePixelArrayPicture(pixels);

			pixels = new Pixel[][] { { RED }, { RED } };
			pic = new MutablePixelArrayPicture(pixels);

			pixels = new Pixel[][] { { RED, BLUE } };
			pic = new MutablePixelArrayPicture(pixels);

			pixels = new Pixel[][] { { RED, BLUE }, { RED, GREEN } };
			pic = new MutablePixelArrayPicture(pixels);

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		System.out.println("Passed");

	}

	@Test
	public void testMPAPWidthHeightInitValueConstructor() {
		// Tests the form of the constructor that takes in width, height, and initial
		// pixel
		System.out.println("Testing MPAP(Width, Height, InitValue) Constructor");

		try {
			MutablePixelArrayPicture.class.getConstructor(int.class, int.class, Pixel.class);
		} catch (Exception e) {
			System.out.println("Failed: no such constructor found");
			fail("No such constructor found");
		}

		try {
			pic = new MutablePixelArrayPicture(-1, 1, RED);

			System.out.println("Failed: exception not thrown for illegal width");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MutablePixelArrayPicture(0, 1, RED);

			System.out.println("Failed: exception not thrown for illegal width");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MutablePixelArrayPicture(1, -2, RED);

			System.out.println("Failed: exception not thrown for illegal height");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MutablePixelArrayPicture(1, 0, RED);

			System.out.println("Failed: exception not thrown for illegal height");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MutablePixelArrayPicture(2, 1, null);

			System.out.println("Failed: exception not thrown for illegal initial pixel value");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MutablePixelArrayPicture(1, 1, RED);
			pic = new MutablePixelArrayPicture(1, 2, new ColorPixel(0.2, 0.2, 0));
			pic = new MutablePixelArrayPicture(3, 2, new GrayPixel(0.2));

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		System.out.println("Passed");

	}

	@Test
	public void testMPAPWidthHeightConstructor() {
		// Tests the form of the constructor that takes in width and height
		System.out.println("Testing MPAP(Width, Height, InitValue) Constructor");

		try {
			MutablePixelArrayPicture.class.getConstructor(int.class, int.class);
		} catch (Exception e) {
			System.out.println("Failed: no such constructor found");
			fail("No such constructor found");
		}

		try {
			pic = new MutablePixelArrayPicture(-2, 1);

			System.out.println("Failed: exception not thrown for illegal width");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MutablePixelArrayPicture(0, 1);

			System.out.println("Failed: exception not thrown for illegal width");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MutablePixelArrayPicture(2, -1);

			System.out.println("Failed: exception not thrown for illegal height");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MutablePixelArrayPicture(1, 0);

			System.out.println("Failed: exception not thrown for illegal height");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MutablePixelArrayPicture(1, 1);
			pic = new MutablePixelArrayPicture(1, 2);
			pic = new MutablePixelArrayPicture(2, 2);

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		System.out.println("Passed");

	}

	@Test
	public void testMPAPDimensionGetters() {
		// Tests getWidth() and getHeight()

		System.out.println("Testing MPAP Dimension Getters");
		try {
			MutablePixelArrayPicture.class.getMethod("getWidth");
			MutablePixelArrayPicture.class.getMethod("getHeight");
		} catch (Exception e) {
			System.out.println("Failed: dimension getters not found");
			fail("Dimension getters not found");
		}

		Pixel[][] pixels;

		pixels = new Pixel[][] { { RED } };
		pic = new MutablePixelArrayPicture(pixels);

		assertEquals(1, pic.getWidth());
		assertEquals(1, pic.getHeight());

		pixels = new Pixel[][] { { BLUE }, { BLUE } };
		pic = new MutablePixelArrayPicture(pixels);

		assertEquals(2, pic.getWidth());
		assertEquals(1, pic.getHeight());

		pixels = new Pixel[][] { { RED, BLUE, GREEN } };
		pic = new MutablePixelArrayPicture(pixels);

		assertEquals(1, pic.getWidth());
		assertEquals(3, pic.getHeight());

		pixels = new Pixel[][] { { BLACK, WHITE }, { YELLOW, PINK } };
		pic = new MutablePixelArrayPicture(pixels);

		assertEquals(2, pic.getWidth());
		assertEquals(2, pic.getHeight());

		pic = new MutablePixelArrayPicture(2, 1, RED);
		assertEquals(2, pic.getWidth());
		assertEquals(1, pic.getHeight());

		pic = new MutablePixelArrayPicture(100, 6, BLUE);
		assertEquals(100, pic.getWidth());
		assertEquals(6, pic.getHeight());

		pic = new MutablePixelArrayPicture(20, 16);
		assertEquals(20, pic.getWidth());
		assertEquals(16, pic.getHeight());

		System.out.println("Passed");
	}

	@Test
	public void testMPAPGetPixel() {
		// Creates a picture from a 2D array of pixels with different pixels in various
		// places.
		// Tests that pixel returned from getPixel at those places returns the same
		// pixels

		System.out.println("Testing MPAP GetPixel()");
		try {
			MutablePixelArrayPicture.class.getMethod("getPixel", int.class, int.class);
		} catch (Exception e) {
			System.out.println("Failed: getPixel() not found");
			fail("Method not found");
		}

		pic = new MutablePixelArrayPicture(6, 5);
		try {
			pic.getPixel(-1, 1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.getPixel(6, 1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.getPixel(1, -1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.getPixel(-1, 5);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		assertTrue(pic.getPixel(0, 0).equals(GRAY));
		try {
			assertTrue(pic.getPixel(5, 4).equals(GRAY));
		} catch (AssertionError e) {
			System.out.println("Failed: dimensions or domain/codomain are incorrect");
			fail("Dimensions or domain/codomain are incorrect");
		}

		pic = new MutablePixelArrayPicture(2, 4, BLUE);
		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				try {
					assertTrue(pic.getPixel(x, y).equals(BLUE));
				} catch (AssertionError e) {
					fail("Fails at (" + x + ", " + y + ")");
				}
			}
		}

		Pixel[][] checkerboard = new Pixel[][] { { BLACK, WHITE, BLACK, WHITE }, { WHITE, BLACK, WHITE, BLACK },
				{ BLACK, WHITE, BLACK, WHITE }, { WHITE, BLACK, WHITE, BLACK } };

		Picture board = new MutablePixelArrayPicture(checkerboard);

		// tests relevant cases (if these return true, then entire thing must be true)
		for (int i = 0; i < checkerboard.length; i++) {
			if (i % 2 == 0) {
				assertEquals(board.getPixel(i, i), checkerboard[i][i]);
			} else {
				assertEquals(board.getPixel(i - 1, i), checkerboard[i - 1][i]);
				assertEquals(board.getPixel(i, i - 1), checkerboard[i][i - 1]);
			}
		}

		try {
			checkerboard[0][1] = PINK;
			assertFalse(board.getPixel(0, 1).equals(checkerboard[0][1]));
		} catch (AssertionError e) {
			System.out.println(
					"Failed: Pixel array must be encapsulated within the object (meaning outside changes to the constructor's");
			System.out.println("pixel_array argument should not affect the pixel array within the object)");
			System.out.println();
			fail("Encapsulation error (see console)");
		}

		System.out.println("Passed");
	}

	@Test
	public void testMPAPPaintSinglePixel() {
		// Tests paint(x,y,p) and paint(x,y,p,f)
		System.out.println("Testing MPAP Paint() - Single Pixel");

		try {
			MutablePixelArrayPicture.class.getMethod("paint", int.class, int.class, Pixel.class);
			MutablePixelArrayPicture.class.getMethod("paint", int.class, int.class, Pixel.class, double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}

		pic = new MutablePixelArrayPicture(6, 4, PURPLE);
		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.paint(6, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.paint(1, -1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.paint(1, 4, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR, -0.1);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR, 1.01);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		pic.paint(2, 3, MY_FAVORITE_COLOR);
		if (pic.getPixel(3, 2).equals(MY_FAVORITE_COLOR)) {
			System.out.println("Failed to paint at target location (coordinates are switched)");
			fail();

		} else if (pic.getPixel(2, 3).equals(PURPLE)) {
			System.out.println("Failed to paint target location with desired color");
			fail();

		} else
			try {
				assertTrue(pic.getPixel(2, 3).equals(MY_FAVORITE_COLOR));

			} catch (AssertionError e) {
				System.out.println("Failed to paint to correct color");
				fail();
			}

		pic.paint(5, 3, RED, 0);
		assertTrue(pic.getPixel(5, 3).equals(PURPLE));

		pic.paint(5, 3, RED, 1);
		assertTrue(pic.getPixel(5, 3).equals(RED));

		pic.paint(5, 3, BLUE, 0.5);
		assertTrue(pic.getPixel(5, 3).equals(PURPLE));
		
		pic.paint(5, 3, WHITE, 0.5);
		assertTrue(pic.getPixel(5, 3).equals(new ColorPixel(0.75, 0.5, 0.75)));

		System.out.println("Passed");
	}

	@Test
	public void testMPAPPaintRectangle() {
		// Tests paint(ax, ay, bx, by, p) and paint(ax, ay, bx, by, p, f)
		// NOTE: This test ONLY checks left to right, as per the README.

		System.out.println("Testing MPAP Paint() - Rectangle");

		try {
			MutablePixelArrayPicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class);
			MutablePixelArrayPicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class, double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}

		pic = new MutablePixelArrayPicture(4, 4, GREEN);
		try { // (-1,1) (3,3) - illegal ax
			pic.paint(-1, 1, 3, 3, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try { // (4,1) (5,3) - illegal ax
			pic.paint(4, 1, 5, 3, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try { // (-2,1) (-1,3) - illegal bx (must be left to right, so ax cannot be adjusted to the right)
			pic.paint(-2, 1, -1, 3, MY_FAVORITE_COLOR);
			
			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try { // (1,1) (4,3) - illegal bx
			pic.paint(1, 1, 4, 3, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		try { // (1,-1) (3,3) - illegal ay
			pic.paint(1, -1, 3, 3, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try { // (1,4) (3,3) - illegal ay
			pic.paint(1, 4, 3, 3, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try { // (1,1) (3,-1) - illegal by 
			pic.paint(1, 1, 3, -1, MY_FAVORITE_COLOR);
			
			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try { // (1,1) (3,-1) - illegal by
			pic.paint(1, 1, 3, -1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try { // (1,1) (3,3) - legal coordinates, illegal factor
			pic.paint(1, 1, 3, 3, MY_FAVORITE_COLOR, -0.1);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try { // (1,3) (3,1) - legal coordinates, illegal factor
			pic.paint(1, 3, 3, 1, MY_FAVORITE_COLOR, 1.01);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}
		
		pic.paint(1, 1, 3, 3, MY_FAVORITE_COLOR);
		// corners check
		assertTrue(pic.getPixel(1, 1).equals(MY_FAVORITE_COLOR));
		assertTrue(pic.getPixel(3, 1).equals(MY_FAVORITE_COLOR));
		assertTrue(pic.getPixel(1, 3).equals(MY_FAVORITE_COLOR));
		assertTrue(pic.getPixel(3, 3).equals(MY_FAVORITE_COLOR));
		
		// comprehensive check
		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				boolean withinBounds = x >= 1 && x <= 3 && y >= 1 && y <= 3;
				if (withinBounds) {
					try {
						assertTrue(pic.getPixel(x, y).equals(MY_FAVORITE_COLOR));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates within rectangle were not painted");
						fail();
					}
					
				} else {
					try {
						assertTrue(pic.getPixel(x, y).equals(GREEN));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates outside rectangle were painted");
						fail();
					}
					
				}
			}
		}
		
		
		pic = new MutablePixelArrayPicture(4, 4);

		pic.paint(0, 0, 3, 3, RED, 0);
		for (int i = 0; i < pic.getHeight(); i++) {
			assertTrue(pic.getPixel(i, i).equals(GRAY));
		}
		
		pic.paint(1, 1, 3, 3, BLACK, 1);
		assertTrue(pic.getPixel(0, 0).equals(GRAY));
		assertTrue(pic.getPixel(0, 3).equals(GRAY));
		assertTrue(pic.getPixel(3, 0).equals(GRAY));
		for (int i = 1; i < pic.getHeight(); i++) {
			assertTrue(pic.getPixel(i, i).equals(BLACK));
		}
		
		pic.paint(1, 1, 2, 2, GRAY, 0.5);
		assertTrue(pic.getPixel(0, 0).equals(GRAY));
		assertTrue(pic.getPixel(1, 3).equals(BLACK));
		assertTrue(pic.getPixel(3, 1).equals(BLACK));
		assertTrue(pic.getPixel(3, 3).equals(BLACK));
		for (int i = 1; i < 3; i++) {
			assertTrue(pic.getPixel(i, i).equals(new GrayPixel(0.25)));
		}
		
		System.out.println("Passed");
	}

	@Test
	public void testMPAPPaintCircle() {
		// Tests paint(cx, cy, radius, p) and paint(cx, cy, radius, p, f);
		System.out.println("Testing MPAP Paint() - Circle");

		try {
			MutablePixelArrayPicture.class.getMethod("paint", int.class, int.class, double.class, Pixel.class);
			MutablePixelArrayPicture.class.getMethod("paint", int.class, int.class, double.class, Pixel.class,
					double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}

		fail("Replace with testing code");
		System.out.println("Passed");
	}

	@Test
	public void testMonochromePictureConstructor() {
		System.out.println("Testing MonochromePicture(Width, Height, InitValue) Constructor");

		try {
			MonochromePicture.class.getConstructor(int.class, int.class, Pixel.class);
		} catch (Exception e) {
			System.out.println("Failed: no such constructor found");
			fail("No such constructor found");
		}

		try {
			pic = new MonochromePicture(-1, 1, RED);

			System.out.println("Failed: exception not thrown for illegal width");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MonochromePicture(0, 1, RED);

			System.out.println("Failed: exception not thrown for illegal width");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MonochromePicture(1, -2, BLUE);

			System.out.println("Failed: exception not thrown for illegal height");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MonochromePicture(1, 0, YELLOW);

			System.out.println("Failed: exception not thrown for illegal height");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MonochromePicture(2, 1, null);

			System.out.println("Failed: exception not thrown for illegal initial pixel value");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MonochromePicture(1, 1, ORANGE);
			pic = new MonochromePicture(1, 2, new ColorPixel(0.2, 0.2, 0));
			pic = new MonochromePicture(3, 2, new GrayPixel(0.2));

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		System.out.println("Passed");
	}

	@Test
	public void testMonochromePictureGetPixel() {
		// Tests that the pixel returned by getPixel is the same one given to the
		// constructor
		System.out.println("Testing MonochromePicture GetPixel()");

		try {
			MonochromePicture.class.getMethod("getPixel", int.class, int.class);
		} catch (Exception e) {
			System.out.println("Failed: getPixel() not found");
			fail("Method not found");
		}

		pic = new MonochromePicture(5, 3, YELLOW);
		try {
			pic.getPixel(-1, 1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.getPixel(5, 1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.getPixel(1, -1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic.getPixel(-1, 3);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException();
		}

		assertTrue(pic.getPixel(0, 0).equals(YELLOW));
		try {
			assertTrue(pic.getPixel(4, 2).equals(YELLOW));
		} catch (AssertionError e) {
			System.out.println("Failed: dimensions or domain/codomain are incorrect");
			fail("Dimensions or domain/codomain are incorrect");
		}

		pic = new MonochromePicture(4, 2, BLUE);
		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				try {
					assertTrue(pic.getPixel(x, y).equals(BLUE));
				} catch (AssertionError e) {
					fail("Fails at (" + x + ", " + y + ")");
				}
			}
		}

		System.out.println("Passed");
	}

	@Test
	public void testMonochromePicturePaintFail() {
		// Tests that trying to paint on a monochrome picture throws an exception of the
		// appropriate type
		System.out.println("Testing MonochromePicture Paint()");

		try { // checks to see if paint methods exist, even if they're unsupported
			MonochromePicture.class.getMethod("paint", int.class, int.class, Pixel.class);
			MonochromePicture.class.getMethod("paint", int.class, int.class, Pixel.class, double.class);
			MonochromePicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class);
			MonochromePicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class,
					double.class);
			MonochromePicture.class.getMethod("paint", int.class, int.class, double.class, Pixel.class);
			MonochromePicture.class.getMethod("paint", int.class, int.class, double.class, Pixel.class, double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}

		Picture pic;
		try {
			pic = new MonochromePicture(1, 1, YELLOW);
			pic.paint(0, 0, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MonochromePicture(1, 1, YELLOW);
			pic.paint(0, 0, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MonochromePicture(3, 3, YELLOW);
			pic.paint(0, 0, 1, 1, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MonochromePicture(3, 3, YELLOW);
			pic.paint(0, 0, 1, 1, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MonochromePicture(3, 3, YELLOW);
			pic.paint(1, 1, 1.0, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}

		try {
			pic = new MonochromePicture(3, 3, YELLOW);
			pic.paint(1, 1, 1.0, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException();
		}

		System.out.println("Passed");
	}

	@Test
	public void testMonochromePictureDimensionGetters() {
		// Tests getWidth() and getHeight()

		System.out.println("Testing MonochromePicture Dimension Getters");
		try {
			MonochromePicture.class.getMethod("getWidth");
			MonochromePicture.class.getMethod("getHeight");
		} catch (Exception e) {
			System.out.println("Failed: dimension getters not found");
			fail("Dimension getters not found");
		}

		pic = new MonochromePicture(2, 5, PURPLE);
		assertEquals(2, pic.getWidth());
		assertEquals(5, pic.getHeight());

		pic = new MonochromePicture(100, 6, BLUE);
		assertEquals(100, pic.getWidth());
		assertEquals(6, pic.getHeight());

		pic = new MonochromePicture(20, 16, new GrayPixel(0.5));
		assertEquals(20, pic.getWidth());
		assertEquals(16, pic.getHeight());

		System.out.println("Passed");
	}

	public static void incorrectException() { // writing this repeatedly is redundant asf
		System.out.println("Failed: correct exception not thrown");
		fail("Correct Exception not thrown");
	}

}
