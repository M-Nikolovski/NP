import java.util.Scanner;
import java.util.stream.IntStream;

public class RomanConverterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        IntStream.range(0, n)
                .forEach(x -> System.out.println(RomanConverter.toRoman(scanner.nextInt())));
        scanner.close();
    }
}


class RomanConverter {
    /**
     * Roman to decimal converter
     *
     * @param n number in decimal format
     * @return string representation of the number in Roman numeral
     */
    public static String toRoman(int n) {
        String convertedNumber = "";
        int M, CM, D, CD, C, XC, L, XL, X, IX, V, IV, I;

        M = n / 1000; n %= 1000;
        CM = n / 900; n %= 900;
        D = n / 500; n %= 500;
        CD = n / 400; n %= 400;
        C = n / 100; n %= 100;
        XC = n / 90; n %= 90;
        L = n / 50; n %= 50;
        XL = n / 40; n %= 40;
        X = n / 10; n %= 10;
        IX = n / 9; n %= 9;
        V = n / 5; n %= 5;
        IV = n / 4; n %= 4;
        I = n;

        for ( ; M > 0; M--)
            convertedNumber = convertedNumber.concat("M");
        for ( ; CM > 0; CM--)
            convertedNumber = convertedNumber.concat("CM");
        for ( ; D > 0; D--)
            convertedNumber = convertedNumber.concat("D");
        for ( ; CD > 0; CD--)
            convertedNumber = convertedNumber.concat("CD");
        for ( ; C > 0; C--)
            convertedNumber = convertedNumber.concat("C");
        for ( ; XC > 0; XC--)
            convertedNumber = convertedNumber.concat("XC");
        for ( ; L > 0; L--)
            convertedNumber = convertedNumber.concat("L");
        for ( ; XL > 0; XL--)
            convertedNumber = convertedNumber.concat("XL");
        for ( ; X > 0; X--)
            convertedNumber = convertedNumber.concat("X");
        for ( ; IX > 0; IX--)
            convertedNumber = convertedNumber.concat("IX");
        for ( ; V > 0; V--)
            convertedNumber = convertedNumber.concat("V");
        for ( ; IV > 0; IV--)
            convertedNumber = convertedNumber.concat("IV");
        for ( ; I > 0; I--)
            convertedNumber = convertedNumber.concat("I");

        return convertedNumber;
    }

}
