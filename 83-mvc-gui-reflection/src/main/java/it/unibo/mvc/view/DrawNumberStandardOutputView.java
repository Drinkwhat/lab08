package it.unibo.mvc.view;

import java.util.Objects;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * Implementazione concreta dell'interfaccia {@link DrawNumberView}.
 * Questa classe gestisce la visualizzazione dei risultati del gioco dei numeri
 * sulla console standard.
 */
public final class DrawNumberStandardOutputView implements DrawNumberView {

    private DrawNumberController controller;

    /**
     * Constructs a new DrawNumberStandardOutputView.
     */
    public DrawNumberStandardOutputView() {
        /*
         * mi serve per non avere errore di compilazione per UncommentedEmptyConstructor: Document empty constructor
         */
    }

    @Override
    public void setController(final DrawNumberController observer) {
        this.controller = Objects.requireNonNull(observer, "Observer cannot be null");
    }

    @Override
    public void start() {
        System.out.println("Benvenuto nel gioco dei numeri"); // NOPMD This println was required by th exercise
    }

    @Override
    public void result(final DrawResult res) {
        System.out.println(res.getDescription()); // NOPMD This println was required by th exercise
        if (controller != null && (res == DrawResult.YOU_LOST || res == DrawResult.YOU_WON)) {
            controller.resetGame();
        }
    }
}
