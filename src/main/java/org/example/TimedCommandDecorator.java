package org.example;

import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Component
public class TimedCommandDecorator implements Command {
    private Command wrapped;

    @Override
    public void execute(Object... args) {
        if (wrapped == null) {
            return;
        }

        if (args.length == 0) {
            return;
        }

        long start = System.currentTimeMillis();
        wrapped.execute(args);
        long end = System.currentTimeMillis();

        System.out.println("Execution time: " + (end - start) + " ms");
    }
}
