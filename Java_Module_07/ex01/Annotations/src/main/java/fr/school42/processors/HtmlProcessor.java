package fr.school42.processors;

import com.google.auto.service.AutoService;

import fr.school42.annotations.HtmlForm;
import fr.school42.annotations.HtmlInput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({ "fr.school42.annotations.HtmlForm" })
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (TypeElement annotation : annotations) {
      Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

      for (Element element : annotatedElements) {
        if (element.getKind().isClass()) {
          TypeElement classElement = (TypeElement) element;
          HtmlForm htmlForm = classElement.getAnnotation(HtmlForm.class);
          String htmlFormCode = generateHtmlFormCode(classElement, htmlForm);

          try {
            writeHtmlFormToFile(htmlForm.fileName(), htmlFormCode);
          } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                "Failed to write HTML form file: " + e.getMessage());
          }
        }
      }
    }

    return true;
  }

  private String generateHtmlFormCode(TypeElement classElement, HtmlForm htmlForm) {
    StringBuilder form = new StringBuilder();
    form.append(String.format("<form action = \"%s\" method = \"%s\">\n", htmlForm.action(), htmlForm.method()));

    for (Element field : classElement.getEnclosedElements()) {
      if (field.getKind().isField()) {
        HtmlInput htmlInput = field.getAnnotation(HtmlInput.class);
        if (htmlInput != null) {
          form.append(String.format("  <input type=\"%s\" name=\"%s\" placeholder=\"%s\"/>\n", htmlInput.type(),
              htmlInput.name(), htmlInput.placeholder()));
        }
      }
    }

    form.append("  <input type=\"submit\" value=\"Send\"/>\n");
    form.append("</form>");
    return form.toString();
  }

  private void writeHtmlFormToFile(String fileName, String htmlFormCode) throws IOException {
    Files.write(Paths.get("target/classes/" + fileName), htmlFormCode.getBytes());
  }
}