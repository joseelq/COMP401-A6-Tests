package a6tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import a6.*;


//A simpler version of A6JediTests that does not require functional Jedi classes as a prerequisite

//These also don't test for several other things that KMP's solution on gradescope doesn't account for, including:
//1) GradientPicture getPixel() edge case where width or height == 1
//2) Proper Encapsulation
public class A6JediTestsSimple extends A6Helper{
	
	@Test
	public void testGradientPictureConstructor() {
		// Tests the constructor for GradientPicture
		System.out.println("Testing GradientPicture Constructor...");
		try {
			GradientPicture.class.getConstructor(int.class, int.class, Pixel.class, Pixel.class, Pixel.class,
					Pixel.class);
		} catch (Exception e) {
			constructorNotFound();
		}

		// Edge Cases
		try {
			pic = new GradientPicture(0, 1, ORANGE, RED, RED, RED);

			unthrownExceptionCatch("illegal width");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic = new GradientPicture(-1, 1, ORANGE, RED, RED, RED);

			unthrownExceptionCatch("illegal width");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic = new GradientPicture(1, -2, YELLOW, YELLOW, YELLOW, YELLOW);

			unthrownExceptionCatch("illegal height");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic = new GradientPicture(1, 0, YELLOW, YELLOW, YELLOW, YELLOW);

			unthrownExceptionCatch("illegal height");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic = new GradientPicture(2, 2, null, WHITE, BLACK, BLUE);

			unthrownExceptionCatch("illegal upper_left pixel value");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic = new GradientPicture(2, 2, GRAY, null, BLACK, BLUE);

			unthrownExceptionCatch("illegal upper_right pixel value");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic = new GradientPicture(2, 2, GRAY, BLACK, null, BLUE);

			unthrownExceptionCatch("illegal lower_left pixel value");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic = new GradientPicture(2, 2, GRAY, BLACK, BLUE, null);

			unthrownExceptionCatch("illegal lower_right pixel value");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		try {
			pic = new GradientPicture(1, 1, RED, YELLOW, ORANGE, PINK);
			pic = new GradientPicture(1, 2, new ColorPixel(0.2, 0.2, 0), YELLOW, ORANGE, PINK);
			pic = new GradientPicture(3, 1, BLUE, new GrayPixel(0.2), GREEN, PURPLE);
			pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);

		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
		}

		System.out.println("Passed!");

	}

	@Test
	public void testGradientPictureGetPixel() {
		// Tests that the pixel returned by getPixel is the same one given to the
		// constructor
		checkGetPixel(GradientPicture.class);


		// Edge Cases
		pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);
		try {
			pic.getPixel(-1, 1);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.getPixel(5, 1);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.getPixel(4, -1);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.getPixel(4, 5);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
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
						legalArgumentExceptionCatch(e, "(Base Case 1, RED)");
					} catch (AssertionError e) {
						System.out.println(
								"Failed: upper_right/lower_left corner pixel should return color passed through constructor (Base Case 1, BLUE)");
						fail("Incorrect blend");
					}

				} else if (Math.abs(x - y) == 2) {
					try {
						assertTrue(pic.getPixel(x, y).equals(BLUE));
					} catch (Exception e) {
						legalArgumentExceptionCatch(e, "(Base Case 1, BLUE)");
					} catch (AssertionError e) {
						System.out.println("Failed: upper_left/lower_right corner pixel should return color passed through constructor (Base Case 1, RED)");
						fail("Incorrect blend");
					}

				} else {
					try {
						assertTrue(pic.getPixel(x, y).equals(PURPLE));
					} catch (Exception e) {
						legalArgumentExceptionCatch(e, "(Base Case 1, PURPLE)");
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
			legalArgumentExceptionCatch(e, "(Base Case 2, corners)");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, lower left corner)");
			fail();
		}

		try {

			assertTrue(pic.getPixel(0, 3).equals(GRAY));

		} catch (Exception e) {
			legalArgumentExceptionCatch(e, "(Base Case 2, corners)");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, upper left corner)");
			fail();
		}

		try {

			assertTrue(pic.getPixel(3, 0).equals(GREEN));

		} catch (Exception e) {
			legalArgumentExceptionCatch(e, "(Base Case 2, corners)");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, lower right corner)");
			fail();
		}

		try {

			assertTrue(pic.getPixel(3, 3).equals(PURPLE));

		} catch (Exception e) {
			legalArgumentExceptionCatch(e, "(Base Case 2, corners)");
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
			legalArgumentExceptionCatch(e, "(Base Case 2, top side)");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, top side)");
			fail("Incorrect blend");
		}

		try {
			// bottom side
			assertTrue(pic.getPixel(2, 3).equals(new ColorPixel(0.5, 1 / 6.0, 0.5)));
			assertTrue(pic.getPixel(1, 3).equals(new ColorPixel(0.5, 1 / 3.0, 0.5)));

		} catch (Exception e) {
			legalArgumentExceptionCatch(e, "(Base Case 2, top side)");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, bottom side)");
			fail("Incorrect blend");
		}

		try {
			// right side
			assertTrue(pic.getPixel(3, 1).equals(new ColorPixel(0.5 / 3, 2 / 3.0, 0.5 / 3)));
			assertTrue(pic.getPixel(3, 2).equals(new ColorPixel(1.0 / 3, 1 / 3.0, 1.0 / 3)));

		} catch (Exception e) {
			legalArgumentExceptionCatch(e, "(Base Case 2, right side)");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, right side)");
			fail("Incorrect blend");
		}

		try {
			// left side
			assertTrue(pic.getPixel(0, 1).equals(new ColorPixel(2.5 / 3, 2.5 / 3.0, 0.5 / 3)));
			assertTrue(pic.getPixel(0, 2).equals(new ColorPixel(2.0 / 3, 2.0 / 3.0, 1.0 / 3)));

		} catch (Exception e) {
			legalArgumentExceptionCatch(e, "(Base Case 2, left side)");
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
			legalArgumentExceptionCatch(e, "(Base Case 2, inside square)");
		} catch (AssertionError e) {
			System.out.println("Failed: getPixel() does not blend appropriately (Base Case 2, inside square)");
			fail("Incorrect blend");
		}

		System.out.println("Passed!");

	}

	@Test
	public void testGradientPaintFail() {
		// Tests that trying to paint on a monochrome picture throws an exception of the
		// appropriate type
		classPaintFail(GradientPicture.class);
		pic = new GradientPicture(5, 5, RED, YELLOW, ORANGE, PINK);
		testPaintFail();
		
	}

	@Test
	public void testGradientPictureDimensionGetters() {
		// Tests getWidth() and getHeight()
		checkDimensionGetters(GradientPicture.class);
		
		try {
			pic = new GradientPicture(2, 5, PURPLE, RED, ORANGE, GREEN);
			assertEquals(2, pic.getWidth());
			assertEquals(5, pic.getHeight());
			
		} catch (AssertionError e) {
			System.out.println("Failed: picture must be initialized to correct width/height ");
			fail("Exception thrown for legal argument");
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
		}
	
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
			constructorNotFound();
		}

		// Edge Cases
		try {
			pic1 = new MonochromePicture(5, 3, RED);
			pic2 = new GradientPicture(5, 4, RED, GREEN, YELLOW, BLUE);

			pic = new HorizontalStackPicture(pic1, pic2);

			unthrownExceptionCatch("geometrically incompatible arguments");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic1 = null;

			pic = new HorizontalStackPicture(pic1, pic2);

			unthrownExceptionCatch("null argument");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic1 = new MonochromePicture(5, 3, RED);
			pic2 = null;

			pic = new HorizontalStackPicture(pic1, pic2);

			unthrownExceptionCatch("null argument");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
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
			legalArgumentExceptionCatch(e);
		}

		System.out.println("Passed!");
	}

	@Test
	public void testHorizontalStackPictureDimensionGetters() {
		// Tests getWidth() and getHeight()
		checkDimensionGetters(HorizontalStackPicture.class);
		
		try {
			pic1 = new MonochromePicture(2, 2, RED);
			pic2 = new MonochromePicture(6, 2, RED);
			pic = new HorizontalStackPicture(pic1, pic2);
			assertEquals(8, pic.getWidth());
			assertEquals(2, pic.getHeight());
			
		} catch (AssertionError e) {
			System.out.println("Failed: picture must be initialized to correct width/height ");
			fail("Exception thrown for legal argument");
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
		}

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
		checkGetPixel(HorizontalStackPicture.class);

		// Edge Cases
		try {
			pic1 = new GradientPicture(3, 8, RED, YELLOW, ORANGE, PINK);
			
		} catch (Exception e) {
			System.out.println("Failed: Needs a working GradientPicture");
		}
		
		pic2 = new MutablePixelArrayPicture(2, 8, PURPLE);
		pic = new HorizontalStackPicture(pic1, pic2);
		try {
			pic.getPixel(-1, 1);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.getPixel(5, 1);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.getPixel(4, -1);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.getPixel(4, 8);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		try {
			assertTrue(pic.getPixel(3, 7).equals(PURPLE));
			assertTrue(pic.getPixel(3, 0).equals(PURPLE));
			assertTrue(pic.getPixel(4, 0).equals(PURPLE));
			assertTrue(pic.getPixel(4, 7).equals(PURPLE));
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
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
			legalArgumentExceptionCatch(e);
		} catch (AssertionError e) {
			if (pic.getPixel(0, 0).equals(ORANGE) && pic.getPixel(0, 7).equals(RED)) {
				System.out.println("Failed: orientation error - (y = 0 should be top)");
			} else {
				System.out.println("Failed: source picture (right) not initalized correctly");
			}
			fail();
		}

		System.out.println("Passed!");
	}

	@Test
	public void testHorizontalStackPicturePaintSinglePixel() {
		// Tests paint(x,y,p) and paint(x,y,p,f)
		checkPaint(HorizontalStackPicture.class, "Single Pixel");

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(4, 4, PURPLE);
		pic2 = new MonochromePicture(4, 4, YELLOW);
		pic = new HorizontalStackPicture(pic1, pic2);

		try {
			pic.paint(0, 0, BLUE);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(7, 3, BLUE);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, BLUE, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(7, 3, BLUE, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Case 2 : 1 mutable picture
		pic1 = new MutablePixelArrayPicture(4, 4, PURPLE);
		pic = new HorizontalStackPicture(pic1, pic2);

		// Edge Cases
		try {
			pic.paint(7, 0, BLUE);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(7, 0, BLUE, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(4, 1, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(3, -1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(3, 4, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR, 1);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(3, 4, MY_FAVORITE_COLOR, 1);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR, -0.1);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR, 1.01);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(3, 3, null);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, null, 0.9);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		try {
			pic.paint(3, 3, RED, 0);
			assertTrue(pic.getPixel(3, 3).equals(PURPLE));
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
		}

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

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(2, 0, BLUE, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(2, 2, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		try {
			pic.paint(3, 2, RED, 0);
			assertTrue(pic.getPixel(3, 2).equals(BLACK));
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
		}

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
			legalArgumentExceptionCatch(e);
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
		checkPaint(HorizontalStackPicture.class, "Rectangle");

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(2, 2, BLACK);
		pic2 = new MonochromePicture(2, 2, WHITE);
		pic = new HorizontalStackPicture(pic1, pic2);

		try {
			pic.paint(1, 0, 2, 1, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 0, 2, 1, MY_FAVORITE_COLOR, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Case 2 : 1 mutable picture
		pic1 = new MutablePixelArrayPicture(2, 2, BLACK);
		pic = new HorizontalStackPicture(pic1, pic2);

		// Edge Cases
		// Unsupported Operations
		try {
			pic.paint(1, 0, 2, 1, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(2, 0, 3, 1, MY_FAVORITE_COLOR, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Illegal arguments
		try { // (-1,0) (1,1) - illegal ax
			pic.paint(-1, 0, 1, 1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (-1,0) (1,1) - illegal ax
			pic.paint(-1, 0, 1, 1, MY_FAVORITE_COLOR, 1);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,0) (4,1) - illegal bx
			pic.paint(0, 0, 4, 1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,0) (4,1) - legal factor, illegal bx
			pic.paint(0, 0, 4, 1, MY_FAVORITE_COLOR, 0.5);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,-1) (1,1) - illegal ay
			pic.paint(0, -1, 1, 1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,2) (1,1) - illegal ay
			pic.paint(0, 2, 1, 1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,-1) (1,1) - legal factor, illegal ay
			pic.paint(0, -1, 1, 1, MY_FAVORITE_COLOR, 0.5);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,2) (1,1) - legal factor, illegal ay
			pic.paint(0, 2, 1, 1, MY_FAVORITE_COLOR, 0.5);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,1) (1,-1) - illegal by
			pic.paint(0, 1, 1, -1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,1) (1,2) - illegal by
			pic.paint(0, 1, 1, 2, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,1) (1,2) - legal factor, illegal by
			pic.paint(0, 1, 1, 2, MY_FAVORITE_COLOR, 0.5);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,1) (1,0) - legal coordinates, illegal factor
			pic.paint(0, 1, 1, 0, MY_FAVORITE_COLOR, -0.1);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,0) (1,1) - legal coordinates, illegal factor
			pic.paint(0, 0, 1, 1, MY_FAVORITE_COLOR, 1.01);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,1) (1,0) - legal coordinates, illegal pixel
			pic.paint(0, 1, 1, 0, null);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,0) (1,1) - legal coordinates and factor, illegal pixel
			pic.paint(0, 0, 1, 1, null, 0.2);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
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
			legalArgumentExceptionCatch(e);
		}
		// corners check
		assertTrue(pic.getPixel(1, 1).equals(BLUE));
		assertTrue(pic.getPixel(3, 1).equals(BLUE));
		assertTrue(pic.getPixel(1, 3).equals(BLUE));
		assertTrue(pic.getPixel(3, 3).equals(BLUE));

		try {
			pic.paint(1, 3, 3, 1, RED, 0.5);
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
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
					try {
						assertTrue(pic.getPixel(x, y).equals(((x + y) % 2 == 0) ? BLACK : WHITE));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates outside rectangle were painted");
						fail();
					}
				}
			}
		}

		System.out.println("Passed!");

	}

	@Test
	public void testHorizontalStackPicturePaintCircle() {
		// Tests paint(cx, cy, radius, p) and paint(cx, cy, radius, p, f);
		checkPaint(HorizontalStackPicture.class, "Circle");

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(2, 3, BLACK);
		pic2 = new MonochromePicture(1, 3, WHITE);
		pic = new HorizontalStackPicture(pic1, pic2);

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Case 2 : 1 mutable picture
		pic1 = new MutablePixelArrayPicture(2, 3, BLACK);
		pic = new HorizontalStackPicture(pic1, pic2);

		// Edge Cases
		// Unsupported Operations
		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Illegal arguments
		try {
			pic.paint(1, 1, -0.1, MY_FAVORITE_COLOR);

			unthrownExceptionCatch("attempting to paint with illegal radius");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, -1, MY_FAVORITE_COLOR, 0.5);

			unthrownExceptionCatch("attempting to paint with illegal radius");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 2.0, MY_FAVORITE_COLOR, -2);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 2.0, MY_FAVORITE_COLOR, 3.01);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 1, null);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 1, null, 0.5);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		pic1 = new MonochromePicture(3, 9, BLACK);
		pic2 = new MutablePixelArrayPicture(6, 9, WHITE);
		pic = new HorizontalStackPicture(pic1, pic2);

		try {
			pic.paint(3, 3, 1, MY_FAVORITE_COLOR, 0.5); // should fail at (2, 3)

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		pic1 = new MonochromePicture(3, 9, BLACK);
		pic2 = new MutablePixelArrayPicture(6, 9, WHITE);
		pic = new HorizontalStackPicture(pic1, pic2); // resets
		try {
			pic.paint(4, 4, Math.sqrt(2), MY_FAVORITE_COLOR); // paints just box of (3,3), (3,5), (5,5), (5,3)

			pic.paint(9, 9, 1, MY_FAVORITE_COLOR, 0.6); // should do nothing
			pic.paint(9, 8, 1, PINK, 0); // paints just (8,8)
			pic.paint(8, 9, 1, PINK, 1); // paints just (8,8)

		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
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
			legalArgumentExceptionCatch(e);
		}

		assertTrue(pic.getPixel(0, 1).equals(BLUE));
		assertTrue(pic.getPixel(1, 0).equals(BLUE));
		assertTrue(pic.getPixel(1, 1).equals(RED));
		assertTrue(pic.getPixel(5, 3).equals(RED));

		try {
			pic.paint(0, 3, Math.sqrt(2), BLUE, 1);
			pic.paint(5, 4, 1, BLUE, 0.5);
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
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
			legalArgumentExceptionCatch(e);
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
			constructorNotFound();
		}

		// Edge Cases
		try {
			pic1 = new MutablePixelArrayPicture(1, 7, GREEN);
			pic2 = new MutablePixelArrayPicture(10, 7, RED);

			pic = new VerticalStackPicture(pic1, pic2);

			unthrownExceptionCatch("geometrically incompatible arguments");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic1 = null;

			pic = new VerticalStackPicture(pic1, pic2);

			unthrownExceptionCatch("null argument");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic1 = new MonochromePicture(3, 3, RED);
			pic2 = null;

			pic = new VerticalStackPicture(pic1, pic2);

			unthrownExceptionCatch("null argument");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		try {
			pic1 = new MonochromePicture(4, 4, RED);
			pic2 = new MonochromePicture(4, 4, YELLOW);
			pic = new VerticalStackPicture(pic1, pic2);

			pic1 = new MutablePixelArrayPicture(4, 10);
			pic = new VerticalStackPicture(pic1, pic);

		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
		}

		System.out.println("Passed!");
	}

	@Test
	public void testVerticalStackPictureDimensionGetters() {
		// Tests getWidth() and getHeight()
		checkDimensionGetters(VerticalStackPicture.class);

		try {
			pic1 = new MonochromePicture(6, 2, RED);
			pic2 = new MonochromePicture(6, 2, RED);
			pic = new VerticalStackPicture(pic1, pic2);
			assertEquals(6, pic.getWidth());
			assertEquals(4, pic.getHeight());
			
		} catch (AssertionError e) {
			System.out.println("Failed: picture must be initialized to correct width/height ");
			fail("Exception thrown for legal argument");
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
		}

		pic1 = new MonochromePicture(4, 4, RED);
		pic2 = new MutablePixelArrayPicture(4, 4);
		pic = new VerticalStackPicture(pic1, pic2);
		assertEquals(4, pic.getWidth());
		assertEquals(8, pic.getHeight());

		pic2 = new MutablePixelArrayPicture(new Pixel[][] 
				{{ BLACK } , { BLACK } , { BLACK } , { BLUE }});
		pic = new VerticalStackPicture(pic, pic2);
		assertEquals(4, pic.getWidth());
		assertEquals(9, pic.getHeight());

		System.out.println("Passed!");
	}

	@Test
	public void testVerticalStackPictureGetPixel() {
		// Creates a picture from a 2D array of pixels with different pixels in various
		// places.
		// Tests that pixel returned from getPixel at those places returns the same
		// pixels
		checkGetPixel(VerticalStackPicture.class);

		// Edge Cases
		pic1 = new MonochromePicture(9, 7, BLACK);
		pic2 = new MonochromePicture(9, 7, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);
		try {
			pic.getPixel(-1, 1);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.getPixel(9, 1);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.getPixel(8, -1);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.getPixel(8, 14);

			illegalCoordinatesGetPixelCatch();			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		assertTrue(pic.getPixel(0, 13).equals(WHITE));
		assertTrue(pic.getPixel(8, 7).equals(WHITE));
		assertTrue(pic.getPixel(0, 6).equals(BLACK));
		assertTrue(pic.getPixel(8, 6).equals(BLACK));
		assertTrue(pic.getPixel(0, 0).equals(BLACK));
		assertTrue(pic.getPixel(5, 3).equals(BLACK));
		assertTrue(pic.getPixel(8, 0).equals(BLACK));

		System.out.println("Passed!");

	}

	@Test
	public void testVerticalStackPicturePaintSinglePixel() {
		// Tests paint(x,y,p) and paint(x,y,p,f)
		checkPaint(VerticalStackPicture.class, "Single Pixel");

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(1, 1, WHITE);
		pic2 = new MonochromePicture(1, 2, RED);
		pic = new VerticalStackPicture(pic1, pic2);

		try {
			pic.paint(0, 0, BLUE);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 2, BLUE);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, BLUE, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 2, BLUE, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Case 2 : 1 mutable picture
		pic1 = new MutablePixelArrayPicture(1, 1, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);

		// Edge Cases
		// Unsupported Operations
		try {
			pic.paint(0, 2, BLUE);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 1, BLUE, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Illegal Arguments
		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, -1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 0, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 3, MY_FAVORITE_COLOR, 0.5);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR, 0);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 4, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR, -0.1);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR, 1.01);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, null, 0.1);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 0, null, 0);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		pic.paint(0, 0, RED, 0);
		assertTrue(pic.getPixel(0, 0).equals(WHITE));

		pic.paint(0, 0, RED, 1);
		assertTrue(pic.getPixel(0, 0).equals(RED));

		pic.paint(0, 0, BLUE, 0.5);
		assertTrue(pic.getPixel(0, 0).equals(PURPLE));

		pic.paint(0, 0, WHITE, 0.5);
		assertTrue(pic.getPixel(0, 0).equals(new ColorPixel(0.75, 0.5, 0.75)));

		// Switching side of mutable picture
		pic1 = new MutablePixelArrayPicture(2, 1, BLACK);
		pic2 = new MonochromePicture(2, 2, ORANGE);
		pic = new VerticalStackPicture(pic2, pic1);

		// Edge Cases
		try {
			pic.paint(0, 0, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 1, MY_FAVORITE_COLOR, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(-1, 1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		pic.paint(1, 2, RED, 0);
		assertTrue(pic.getPixel(1, 2).equals(BLACK));

		pic.paint(1, 2, RED);
		assertTrue(pic.getPixel(1, 2).equals(RED));

		pic.paint(1, 2, BLUE, 0.5);
		assertTrue(pic.getPixel(1, 2).equals(PURPLE));

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
		checkPaint(VerticalStackPicture.class, "Rectangle");

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(2, 2, BLACK);
		pic2 = new MonochromePicture(2, 2, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);

		try {
			pic.paint(0, 0, 1, 1, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 2, 1, 3, MY_FAVORITE_COLOR, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Case 2 : 1 mutable picture
		pic1 = new MutablePixelArrayPicture(2, 2, BLACK);
		pic = new VerticalStackPicture(pic1, pic2);

		// Edge Cases
		// Unsupported Operations
		try {
			pic.paint(0, 3, 1, 2, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(0, 2, 1, 0, MY_FAVORITE_COLOR, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Illegal arguments
		try { // (-1,2) (1,2) - illegal ax
			pic.paint(-1, 2, 1, 2, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (-1,3) (1,2) - illegal ax
			pic.paint(-1, 3, 1, 2, MY_FAVORITE_COLOR, 1);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,2) (1,1) - legal factor, illegal ax
			pic.paint(-1, 3, 1, 2, MY_FAVORITE_COLOR, 0.5);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,-1) (1,2) - illegal ay
			pic.paint(0, -1, 1, 2, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,4) (1,2) - illegal ay
			pic.paint(0, 4, 1, 2, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,-1) (1,1) - legal factor, illegal ay
			pic.paint(0, 4, 1, 2, MY_FAVORITE_COLOR, 0.5);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,1) (1,-1) - illegal by
			pic.paint(0, 1, 1, -1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,2) (1,-1) - illegal by
			pic.paint(0, 2, 1, -1, MY_FAVORITE_COLOR);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,2) (1,4) - legal factor, illegal by
			pic.paint(0, 2, 1, 4, MY_FAVORITE_COLOR, 0.5);

			illegalCoordinatesPaintCatch();;			
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,1) (1,0) - legal coordinates, illegal factor
			pic.paint(0, 1, 1, 0, MY_FAVORITE_COLOR, -0.1);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,0) (1,1) - legal coordinates, illegal factor
			pic.paint(0, 0, 1, 1, MY_FAVORITE_COLOR, 1.01);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,2) (1,2) - legal coordinates, illegal pixel
			pic.paint(0, 2, 1, 2, null);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try { // (0,3) (1,3) - legal coordinates and factor, illegal pixel
			pic.paint(0, 3, 1, 3, null, 0.2);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		pic1 = new MonochromePicture(2, 2, WHITE);
		pic2 = new MutablePixelArrayPicture(2, 2, BLACK);
		pic = new VerticalStackPicture(pic1, pic2);
		
		try {
			pic.paint(0, 3, 1, 3, MY_FAVORITE_COLOR);
		} catch (UnsupportedOperationException e) {
			System.out.print("Failed: attempted to paint immutable area");
			fail("Unsupported Operation");
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
		}
		
		try {
			assertTrue(pic.getPixel(0, 3).equals(MY_FAVORITE_COLOR));
			assertTrue(pic.getPixel(1, 3).equals(MY_FAVORITE_COLOR));
			assertTrue(pic.getPixel(1, 2).equals(BLACK));
			assertTrue(pic.getPixel(0, 1).equals(WHITE));
		
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
		}
		
		pic1 = new MonochromePicture(2, 2, WHITE);
		pic2 = new MutablePixelArrayPicture(2, 2, BLACK);
		pic = new VerticalStackPicture(pic2, pic1);

		pic.paint(0, 1, 0, 0, BLACK, 1);
		pic.paint(0, 1, 0, 1, WHITE, 0.5);
		assertTrue(pic.getPixel(0, 0).equals(BLACK));
		assertTrue(pic.getPixel(0, 1).equals(GRAY));
		assertTrue(pic.getPixel(1, 1).equals(BLACK));
		assertTrue(pic.getPixel(1, 2).equals(WHITE));

		// Case 3 : 2 mutable pictures
		pic1 = new MutablePixelArrayPicture(4, 4, GREEN);
		pic2 = new MutablePixelArrayPicture(checkerboard);
		pic = new VerticalStackPicture(pic2, pic1);

		// Base Cases
		try {
			pic.paint(0, 7, 3, 4, BLUE);
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
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
			legalArgumentExceptionCatch(e);
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
					try {
						assertTrue(pic.getPixel(x, y).equals(((x + y) % 2 == 0) ? new ColorPixel(0.5, 0, 0) : new ColorPixel(1, 0.5, 0.5)));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates within rectangle were not blended");
						fail();
					}

				} else {
					try {
						assertTrue(pic.getPixel(x, y).equals((x % 2 == 0) ? BLACK : WHITE));
					} catch (AssertionError e) {
						System.out.println("Failed: coordinates outside rectangle were painted");
						fail();
					}
				
				}
			}
		}

		System.out.println("Passed!");
	}

	@Test
	public void testVerticalStackPicturePaintCircle() {
		// Tests paint(cx, cy, radius, p) and paint(cx, cy, radius, p, f);
		checkPaint(VerticalStackPicture.class, "Circle");

		// Case 1 : 2 immutable pictures
		pic1 = new MonochromePicture(3, 1, BLACK);
		pic2 = new MonochromePicture(3, 2, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Case 2 : 1 mutable picture
		pic2 = new MutablePixelArrayPicture(3, 2, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);

		// Edge Cases
		// Unsupported Operations
		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 1, MY_FAVORITE_COLOR, 0.5);

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Illegal arguments
		try {
			pic.paint(1, 1, -0.1, MY_FAVORITE_COLOR);

			unthrownExceptionCatch("attempting to paint with illegal radius");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, -1, MY_FAVORITE_COLOR, 0.5);

			unthrownExceptionCatch("attempting to paint with illegal radius");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 2.0, MY_FAVORITE_COLOR, -2);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 2.0, MY_FAVORITE_COLOR, 3.01);

			unthrownExceptionCatch("attempting to paint with illegal blend factor");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 1, null);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		try {
			pic.paint(1, 1, 1, null, 0.5);

			unthrownExceptionCatch("attempting to paint with illegal pixel");			
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		// Base Cases
		pic1 = new MonochromePicture(9, 3, BLACK);
		pic2 = new MutablePixelArrayPicture(9, 6, WHITE);
		pic = new VerticalStackPicture(pic1, pic2);

		try {
			pic.paint(3, 3, 1, MY_FAVORITE_COLOR, 0.5); // should fail at (3, 2)

			unthrownUnsupportedOperationExceptionCatch();
		} catch (UnsupportedOperationException e) {
		} catch (Exception e) {
			incorrectExceptionCatch(e);
		}

		pic1 = new MonochromePicture(9, 3, BLACK);
		pic2 = new MutablePixelArrayPicture(9, 6, WHITE);
		pic = new VerticalStackPicture(pic1, pic2); // resets
		try {
			pic.paint(4, 4, Math.sqrt(2), MY_FAVORITE_COLOR); // paints just box of (3,3), (3,5), (5,5), (5,3)

			pic.paint(9, 9, 1, MY_FAVORITE_COLOR, 0.6); // should do nothing
			pic.paint(9, 8, 1, PINK, 0); // paints just (8,8)
			pic.paint(8, 9, 1, PINK, 1); // paints just (8,8)

		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
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
		pic = new VerticalStackPicture(pic2, pic1); // 6 x 4

		// Base Cases
		try {
			pic.paint(0, 0, 1, BLUE);
			pic.paint(3, 6, 1, RED);
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
		}

		assertTrue(pic.getPixel(0, 1).equals(BLUE));
		assertTrue(pic.getPixel(1, 0).equals(BLUE));
		assertTrue(pic.getPixel(1, 1).equals(RED));
		assertTrue(pic.getPixel(3, 5).equals(RED));

		try {
			pic.paint(3, 0, Math.sqrt(2), BLUE, 1);
			pic.paint(4, 5, 1, BLUE, 0.5);
		} catch (Exception e) {
			legalArgumentExceptionCatch(e);
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
			legalArgumentExceptionCatch(e);
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

}
