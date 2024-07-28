package dev.jmina.core;

class DoNothingCheck implements Check {
    private static final Check INSTANCE = new DoNothingCheck();

    private DoNothingCheck() {
    }

    public static Check getInstance() {
        return INSTANCE;
    }

    @Override
    public void verify(int index, Object[] arguments, Throwable throwable) {
        // Do nothing
    }
}
