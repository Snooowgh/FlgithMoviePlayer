package app;

public class Main {
    public static void main(String[] args) {
        // It is better to keep the main() method clean and
        // handle the booting logic in the controller object
        Controller controller = new Controller();
        controller.run(args);
    }
}
