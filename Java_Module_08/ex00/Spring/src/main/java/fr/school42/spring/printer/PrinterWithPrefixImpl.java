package fr.school42.spring.printer;

import fr.school42.spring.renderer.Renderer;

public class PrinterWithPrefixImpl implements Printer {
  private Renderer renderer;
  private String prefix;

  public PrinterWithPrefixImpl(Renderer renderer) {
    this.renderer = renderer;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  @Override
  public void print(String message) {
    renderer.render(prefix + " " + message);
  }
}
