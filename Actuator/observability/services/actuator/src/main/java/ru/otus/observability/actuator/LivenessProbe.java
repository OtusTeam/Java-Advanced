package ru.otus.observability.actuator;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LivenessProbe {

    private final ApplicationEventPublisher publisher;

    public LivenessProbe(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Scheduled(cron = "*/45 * * * * *")
    public void simulateBroken() {
        System.out.println("broken");
        AvailabilityChangeEvent.publish(publisher, this, LivenessState.BROKEN);
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void simulateCorrect() {
        System.out.println("correct");
        AvailabilityChangeEvent.publish(publisher, this, LivenessState.CORRECT);
    }
}
