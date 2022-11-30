package application.components;

import org.jetbrains.annotations.NotNull;
import statistic.StatisticController;
import utils.Event;
import utils.Type;

import java.util.List;

public class ReceptionDesk {
  @NotNull
  final private Buffer buffer;
  @NotNull
  Patient[] patients;
  @NotNull
  private final StatisticController statistics;

  public ReceptionDesk(@NotNull final Buffer buffer,
                       @NotNull final Patient[] patients,
                       @NotNull final StatisticController statistics) {
    this.buffer = buffer;
    this.patients = patients;
    this.statistics = statistics;
  }

  public List<Event> sendRequestToBuffer(final int currentId, final double currentTime) {
    buffer.add(patients[currentId].generateRequest(currentTime));
    List<Event> events = List.of(
      new Event(Type.Generated, currentTime + patients[currentId].getNextRequestGenerationTime(), currentId),
      new Event(Type.Unbuffered, currentTime));
    statistics.orderGenerated(currentId);
    return events;
  }
}
