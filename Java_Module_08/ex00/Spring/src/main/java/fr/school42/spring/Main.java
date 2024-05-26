package fr.school42.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.school42.spring.printer.Printer;

// public class Main {
//   public static void main(String[] args) {
//     PreProcessor preProcessor = new PreProcessorToUpperImpl();
//     Renderer renderer = new RendererErrImpl(preProcessor);
//     PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
//     printer.setPrefix("Prefix");
//     printer.print("Hello!");
//   }
// }

public class Main {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
    Printer printer = context.getBean("printerWithPrefix", Printer.class);
    printer.print("Hello!");
  }
}