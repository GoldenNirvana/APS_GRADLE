package GUI.actions;

import GUI.frames.ResultFrame;
import application.Controller;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GetResultAction extends AbstractAction {
  @NotNull
  final private JFrame chooseModeFrame;
  final private Controller controller;

  public GetResultAction(@NotNull final JFrame frame, Controller controller) {
    this.chooseModeFrame = frame;
    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    controller.autoMode();
    chooseModeFrame.setVisible(false);
    ResultFrame modeFrame = new ResultFrame();
    modeFrame.setStatistics(controller.getStatistics());
    try {
      modeFrame.start();
    } catch (InterruptedException ex) {
      throw new RuntimeException(ex);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
