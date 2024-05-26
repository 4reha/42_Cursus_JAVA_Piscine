package fr.school42.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HtmlForm {

  String fileName();

  String action();

  String method();
}
