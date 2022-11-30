package statistic;


import lombok.Getter;

@Getter
public class ClientStatistic {
  private int generatedTasksCount;
  private int canceledTasksCount;
  private double totalTime;
  private double squaredTotalTime;
  private double totalBufferedTime;
  private double squaredTotalBufferedTime;
  private double timeInWork;
  private double squaredTimeInWork;

  public ClientStatistic() {
    generatedTasksCount = 0;
    canceledTasksCount = 0;
    totalTime = 0;
    squaredTotalTime = 0;
    totalBufferedTime = 0;
    squaredTotalBufferedTime = 0;
  }
  public void incrementGeneratedTask() {
    generatedTasksCount++;
  }

  public void incrementCanceledTask() {
    canceledTasksCount++;
  }


  public void addTime(double time) {
    totalTime += time;
    squaredTotalTime += time * time;
  }

  public void addBufferedTime(double time) {
    totalBufferedTime += time;
    squaredTotalBufferedTime += time * time;
  }

  public void addTimeInWork(double time) {
    timeInWork += time;
    squaredTimeInWork += time * time;
  }

  public double getBufferDispersion() {
    return (squaredTotalBufferedTime / generatedTasksCount - Math.pow(totalBufferedTime / generatedTasksCount, 2));
  }

  public double getDeviceDispersion() {
    return (squaredTimeInWork / generatedTasksCount - Math.pow(timeInWork / generatedTasksCount, 2));
  }
}