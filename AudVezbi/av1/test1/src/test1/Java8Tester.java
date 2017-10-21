package test1;

public class Java8Tester {
    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    public int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}
