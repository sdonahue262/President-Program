public class EvolutionSimulation{
  double[] inputs;
  double target;
  NetworkPopulation batch;
  public EvolutionSimulation(double[] _inputs, double _target){
    inputs = _inputs;
    target = _target;
    batch = new NetworkPopulation(100, inputs, target);
  }
  public void evolve(int numGenerations){
    for(int i = 0; i < numGenerations; i++){
      batch.sortNetworksAndCull();
      NetworkPopulation newBatch = new NetworkPopulation(batch.createNextGeneration());
      batch = newBatch;
      newBatch = null; 
    }

  }
  public String toString(){
    return String.format(""+batch.getBest().getShape());
  }
}
