package a6tests;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import a6.*;

public class A6JediTests {

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

	final static Pixel[][] checkerboard = new Pixel[][] { { BLACK, WHITE, BLACK, WHITE },
			{ WHITE, BLACK, WHITE, BLACK }, { BLACK, WHITE, BLACK, WHITE }, { WHITE, BLACK, WHITE, BLACK } };

	Picture pic, pic1, pic2;

	@BeforeEach
	public void setUp() {
		System.out.println();
	}

	@Test
	public void testGradientPictureConstructor() {
		// Tests the constructor for GradientPicture
		System.out.println("Testing GradientPicture Constructor...");
		try {
			GradientPicture.class.getConstructor(int.class, int.class, Pixel.class, Pixel.class, Pixel.class,
					Pixel.class);
		} catch (Exception e) {
			System.out.println("Failed: no such constructor found");
			fail("No such constructor found");
		}

		Picture pic;
		// Edge Cases
		try {
			pic = new GradientPicture(0, 1, ORANGE, RED, RED, RED);

			System.out.println("Failed: exception not thrown for illegal width");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(-1, 1, ORANGE, RED, RED, RED);

			System.out.println("Failed: exception not thrown for illegal width");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(1, -2, YELLOW, YELLOW, YELLOW, YELLOW);

			System.out.println("Failed: exception not thrown for illegal height");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(1, 0, YELLOW, YELLOW, YELLOW, YELLOW);

			System.out.println("Failed: exception not thrown for illegal height");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(2, 2, null, WHITE, BLACK, BLUE);

			System.out.println("Failed: exception not thrown for illegal upper_left pixel value");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(2, 2, GRAY, null, BLACK, BLUE);

			System.out.println("Failed: exception not thrown for illegal upper_right pixel value");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(2, 2, GRAY, BLACK, null, BLUE);

			System.out.println("Failed: exception not thrown for illegal lower_left pixel value");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(2, 2, GRAY, BLACK, BLUE, null);

			System.out.println("Failed: exception not thrown for illegal lower_right pixel value");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		try {
			pic = new GradientPicture(1, 1, RED, YELLOW, ORANGE, PINK);
			pic = new GradientPicture(1, 2, new ColorPixel(0.2, 0.2, 0), YELLOW, ORANGE, PINK);
			pic = new GradientPicture(3, 1, BLUE, new GrayPixel(0.2), GREEN, PURPLE);
			pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		System.out.println("Passed!");

	}

	@Test
	public void testGradientPictureFieldEncapsulation() {
		System.out.println("Testing GradientPicture Field Encapsulation...");
		try {
			for (Field field : GradientPicture.class.getDeclaredFields()) {

				if (field.getType().equals(Pixel[][].class)) {
					throw new DisallowedFieldException(Pixel[][].class);
				} else if (!field.getType().equals(int.class) && !field.getType().equals(Pixel.class)) {
					throw new DisallowedFieldException(field.getType());
				}

				if (!field.toString().contains("private") && !field.toString().contains("protected")) {
					throw new ExposedAccessException(field);
				}
			}

		} catch (Exception e) {
			System.out.println("Failed: " + e.getMessage());
			fail(e.getMessage());
		}
		System.out.println("Passed!");
	}

	@Test
	public void testGradientPictureGetPixel() {
		// Tests that the pixel returned by getPixel is the same one given to the
		// constructor
		System.out.println("Testing GradientPicture GetPixel()...");
		try {
			GradientPicture.class.getMethod("getPixel", int.class, int.class);
		} catch (Exception e) {
			System.out.println("Failed: getPixel() not found");
			fail("Method not found");
		}

		// Edge Cases
		pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);
		try {
			pic.getPixel(-1, 1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.getPixel(5, 1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.getPixel(4, -1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.getPixel(4, 5);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Edge Cases: Width || Height == 1
		// Case : W == H == 1
		boolean passesEdgeCase = true;
		try {
			pic = new GradientPicture(1, 1, RED, RED, RED, RED);
			assertTrue(pic.getPixel(0, 0).equals(RED));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (width = 1 && height == 1)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			if (!pic.getPixel(0, 0).equals(pic.getPixel(0, 0))) {
				System.out.println("Failed: does not account for width = 1 or height = 1");
				passesEdgeCase = false;
			} else { 
				System.out.println("Failed: getPixel() does not blend appropriately for case (width = 1 && height = 1) - should return RED");
				fail();
			}
		}

		try {

			pic = new GradientPicture(1, 1, RED, RED, BLUE, BLUE);
			assertTrue(pic.getPixel(0, 0).equals(PURPLE));

			pic = new GradientPicture(1, 1, BLUE, BLUE, RED, RED);
			assertTrue(pic.getPixel(0, 0).equals(PURPLE));

			pic = new GradientPicture(1, 1, RED, BLUE, RED, BLUE);
			assertTrue(pic.getPixel(0, 0).equals(PURPLE));

			pic = new GradientPicture(1, 1, BLUE, RED, BLUE, RED);
			assertTrue(pic.getPixel(0, 0).equals(PURPLE));

			pic = new GradientPicture(1, 1, BLUE, RED, RED, BLUE);
			assertTrue(pic.getPixel(0, 0).equals(PURPLE));

			pic = new GradientPicture(1, 1, RED, BLUE, BLUE, RED);
			assertTrue(pic.getPixel(0, 0).equals(PURPLE));
		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (width = 1 && height == 1)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			if (pic.getPixel(0, 0).equals(pic.getPixel(0, 0))) {
				System.out.println("Failed: getPixel() does not blend appropriately for case (width = 1 && height = 1) - should return PURPLE");
				fail();
			}
		}

		// Case : H == 2
		try {
			pic = new GradientPicture(1, 2, BLACK, WHITE, WHITE, BLACK);
			assertTrue(pic.getPixel(0, 0).equals(GRAY));
			assertTrue(pic.getPixel(0, 1).equals(GRAY));

			pic = new GradientPicture(1, 2, BLACK, WHITE, BLACK, WHITE);
			assertTrue(pic.getPixel(0, 0).equals(GRAY));
			assertTrue(pic.getPixel(0, 1).equals(GRAY));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (width = 1 && height == 2)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			if (pic.getPixel(0, 0).equals(pic.getPixel(0, 0))) {
				System.out.println("Failed: getPixel() does not blend appropriately for case (width = 1 && height == 2)");
				fail();
			} else {
				System.out.println("Failed: does not account for width = 1");
				passesEdgeCase = false;
			}
		}

		try {
			pic = new GradientPicture(1, 2, BLACK, BLACK, WHITE, WHITE);
			assertTrue(pic.getPixel(0, 0).equals(BLACK));
			assertTrue(pic.getPixel(0, 1).equals(WHITE));
		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (width = 1 && height == 2)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately for case (width = 1 && height == 2)");
			if (pic.getPixel(0, 0).equals(WHITE) && pic.getPixel(0, 1).equals(BLACK)) {
				System.out.println("Orientation error (y = 0 should be top)");
			}
			if (pic.getPixel(0, 0).equals(pic.getPixel(0, 0))) {
				fail();
			}
		}

		// Case: H == 3
		try {
			pic = new GradientPicture(1, 3, BLACK, BLACK, WHITE, WHITE);
			assertTrue(pic.getPixel(0, 0).equals(BLACK));
			assertTrue(pic.getPixel(0, 1).equals(GRAY));
			assertTrue(pic.getPixel(0, 2).equals(WHITE));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (width = 1 && height == 3)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately for case (width = 1 && height == 3)");
			if (pic.getPixel(0, 0).equals(WHITE) && pic.getPixel(0, 2).equals(BLACK)) {
				System.out.println("Orientation error (y = 0 should be top)");
			}
			if (pic.getPixel(0, 0).equals(pic.getPixel(0, 0))) {
				fail();
			}
		}

		// Case: W == 2
		try {
			pic = new GradientPicture(2, 1, BLACK, WHITE, WHITE, BLACK);
			assertTrue(pic.getPixel(0, 0).equals(GRAY));
			assertTrue(pic.getPixel(1, 0).equals(GRAY));

			pic = new GradientPicture(2, 1, BLACK, BLACK, WHITE, WHITE);
			assertTrue(pic.getPixel(0, 0).equals(GRAY));
			assertTrue(pic.getPixel(1, 0).equals(GRAY));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (width = 2 && height == 1)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately for case (width = 2 && height == 1)");
			if (pic.getPixel(0, 0).equals(pic.getPixel(0, 0))) {
				fail();
			} else {
				System.out.println("Failed: does not account for height = 1");
				passesEdgeCase = false;
			}
			
		}

		try {
			pic = new GradientPicture(2, 1, BLACK, WHITE, BLACK, WHITE);
			assertTrue(pic.getPixel(0, 0).equals(BLACK));
			assertTrue(pic.getPixel(1, 0).equals(WHITE));
		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (width = 1 && height == 3)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately for case (width = 1 && height == 3)");
			if (pic.getPixel(0, 0).equals(WHITE) && pic.getPixel(1, 0).equals(BLACK)) {
				System.out.println("Orientation error (x = 0 should be left)");
			}
			
			if (pic.getPixel(0, 0).equals(pic.getPixel(0, 0))) {
				fail();
			}
		}

		// Case : W == 3
		try {
			pic = new GradientPicture(3, 1, BLACK, WHITE, BLACK, WHITE);
			assertTrue(pic.getPixel(0, 0).equals(BLACK));
			assertTrue(pic.getPixel(1, 0).equals(GRAY));
			assertTrue(pic.getPixel(2, 0).equals(WHITE));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (width = 3 && height == 1)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately for case (width = 3 && height == 1)");
			if (pic.getPixel(0, 0).equals(pic.getPixel(0, 0))) {
				fail();
			} 
			
		}

		// Base Cases
		// Base Case 1
		pic = new GradientPicture(3, 3, RED, BLUE, BLUE, RED);
		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				if (x == y && x != 1) {
					try {
						assertTrue(pic.getPixel(x, y).equals(RED));
					} catch (Exception e) {
						System.out.println("Failed: Exception thrown for legal arguments (Base Case 1, BLUE)");
						fail("Exception thrown for legal arguments");
					} catch (AssertionError e) {
						System.out.println(
								"Failed: upper_right/lower_left corner pixel should return color passed through constructor (Base Case 1, BLUE)");
						fail("Incorrect blend");
					}

				} else if (Math.abs(x - y) == 2) {
					try {
						assertTrue(pic.getPixel(x, y).equals(BLUE));
					} catch (Exception e) {
						System.out.println("Failed: Exception thrown for legal arguments (Base Case 1, RED)");
						fail("Exception thrown for legal arguments");
					} catch (AssertionError e) {
						System.out.println(
								"Failed: upper_left/lower_right corner pixel should return color passed through constructor (Base Case 1, RED)");
						fail("Incorrect blend");
					}

				} else {
					try {
						assertTrue(pic.getPixel(x, y).equals(PURPLE));
					} catch (Exception e) {
						System.out.println("Failed: Exception thrown for legal arguments (Base Case 1, PURPLE)");
						fail("Exception thrown for legal arguments");
					} catch (AssertionError e) {
						System.out.println("Failed: inside pixels should return proper blend (Base Case 1, PURPLE)");
						fail("Incorrect blend");
					}
				}
			}
		}

		// Base Case 2
		pic = new GradientPicture(4, 4, YELLOW, GREEN, GRAY, PURPLE);

		// corners check
		try {

			assertTrue(pic.getPixel(0, 0).equals(YELLOW));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (Base Case 2, corners)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, lower left corner)");
			fail();
		}

		try {

			assertTrue(pic.getPixel(0, 3).equals(GRAY));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (Base Case 2, corners)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, upper left corner)");
			fail();
		}

		try {

			assertTrue(pic.getPixel(3, 0).equals(GREEN));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (Base Case 2, corners)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, lower right corner)");
			fail();
		}

		try {

			assertTrue(pic.getPixel(3, 3).equals(PURPLE));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (Base Case 2, corners)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, upper right corner)");
			fail();
		}

		// inside check
		try {
			// top side
			assertTrue(pic.getPixel(2, 0).equals(new ColorPixel(1.0 / 3.0, 1, 0)));
			assertTrue(pic.getPixel(1, 0).equals(new ColorPixel(2.0 / 3.0, 1, 0)));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (Base Case 2, top side)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, top side)");
			fail("Incorrect blend");
		}

		try {
			// bottom side
			assertTrue(pic.getPixel(2, 3).equals(new ColorPixel(0.5, 1 / 6.0, 0.5)));
			assertTrue(pic.getPixel(1, 3).equals(new ColorPixel(0.5, 1 / 3.0, 0.5)));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (Base Case 2, bottom side)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, bottom side)");
			fail("Incorrect blend");
		}

		try {
			// right side
			assertTrue(pic.getPixel(3, 1).equals(new ColorPixel(0.5 / 3, 2 / 3.0, 0.5 / 3)));
			assertTrue(pic.getPixel(3, 2).equals(new ColorPixel(1.0 / 3, 1 / 3.0, 1.0 / 3)));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (Base Case 2, right side)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, right side)");
			fail("Incorrect blend");
		}

		try {
			// left side
			assertTrue(pic.getPixel(0, 1).equals(new ColorPixel(2.5 / 3, 2.5 / 3.0, 0.5 / 3)));
			assertTrue(pic.getPixel(0, 2).equals(new ColorPixel(2.0 / 3, 2.0 / 3.0, 1.0 / 3)));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (Base Case 2, left side)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, left side)");
			fail("Incorrect blend");
		}

		try {
			// inside square
			assertTrue(pic.getPixel(2, 1).equals(new ColorPixel(7.0 / 18, 13.0 / 18, 0.5 / 3)));
			assertTrue(pic.getPixel(1, 2).equals(new ColorPixel(5.0 / 9, 5.0 / 9, 1.0 / 3)));
			assertTrue(pic.getPixel(2, 2).equals(new ColorPixel(4.0 / 9, 4.0 / 9, 1.0 / 3)));
			assertTrue(pic.getPixel(1, 1).equals(new ColorPixel(11.0 / 18, 7.0 / 9, 0.5 / 3)));

		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments (Base Case 2, inside square)");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, inside square)");
			fail("Incorrect blend");
		}

		if (passesEdgeCase) {
			System.out.println("Passed!");
		}

	}

	@Test
	public void testGradientPaintFail() {
		// Tests that trying to paint on a monochrome picture throws an exception of the
		// appropriate type
		System.out.println("Testing GradientPicture Paint()...");

		try {
			GradientPicture.class.getMethod("paint", int.class, int.class, Pixel.class);
			GradientPicture.class.getMethod("paint", int.class, int.class, Pixel.class, double.class);
			GradientPicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class);
			GradientPicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class,
					double.class);
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
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);
			pic.paint(3, 4, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);
			pic.paint(0, 0, 2, 2, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(5, 5, BLUE, GREEN, BLACK, PINK);
			pic.paint(0, 0, 1, 1, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(3, 3, RED, YELLOW, ORANGE, PINK);
			pic.paint(1, 1, 1.0, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic = new GradientPicture(3, 3, RED, YELLOW, ORANGE, PINK);
			pic.paint(1, 1, 1.0, GREEN, 0.4);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		System.out.println("Passed!");
	}

	@Test
	public void testGradientPictureDimensionGetters() {
		// Tests getWidth() and getHeight()
		System.out.println("Testing GradientPicture Dimension Getters...");
		try {
			GradientPicture.class.getMethod("getWidth");
			GradientPicture.class.getMethod("getHeight");
		} catch (Exception e) {
			System.out.println("Failed: dimension getters not found");
			fail("Dimension getters not found");
		}

		pic = new GradientPicture(2, 5, PURPLE, RED, ORANGE, GREEN);
		assertEquals(2, pic.getWidth());
		assertEquals(5, pic.getHeight());

		pic = new GradientPicture(100, 6, BLUE, RED, BLUE, RED);
		assertEquals(100, pic.getWidth());
		assertEquals(6, pic.getHeight());

		pic = new GradientPicture(20, 16, PURPLE, PURPLE, PURPLE, PINK);
		assertEquals(20, pic.getWidth());
		assertEquals(16, pic.getHeight());

		System.out.println("Passed!");
	}

	@Test
	public void testHorizontalStackPictureConstructor() {
		// Tests horizontal stack picture constructor
		System.out.println("Testing HorizontalStackPicture Constructor...");
		try {
			HorizontalStackPicture.class.getConstructor(Picture.class, Picture.class);
		} catch (Exception e) {
			System.out.println("Failed: no such constructor found");
			fail("No such constructor found");
		}

		// Edge Cases
		try {
			pic1 = new MonochromePicture(5, 3, RED);
			pic2 = new GradientPicture(5, 4, RED, GREEN, YELLOW, BLUE);

			pic = new HorizontalStackPicture(pic1, pic2);

			System.out.println("Failed: exception not thrown for geometrically incompatible arguments");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic1 = null;

			pic = new HorizontalStackPicture(pic1, pic2);

			System.out.println("Failed: exception not thrown for null argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic1 = new MonochromePicture(5, 3, RED);
			pic2 = null;

			pic = new HorizontalStackPicture(pic1, pic2);

			System.out.println("Failed: exception not thrown for null argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		try {
			pic1 = new MonochromePicture(5, 3, RED);
			pic2 = new GradientPicture(5, 3, RED, GREEN, YELLOW, BLUE);
			pic = new HorizontalStackPicture(pic1, pic2);

			pic2 = new GradientPicture(2, 3, RED, GREEN, YELLOW, BLUE);
			pic = new HorizontalStackPicture(pic, pic2);

			pic1 = new MutablePixelArrayPicture(4, 3);
			pic = new HorizontalStackPicture(pic1, pic);

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		System.out.println("Passed!");
	}

	@Test
	public void testHorizontalStackPictureFieldEncapsulation() {
		System.out.println("Testing HorizontalStackPicture Field Encapsulation...");
		try {
			for (Field field : HorizontalStackPicture.class.getDeclaredFields()) {
				if (!field.toString().contains("private") && !field.toString().contains("protected")) {
					throw new ExposedAccessException(field);
				}
			}

		} catch (Exception e) {
			System.out.println("Failed: " + e.getMessage());
			fail(e.getMessage());
		}
		System.out.println("Passed!");
	}

	@Test
	public void testHorizontalStackPictureDimensionGetters() {
		// Tests getWidth() and getHeight()
		System.out.println("Testing HorizontalStackPicture Dimension Getters...");
		try {
			HorizontalStackPicture.class.getMethod("getWidth");
			HorizontalStackPicture.class.getMethod("getHeight");
		} catch (Exception e) {
			System.out.println("Failed: dimension getters not found");
			fail("Dimension getters not found");
		}

		pic1 = new MonochromePicture(2, 2, RED);
		pic2 = new MonochromePicture(6, 2, RED);
		pic = new HorizontalStackPicture(pic1, pic2);
		assertEquals(8, pic.getWidth());
		assertEquals(2, pic.getHeight());

		pic1 = new MonochromePicture(6, 3, RED);
		pic2 = new GradientPicture(4, 3, RED, GREEN, YELLOW, BLUE);
		pic = new HorizontalStackPicture(pic1, pic2);
		assertEquals(10, pic.getWidth());
		assertEquals(3, pic.getHeight());

		pic2 = new GradientPicture(2, 3, RED, GREEN, YELLOW, BLUE);
		pic = new HorizontalStackPicture(pic, pic2);
		assertEquals(12, pic.getWidth());
		assertEquals(3, pic.getHeight());

		pic1 = new MutablePixelArrayPicture(4, 3);
		pic = new HorizontalStackPicture(pic1, pic);
		assertEquals(16, pic.getWidth());
		assertEquals(3, pic.getHeight());

		System.out.println("Passed!");
	}

	@Test
	public void testHorizontalStackPictureGetPixel() {
		// Creates a picture from a 2D array of pixels with different pixels in various
		// places.
		// Tests that pixel returned from getPixel at those places returns the same
		// pixels
		System.out.println("Testing HorizontalStackPicture GetPixel()...");
		try {
			HorizontalStackPicture.class.getMethod("getPixel", int.class, int.class);
		} catch (Exception e) {
			System.out.println("Failed: getPixel() not found");
			fail("Method not found");
		}

		// Edge Cases
		pic1 = new GradientPicture(3, 8, RED, YELLOW, ORANGE, PINK);
		pic2 = new MutablePixelArrayPicture(2, 8, PURPLE);
		pic = new HorizontalStackPicture(pic1, pic2);
		try {
			pic.getPixel(-1, 1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.getPixel(5, 1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.getPixel(4, -1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.getPixel(4, 8);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		try {
			assertTrue(pic.getPixel(3, 7).equals(PURPLE));
			assertTrue(pic.getPixel(3, 0).equals(PURPLE));
			assertTrue(pic.getPixel(4, 0).equals(PURPLE));
			assertTrue(pic.getPixel(4, 7).equals(PURPLE));
		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {
			if (pic.getPixel(4, 7).equals(PINK) || pic.getPixel(0, 7).equals(PINK)) {
				System.out.println("Failed: orientation error - (right picture is on the left)");
			} else {
				System.out.println("Failed: source picture (right) not initalized correctly");
			}
			fail();
		}

		try {
			assertTrue(pic.getPixel(0, 7).equals(ORANGE));
			assertTrue(pic.getPixel(0, 0).equals(RED));
			assertTrue(pic.getPixel(1, 0).equals(new ColorPixel(1, 0.5, 0)));
			assertTrue(pic.getPixel(2, 0).equals(YELLOW));
			assertTrue(pic.getPixel(2, 7).equals(PINK));
		} catch (Exception e) {
			System.out.println("Failed: Exception thrown for legal arguments");
			fail("Exception thrown for legal arguments");
		} catch (AssertionError e) {

			if (pic.getPixel(0, 0).equals(ORANGE) && pic.getPixel(0, 7).equals(RED)) {
				System.out.println("Failed: orientation error - (y = 0 should be top)");
			} else {
				System.out.println("Failed: source picture (right) not initalized correctly");
			}
			fail();
		}

		// Encapsulation Edge Cases (if required, makes the problem set a little more
		// challenging)
		try {
			pic2.paint(0, 0, MY_FAVORITE_COLOR);

			assertFalse(pic2.getPixel(0, 0).equals(pic.getPixel(3, 0)));
		} catch (AssertionError e) {
			System.out.println("Failed: source pictures not properly encapsulated");
//			fail("Improper encapsulation");
		}

		try {
			pic = new HorizontalStackPicture(pic2, pic1);
			pic2.paint(0, 0, GREEN);

			assertFalse(pic2.getPixel(0, 0).equals(pic.getPixel(0, 0)));
		} catch (AssertionError e) {
			System.out.println("Failed: source pictures not properly encapsulated");
//			fail("Improper encapsulation");
		}

		System.out.println("Passed!");
	}

	@Test
	public void testHorizontalStackPicturePaintSinglePixel() {
		// Tests paint(x,y,p) and paint(x,y,p,f)
		System.out.println("Testing HorizontalStackPicture Paint() - Single Pixel...");
		try {
			HorizontalStackPicture.class.getMethod("paint", int.class, int.class, Pixel.class);
			HorizontalStackPicture.class.getMethod("paint", int.class, int.class, Pixel.class, double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(4, 4, PURPLE);
		pic2 = new GradientPicture(4, 4, YELLOW, GREEN, GRAY, PURPLE);
		pic = new HorizontalStackPicture(pic1, pic2);

		try {
			pic.paint(0, 0, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(7, 3, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 0, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(7, 3, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Case 2 : 1 mutable picture
		pic1 = new MutablePixelArrayPicture(4, 4, PURPLE);
		pic = new HorizontalStackPicture(pic1, pic2);

		// Edge Cases
		try {
			pic.paint(7, 0, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(7, 0, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(4, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(3, -1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(3, 4, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR, 1);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(3, 4, MY_FAVORITE_COLOR, 1);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR, -0.1);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR, 1.01);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(3, 3, null);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 0, null, 0.9);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		pic.paint(3, 3, RED, 0);
		assertTrue(pic.getPixel(3, 3).equals(PURPLE));

		pic.paint(3, 3, RED, 1);
		assertTrue(pic.getPixel(3, 3).equals(RED));

		pic.paint(3, 3, BLUE, 0.5);
		assertTrue(pic.getPixel(3, 3).equals(PURPLE));

		pic.paint(1, 0, WHITE, 0.5);
		assertTrue(pic.getPixel(1, 0).equals(new ColorPixel(0.75, 0.5, 0.75)));

		// Switching side of mutable picture
		pic1 = new MutablePixelArrayPicture(3, 3, BLACK);
		pic2 = new MonochromePicture(3, 3, ORANGE);
		pic = new HorizontalStackPicture(pic2, pic1);

		// Edge Cases
		try {
			pic.paint(2, 2, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(2, 0, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(2, 2, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		pic.paint(3, 2, RED, 0);
		assertTrue(pic.getPixel(3, 2).equals(BLACK));

		pic.paint(3, 2, RED);
		assertTrue(pic.getPixel(3, 2).equals(RED));

		pic.paint(3, 2, BLUE, 0.5);
		assertTrue(pic.getPixel(3, 2).equals(PURPLE));

		pic.paint(3, 0, WHITE, 0.5);
		assertTrue(pic.getPixel(3, 0).equals(GRAY));

		// Case 3 : 2 mutable pictures
		pic1 = new MutablePixelArrayPicture(1, 1, RED);
		pic2 = new MutablePixelArrayPicture(1, 1, BLUE);
		pic = new HorizontalStackPicture(pic1, pic2);

		try {
			pic.paint(0, 0, BLUE, 1);
			pic.paint(1, 0, RED);
		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal values");
		}

		pic.paint(0, 0, RED);
		assertTrue(pic.getPixel(0, 0).equals(RED));

		pic.paint(1, 0, BLUE, 1);
		assertTrue(pic.getPixel(1, 0).equals(BLUE));
		assertTrue(pic.getPixel(0, 0).equals(RED));

		pic.paint(0, 0, BLUE, 0.5);
		assertTrue(pic.getPixel(0, 0).equals(PURPLE));

		System.out.println("Passed!");
	}

	@Test
	public void testHorizontalStackPicturePaintRectangle() {
		// Tests paint(ax, ay, bx, by, p) and paint(ax, ay, bx, by, p, f)
		// NOTE: This test ONLY checks left to right, as per the README.

		System.out.println("Testing HorizontalStackPicture Paint() - Rectangle...");

		try {
			HorizontalStackPicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class);
			HorizontalStackPicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class,
					double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(2, 2, BLACK);
		pic2 = new MonochromePicture(2, 2, WHITE);
		pic = new HorizontalStackPicture(pic1, pic2);

		try {
			pic.paint(1, 0, 2, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 0, 2, 1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Case 2 : 1 mutable picture
		pic1 = new MutablePixelArrayPicture(2, 2, BLACK);
		pic = new HorizontalStackPicture(pic1, pic2);

		// Edge Cases
		// Unsupported Operations
		try {
			pic.paint(1, 0, 2, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(2, 0, 3, 1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Illegal arguments
		try { // (-1,0) (1,1) - illegal ax
			pic.paint(-1, 0, 1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (-1,0) (1,1) - illegal ax
			pic.paint(-1, 0, 1, 1, MY_FAVORITE_COLOR, 1);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,0) (4,1) - illegal bx
			pic.paint(0, 0, 4, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,0) (4,1) - legal factor, illegal bx
			pic.paint(0, 0, 4, 1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,-1) (1,1) - illegal ay
			pic.paint(0, -1, 1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,2) (1,1) - illegal ay
			pic.paint(0, 2, 1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,-1) (1,1) - legal factor, illegal ay
			pic.paint(0, -1, 1, 1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,2) (1,1) - legal factor, illegal ay
			pic.paint(0, 2, 1, 1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,1) (1,-1) - illegal by
			pic.paint(0, 1, 1, -1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,1) (1,2) - illegal by
			pic.paint(0, 1, 1, 2, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,1) (1,2) - legal factor, illegal by
			pic.paint(0, 1, 1, 2, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,1) (1,0) - legal coordinates, illegal factor
			pic.paint(0, 1, 1, 0, MY_FAVORITE_COLOR, -0.1);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,0) (1,1) - legal coordinates, illegal factor
			pic.paint(0, 0, 1, 1, MY_FAVORITE_COLOR, 1.01);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,1) (1,0) - legal coordinates, illegal pixel
			pic.paint(0, 1, 1, 0, null);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,0) (1,1) - legal coordinates and factor, illegal pixel
			pic.paint(0, 0, 1, 1, null, 0.2);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		pic1 = new MutablePixelArrayPicture(2, 2, BLACK);
		pic2 = new MonochromePicture(2, 2, WHITE);
		pic = new HorizontalStackPicture(pic1, pic2);

		pic.paint(0, 0, 1, 0, MY_FAVORITE_COLOR);
		assertTrue(pic.getPixel(0, 0).equals(MY_FAVORITE_COLOR));
		assertTrue(pic.getPixel(1, 0).equals(MY_FAVORITE_COLOR));
		assertTrue(pic.getPixel(1, 1).equals(BLACK));
		assertTrue(pic.getPixel(2, 0).equals(WHITE));

		pic.paint(0, 1, 0, 0, BLACK, 1);
		pic.paint(0, 1, 0, 1, WHITE, 0.5);
		assertTrue(pic.getPixel(0, 0).equals(BLACK));
		assertTrue(pic.getPixel(0, 1).equals(GRAY));
		assertTrue(pic.getPixel(1, 0).equals(MY_FAVORITE_COLOR));
		assertTrue(pic.getPixel(1, 1).equals(BLACK));
		assertTrue(pic.getPixel(2, 0).equals(WHITE));

		// Case 3 : 2 mutable pictures
		pic1 = new MutablePixelArrayPicture(2, 4, RED);
		pic2 = new MutablePixelArrayPicture(checkerboard);
		pic = new HorizontalStackPicture(pic1, pic2);

		// Base Cases
		try {
			pic.paint(1, 1, 3, 3, BLUE);
		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}
		// corners check
		assertTrue(pic.getPixel(1, 1).equals(BLUE));
		assertTrue(pic.getPixel(3, 1).equals(BLUE));
		assertTrue(pic.getPixel(1, 3).equals(BLUE));
		assertTrue(pic.getPixel(3, 3).equals(BLUE));

		try {
			pic.paint(1, 3, 3, 1, RED, 0.5);
		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}
		assertTrue(pic.getPixel(1, 1).equals(PURPLE));
		assertTrue(pic.getPixel(3, 1).equals(PURPLE));
		assertTrue(pic.getPixel(1, 3).equals(PURPLE));
		assertTrue(pic.getPixel(3, 3).equals(PURPLE));

		// comprehensive check
		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				boolean withinBounds = (x >= 1 && x <= 3 && y >= 1 && y <= 3);
				if (withinBounds) {
					try {
						assertTrue(pic.getPixel(x, y).equals(PURPLE));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates within rectangle were not painted");
						fail();
					}

				} else if (x < 2) {
					try {
						assertTrue(pic.getPixel(x, y).equals(RED));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates outside rectangle were painted");
						fail();
					}
				} else {
					assertTrue(pic.getPixel(x, y).equals(((x + y) % 2 == 0) ? BLACK : WHITE));
				}
			}
		}

		System.out.println("Passed!");

	}

	@Test
	public void testHorizontalStackPicturePaintCircle() {
		// Tests paint(cx, cy, radius, p) and paint(cx, cy, radius, p, f);
		System.out.println("Testing HorizontalStackPicture Paint() - Circle...");

		try {
			HorizontalStackPicture.class.getMethod("paint", int.class, int.class, double.class, Pixel.class);
			HorizontalStackPicture.class.getMethod("paint", int.class, int.class, double.class, Pixel.class,
					double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(2, 3, BLACK);
		pic2 = new MonochromePicture(1, 3, WHITE);
		pic = new HorizontalStackPicture(pic1, pic2);

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Case 2 : 1 mutable picture
		pic1 = new MutablePixelArrayPicture(2, 3, BLACK);
		pic = new HorizontalStackPicture(pic1, pic2);

		// Edge Cases
		// Unsupported Operations
		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Illegal arguments
		try {
			pic.paint(1, 1, -0.1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal radius");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, -1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal radius");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 2.0, MY_FAVORITE_COLOR, -2);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 2.0, MY_FAVORITE_COLOR, 3.01);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 1, null);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 1, null, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		pic1 = new MonochromePicture(3, 9, BLACK);
		pic2 = new MutablePixelArrayPicture(6, 9, WHITE);
		pic = new HorizontalStackPicture(pic1, pic2);

		try {
			pic.paint(3, 3, 1, MY_FAVORITE_COLOR, 0.5); // should fail at (2, 3)

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		pic = new HorizontalStackPicture(pic1, pic2); // resets
		try {
			pic.paint(4, 4, Math.sqrt(2), MY_FAVORITE_COLOR); // paints just box of (3,3), (3,5), (5,5), (5,3)

			pic.paint(9, 9, 1, MY_FAVORITE_COLOR, 0.6); // should do nothing
			pic.paint(9, 8, 1, PINK, 0); // paints just (8,8)
			pic.paint(8, 9, 1, PINK, 1); // paints just (8,8)

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		// comprehensive check
		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {

				if (x >= 3 && x <= 5 && y >= 3 && y <= 5) {

					try {
						assertTrue(pic.getPixel(x, y).equals(MY_FAVORITE_COLOR));

					} catch (AssertionError e) {
						System.out.println("Failed: pixel was not painted in target circle");
						fail();
					}

				} else if (x == y && x == 8) {

					try {
						assertTrue(pic.getPixel(x, y).equals(PINK));

					} catch (AssertionError e) {
						System.out.println("Failed: pixel was not painted in target circle");
						fail();
					}

				} else {

					try {
						assertTrue(pic.getPixel(x, y).equals((x > 2) ? WHITE : BLACK));

					} catch (AssertionError e) {
						System.out.println("Failed: pixel was painted outside target circle at (" + x + "," + y + ")");
						fail();
					}
				}
			}
		}

		// Case 3 : 2 mutable pictures

		pic1 = new MutablePixelArrayPicture(2, 4, RED);
		pic2 = new MutablePixelArrayPicture(checkerboard);
		pic = new HorizontalStackPicture(pic1, pic2); // 6 x 4

		// Base Cases
		try {
			pic.paint(0, 0, 1, BLUE);
			pic.paint(6, 3, 1, RED);
		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		assertTrue(pic.getPixel(0, 1).equals(BLUE));
		assertTrue(pic.getPixel(1, 0).equals(BLUE));
		assertTrue(pic.getPixel(1, 1).equals(RED));
		assertTrue(pic.getPixel(5, 3).equals(RED));

		try {
			pic.paint(0, 3, Math.sqrt(2), BLUE, 1);
			pic.paint(5, 4, 1, BLUE, 0.5);
		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		// comprehensive check
		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				if (x == 1 && y == 1) {
					try {
						assertTrue(pic.getPixel(x, y).equals(RED));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates outside circle were painted");
						fail();
					}

				} else if (x == 5 && y == 3) {
					try {
						assertTrue(pic.getPixel(x, y).equals(PURPLE));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates inside circle were not painted");
						fail();
					}

				} else if (x < 2) {
					try {
						assertTrue(pic.getPixel(x, y).equals(BLUE));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates inside circle were not painted");
						fail();
					}
				} else {
					assertTrue(pic.getPixel(x, y).equals(((x + y) % 2 == 0) ? BLACK : WHITE));
				}
			}
		}

		try {
			pic.paint(-1, -1, 7, PINK, 0.99999); // radius < |<6,4>| (or distance to (5,3))

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		for (int x = 0; x <= 5; x++) {
			for (int y = 0; y <= 3; y++) {
				try {
					assertTrue(pic.getPixel(x, y).equals((x == 5 && y == 3) ? PURPLE : PINK));
				} catch (AssertionError e) {
					System.out.println("Failed: pixel was painted outside of target circle");
					fail();
				}

			}
		}

		System.out.println("Passed!");

	}

	@Test
	public void testVerticalStackPictureConstructor() {
		// Tests vertical stack picture constructor
		System.out.println("Testing VerticalStackPicture Constructor...");
		try {
			VerticalStackPicture.class.getConstructor(Picture.class, Picture.class);
		} catch (Exception e) {
			System.out.println("Failed: no such constructor found");
			fail("No such constructor found");
		}

		// Edge Cases
		try {
			pic1 = new GradientPicture(8, 7, RED, GREEN, YELLOW, BLUE);
			pic2 = new MutablePixelArrayPicture(10, 7, RED);

			pic = new VerticalStackPicture(pic1, pic2);

			System.out.println("Failed: exception not thrown for geometrically incompatible arguments");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic1 = null;

			pic = new VerticalStackPicture(pic1, pic2);

			System.out.println("Failed: exception not thrown for null argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic1 = new MonochromePicture(3, 3, RED);
			pic2 = null;

			pic = new VerticalStackPicture(pic1, pic2);

			System.out.println("Failed: exception not thrown for null argument");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		try {
			pic1 = new MonochromePicture(4, 4, RED);
			pic2 = new GradientPicture(4, 4, RED, GREEN, YELLOW, BLUE);
			pic = new VerticalStackPicture(pic1, pic2);

			pic2 = new HorizontalStackPicture(new MutablePixelArrayPicture(3, 3), new MonochromePicture(1, 3, ORANGE));
			pic = new VerticalStackPicture(pic, pic2);

			pic1 = new MutablePixelArrayPicture(4, 10);
			pic = new VerticalStackPicture(pic1, pic);

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		System.out.println("Passed!");
	}

	@Test
	public void testVerticalStackPictureFieldEncapsulation() {
		System.out.println("Testing VerticalStackPicture Field Encapsulation...");
		try {
			for (Field field : VerticalStackPicture.class.getDeclaredFields()) {
				if (!field.toString().contains("private") && !field.toString().contains("protected")) {
					throw new ExposedAccessException(field);
				}
			}

		} catch (Exception e) {
			System.out.println("Failed: " + e.getMessage());
			fail(e.getMessage());
		}
		System.out.println("Passed!");
	}

	@Test
	public void testVerticalStackPictureDimensionGetters() {
		// Tests getWidth() and getHeight()
		System.out.println("Testing VerticalStackPicture Dimension Getters...");
		try {
			VerticalStackPicture.class.getMethod("getWidth");
			VerticalStackPicture.class.getMethod("getHeight");
		} catch (Exception e) {
			System.out.println("Failed: dimension getters not found");
			fail("Dimension getters not found");
		}

		pic1 = new MonochromePicture(6, 2, RED);
		pic2 = new MonochromePicture(6, 2, RED);
		pic = new VerticalStackPicture(pic1, pic2);
		assertEquals(6, pic.getWidth());
		assertEquals(4, pic.getHeight());

		pic1 = new MonochromePicture(4, 4, RED);
		pic2 = new GradientPicture(4, 4, RED, GREEN, YELLOW, BLUE);
		pic = new VerticalStackPicture(pic1, pic2);
		assertEquals(4, pic.getWidth());
		assertEquals(8, pic.getHeight());

		pic2 = new HorizontalStackPicture(new MutablePixelArrayPicture(3, 3), new MonochromePicture(1, 3, ORANGE));
		pic = new VerticalStackPicture(pic, pic2);
		assertEquals(4, pic.getWidth());
		assertEquals(11, pic.getHeight());

		pic1 = new MutablePixelArrayPicture(4, 10);
		pic = new VerticalStackPicture(pic1, pic);
		assertEquals(4, pic.getWidth());
		assertEquals(21, pic.getHeight());

		System.out.println("Passed!");
	}

	@Test
	public void testVerticalStackPictureGetPixel() {
		// Creates a picture from a 2D array of pixels with different pixels in various
		// places.
		// Tests that pixel returned from getPixel at those places returns the same
		// pixels
		System.out.println("Testing VerticalStackPicture GetPixel()...");
		try {
			VerticalStackPicture.class.getMethod("getPixel", int.class, int.class);
		} catch (Exception e) {
			System.out.println("Failed: getPixel() not found");
			fail("Method not found");
		}

		// Edge Cases
		pic1 = new GradientPicture(9, 7, BLACK, RED, BLUE, GREEN);
		pic2 = new MonochromePicture(9, 7, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);
		try {
			pic.getPixel(-1, 1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.getPixel(9, 1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.getPixel(8, -1);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.getPixel(8, 14);

			System.out.println("Failed: exception not thrown for attempting to get pixel at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		assertTrue(pic.getPixel(0, 0).equals(WHITE));
		assertTrue(pic.getPixel(8, 6).equals(WHITE));
		assertTrue(pic.getPixel(0, 7).equals(BLUE));
		assertTrue(pic.getPixel(8, 7).equals(GREEN));
		assertTrue(pic.getPixel(0, 13).equals(BLACK));
		assertTrue(pic.getPixel(5, 10).equals(pic1.getPixel(5, 3)));
		assertTrue(pic.getPixel(8, 13).equals(RED));

		// Encapsulation Edge Cases (if required, makes the problem set a little more
		// challenging)
		try {
			pic2 = new MutablePixelArrayPicture(9, 5, WHITE);
			pic = new VerticalStackPicture(pic1, pic2);

			pic2.paint(0, 1, MY_FAVORITE_COLOR);

			assertFalse(pic2.getPixel(0, 1).equals(pic.getPixel(0, 1)));
		} catch (AssertionError e) {
			System.out.println("Failed: source pictures not properly encapsulated");
//			fail("Improper encapsulation");
		}

		try {
			pic = new VerticalStackPicture(pic2, pic1);
			pic2.paint(2, 2, ORANGE);

			assertFalse(pic2.getPixel(2, 2).equals(pic.getPixel(2, 10)));
		} catch (AssertionError e) {
			System.out.println("Failed: source pictures not properly encapsulated");
//			fail("Improper encapsulation");
		}

		System.out.println("Passed!");

	}

	@Test
	public void testVerticalStackPicturePaintSinglePixel() {
		// Tests paint(x,y,p) and paint(x,y,p,f)
		System.out.println("Testing VerticalStackPicture Paint() - Single Pixel...");
		try {
			VerticalStackPicture.class.getMethod("paint", int.class, int.class, Pixel.class);
			VerticalStackPicture.class.getMethod("paint", int.class, int.class, Pixel.class, double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(1, 1, WHITE);
		pic2 = new GradientPicture(1, 2, RED, BLUE, BLUE, RED);
		pic = new VerticalStackPicture(pic1, pic2);

		try {
			pic.paint(0, 0, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 2, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 0, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 2, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Case 2 : 1 mutable picture
		pic1 = new MutablePixelArrayPicture(1, 1, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);

		// Edge Cases
		// Unsupported Operations
		try {
			pic.paint(0, 0, BLUE);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 1, BLUE, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Illegal Arguments
		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, -1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 0, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 3, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR, 0);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 4, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR, -0.1);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR, 1.01);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 0, null, 0.1);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 0, null, 0);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		pic.paint(0, 2, RED, 0);
		assertTrue(pic.getPixel(0, 2).equals(WHITE));

		pic.paint(0, 2, RED, 1);
		assertTrue(pic.getPixel(0, 2).equals(RED));

		pic.paint(0, 2, BLUE, 0.5);
		assertTrue(pic.getPixel(0, 2).equals(PURPLE));

		pic.paint(0, 2, WHITE, 0.5);
		assertTrue(pic.getPixel(0, 2).equals(new ColorPixel(0.75, 0.5, 0.75)));

		// Switching side of mutable picture
		pic1 = new MutablePixelArrayPicture(1, 1, BLACK);
		pic2 = new MonochromePicture(1, 2, ORANGE);
		pic = new VerticalStackPicture(pic2, pic1);

		// Edge Cases
		try {
			pic.paint(0, 2, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		pic.paint(0, 0, RED, 0);
		assertTrue(pic.getPixel(0, 0).equals(BLACK));

		pic.paint(0, 0, RED);
		assertTrue(pic.getPixel(0, 0).equals(RED));

		pic.paint(0, 0, BLUE, 0.5);
		assertTrue(pic.getPixel(0, 0).equals(PURPLE));

		// Case 3 : 2 mutable pictures
		pic1 = new MutablePixelArrayPicture(1, 1, BLUE);
		pic2 = new MutablePixelArrayPicture(1, 1, RED);
		pic = new VerticalStackPicture(pic1, pic2);

		pic.paint(0, 0, RED);
		assertTrue(pic.getPixel(0, 0).equals(RED));

		pic.paint(0, 1, BLUE, 1);
		assertTrue(pic.getPixel(0, 1).equals(BLUE));
		assertTrue(pic.getPixel(0, 0).equals(RED));

		pic.paint(0, 0, BLUE, 0.5);
		assertTrue(pic.getPixel(0, 0).equals(PURPLE));

		System.out.println("Passed!");

	}

	@Test
	public void testVerticalStackPicturePaintRectangle() {
		// Tests paint(ax, ay, bx, by, p) and paint(ax, ay, bx, by, p, f)
		// NOTE: This test ONLY checks left to right, as per the README.

		System.out.println("Testing VerticalStackPicture Paint() - Rectangle...");

		try {
			VerticalStackPicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class);
			VerticalStackPicture.class.getMethod("paint", int.class, int.class, int.class, int.class, Pixel.class,
					double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(2, 2, BLACK);
		pic2 = new MonochromePicture(2, 2, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);

		try {
			pic.paint(0, 0, 1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 2, 1, 3, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Case 2 : 1 mutable picture
		pic1 = new MutablePixelArrayPicture(2, 2, BLACK);
		pic = new VerticalStackPicture(pic1, pic2);

		// Edge Cases
		// Unsupported Operations
		try {
			pic.paint(0, 0, 1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(0, 1, 1, 3, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Illegal arguments
		try { // (-1,2) (1,2) - illegal ax
			pic.paint(-1, 2, 1, 2, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (-1,3) (1,2) - illegal ax
			pic.paint(-1, 3, 1, 2, MY_FAVORITE_COLOR, 1);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,2) (1,1) - legal factor, illegal ax
			pic.paint(-1, 3, 1, 2, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,-1) (1,2) - illegal ay
			pic.paint(0, -1, 1, 2, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,4) (1,2) - illegal ay
			pic.paint(0, 4, 1, 2, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,-1) (1,1) - legal factor, illegal ay
			pic.paint(0, 4, 1, 2, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,1) (1,-1) - illegal by
			pic.paint(0, 1, 1, -1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,2) (1,-1) - illegal by
			pic.paint(0, 2, 1, -1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,2) (1,4) - legal factor, illegal by
			pic.paint(0, 2, 1, 4, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint at illegal coordinates");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,2) (1,3) - legal coordinates, illegal factor
			pic.paint(0, 2, 1, 3, MY_FAVORITE_COLOR, -0.1);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,3) (1,2) - legal coordinates, illegal factor
			pic.paint(0, 3, 1, 2, MY_FAVORITE_COLOR, 1.01);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,2) (1,2) - legal coordinates, illegal pixel
			pic.paint(0, 2, 1, 2, null);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try { // (0,3) (1,3) - legal coordinates and factor, illegal pixel
			pic.paint(0, 3, 1, 3, null, 0.2);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		pic1 = new MonochromePicture(2, 2, WHITE);
		pic2 = new MutablePixelArrayPicture(2, 2, BLACK);
		pic = new VerticalStackPicture(pic1, pic2);

		pic.paint(0, 0, 1, 0, MY_FAVORITE_COLOR);
		assertTrue(pic.getPixel(0, 0).equals(MY_FAVORITE_COLOR));
		assertTrue(pic.getPixel(1, 0).equals(MY_FAVORITE_COLOR));
		assertTrue(pic.getPixel(1, 1).equals(BLACK));
		assertTrue(pic.getPixel(0, 2).equals(WHITE));

		pic.paint(0, 1, 0, 0, BLACK, 1);
		pic.paint(0, 1, 0, 1, WHITE, 0.5);
		assertTrue(pic.getPixel(0, 0).equals(BLACK));
		assertTrue(pic.getPixel(0, 1).equals(GRAY));
		assertTrue(pic.getPixel(1, 0).equals(MY_FAVORITE_COLOR));
		assertTrue(pic.getPixel(1, 1).equals(BLACK));
		assertTrue(pic.getPixel(1, 2).equals(WHITE));

		// Case 3 : 2 mutable pictures
		pic1 = new HorizontalStackPicture(new MutablePixelArrayPicture(2, 4, GREEN),
				new MutablePixelArrayPicture(2, 4, GREEN));
		pic2 = new MutablePixelArrayPicture(checkerboard);
		pic = new VerticalStackPicture(pic1, pic2);

		// Base Cases
		try {
			pic.paint(0, 7, 3, 4, BLUE);
		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		// corners check
		assertTrue(pic.getPixel(3, 4).equals(BLUE));
		assertTrue(pic.getPixel(0, 4).equals(BLUE));
		assertTrue(pic.getPixel(0, 7).equals(BLUE));
		assertTrue(pic.getPixel(3, 7).equals(BLUE));

		try {
			pic.paint(1, 1, 3, 7, RED, 0.5);
			pic.paint(0, 7, 0, 0, GREEN, 1.0);
		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}
		assertTrue(pic.getPixel(1, 1).equals(PURPLE));
		assertTrue(pic.getPixel(3, 1).equals(PURPLE));
		assertTrue(pic.getPixel(1, 3).equals(PURPLE));
		assertTrue(pic.getPixel(3, 3).equals(PURPLE));

		// comprehensive check
		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				if (x == 0) {
					try {
						assertTrue(pic.getPixel(x, y).equals(GREEN));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates within rectangle were not painted");
						fail();
					}
				} else if (y >= 4) {
					try {
						assertTrue(pic.getPixel(x, y).equals(PURPLE));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates within rectangle were not painted");
						fail();
					}

				} else if (y > 0) {
					if ((x + y) % 2 == 0) {
						try {
							assertTrue(pic.getPixel(x, y).equals(new ColorPixel(0.5, 0, 0)));
						} catch (AssertionError e) {
							System.out.println("Failed: coordinates within rectangle were not blended");
							fail();
						}
					} else {
						try {
							assertTrue(pic.getPixel(x, y).equals(new ColorPixel(1, 0.5, 0.5)));
						} catch (AssertionError e) {
							System.out.println("Failed: coordinates within rectangle were not blended");
							fail();
						}
					}

				} else {
					if (x % 2 == 0) {
						assertTrue(pic.getPixel(x, y).equals(BLACK));
					} else {
						assertTrue(pic.getPixel(x, y).equals(WHITE));
					}
				}
			}
		}

		System.out.println("Passed!");
	}

	@Test
	public void testVerticalStackPicturePaintCircle() {
		// Tests paint(cx, cy, radius, p) and paint(cx, cy, radius, p, f);
		System.out.println("Testing VerticalStackPicture Paint() - Circle...");

		try {
			VerticalStackPicture.class.getMethod("paint", int.class, int.class, double.class, Pixel.class);
			VerticalStackPicture.class.getMethod("paint", int.class, int.class, double.class, Pixel.class,
					double.class);
		} catch (Exception e) {
			System.out.println("Failed: one or more paint methods not found");
			fail("Paint method(s) not found");
		}

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(3, 1, BLACK);
		pic2 = new MonochromePicture(3, 2, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Case 2 : 1 mutable picture
		pic2 = new MutablePixelArrayPicture(3, 2, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);

		// Edge Cases
		// Unsupported Operations
		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Illegal arguments
		try {
			pic.paint(1, 1, -0.1, MY_FAVORITE_COLOR);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal radius");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, -1, MY_FAVORITE_COLOR, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal radius");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 2.0, MY_FAVORITE_COLOR, -2);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 2.0, MY_FAVORITE_COLOR, 3.01);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal blend factor");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 1, null);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		try {
			pic.paint(1, 1, 1, null, 0.5);

			System.out.println("Failed: exception not thrown for attempting to paint with illegal pixel");
			fail("Exception not thrown for illegal argument");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		// Base Cases
		pic1 = new MonochromePicture(9, 3, BLACK);
		pic2 = new MutablePixelArrayPicture(9, 6, WHITE);
		pic = new VerticalStackPicture(pic2, pic1);

		try {
			pic.paint(3, 3, 1, MY_FAVORITE_COLOR, 0.5); // should fail at (3, 2)

			System.out.println("Failed: exception not thrown for unsupported operation");
			fail("Exception not thrown for illegal argument");
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectException(e);
		}

		pic = new VerticalStackPicture(pic2, pic1); // resets
		try {
			pic.paint(4, 4, Math.sqrt(2), MY_FAVORITE_COLOR); // paints just box of (3,3), (3,5), (5,5), (5,3)

			pic.paint(9, 9, 1, MY_FAVORITE_COLOR, 0.6); // should do nothing
			pic.paint(9, 8, 1, PINK, 0); // paints just (8,8)
			pic.paint(8, 9, 1, PINK, 1); // paints just (8,8)

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		// comprehensive check
		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {

				if (x >= 3 && x <= 5 && y >= 3 && y <= 5) {

					try {
						assertTrue(pic.getPixel(x, y).equals(MY_FAVORITE_COLOR));

					} catch (AssertionError e) {
						System.out.println("Failed: pixel was not painted in target circle");
						fail();
					}

				} else if (x == y && x == 8) {

					try {
						assertTrue(pic.getPixel(x, y).equals(PINK));

					} catch (AssertionError e) {
						System.out.println("Failed: pixel was not painted in target circle");
						fail();
					}

				} else {

					try {
						assertTrue(pic.getPixel(x, y).equals((y > 2) ? WHITE : BLACK));

					} catch (AssertionError e) {
						System.out.println("Failed: pixel was painted outside target circle at (" + x + "," + y + ")");
						fail();
					}
				}
			}
		}

		// Case 3 : 2 mutable pictures

		pic1 = new MutablePixelArrayPicture(checkerboard);
		pic2 = new MutablePixelArrayPicture(4, 2, RED);
		pic = new VerticalStackPicture(pic1, pic2); // 6 x 4

		// Base Cases
		try {
			pic.paint(0, 0, 1, BLUE);
			pic.paint(3, 6, 1, RED);
		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		assertTrue(pic.getPixel(0, 1).equals(BLUE));
		assertTrue(pic.getPixel(1, 0).equals(BLUE));
		assertTrue(pic.getPixel(1, 1).equals(RED));
		assertTrue(pic.getPixel(3, 5).equals(RED));

		try {
			pic.paint(3, 0, Math.sqrt(2), BLUE, 1);
			pic.paint(4, 5, 1, BLUE, 0.5);
		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		// comprehensive check
		for (int x = 0; x < pic.getWidth(); x++) {
			for (int y = 0; y < pic.getHeight(); y++) {
				if (x == 1 && y == 1) {
					try {
						assertTrue(pic.getPixel(x, y).equals(RED));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates outside circle were painted");
						fail();
					}

				} else if (x == 3 && y == 5) {
					try {
						assertTrue(pic.getPixel(x, y).equals(PURPLE));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates inside circle were not painted");
						fail();
					}

				} else if (y < 2) {
					try {
						assertTrue(pic.getPixel(x, y).equals(BLUE));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates inside circle were not painted");
						fail();
					}
				} else {
					assertTrue(pic.getPixel(x, y).equals(((x + y) % 2 == 0) ? BLACK : WHITE));
				}
			}
		}

		try {
			pic.paint(-1, -1, 7, PINK, 0.99999); // radius < |<4,6>| (or distance to (3,5))

		} catch (Exception e) {
			System.out.println("Failed: exception thrown for legal arguments");
			fail("Exception thrown for legal argument");
		}

		for (int x = 0; x <= 3; x++) {
			for (int y = 0; y <= 5; y++) {
				try {
					assertTrue(pic.getPixel(x, y).equals((x == 3 && y == 5) ? PURPLE : PINK));

				} catch (AssertionError e) {
					System.out.println("Failed: pixel was painted outside of target circle");
					fail();
				}

			}

		}

		System.out.println("Passed!");
	}

	public void incorrectException(Exception e) {
		System.out.println("Failed: correct exception not thrown");
		System.out.println();
		e.printStackTrace();
		System.out.println();
		fail("Correct Exception not thrown");
	}

}
