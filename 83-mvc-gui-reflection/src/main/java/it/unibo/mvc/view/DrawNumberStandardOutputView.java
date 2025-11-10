package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

public final class DrawNumberStandardOutputView implements DrawNumberView {
    
    private DrawNumberController controller;

    @Override
    public void setController(DrawNumberController observer) {
        this.controller = observer;
    }

    @Override
    public void start() {
        System.out.println("Benvenuto nel gioco dei numeri");
    }

    @Override
    public void result(DrawResult res) {
        // TODO capire perché non va il YOU_WON e YOU_LOST
        switch (res) {
            case YOURS_HIGH:
            case YOURS_LOW: {
                System.out.println(res.getDescription());
                return;
            }
            case YOU_WON: {
                System.out.println(res.getDescription());
                return;
            }
            case YOU_LOST: {
                System.out.println(res.getDescription());
                return;
            }
        }
        controller.resetGame();
    }
}