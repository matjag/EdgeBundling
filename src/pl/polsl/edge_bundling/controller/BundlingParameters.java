package pl.polsl.edge_bundling.controller;

public class BundlingParameters {

    private double compatibilityThreshold;

    private double initialStep;

    private double stepChangeRate;

    private double numberOfIterations;

    private double iterationChangeRate;

    private double numberOfCycles;

    private double globalSpringConstant;

    public BundlingParameters(double compatibilityThreshold, double initialStep, double stepChangeRate, double numberOfIterations, double iterationChangeRate, double numberOfCycles, double globalSpringConstant) {
        this.compatibilityThreshold = compatibilityThreshold;
        this.initialStep = initialStep;
        this.stepChangeRate = stepChangeRate;
        this.numberOfIterations = numberOfIterations;
        this.iterationChangeRate = iterationChangeRate;
        this.numberOfCycles = numberOfCycles;
        this.globalSpringConstant = globalSpringConstant;
    }

    public double getCompatibilityThreshold() {
        return compatibilityThreshold;
    }

    public void setCompatibilityThreshold(double compatibilityThreshold) {
        this.compatibilityThreshold = compatibilityThreshold;
    }

    public double getInitialStep() {
        return initialStep;
    }

    public void setInitialStep(double initialStep) {
        this.initialStep = initialStep;
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

    public void setNumberOfIterations(double numberOfIterations) {
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

    public void setNumberOfCycles(double numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }

    public double getGlobalSpringConstant() {
        return globalSpringConstant;
    }

    public void setGlobalSpringConstant(double globalSpringConstant) {
        this.globalSpringConstant = globalSpringConstant;
    }
}
