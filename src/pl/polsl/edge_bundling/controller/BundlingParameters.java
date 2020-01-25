package pl.polsl.edge_bundling.controller;

class BundlingParameters {

    private double compatibilityThreshold;

    private double step;

    private double stepChangeRate;

    private int numberOfIterations;

    private double iterationChangeRate;

    private int numberOfCycles;

    private double globalSpringConstant;

    private double shortEdgeThreshold;

    double getShortEdgeThreshold() {
        return shortEdgeThreshold;
    }

    BundlingParameters(double compatibilityThreshold, double step, double stepChangeRate, int numberOfIterations,
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

}
