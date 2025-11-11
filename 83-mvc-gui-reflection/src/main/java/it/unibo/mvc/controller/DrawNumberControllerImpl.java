package it.unibo.mvc.controller;

import it.unibo.mvc.api.DrawNumber;
import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * This class implements the game controller. It orchestrates the game, exposes methods to its observers
 * (the boundaries), and sends results to them.
 */
public final class DrawNumberControllerImpl implements DrawNumberController {

    private final DrawNumber model;
    private final Set<DrawNumberView> views = new HashSet<>();

    /**
     * Builds a new game controller provided a game model.
     *
     * @param model the implementation of the game model
     */
    public DrawNumberControllerImpl(final DrawNumber model) {
        this.model = model;
    }

    @Override
    public void addView(final DrawNumberView view) {
        Objects.requireNonNull(view, "Cannot set a null view");
        this.views.add(view);
        view.setController(this);
        view.start();
    }

    @Override
    public void newAttempt(final int n) {
        final var attempt = model.attempt(n);
        if (views.isEmpty()) {
            throw new IllegalStateException("There is no view attached!");
        }
        for (final DrawNumberView view : views) {
            view.result(attempt);
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @SuppressFBWarnings(
        value = "",
        justification = "This System.exit(0) is required for exercise"
    )
    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

}
