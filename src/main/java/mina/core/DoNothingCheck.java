package mina.core;

public class DoNothingCheck implements MinaCheck {
    private static final MinaCheck INSTANCE = new DoNothingCheck();

    private DoNothingCheck() {
    }

    public static MinaCheck getInstance() {
        return INSTANCE;
    }

    @Override
    public void verify(int index, Object[] arguments, Throwable throwable) {
        // Do nothing
    }
}
