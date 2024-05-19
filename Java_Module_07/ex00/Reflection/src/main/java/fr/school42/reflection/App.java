package fr.school42.reflection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.lang.reflect.*;

public class App {

  private static final String PACKAGE_NAME = "fr.school42.classes";
  private static Scanner scanner = new Scanner(System.in);
  private static Set<Class<?>> classes = new HashSet<>();

  public static void main(String[] args) {

    try {
      getAllClasses();
      printClasses();
      Class<?> clazz = useClass();
      printClassInfo(clazz);
      Object object = initObject(clazz);
      updateObject(object);
      callMethod(object);
    } catch (Throwable e) {
      e.printStackTrace();
    }

  }

  private static void getAllClasses() {
    String packagePath = "/" + PACKAGE_NAME.replace(".", "/");
    InputStream stream = App.class.getResourceAsStream(packagePath);
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    classes = reader.lines()
        .filter(line -> line.endsWith(".class"))
        .map(line -> PACKAGE_NAME + "." + line.substring(0, line.length() - 6))
        .map(App::getClass)
        .collect(Collectors.toSet());
  }

  private static void printClasses() {
    System.out.println("Classes:");
    for (Class<?> clazz : classes) {
      System.out.println(clazz.getSimpleName());
    }
  }

  private static Class<?> useClass() {
    System.out.println("---------------------");
    System.out.println("Enter class name:");
    System.out.print("-> ");
    String className = scanner.nextLine();
    Class<?> clazz = classes.stream()
        .filter(c -> c.getSimpleName().equals(className))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("No " + className + " class found"));
    return clazz;
  }

  private static void printClassInfo(Class<?> clazz) {
    System.out.println("---------------------");
    System.out.println("fields:");
    for (Field field : clazz.getDeclaredFields()) {
      System.out.println("    " + field.getType().getSimpleName() + " " + field.getName());
    }
    System.out.println("methods:");
    for (Method method : clazz.getDeclaredMethods()) {
      // skip Overidden Object methods
      if (Arrays.stream(Object.class.getDeclaredMethods())
          .anyMatch(m -> m.getName().equals(method.getName()))) {
        continue;
      }

      System.out.print("    " + method.getReturnType().getSimpleName());
      System.out.print(" " + method.getName());
      System.out.println("(" + Arrays.stream(method.getParameterTypes())
          .map(Class::getSimpleName)
          .collect(Collectors.joining(", ")) + ")");
    }

  }

  private static Object initObject(Class<?> clazz)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    System.out.println("---------------------");
    System.out.println("Let's create an object.");

    Constructor<?> constructor = Arrays.stream(clazz.getConstructors())
        .filter(c -> c.getParameterCount() > 0).findFirst()
        .orElseThrow(() -> new NoSuchElementException("No suitable constructor found"));

    Parameter[] parameters = constructor.getParameters();
    List<Object> args = new ArrayList<>();
    for (Parameter parameter : parameters) {
      System.out.println(parameter.getName() + ":");
      System.out.print("-> ");
      String value = scanner.nextLine();
      args.add(parseValue(parameter.getType(), value));
    }

    System.out.println(args);

    Object object = constructor.newInstance(args.toArray());
    System.out.println("Object created: " + object);

    return object;
  }

  private static void updateObject(Object object) throws IllegalArgumentException, IllegalAccessException {
    System.out.println("---------------------");
    System.out.println("Enter name of the field for changing:");
    System.out.print("-> ");
    String fieldName = scanner.nextLine();
    Field field = Arrays.stream(object.getClass().getDeclaredFields())
        .filter(f -> f.getName().equals(fieldName))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("No " + fieldName + " field found"));

    String filedType = field.getType().getSimpleName();
    System.out.println("Enter " + filedType + " value:");
    System.out.print("-> ");
    Object value = parseValue(field.getType(), scanner.nextLine());

    field.setAccessible(true);
    field.set(object, value);

    System.out.println("Object updated: " + object);
  }

  private static void callMethod(Object object)
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    System.out.println("---------------------");
    System.out.println("Enter name of the method for call:");
    System.out.print("-> ");
    String methodWithParams = scanner.nextLine();
    Method method = Arrays.stream(object.getClass().getDeclaredMethods())
        .filter(m -> {
          String calledMethod = m.getName() + "(" + Arrays.stream(m.getParameterTypes())
              .map(Class::getSimpleName)
              .collect(Collectors.joining(", ")) + ")";
          return calledMethod.equals(methodWithParams);
        })
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("No " + methodWithParams + " method found"));

    Parameter[] parameters = method.getParameters();
    List<Object> args = new ArrayList<>();
    for (Parameter parameter : parameters) {
      System.out.println("Enter " + parameter.getType().getSimpleName() + " value:");
      System.out.print("-> ");
      String value = scanner.nextLine();
      args.add(parseValue(parameter.getType(), value));
    }

    Object result = method.invoke(object, args.toArray());
    System.out.println("Method returned:\n" + result);
  }

  private static Object parseValue(Class<?> type, String value) {
    if (type == null || value == null) {
      return null;
    }

    switch (type.getName()) {
      case "java.lang.String":
        return value;
      case "java.lang.Integer":
      case "int":
        return Integer.parseInt(value);
      case "java.lang.Long":
      case "long":
        return Long.parseLong(value);
      case "java.lang.Double":
      case "double":
        return Double.parseDouble(value);
      case "java.lang.Boolean":
      case "boolean":
        return Boolean.parseBoolean(value);
      default:
        // Handle other types or throw an exception
        throw new IllegalArgumentException("Unsupported type: " + type.getName());
    }
  }

  private static Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

}