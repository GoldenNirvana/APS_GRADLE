package application.components;

import lombok.Getter;

import static statistic.StatisticController.lambda;

@Getter
public class Patient {
  final private int patientId;
  private int requestNumber;

  public Patient(final int patientId) {
    this.patientId = patientId;
    requestNumber = 0;
  }

  public Request generateRequest(final double currentTime) {
    requestNumber++;
    return new Request(patientId, String.valueOf(patientId) + '-' + requestNumber, currentTime);
  }

  public double getNextRequestGenerationTime() {
    return (-1.0 / lambda) * Math.log(Math.random());
  }
}
