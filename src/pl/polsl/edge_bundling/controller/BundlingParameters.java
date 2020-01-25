package pl.polsl.edge_bundling.controller;

public class BundlingParameters {

    private double compatibilityThreshold;

    private double step;

    private double stepChangeRate;

    private int numberOfIterations;

    private double iterationChangeRate;

    private int numberOfCycles;

    private double globalSpringConstant;

    private double shortEdgeThreshold;

    public double getShortEdgeThreshold() {
        return shortEdgeThreshold;
    }

    public void setShortEdgeThreshold(int shortEdgeThreshold) {
        this.shortEdgeThreshold = shortEdgeThreshold;
    }

    public BundlingParameters(double compatibilityThreshold, double step, double stepChangeRate, int numberOfIterations,
                              double iterationChangeRate, int numberOfCycles, double globalSpringConstant, double shortEdgeThreshold) {
        this.compatibilityThreshold = compatibilityThreshold;
        this.step = step;
        this.stepChangeRate = stepChangeRate;
        this.numberOfIterations = numberOfIterations;
        this.iterationChangeRate = iterationChangeRate;
        this.numberOfCycles = numberOfCycles;
        this.globalSpringConstant = globalSpringConstant;
        this.shortEdgeThreshold = shortEdgeThreshold;
    }

    public void updateStep() {
        step *= stepChangeRate;
    }

    public void updateNumberOfIterations() {
        numberOfIterations *= iterationChangeRate;
    }

    public double getCompatibilityThreshold() {
        return compatibilityThreshold;
    }

    public void setCompatibilityThreshold(double compatibilityThreshold) {
        this.compatibilityThreshold = compatibilityThreshold;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public double getStepChangeRate() {
        return stepChangeRate;
    }

    public void setStepChangeRate(double stepChangeRate) {
        this.stepChangeRate = stepChangeRate;
    }

    public double getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public double getIterationChangeRate() {
        return iterationChangeRate;
    }

    public void setIterationChangeRate(double iterationChangeRate) {
        this.iterationChangeRate = iterationChangeRate;
    }

    public double getNumberOfCycles() {
        return numberOfCycles;
    }

    public void setNumberOfCycles(int numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }

    public double getGlobalSpringConstant() {
        return globalSpringConstant;
    }

    public void setGlobalSpringConstant(double globalSpringConstant) {
        this.globalSpringConstant = globalSpringConstant;
    }
}
