package application.components;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import utils.Event;
import utils.Type;

import java.util.Random;

@Getter
public class ExtractionManager {
  private final Device[] devices;
  private final Buffer buffer;
  private final Random generator;
  private final double maxRand;

  public ExtractionManager(@NotNull final Buffer buffer,
                           @NotNull final Device[] devices) {
    this.buffer = buffer;
    this.devices = devices;
    this.generator = new Random();
    this.maxRand = generator.nextDouble();
  }

  public void addRequestInDevice(int numDevice, Request request) {
    devices[numDevice].setRequest(request);
  }

  public Event sendOrderToDevice(final double currentTime) {
    int freeDeviceIndex = findFreeDeviceIndex();
    Device currentDevice = devices[freeDeviceIndex];
    if (currentDevice.isFree() && !buffer.isEmpty()) {
      final Request order = buffer.getRequest();
      addRequestInDevice(currentDevice.getDeviceId(), order);
      devices[freeDeviceIndex].setRequestStartTime(currentTime);
      return new Event(Type.Completed,
        currentTime + devices[freeDeviceIndex].getReleaseTime(),
        currentDevice.getDeviceId());
    }
    return null;
  }

  private int findFreeDeviceIndex() {
    int ind = 0;
    while (!devices[ind].isFree() && ind < devices.length - 1) {
      ind++;
    }
    return ind;
  }
}
