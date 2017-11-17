import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Trial{

  private static BufferedReader input = null;
  private static double[] inputs;
  public static void main(String[] args){
    InputStream isInput = System.in;
    InputStream isTarget = System.in;
    if (args.length == 2) {
      try {
        isInput = new FileInputStream(args[0]);
        System.out.println("Reading input from file '" + args[0] + "'");
      }
      catch (FileNotFoundException fnfe) {
        System.out.println("Unable to open file '" + args[0] + "': " +
                             fnfe);
      }
      try {
        isTarget = new FileInputStream(args[1]);
        System.out.println("Reading target from file '" + args[1] + "'");
      }
      catch (FileNotFoundException fnfe) {
        System.out.println("Unable to open file '" + args[1] + "': " +
                             fnfe);
      }
      input = new BufferedReader(new InputStreamReader(isInput));

      try{
        int counter = 0;
        while(input.readLine() != null){
          counter++;
        }
        inputs = new double[counter];
      }catch(IOException ioe){
        System.err.println("Unable to read file");
      }
    }
  }
  // public void run(double[] inputs, double target){
  //   EvolutionSimulation sim = new EvolutionSimulation(inputs, target);
  // }
}
