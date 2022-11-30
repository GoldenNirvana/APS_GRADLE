package application;

import application.components.*;
import lombok.Getter;
import statistic.StatisticController;
import utils.Event;
import utils.Type;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Controller {
  public static StatisticController statistics;
  private final int requiredOrdersCount = StatisticController.countOfRequiredOrders;
  private double currentTime;
  private final Buffer buffer;
  private final ReceptionDesk receptionDesk;
  private final ExtractionManager extractionManager;
  private final Patient[] patients;
  private final Device[] devices;
  private ArrayList<Event> events;

  private void initEvents() {
    events = new ArrayList<>();
    for (int i = 0; i < statistics.getClientsCount(); i++) {
      events.add(new Event(Type.Generated, patients[i].getNextRequestGenerationTime(), i));
    }
    if (events.size() > 0) {
      events.sort(Event::compare);
    }
  }

  public Controller() {
    currentTime = 0;
    statistics = StatisticController.getInstance();
    buffer = new Buffer(statistics.getBufferSize());
    devices = new Device[statistics.getDevicesCount()];
    for (int i = 0; i < statistics.getDevicesCount(); i++) {
      devices[i] = new Device(i, statistics);
    }
    extractionManager = new ExtractionManager(buffer, devices);
    patients = new Patient[statistics.getClientsCount()];
    for (int i = 0; i < statistics.getClientsCount(); i++) {
      patients[i] = new Patient(i);
    }
    receptionDesk = new ReceptionDesk(buffer, patients, statistics);
    initEvents();
  }

  public void autoMode() {
    while (!events.isEmpty()) {
      stepMode();
    }
  }

  public Event stepMode() {
    if (events.isEmpty())
      return null;
    final Event currentEvent = events.remove(0);
    final Type currentType = currentEvent.eventType;
    final int currentId = currentEvent.id;
    currentTime = currentEvent.eventTime;
    if (currentType == Type.Generated) {
      if (statistics.getTotalOrdersCount() < requiredOrdersCount) {
        List<Event> newEvents = receptionDesk.sendRequestToBuffer(currentId, currentTime);
        if (!events.isEmpty() || currentId == 0) {
          events.addAll(newEvents);
          events.sort(Event::compare);
        }
      }
    } else if (currentType == Type.Unbuffered) {
      final Event newEvent = extractionManager.sendOrderToDevice(currentTime);
      if (newEvent != null) {
        events.add(newEvent);
        events.sort(Event::compare);
      }
    } else if (currentType == Type.Completed) {
      devices[currentId].release(currentTime);
      events.add(new Event(Type.Unbuffered, currentTime));
      events.sort(Event::compare);
    }
    return currentEvent;
  }

  public StatisticController getStatistics() {
    return statistics;
  }
}

