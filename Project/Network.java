public class Network{
  Node[][] net;
  double[][] outputs;
  double[] inputs;
  int[] shape;
  public Network(int[] _shape, double[] _inputs){
    net = new Node[shape.length][];
    shape = _shape;
    outputs = new double[shape.length][];
    inputs = _inputs;

    //initialize arrays at each index
    for (int i = 0; i < net.length; i++) {
      net[i] = new Node[shape[i]];
      outputs[i] = new double[shape[i]];
    }

    for (int i = 0; i < net[0].length; i++) {
      net[0][i] = new Node(inputs);
      outputs[0][i] = net[0][i].getOutput();
    }

    for (int i = 1; i < net.length; i++) {
      for (int n = 0; n < net[i].length; n++) {
        net[i][n] = new Node(outputs[i-1]);
      }
    }
  }

  public void train(double target) {
    for (int i = 0; i < net.length; i++) {
      for (int n = 0; n < net[i].length; n++) {
        net[i][n].update(target);
      }
    }
  }

  public void test(double[] newInputs) {
    for (int i = 0; i < net.length; i++) {
      for (int n = 0; n < net[i].length; n++) {
        net[i][n].getOutput();
      }
    }
  }

  public void setInputs(double[] newInputs) {
    inputs = newInputs;
  }
  public double getOutput(){
    return net[net.length-1][net[net.length-1].length-1].getOutput();
  }
  public int[] getShape(){
    return shape;
  }
}
