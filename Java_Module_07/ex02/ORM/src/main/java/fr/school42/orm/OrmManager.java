package fr.school42.orm;

import org.springframework.jdbc.core.JdbcTemplate;
import org.reflections.Reflections;

import fr.school42.annotations.OrmColumn;
import fr.school42.annotations.OrmColumnId;
import fr.school42.annotations.OrmEntity;
import java.util.stream.Collectors;
import javax.sql.DataSource;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.lang.Class;

public class OrmManager {

  private final JdbcTemplate jdbcTemplate;
  private final String MODELS_PACKAGE = "fr.school42.models";

  private final String DROP_TABLE = "DROP TABLE IF EXISTS %s CASCADE";
  private final String CREATE_TABLE = "CREATE TABLE %s (%s)";
  private final String INSERT = "INSERT INTO %s (%s) VALUES (%s)";
  private final String UPDATE = "UPDATE %s SET %s WHERE id = %s";
  private final String SELECT = "SELECT * FROM %s WHERE id = %s";

  public OrmManager(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    try {
      init();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  public void init() throws ClassNotFoundException {
    Reflections reflections = new Reflections(MODELS_PACKAGE);
    Set<Class<?>> classesSet = reflections.getTypesAnnotatedWith(OrmEntity.class);

    for (Class<?> clazz : classesSet) {
      OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);

      jdbcTemplate.execute(String.format(DROP_TABLE, ormEntity.table()));
      System.out.println(String.format(DROP_TABLE, ormEntity.table()));

      Field[] fields = clazz.getDeclaredFields();
      List<String> columns = Arrays.stream(fields)
          .filter(field -> field.isAnnotationPresent(OrmColumn.class) || field.isAnnotationPresent(OrmColumnId.class))
          .map(this::createSqlColumn)
          .collect(Collectors.toList());

      String tableColumns = String.join(", ", columns);

      jdbcTemplate.execute(String.format(CREATE_TABLE, ormEntity.table(), tableColumns));
      System.out.println(String.format(CREATE_TABLE, ormEntity.table(), tableColumns));
    }
  }

  public void save(Object entity) {
    Class<?> aClass = entity.getClass();
    OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
    if (ormEntity != null) {
      Field[] fields = aClass.getDeclaredFields();

      List<String> columns = Arrays.stream(fields)
          .filter(field -> field.isAnnotationPresent(OrmColumn.class))
          .map(field -> field.getAnnotation(OrmColumn.class).name())
          .collect(Collectors.toList());

      List<Object> values = Arrays.stream(fields)
          .filter(field -> field.isAnnotationPresent(OrmColumn.class))
          .map(field -> {
            field.setAccessible(true);
            try {
              return field.get(entity);
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            }
            return null;
          })
          .collect(Collectors.toList());

      String columnsSql = String.join(", ", columns);
      String valuesSql = values.stream().map(v -> (v == null) ? "NULL" : String.format("'%s'", v))
          .collect(Collectors.joining(", "));

      jdbcTemplate.execute(String.format(INSERT, ormEntity.table(), columnsSql, valuesSql));
      System.out.println(String.format(INSERT, ormEntity.table(), columnsSql, valuesSql));
    }
  }

  public void update(Object entity) {
    Class<?> aClass = entity.getClass();
    OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
    if (ormEntity != null) {
      Field[] fields = aClass.getDeclaredFields();

      String columnsValues = Arrays.stream(fields)
          .filter(field -> field.isAnnotationPresent(OrmColumn.class))
          .map(field -> {
            field.setAccessible(true);
            try {
              String value = field.get(entity) == null ? "NULL" : "'" + field.get(entity).toString() + "'";
              return field.getAnnotation(OrmColumn.class).name() + " = " + value;
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            }
            return null;
          })
          .collect(Collectors.joining(", "));

      String id = Arrays.stream(fields)
          .filter(field -> field.isAnnotationPresent(OrmColumnId.class))
          .map(field -> {
            field.setAccessible(true);
            try {
              return field.get(entity) == null ? "NULL" : field.get(entity).toString();
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            }
            return null;
          })
          .findFirst().get();

      jdbcTemplate.execute(String.format(UPDATE, ormEntity.table(), columnsValues, id));
      System.out.println(String.format(UPDATE, ormEntity.table(), columnsValues, id));
    }

  }

  public <T> T findById(Long id, Class<T> aClass) {
    OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
    if (ormEntity != null) {
      List<T> result = jdbcTemplate.query(String.format(SELECT, ormEntity.table(), id),
          (rs, rowNum) -> mapRowToEntity(rs, aClass));
      System.out.println(String.format(SELECT, ormEntity.table(), id));
      return result.isEmpty() ? null : result.get(0);
    }
    return null;
  }

  private <T> T mapRowToEntity(ResultSet rs, Class<T> aClass) {
    try {
      T instance = aClass.getDeclaredConstructor().newInstance();
      Field[] fields = aClass.getDeclaredFields();
      for (Field field : fields) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(OrmColumn.class)) {
          field.set(instance, rs.getObject(field.getAnnotation(OrmColumn.class).name()));
        } else if (field.isAnnotationPresent(OrmColumnId.class)) {
          field.set(instance, rs.getLong(field.getName()));
        }
      }
      return instance;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private String createSqlColumn(Field field) {
    String columnName = field.getName();
    String type = getSqlType(field);

    if (field.isAnnotationPresent(OrmColumnId.class)) {
      return columnName + " SERIAL PRIMARY KEY";
    }

    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
    return ormColumn.name() + " " + type;
  }

  private String getSqlType(Field field) {
    String type = field.getType().getSimpleName().toUpperCase();

    switch (type) {
      case "STRING":
        OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
        return String.format("VARCHAR(%d)", ormColumn.length());
      case "INTEGER":
        return "INTEGER";
      case "DOUBLE":
        return "DOUBLE PRECISION";
      case "LONG":
        return "BIGINT";
      case "BOOLEAN":
        return "BOOLEAN";
      default:
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
  }
}
