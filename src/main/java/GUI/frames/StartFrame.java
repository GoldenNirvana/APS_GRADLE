package GUI.frames;

import GUI.actions.SetDataAction;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Getter
public class StartFrame extends CustomFrame {
  private final ArrayList<JTextField> startDataFields = new ArrayList<>(7);

  public void start(String devices, String bufferSize, String min, String max, String l) {
    JPanel panel = new JPanel();
    for (int i = 0; i < 7; i++) {
      startDataFields.add(null);
    }
    Box box = Box.createVerticalBox();

    currentFrame = createFrame("Parameters");
    currentFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    currentFrame.add(panel);

    JLabel label1 = new JLabel("Devices count");
    label1.setPreferredSize(new Dimension(100, 40));

    JLabel label2 = new JLabel("Clients count");
    label2.setPreferredSize(new Dimension(100, 40));

    JLabel label3 = new JLabel("Tasks count");
    label3.setPreferredSize(new Dimension(100, 40));

    JLabel label4 = new JLabel("Buffer size");
    label4.setPreferredSize(new Dimension(100, 40));

    JLabel label5 = new JLabel("Min");
    label5.setPreferredSize(new Dimension(100, 40));

    JLabel label6 = new JLabel("Max");
    label6.setPreferredSize(new Dimension(100, 40));

    JLabel label7 = new JLabel("Lambda");
    label7.setPreferredSize(new Dimension(100, 40));

    Box string1 = Box.createHorizontalBox();
    string1.add(label1);
    JTextField text1 = new JTextField(devices, 20);
    string1.add(text1);

    Box string2 = Box.createHorizontalBox();
    string2.add(label2);
    JTextField text2 = new JTextField("25", 20);
    string2.add(text2);

    Box string3 = Box.createHorizontalBox();
    string3.add(label3);
    JTextField text3 = new JTextField("1000", 20);
    string3.add(text3);

    Box string4 = Box.createHorizontalBox();
    string4.add(label4);
    JTextField text4 = new JTextField(bufferSize, 20);
    string4.add(text4);

    Box string5 = Box.createHorizontalBox();
    string5.add(label5);
    JTextField text5 = new JTextField(min, 20);
    string5.add(text5);

    Box string6 = Box.createHorizontalBox();
    string6.add(label6);
    JTextField text6 = new JTextField(max, 20);
    string6.add(text6);

    Box string7 = Box.createHorizontalBox();
    string7.add(label7);
    JTextField text7 = new JTextField(l, 20);
    string7.add(text7);

    startDataFields.set(0, text1);
    startDataFields.set(1, text2);
    startDataFields.set(2, text3);
    startDataFields.set(3, text4);
    startDataFields.set(4, text5);
    startDataFields.set(5, text6);
    startDataFields.set(6, text7);


    JButton button = new JButton(new SetDataAction(currentFrame, startDataFields));
    button.setText("start");
    box.add(string1);
    box.add(string2);
    box.add(string3);
    box.add(string4);
    box.add(string5);
    box.add(string6);
    box.add(string7);
    box.add(Box.createVerticalStrut(25));
    box.add(button);
    panel.add(box);
    currentFrame.revalidate();
//    button.doClick();
  }
}
