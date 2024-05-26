package fr.school42.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HtmlInput {

  String type();

  String name();

  String placeholder();
}
