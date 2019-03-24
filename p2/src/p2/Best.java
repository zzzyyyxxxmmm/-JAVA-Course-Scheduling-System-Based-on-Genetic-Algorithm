package p2;

/**
 * @author jikangwang
 */
public class Best {
    public int generations;
    public String str[];
    private double fitness;
    public String x[] = new String[1000];

    public Best() {
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public String[] getStr() {
        return str;
    }

    public void setStr(String[] str) {
        this.str = str;
    }

    public double getFitness() {
        return fitness;
    }

    public String[] getX() {
        return x;
    }

    public void setX(String[] x) {
        this.x = x;
    }
}
