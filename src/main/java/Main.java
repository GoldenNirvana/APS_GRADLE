import GUI.UI;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws InterruptedException, IOException {
    UI ui = new UI();
    ui.execute("5", "10", "0.2", "0.3", "0.8");
  }
}



