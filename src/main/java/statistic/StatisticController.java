package statistic;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class StatisticController {
  public static int countOfRestaurantDevices;
  public static int countOfClients;
  public static int countOfRequiredOrders;
  public static int sizeOfBuffer;
  public static double minimum;
  public static double maximum;
  public static double lambda;
  public static Double Total;
  public static Double realTotalTime;

  private int bufferSize;
  private int devicesCount;
  private int clientsCount;
  private int totalOrdersCount;
  private int completedOrdersCount;
  private double totalTime;
  private ArrayList<ClientStatistic> clientsStats;
  private Double[] deviceTime;

  private StatisticController(double totalTime, int devicesCount, int clientsCount) {
    this.devicesCount = devicesCount;
    this.clientsCount = clientsCount;
    this.totalTime = totalTime;
    this.bufferSize = sizeOfBuffer;
    this.totalOrdersCount = 0;
    this.completedOrdersCount = 0;
    Total = 0.0;
    realTotalTime = 0.0;
    this.clientsStats = new ArrayList<>(this.clientsCount);
    for (int i = 0; i < this.clientsCount; i++) {
      clientsStats.add(i, new ClientStatistic());
    }
    this.deviceTime = new Double[this.devicesCount];
    for (int i = 0; i < this.devicesCount; i++) {
      deviceTime[i] = 0.0;
    }

  }

  public static StatisticController getInstance() {
    return new StatisticController(0, countOfRestaurantDevices, countOfClients);
  }

  public void orderGenerated(int srcId) {
    clientsStats.get(srcId).incrementGeneratedTask();
    totalOrdersCount++;
  }

  public void taskCanceled(int srcId, double usedTime) {
    clientsStats.get(srcId).incrementCanceledTask();
    taskCompleted(srcId, usedTime, 0);
  }

  public void taskCompleted(int srcId, double usedTime, double processedTime) {
    clientsStats.get(srcId).addTime(usedTime);
    clientsStats.get(srcId).addBufferedTime(usedTime - processedTime);
    completedOrdersCount++;
  }
}
