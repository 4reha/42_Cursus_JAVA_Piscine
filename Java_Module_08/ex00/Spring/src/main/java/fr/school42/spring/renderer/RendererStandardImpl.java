package fr.school42.spring.renderer;

import fr.school42.spring.preprocessor.PreProcessor;

public class RendererStandardImpl implements Renderer {
  private PreProcessor preProcessor;

  public RendererStandardImpl(PreProcessor preProcessor) {
    this.preProcessor = preProcessor;
  }

  @Override
  public void render(String message) {
    System.out.println(preProcessor.process(message));
  }
}
