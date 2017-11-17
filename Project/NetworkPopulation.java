import java.time.Instant;
import java.time.Duration;

public class NetworkPopulation{
  private Network[] population;
  private double[] inputs;
  private double target;
  private double[] fitness;
  NetworkPopulation(int batchSize, double[] _inputs, double _target){
    inputs = _inputs;
    target = _target;
    population = new Network[batchSize];
    fitness = new double[batchSize];
    //initialize population with random shape
    for(int i = 0; i < population.length; i++){
      int[] randomShape = new int[(int)Math.random()*10];
      for(int j = 0; j < randomShape.length; j++){
        if(j == 0){
          randomShape[j] = inputs.length;
        }
        randomShape[j] = (int)Math.random()*10;
      }
      population[i] = new Network(randomShape, inputs);
    }

    //initialize fitness array
    for(int i = 0; i < population.length; i++){
      Instant start = Instant.now();
      double err = target-population[i].getOutput();
      while(err < .001 && err > -.001){
        population[i].train(target);
      }
      Instant end = Instant.now();
      fitness[i] = population[i].getShape().length/Double.parseDouble(Duration.between(start,end).toString());
    }

  }
  NetworkPopulation(Network[] _population){
      population = _population;
  }
  public void sortNetworksAndCull(){
    Network[] newPop = new Network[population.length/2];
    for(int i = 0; i < population.length-1; i++){
      if(fitness[i] > fitness[i+1]){
        Network netTemp = population[i];
        double fitTemp = fitness[i];

        population[i] = population[i+1];
        population[i+1] = netTemp;

        fitness[i] = fitness[i+1];
        fitness[i+1] = fitTemp;

        i = 0;
      }
    }
    //after loop exits population must be sorted
    for(int i = 0; i < newPop.length; i++){
      newPop[i] =  population[i];//erase the weak networks
    }
    population = newPop;
    newPop = null;
  }

  public Network[] createNextGeneration(){
    Network[] children = new Network[population.length];
    for(int i = 0; i < population.length; i++){

      int[] dadShape = new int[population[i].getShape().length-2];
      int[] momShape = new int[population[i+1].getShape().length-2];

      //intitialize dadShape and momShape as the shape of this index
      for(int n = 0; n < dadShape.length; n++){
        dadShape[n] = population[i].getShape()[n+1];
      }
      for(int n = 0; n < momShape.length; n++){
        momShape[n] = population[i].getShape()[n+1];
      }
      int dadHalf = (int) Math.random()*dadShape.length;
      int[] dadChromeOne = new int[dadHalf];
      int[] dadChromeTwo = new int[dadShape.length-dadChromeOne.length];

      for(int n = 0; n < dadShape.length; n++){
        if(n < dadChromeOne.length){
          dadChromeOne[n] = dadShape[n];
        }else{
          dadChromeTwo[n] = dadShape[n];
        }
      }

      int momHalf = (int) Math.random()*momShape.length;
      int[] momChromeOne = new int[momHalf];
      int[] momChromeTwo = new int[momShape.length-momChromeOne.length];

      for(int n = 0; n < momShape.length; n++){
        if(n < momChromeOne.length){
          momChromeOne[n] = momShape[n];
        }else{
          momChromeTwo[n] = momShape[n];
        }
      }


      int[] childOneShape = new int[dadChromeOne.length+momChromeTwo.length+2];
      int[] childTwoShape = new int[momChromeOne.length+dadChromeTwo.length+2];


      for(int n = 0; n < childOneShape.length; n++){
        if(n == 0 || n == population[i].getShape().length-1){
          childOneShape[n] = population[i].getShape()[n];
        }else if(i < dadChromeOne.length){
          childOneShape[n] = dadChromeOne[n];
        }else{
          childOneShape[n] = momChromeTwo[n];
        }
      }
      for(int n = 0; n < childTwoShape.length; n++){
        if(n == 0 || n == population[i].getShape().length-1){
          childTwoShape[n] = population[i].getShape()[i];
        }else if(i < momChromeOne.length){
          childTwoShape[n] = momChromeOne[n];
        }else{
          childTwoShape[n] = dadChromeTwo[n];
        }
      }
      //i need to find a better way of making children

      children[i] = new Network(childOneShape, inputs);
      children[i+1] = new Network(childTwoShape, inputs);
    }


    return children;
  }
  public Network getBest(){
    return population[0];
  }
}
