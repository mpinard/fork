package mpinard.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.random.RandomGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sample {

    public static void main(String[] args) throws InterruptedException {
        log.info("Starting sample");
        Sample sample = new Sample();
        sample.execute();
        log.info("Ending sample");
    }

    public void execute() throws InterruptedException {
        List<StructuredTaskScope.Subtask<Results>> tasks = new ArrayList<>();
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            for (int i = 0; i < 5000; i++) {
                final int each = i;
                tasks.add(scope.fork(() -> runTask(each)));
            }
            scope.join().throwIfFailed();

            tasks.forEach(task -> log.info("Result={}", task.get()));

        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private Results runTask(int i) throws InterruptedException {
        long sleepLength = RandomGenerator.getDefault().nextLong(5000);
        log.info("Starting task #{}", i);
        Thread.sleep(sleepLength);
        log.info("Ending task #{}", i);
        return new Results(i, sleepLength);
    }

    private record Results(int index, long sleepLength) {
    }
}
