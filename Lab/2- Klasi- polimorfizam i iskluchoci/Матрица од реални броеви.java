import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;


public class DoubleMatrixTester {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        DoubleMatrix fm = null;

        double[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    double[] f = new double[N];

                    for (int i = 0; i < f.length; i++)
                        f[i] = scanner.nextDouble();

                    try {
                        if(f.length < R * C) { throw new InsufficientElementsException("Insufficient number of elements"); }
                        fm = new DoubleMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++)
                        sb.append(scanner.nextLine() + "\n");

                    fm = MatrixReader.read(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new double[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextDouble();
                    tempScanner.nextDouble();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextDouble();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.rows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.columns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        if ((row > fm.rows()) || (row < 0)) { throw new InvalidRowNumberException("Invalid row number"); }
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        if ((col > fm.columns()) || (col < 0)) { throw new InvalidColumnNumberException("Invalid column number"); }
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        double work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            double temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(),
                                fm.columns());
                        System.out
                                .println("Equals check 1: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    if (maxOps % 2 == 0) {
                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
                                7.5}, 1, 1);

                        System.out
                                .println("Equals check 2: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    double[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0)
                        arrayString += format.format(arr[0]) + "";

                    for (int i = 1; i < arr.length; i++)
                        arrayString += ", " + format.format(arr[i]);

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);
                    break;
                }

            }

        }

        scanner.close();
    }
}



class InsufficientElementsException extends Exception {
    public InsufficientElementsException(String message) {
        super(message);
    }
}

class InvalidRowNumberException extends Exception {
    public InvalidRowNumberException(String message) {
        super(message);
    }
}

class InvalidColumnNumberException extends Exception {
    public InvalidColumnNumberException(String message) {
        super(message);
    }
}


class DoubleMatrix {

    private double [][] matrix;
    private int rows;
    private int columns;

    public DoubleMatrix(double [] a, int m, int n) {
        rows = m;
        columns = n;
        matrix = new double[rows][columns];
        for (int i = 0, k = a.length - m*n; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = a[k++];
            }
        }
    }

    public String getDimensions() {
        return "[" + rows + " x " + columns + "]";
    }

    public double getElementAt(int i, int j) { return matrix[i][j]; }

    public int rows() { return rows; }

    public int columns() { return columns; }

    public double maxElementAtRow(int row) {
        double max = matrix[row-1][0];
        for(int i = 1; i < columns; i++) {
            if (matrix[row-1][i] > max)
                max = matrix[row-1][i];
        }
        return max;
    }

    public double maxElementAtColumn(int column) {
        double max = matrix[0][column-1];
        for(int i = 1; i < rows; i++) {
            if (matrix[i][column-1] > max)
                max = matrix[i][column-1];
        }
        return max;
    }

    public double sum() {
        double sum = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                sum += matrix[i][j];
        return sum;
    }

    public double[] toSortedArray() {
        // matrix to array
        double [] array = new double[rows*columns];
        for (int i = 0, k = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                array[k++] = matrix[i][j];

        // array sorting
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] < array[j + 1]) {
                    double temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    public boolean equals(DoubleMatrix dm) {
        if ((rows != dm.rows()) || (columns != dm.columns()))
            return false;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (matrix[i][j] != dm.getElementAt(i, j)) {
                    System.out.println("### " + matrix[i][j] + " not equal " + dm.getElementAt(i,j));
                    return false;
                }
        return true;
    }

    @Override
    public String toString() {
        String matrixString = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrixString = matrixString.concat(String.format("%.2f", matrix[i][j]));
                if (j != columns - 1)
                    matrixString = matrixString.concat("\t");
            }
            if (i != rows - 1)
                matrixString = matrixString.concat("\n");
        }
        return matrixString;
    }
}


class MatrixReader {

    public static DoubleMatrix read(InputStream input) {
        Scanner inputStream = new Scanner(input);
        int rows = inputStream.nextInt();
        int cols = inputStream.nextInt();
        double [] array = new double[rows*cols];
        for (int i = 0; i < rows*cols; i++)
            array[i] = inputStream.nextDouble();
        return new DoubleMatrix(array, rows, cols);
    }
}
