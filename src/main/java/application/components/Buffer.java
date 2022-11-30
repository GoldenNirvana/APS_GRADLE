package application.components;

import application.Controller;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class Buffer {
  private final Request[] requests;
  private int currentIndex;
  private final int capacity;
  private int size;
  private final Boolean[] elementsToDelete;

  public Buffer(final int capacity) {
    this.capacity = capacity;
    this.size = 0;
    this.currentIndex = 0;
    requests = new Request[capacity];
    for (int i = 0; i < capacity; i++) {
      requests[i] = null;
    }
    elementsToDelete = new Boolean[capacity];
    for (int i = 0; i < capacity; i++) {
      elementsToDelete[i] = false;
    }
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean isFull() {
    return size == capacity;
  }

  public void add(@NotNull final Request request) {
    if (isFull()) {
      Request oldRequest = requests[currentIndex];
      requests[currentIndex] = request;
      elementsToDelete[currentIndex] = false;
      currentIndex = (currentIndex + 1) % capacity;
      Controller.statistics.taskCanceled(oldRequest.patientId(), request.startTime() - oldRequest.startTime()); // FIXME выбиваем из буфера
    } else {
      while (requests[currentIndex] != null) {
        currentIndex = (currentIndex + 1) % capacity;
      }
      requests[currentIndex] = request; // FIXME вставили в буфер
      currentIndex = (currentIndex + 1) % capacity;
      size++;
    }
  }

  public Request getRequest() {
    if (isEmpty()) {
      return null;
    }
    if (isElementInQueueToDelete()) {
      double minStartTime = Double.MAX_VALUE;
      int indexToDelete = Integer.MAX_VALUE;
      for (int i = 0; i < requests.length; i++) {
        if (elementsToDelete[i] && minStartTime > requests[i].startTime()) {
          minStartTime = requests[i].startTime();
          indexToDelete = i;
        }
      }
      size--;
      Request request = requests[indexToDelete];
      requests[indexToDelete] = null;
      elementsToDelete[indexToDelete] = false;
      return request;
    } else {
      int minElement = Integer.MAX_VALUE;
      for (int i = 0; i < requests.length; i++) {
        if (requests[i] != null && minElement > requests[i].patientId()) {
          minElement = requests[i].patientId();
        }
      }
      for (int i = 0; i < requests.length; i++) {
        if (requests[i] != null && requests[i].patientId() == minElement) {
          elementsToDelete[i] = true;
        }
      }
      if (isElementInQueueToDelete()) {
        double minStartTime = Double.MAX_VALUE;
        int indexToDelete = Integer.MAX_VALUE;
        for (int i = 0; i < requests.length; i++) {
          if (elementsToDelete[i] && minStartTime > requests[i].startTime()) {
            minStartTime = requests[i].startTime();
            indexToDelete = i;
          }
        }
        size--;
        Request request = requests[indexToDelete];
        requests[indexToDelete] = null;
        elementsToDelete[indexToDelete] = false;
        return request;
      }
    }
    return null;
  }

  boolean isElementInQueueToDelete() {
    for (Boolean aBoolean : elementsToDelete) {
      if (aBoolean) {
        return true;
      }
    }
    return false;
  }
}
