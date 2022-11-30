package GUI.actions;

import GUI.frames.StepModeFrame;
import application.Controller;
import org.jetbrains.annotations.NotNull;
import statistic.StatisticController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static application.Controller.statistics;

public class SetDataAction extends AbstractAction {
  @NotNull
  private ArrayList<JTextField> startDataFields;
  @NotNull
  private final JFrame startFrame;

  public SetDataAction(@NotNull final JFrame startFrame, @NotNull final ArrayList<JTextField> array) {
    this.startFrame = startFrame;
    this.startDataFields = array;
  }

  @Override
  public void actionPerformed(@NotNull final ActionEvent e) {
    StatisticController.countOfRestaurantDevices = Integer.parseInt(startDataFields.get(0).getText());
    StatisticController.countOfClients = Integer.parseInt(startDataFields.get(1).getText());
    StatisticController.countOfRequiredOrders = Integer.parseInt(startDataFields.get(2).getText());
    StatisticController.sizeOfBuffer = Integer.parseInt(startDataFields.get(3).getText());
    StatisticController.minimum = Double.parseDouble(startDataFields.get(4).getText());
    StatisticController.maximum = Double.parseDouble(startDataFields.get(5).getText());
    StatisticController.lambda = Double.parseDouble(startDataFields.get(6).getText());
    startFrame.setVisible(false);
    Controller controller = new Controller();
    StepModeFrame newFrame = new StepModeFrame(statistics, controller);
    newFrame.start();
  }
}

