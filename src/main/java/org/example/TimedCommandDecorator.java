package org.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimedCommandDecorator implements Command {
    private final Command wrapped;

    @Autowired
    public TimedCommandDecorator(Command wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void execute() {
        long start = System.currentTimeMillis();
        wrapped.execute();
        long end = System.currentTimeMillis();
        System.out.println("Execution time: " + (end - start) + " ms");
    }
}


