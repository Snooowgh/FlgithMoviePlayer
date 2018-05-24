package app.tasks;

import javafx.concurrent.Task;

public class OnStartTask extends Task<Integer> {
    @Override
    protected Integer call() throws Exception {
        Thread.sleep(1000);
        return 1;
    }
}
