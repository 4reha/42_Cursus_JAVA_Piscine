package fr.school42.forms;

import fr.school42.annotations.HtmlForm;
import fr.school42.annotations.HtmlInput;

@HtmlForm(fileName = "test_form.html", action = "/test", method = "post")
public class TestForm {

  @HtmlInput(type = "text", name = "subject", placeholder = "Enter Subject")
  private String subject;

  @HtmlInput(type = "text", name = "grade", placeholder = "Enter Grade")
  private String grade;

}
