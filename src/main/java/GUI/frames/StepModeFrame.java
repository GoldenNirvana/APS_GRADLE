package GUI.frames;

import GUI.actions.NextStepAction;
import GUI.actions.GetResultAction;
import GUI.frames.graphics.DrawStepPanel;
import application.Controller;
import org.jetbrains.annotations.NotNull;
import statistic.StatisticController;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StepModeFrame extends CustomFrame {
  final StatisticController statistics;
  final Controller controller;

  public StepModeFrame(@NotNull final StatisticController statistics, @NotNull final Controller controller) {
    this.statistics = statistics;
    this.controller = controller;
  }

  public void start() {
    currentFrame = createFrame("Step by step mode");
    currentFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    DrawStepPanel drawStepPanel = new DrawStepPanel(controller);
    drawStepPanel.setBounds(0, 0, 1000, 400);
    JButton button = new JButton(new NextStepAction(currentFrame, controller));
    button.setText("Next step");
    JButton button1 = new JButton(new GetResultAction(currentFrame, controller));
    button1.setText("Results");
    KeyListener kl = new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        button.doClick();
      }
    };
    button.addKeyListener(kl);
    button.setBounds(850, 25, 100, 30);
    button1.setBounds(850,  75, 100, 30);
    currentFrame.add(button);
    currentFrame.add(button1);
    currentFrame.add(drawStepPanel);
    currentFrame.revalidate();
    //button1.doClick();
  }
}
