package application.components;


import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import statistic.StatisticController;

import java.util.Random;

@Getter
@Setter
public class Device {
  private int deviceId;
  private Request request;
  private double requestStartTime;
  private final StatisticController statistics;

  public Device(final int deviceId,
                          @NotNull final StatisticController statistics) {
    this.deviceId = deviceId;
    this.requestStartTime = 0;
    this.request = null;
    this.statistics = statistics;
  }

  public double getReleaseTime() {
    Random rand = new Random();
    double r = StatisticController.minimum + rand.nextDouble() * (StatisticController.maximum - StatisticController.minimum);
    statistics.getClientsStats().get(request.patientId()).addTimeInWork(r);
    return r;
  }

  public boolean isFree() {
    return (request == null);
  }

  public void release(final double currentTime) {
    if (request != null) {
      statistics.taskCompleted(request.patientId(),
        currentTime - requestStartTime,
        currentTime - requestStartTime);
      request = null;
      statistics.getDeviceTime()[deviceId] += currentTime - requestStartTime;
      StatisticController.Total += currentTime - requestStartTime;
      requestStartTime = currentTime;
    }
  }
}
