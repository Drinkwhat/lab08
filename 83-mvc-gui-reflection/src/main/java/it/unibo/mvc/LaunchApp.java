package it.unibo.mvc;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

import java.lang.reflect.InvocationTargetException;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) throws 
            ClassNotFoundException, 
            NoSuchMethodException, 
            InvocationTargetException, 
            InstantiationException, 
            IllegalAccessException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);

        final Class<?> swingViewClass = Class.forName("it.unibo.mvc.view.DrawNumberSwingView");
        final Class<?> stdOutputViewClass = Class.forName("it.unibo.mvc.view.DrawNumberStandardOutputView");
        
        final var swingViewConstructor = swingViewClass.getConstructor();
        final var stdOutputViewConstructor = stdOutputViewClass.getConstructor();
        
        for (int i = 0; i < 3; i++) {
            final DrawNumberView view = (DrawNumberView) swingViewConstructor.newInstance();
            app.addView(view);
        }
        
        for (int i = 0; i < 3; i++) {
            final DrawNumberView view = (DrawNumberView) stdOutputViewConstructor.newInstance();
            app.addView(view);
        }
    }
}
