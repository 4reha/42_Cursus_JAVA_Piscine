package fr.school42.spring.printer;

import java.time.LocalDateTime;

import fr.school42.spring.renderer.Renderer;

public class PrinterWithDateTimeImpl implements Printer {
  private Renderer renderer;

  public PrinterWithDateTimeImpl(Renderer renderer) {
    this.renderer = renderer;
  }

  @Override
  public void print(String message) {
    renderer.render(LocalDateTime.now() + " " + message);
  }
}