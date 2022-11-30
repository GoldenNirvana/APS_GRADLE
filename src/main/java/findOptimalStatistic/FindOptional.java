package findOptimalStatistic;

import statistic.StatisticController;

import java.io.IOException;

public class FindOptional {

  private Double maxFail;
  private Double minWork;

  public FindOptional(StatisticController statisticController) throws IOException {
    this.maxFail = 0.0;
    this.minWork = 1.0;
    findOptionalSettings(statisticController);
  }

  private double getMaxDeviceTime(Double[] doubles) {
    Double d = 0.0;
    for (Double aDouble : doubles) {
      if (aDouble > d) {
        d = aDouble;
      }
    }
    return d;
  }

  public void findOptionalSettings(StatisticController statisticController) throws IOException {
    for (int i = 0; i < statisticController.getClientsCount() || i < statisticController.getDevicesCount(); i++) {
      if (i < statisticController.getClientsCount() && maxFail < (double) statisticController.getClientsStats().get(i).getCanceledTasksCount() / statisticController.getClientsStats().get(i).getGeneratedTasksCount()) {
        maxFail = (double) statisticController.getClientsStats().get(i).getCanceledTasksCount() / statisticController.getClientsStats().get(i).getGeneratedTasksCount();
      }
      if (i < statisticController.getDevicesCount() && minWork > statisticController.getDeviceTime()[i] / getMaxDeviceTime(statisticController.getDeviceTime())) {
        minWork = statisticController.getDeviceTime()[i] / getMaxDeviceTime(statisticController.getDeviceTime());
      }
    }
    Results.maxFail = this.maxFail;
    Results.minWork = this.minWork;
  }
}
