public class Node{
  private double[] weights;
  private double[] biases;
  private double[] inputs;
  private double lr;

  public Node(double[] _inputs){
    inputs = _inputs;
    weights = new double[inputs.length];
    biases = new double[inputs.length];

    for(int i = 0; i < weights.length; i++){
      weights[i] = Math.random();
      biases[i] = Math.random();
    }

    lr = 0.001; //prolly to big, to be determined
  } 

  private double sum(double[] m, double[] x, double[] b){
  if(m.length != x.length && x.length != b.length){ return -1; };
  double y = 0;
  for(int i = 0; i < x.length; i++){
      y += m[i]*x[i]+b[i];
  }
  return y;
  }

  private double sig(double z){
    return 1/1+Math.exp(z);
  }

  public double getOutput(){
    return sig(sum(weights, inputs, biases));
  }

  public void update(double target){
    double err = target-getOutput();
    for (int i = 0; i < weights.length; i++){
      weights[i] += err * inputs[i] * lr;
      biases[i] += err * lr;
    }
  }

}
