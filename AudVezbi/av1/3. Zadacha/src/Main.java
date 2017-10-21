public class Main {

    public static void formatNumbers(int howMany, int lineLength) {
        int counter = 0;
        for (Integer i = 1; i <= howMany; i++) {
            counter += 2 + i.toString().length();
//            System.out.print(counter);
            if (counter <= 60) {
                System.out.printf("[%d]", i);
            }
            else {
                System.out.printf("%n[%d]", i);
                counter = 2 + i.toString().length();
            }
        }
    }

    public static void main(String[] args) {
        formatNumbers(250, 60);
    }
}
