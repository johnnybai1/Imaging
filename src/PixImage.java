/* PixImage.java */

/**
 *  The PixImage class represents an image, which is a rectangular grid of
 *  color pixels.  Each pixel has red, green, and blue intensities in the range
 *  0...255.  Descriptions of the methods you must implement appear below.
 *  They include a constructor of the form
 *
 *      public PixImage(int width, int height);
 *
 *  that creates a black (zero intensity) image of the specified width and
 *  height.  Pixels are numbered in the range (0...width - 1, 0...height - 1).
 *
 *  All methods in this class must be implemented to complete Part I.
 *  See the README file accompanying this project for additional details.
 */

public class PixImage {

    /**
     *  Define any variables associated with a PixImage object here.  These
     *  variables MUST be private.
     */
    private short[][] red;
    private short[][] green;
    private short[][] blue;

    private static final short[][] NEIGHBORS = {
            {-1, -1}, {-1, 0}, {-1, 1}, // TL, L, BL
            { 0, -1}, { 0, 0}, { 0, 1}, // T, C, B
            { 1, -1}, { 1, 0}, { 1, 1} // TR, R, BR
    };

    private static final short[][] GX = {
            {1, 0, -1},
            {2, 0, -2},
            {1, 0, -1}
    };

    private static final short[][] GY = {
            { 1,  2,  1},
            { 0,  0,  0},
            {-1, -2, -1}
    };



    /**
     * PixImage() constructs an empty PixImage with a specified width and height.
     * Every pixel has red, green, and blue intensities of zero (solid black).
     *
     * @param width the width of the image.
     * @param height the height of the image.
     */
    public PixImage(int width, int height) {
        red = new short[width][height];
        green = new short[width][height];
        blue = new short[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                red[x][y] = 0;
                green[x][y] = 0;
                blue[x][y] = 0;
            }
        }
    }

    /**
     * getWidth() returns the width of the image.
     *
     * @return the width of the image.
     */
    public int getWidth() {
        return red.length;
    }

    /**
     * getHeight() returns the height of the image.
     *
     * @return the height of the image.
     */
    public int getHeight() {
        return red[0].length;
    }

    /**
     * getRed() returns the red intensity of the pixel at coordinate (x, y).
     *
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @return the red intensity of the pixel at coordinate (x, y).
     */
    public short getRed(int x, int y) {
        return red[x][y];
    }

    /**
     * getGreen() returns the green intensity of the pixel at coordinate (x, y).
     *
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @return the green intensity of the pixel at coordinate (x, y).
     */
    public short getGreen(int x, int y) {
        return green[x][y];
    }

    /**
     * getBlue() returns the blue intensity of the pixel at coordinate (x, y).
     *
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @return the blue intensity of the pixel at coordinate (x, y).
     */
    public short getBlue(int x, int y) {
        return blue[x][y];
    }

    /**
     * setPixel() sets the pixel at coordinate (x, y) to specified red, green,
     * and blue intensities.
     *
     * If any of the three color intensities is NOT in the range 0...255, then
     * this method does NOT change any of the pixel intensities.
     *
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @param red the new red intensity for the pixel at coordinate (x, y).
     * @param green the new green intensity for the pixel at coordinate (x, y).
     * @param blue the new blue intensity for the pixel at coordinate (x, y).
     */
    public void setPixel(int x, int y, short red, short green, short blue) {
        if (red >= 0 || red < 256 || green >= 0 || green < 256 || blue >= 0 || blue < 256) {
            this.red[x][y] = red;
            this.green[x][y] = green;
            this.blue[x][y] = blue;
        }
    }

    /**
     * toString() returns a String representation of this PixImage.
     *
     * This method isn't required, but it should be very useful to you when
     * you're debugging your code.  It's up to you how you represent a PixImage
     * as a String.
     *
     * @return a String representation of this PixImage.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int height = getHeight();
        int width = getWidth();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                sb.append("(");
                sb.append(getRed(x, y));
                sb.append(", ");
                sb.append(getGreen(x, y));
                sb.append(", ");
                sb.append(getBlue(x, y));
                sb.append(")");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * boxBlur() returns a blurred version of "this" PixImage.
     *
     * If numIterations == 1, each pixel in the output PixImage is assigned
     * a value equal to the average of its neighboring pixels in "this" PixImage,
     * INCLUDING the pixel itself.
     *
     * A pixel not on the image boundary has nine neighbors--the pixel itself and
     * the eight pixels surrounding it.  A pixel on the boundary has six
     * neighbors if it is not a corner pixel; only four neighbors if it is
     * a corner pixel.  The average of the neighbors is the sum of all the
     * neighbor pixel values (including the pixel itself) divided by the number
     * of neighbors, with non-integer quotients rounded toward zero (as Java does
     * naturally when you divide two integers).
     *
     * Each color (red, green, blue) is blurred separately.  The red input should
     * have NO effect on the green or blue outputs, etc.
     *
     * The parameter numIterations specifies a number of repeated iterations of
     * box blurring to perform.  If numIterations is zero or negative, "this"
     * PixImage is returned (not a copy).  If numIterations is positive, the
     * return value is a newly constructed PixImage.
     *
     * IMPORTANT:  DO NOT CHANGE "this" PixImage!!!  All blurring/changes should
     * appear in the new, output PixImage only.
     *
     * @param numIterations the number of iterations of box blurring.
     * @return a blurred version of "this" PixImage.
     */
    public PixImage boxBlur(int numIterations) {
        if (numIterations <= 0) {
            return this;
        }
        else {
            PixImage blur = boxBlur();
            for (int i = 1; i < numIterations; i++) {
                blur = blur.boxBlur();
            }
            return blur;
        }
    }

    private PixImage boxBlur() {
        int width = getWidth();
        int height = getHeight();
        PixImage blur = new PixImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                short r = getNeighborAverage(red, x, y);
                short g = getNeighborAverage(green, x, y);
                short b = getNeighborAverage(blue, x, y);
                blur.setPixel(x, y, r, g, b);
            }
        }
        return blur;
    }

    /**
     * mag2gray() maps an energy (squared vector magnitude) in the range
     * 0...24,969,600 to a grayscale intensity in the range 0...255.  The map
     * is logarithmic, but shifted so that values of 5,080 and below map to zero.
     *
     * DO NOT CHANGE THIS METHOD.  If you do, you will not be able to get the
     * correct images and pass the autograder.
     *
     * @param mag the energy (squared vector magnitude) of the pixel whose
     * intensity we want to compute.
     * @return the intensity of the output pixel.
     */
    private static short mag2gray(long mag) {
        short intensity = (short) (30.0 * Math.log(1.0 + (double) mag) - 256.0);

        // Make sure the returned intensity is in the range 0...255, regardless of
        // the input value.
        if (intensity < 0) {
            intensity = 0;
        } else if (intensity > 255) {
            intensity = 255;
        }
        return intensity;
    }

    /**
     * sobelEdges() applies the Sobel operator, identifying edges in "this"
     * image.  The Sobel operator computes a magnitude that represents how
     * strong the edge is.  We compute separate gradients for the red, blue, and
     * green components at each pixel, then sum the squares of the three
     * gradients at each pixel.  We convert the squared magnitude at each pixel
     * into a grayscale pixel intensity in the range 0...255 with the logarithmic
     * mapping encoded in mag2gray().  The output is a grayscale PixImage whose
     * pixel intensities reflect the strength of the edges.
     *
     * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
     *
     * @return a grayscale PixImage representing the edges of the input image.
     * Whiter pixels represent stronger edges.
     */
    public PixImage sobelEdges() {
        int width = getWidth();
        int height = getHeight();
        PixImage sobel = new PixImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                short intensity = mag2gray(energy(x, y));
                sobel.setPixel(x, y, intensity, intensity, intensity);
            }
        }
        return sobel;
    }

    private short[][] getReflectedNeighbors(short[][] color, int x, int y) {
        short[][] grid = new short[3][3];
        int count = 0; // keep track of where we are
        for (short[] offset : NEIGHBORS) {
            grid[count / 3][count % 3] = getReflectedValue(color, x + offset[0], y + offset[1]);
            // System.out.println("(" + offset[0] + ", " + offset[1] + ")");
            count++;
        }
        return grid;
    }

    private short getReflectedValue(short[][] color, int x, int y) {
        if (x == -1) {
            if (y == -1) {
                return color[0][0];
            }
            if (y == getHeight()) {
                return color[0][y - 1];
            }
            else return color[0][y];
        }
        else if (x == getWidth()) {
            if (y == -1) {
                return color[x - 1][0];
            }
            if (y == getHeight()) {
                return color[x - 1][y - 1];
            }
            else return color[x - 1][y];
        }
        else if (y == - 1) {
            return color[x][0];
        }
        else if (y == getHeight()) {
            return color[x][y - 1];
        }
        else return color[x][y];
    }

    private long sumGX(short[][] grid, int x, int y) {
        long result = 0;
        short[][] neighbors = getReflectedNeighbors(grid, x, y);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result += (neighbors[i][j] * GX[i][j]);
            }
        }
        return result;
    }

    private long sumGY(short[][] grid, int x, int y) {
        long result = 0;
        short[][] neighbors = getReflectedNeighbors(grid, x, y);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result += (neighbors[i][j] * GY[i][j]);
            }
        }
        return result;
    }

    private long energy(int x, int y) {
        long rGX = sumGX(red, x, y);
        long rGY = sumGY(red, x, y);
        long gGX = sumGX(green, x, y);
        long gGY = sumGY(green, x, y);
        long bGX = sumGX(blue, x, y);
        long bGY = sumGY(blue, x, y);
        return (rGX * rGX) + (rGY * rGY) + (gGX * gGX) + (gGY * gGY) + (bGX * bGX) + (bGY * bGY);
    }

    /**
     * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
     * You are welcome to add tests, though.  Methods below this point will not
     * be tested.  This is not the autograder, which will be provided separately.
     */


    /**
     * doTest() checks whether the condition is true and prints the given error
     * message if it is not.
     *
     * @param b the condition to check.
     * @param msg the error message to print if the condition is false.
     */
    private static void doTest(boolean b, String msg) {
        if (b) {
            System.out.println("Good.");
        } else {
            System.err.println(msg);
        }
    }

    /**
     * array2PixImage() converts a 2D array of grayscale intensities to
     * a grayscale PixImage.
     *
     * @param pixels a 2D array of grayscale intensities in the range 0...255.
     * @return a new PixImage whose red, green, and blue values are equal to
     * the input grayscale intensities.
     */
    private static PixImage array2PixImage(int[][] pixels) {
        int width = pixels.length;
        int height = pixels[0].length;
        PixImage image = new PixImage(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                        (short) pixels[x][y]);
            }
        }

        return image;
    }

    /**
     * equals() checks whether two images are the same, i.e. have the same
     * dimensions and pixels.
     *
     * @param image a PixImage to compare with "this" PixImage.
     * @return true if the specified PixImage is identical to "this" PixImage.
     */
    public boolean equals(PixImage image) {
        int width = getWidth();
        int height = getHeight();

        if (image == null ||
                width != image.getWidth() || height != image.getHeight()) {
            return false;
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (! (getRed(x, y) == image.getRed(x, y) &&
                        getGreen(x, y) == image.getGreen(x, y) &&
                        getBlue(x, y) == image.getBlue(x, y))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * onBoard() checks whether or not the specified indices point to a pixel or not
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return true if not null, false if null
     */
    private boolean onBoard(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    /**
     * getNeighborAverage() calculates what the average intensity of a pixel based on its neighbors
     * @param color the color we wish to calculate the average for
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return the average intensity of a particular color
     */
    private short getNeighborAverage(short[][] color, int x, int y) {
        short result = 0;
        int count = 0;
        for (short[] offset : NEIGHBORS) {
            if (onBoard(x + offset[1], y + offset[0])) {
                result += color[x + offset[1]][y + offset[0]];
                count ++;
            }
        }
        return (short) (result / count);
    }

    /**
     * main() runs a series of tests to ensure that the convolutions (box blur
     * and Sobel) are correct.
     */
    public static void main(String[] args) {
        // Be forwarned that when you write arrays directly in Java as below,
        // each "row" of text is a column of your image--the numbers get
        // transposed.

        PixImage image1 = array2PixImage(new int[][] {
                { 0, 10, 240 },
                { 30, 120, 250 },
                { 80, 250, 255 } });

        System.out.println("Testing getWidth/getHeight on a 3x3 image.  " +
                "Input image:");
        System.out.print(image1);
        doTest(image1.getWidth() == 3 && image1.getHeight() == 3,
                "Incorrect image width and height.");

        System.out.println("Testing blurring on a 3x3 image.");
        doTest(image1.boxBlur(1).equals(
                array2PixImage(new int[][] { { 40, 108, 155 },
                        { 81, 137, 187 },
                        { 120, 164, 218 } })),
                "Incorrect box blur (1 rep):\n" + image1.boxBlur(1));
        doTest(image1.boxBlur(2).equals(
                array2PixImage(new int[][] { { 91, 118, 146 },
                        { 108, 134, 161 },
                        { 125, 151, 176 } })),
                "Incorrect box blur (2 rep):\n" + image1.boxBlur(2));
        doTest(image1.boxBlur(2).equals(image1.boxBlur(1).boxBlur(1)),
                "Incorrect box blur (1 rep + 1 rep):\n" +
                        image1.boxBlur(2) + image1.boxBlur(1).boxBlur(1));

        System.out.println("Testing edge detection on a 3x3 image.");
        doTest(image1.sobelEdges().equals(
                array2PixImage(new int[][] { { 104, 189, 180 },
                        { 160, 193, 157 },
                        { 166, 178, 96 } })),
                "Incorrect Sobel:\n" + image1.sobelEdges());


        PixImage image2 = array2PixImage(new int[][] { { 0, 100, 100 },
                { 0, 0, 100 } });
        System.out.println("Testing getWidth/getHeight on a 2x3 image.  " +
                "Input image:");
        System.out.print(image2);
        doTest(image2.getWidth() == 2 && image2.getHeight() == 3,
                "Incorrect image width and height.");

        System.out.println("Testing blurring on a 2x3 image.");
        doTest(image2.boxBlur(1).equals(
                array2PixImage(new int[][] { { 25, 50, 75 },
                        { 25, 50, 75 } })),
                "Incorrect box blur (1 rep):\n" + image2.boxBlur(1));

        System.out.println("Testing edge detection on a 2x3 image.");
        doTest(image2.sobelEdges().equals(
                array2PixImage(new int[][] { { 122, 143, 74 },
                        { 74, 143, 122 } })),
                "Incorrect Sobel:\n" + image2.sobelEdges());
    }
}