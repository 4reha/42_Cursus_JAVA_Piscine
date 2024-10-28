package fr.school42.sockets.models;

import org.json.JSONObject;

public class Command {
  private String type; // "message", "command" , "response", "menu" , "error"
  private String content;
  private String from; // "server", "client"
  private String[] options;

  public Command() {
  }

  public Command(String type, String from, String content) {
    setType(type);
    setFrom(from);
    setContent(content);
  }

  public Command(String type, String from, String content, String[] options) {
    setType(type);
    setFrom(from);
    setContent(content);
    setOptions(options);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type != null && !type.isEmpty() && !type.isBlank() ? type : null;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content != null && !content.isEmpty() && !content.isBlank() ? content : null;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from != null && !from.isEmpty() && !from.isBlank() ? from : null;
  }

  public String[] getOptions() {
    return options != null ? options : new String[0];
  }

  public void setOptions(String[] options) {
    this.options = options;
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("type", type);
    json.put("content", content);
    json.put("from", from);
    json.put("options", options);
    return json;
  }

  public static Command fromJson(JSONObject json) {
    Command command = new Command();
    command.setType(json.optString("type", null));
    command.setContent(json.optString("content", null));
    command.setFrom(json.optString("from", null));
    command
        .setOptions(json.has("options") ? json.getJSONArray("options").toList().toArray(new String[0]) : new String[0]);
    return command;
  }

  public static Command fromJson(String jsonString) {
    if (jsonString == null || jsonString.isEmpty() || jsonString.isBlank())
      throw new IllegalArgumentException("Invalid Command string");
    JSONObject json = new JSONObject(jsonString);
    return fromJson(json);
  }

  @Override
  public String toString() {
    return "Command{" +
        "type='" + type + '\'' +
        ", content='" + content + '\'' +
        ", from='" + from + '\'' +
        ", options=" + options +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Command command = (Command) o;

    if (type != null ? !type.equals(command.type) : command.type != null)
      return false;
    if (content != null ? !content.equals(command.content) : command.content != null)
      return false;
    if (from != null ? !from.equals(command.from) : command.from != null)
      return false;
    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    return options != null ? options.equals(command.options) : command.options == null;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (content != null ? content.hashCode() : 0);
    result = 31 * result + (from != null ? from.hashCode() : 0);
    result = 31 * result + (options != null ? options.hashCode() : 0);
    return result;
  }

}