package GUI;


import GUI.frames.StartFrame;

public class UI {
  public void execute(String devices, String bufferSize, String min, String max, String l) {
    StartFrame startFrame = new StartFrame();
    startFrame.start(devices, bufferSize, min, max, l);
  }

}
