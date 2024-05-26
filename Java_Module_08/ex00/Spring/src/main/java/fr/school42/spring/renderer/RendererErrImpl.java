package fr.school42.spring.renderer;

import fr.school42.spring.preprocessor.PreProcessor;

public class RendererErrImpl implements Renderer {
  private PreProcessor preProcessor;

  public RendererErrImpl(PreProcessor preProcessor) {
    this.preProcessor = preProcessor;
  }

  @Override
  public void render(String message) {
    System.err.println("\u001B[31m" + preProcessor.process(message) + "\u001B[0m");
  }
}
