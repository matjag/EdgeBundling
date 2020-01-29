package pl.polsl.edge_bundling.controller;

public class BundlingParameters {

    private double compatibilityThreshold;

    private double shortEdgeThreshold;

    private double step;

    private double stepChangeRate;

    private int numberOfIterations;

    private double iterationChangeRate;

    private int numberOfCycles;

    private double globalSpringConstant;

    double getShortEdgeThreshold() {
        return shortEdgeThreshold;
    }

    BundlingParameters(double compatibilityThreshold, double shortEdgeThreshold, double step, double stepChangeRate, int numberOfIterations,
                       double iterationChangeRate, int numberOfCycles, double globalSpringConstant) {
        this.compatibilityThreshold = compatibilityThreshold;
        this.shortEdgeThreshold = shortEdgeThreshold;
        this.step = step;
        this.stepChangeRate = stepChangeRate;
        this.numberOfIterations = numberOfIterations;
        this.iterationChangeRate = iterationChangeRate;
        this.numberOfCycles = numberOfCycles;
        this.globalSpringConstant = globalSpringConstant;
    }

    public BundlingParameters(BundlingParameters template) {
        this.compatibilityThreshold = template.compatibilityThreshold;
        this.shortEdgeThreshold = template.shortEdgeThreshold;
        this.step = template.step;
        this.stepChangeRate = template.stepChangeRate;
        this.numberOfIterations = template.numberOfIterations;
        this.iterationChangeRate = template.iterationChangeRate;
        this.numberOfCycles = template.numberOfCycles;
        this.globalSpringConstant = template.globalSpringConstant;
    }

    void updateStep() {
        step *= stepChangeRate;
    }

    void updateNumberOfIterations() {
        numberOfIterations *= iterationChangeRate;
    }

    double getCompatibilityThreshold() {
        return compatibilityThreshold;
    }

    double getStep() {
        return step;
    }

    double getNumberOfIterations() {
        return numberOfIterations;
    }

    double getNumberOfCycles() {
        return numberOfCycles;
    }

    double getGlobalSpringConstant() {
        return globalSpringConstant;
    }

    @Override
    public String toString() {
        return
                "tc" + compatibilityThreshold +
                        "ts" + shortEdgeThreshold +
                        "s" + step +
                        "sr" + stepChangeRate +
                        "i" + numberOfIterations +
                        "ir" + iterationChangeRate +
                        "c" + numberOfCycles +
                        "k" + globalSpringConstant;
    }
}
