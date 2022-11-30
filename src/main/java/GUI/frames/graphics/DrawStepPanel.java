package GUI.frames.graphics;

import GUI.actions.NextStepAction;
import application.Controller;
import application.components.Buffer;
import application.components.Device;
import application.components.Request;
import org.jetbrains.annotations.NotNull;
import statistic.StatisticController;
import utils.Event;
import utils.Type;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static application.Controller.statistics;


public class DrawStepPanel extends JPanel {
  private final Controller controller;
  private int prevSize = -1;

  public DrawStepPanel(Controller controller) {
    this.controller = controller;
  }

  public void paint(Graphics g) {
    this.setBackground(Color.WHITE);
    for (int i = 10; i <= 60; i += 50) {
      g.setColor(Color.lightGray);
      g.drawRect(20, i, 100, 30);
      g.fillRect(20, i, 100, 30);
      g.setColor(Color.black);
      g.drawLine(120, i + 15, 150, i + 15);
    }
    for (int i = 115; i <= 165; i += 25) {
      g.drawOval(65, i, 1, 1);
    }
    g.drawRect(20, 190, 100, 30);
    g.setColor(Color.lightGray);
    g.fillRect(20, 190, 100, 30);
    g.setColor(Color.black);
    g.drawString("И0", 30, 30);
    g.drawString("И1", 30, 80);
    g.drawString("И" + (StatisticController.countOfClients - 1), 30, 210);

    int dc = StatisticController.countOfRestaurantDevices;
    for (int i = 0; i < dc; i++) {
      g.drawRect(600, 10 + (i) * 50, 100, 30);
      g.drawLine(570, 25 + i * 50, 600, 25 + i * 50);
      g.drawLine(700, 25 + i * 50, 730, 25 + i * 50);
      g.drawChars(new char[]{'Д', String.valueOf(i).charAt(0)}, 0, 2, 610, 30 + (i) * 50);

    }
    g.drawLine(570, 25 + 50 * (dc - 1), 570, 25);
    g.drawLine(730, 25 + 50 * (dc - 1), 730, 25);
    g.drawLine(730, 25, 800, 25);

    int ptr = controller.getBuffer().getCurrentIndex();
    int bs = StatisticController.sizeOfBuffer;
    Color color = g.getColor();
    for (int i = 0; i < bs; i++) {
      g.setColor(color);
      g.drawRect(310, 10 + (i + 1) * 50, 100, 30);
      if (i == ptr) {
        g.setColor(Color.CYAN);
        g.fillRect(310, 10 + (i + 1) * 50, 100, 30);
      }
      g.setColor(color);
      g.drawLine(360, 40 + i * 50, 360, 40 + i * 50 + 20);
      g.drawChars(new char[]{'Б', String.valueOf(i).charAt(0)}, 0, 2, 320, 25 + (i + 1) * 50);

    }

    g.drawLine(120, 25, 180, 25);
    g.drawLine(120, 195, 150, 195);
    g.drawLine(150, 195, 150, 25);
    g.drawLine(280, 25, 310, 25);
    g.drawLine(410, 25, 440, 25);
    g.drawLine(540, 25, 570, 25);
    g.drawLine(230, 40, 230, 190);

    g.setColor(Color.lightGray);
    g.drawRect(180, 10, 100, 30);
    g.fillRect(180, 10, 100, 30);
    g.drawRect(310, 10, 100, 30);
    g.fillRect(310, 10, 100, 30);
    g.drawRect(440, 10, 100, 30);
    g.fillRect(440, 10, 100, 30);

    g.setColor(Color.black);
    g.drawRect(180, 190, 100, 30);
    g.drawString("ДП", 190, 30);
    g.drawString("БП", 320, 30);
    g.drawString("ДВ", 450, 30);

    g.drawString("Canceled", 190, 210);
    g.drawString("Exit", 750, 20);
    String time = String.valueOf(controller.getCurrentTime());
    time = time.substring(0, time.indexOf('.') + 2);
    g.drawString("Time: " + time, 750, 50);
  }

  public void print(@NotNull final Graphics g) {
    g.clearRect(0, 0, 1920, 1080);
    this.paint(g);
    List<Device> d = Arrays.stream(controller.getDevices()).toList();
    Buffer b = controller.getBuffer();

    String current;
    Event event = NextStepAction.event;
    if (event == null)
    {
      return;
    }

    if (event.eventType == Type.Generated) {
      current = event.id + "-" + (statistics.getClientsStats()
        .get(event.id)
        .getGeneratedTasksCount());
      if (b.isFull() && !(prevSize + 1 == b.getSize())) {
        g.setColor(Color.RED);
        g.fillRect(181, 191, 99, 29);
        g.setColor(Color.black);
      }
      g.drawString(current, 220, 30);
    }
    if (event.eventType == Type.Completed) {
      g.setColor(Color.green);
      g.fillRect(601, 11 + (event.id) * 50, 99, 29);
      g.setColor(Color.black);
      g.drawString("free", 650, 30 + (event.id) * 50);
    }

    for (int i = 0; i < StatisticController.countOfRestaurantDevices; i++) {
      Device temp = d.get(i);
      if (!temp.isFree()) {
        g.drawString(temp.getRequest().requestId(), 650, 30 + (i) * 50);
      }
    }

    for (int i = 0; i < StatisticController.sizeOfBuffer; i++) {
      Request[] q = b.getRequests();
      if (!(q[i] == null)) {
        String id = q[i].requestId();
        g.drawString(id, 360, 30 + (i + 1) * 50);
      }
    }

    prevSize = b.getSize();
  }
}
