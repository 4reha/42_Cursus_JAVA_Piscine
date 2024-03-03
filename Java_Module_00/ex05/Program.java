import java.util.Scanner;

public class Program {

  private static Scanner scanner = new Scanner(System.in);

  private static final String[] WEEK_DAYS = { "MO", "TU", "WE", "TH", "FR", "SA", "SU" };
  private static final int DAYS_IN_MONTH = 30;
  private static final int START_OF_THE_MONTH = 1;

  private static String[] students = new String[10];
  private static int studentsCount = 0;
  private static boolean[][] lessonsSchedule = new boolean[30][6];
  private static int[][][] attendanceRecord = new int[30][6][10];

  private static void addStudents() {
    System.out.println("Enter students names:");
    while (true) {
      if (scanner.hasNextLine()) {
        String line = scanner.next();
        if (line.equals(".")) {
          break;
        } else if (line.length() <= 0 || line.length() > 10) {
          System.err.println("Error: name length invalid");
          continue;
        } else {
          students[studentsCount++] = line;
          if (studentsCount == students.length) {
            System.err.println("Students limit reached");
            break;
          }
        }
      } else {
        break;
      }
    }
  }

  private static void scheduleLessons() {
    System.out.println("Enter classes schedule:");
    while (true) {
      if (scanner.hasNextLine()) {
        if (scanner.hasNextInt()) {
          int hour = scanner.nextInt();
          if (hour < 1 || hour > 6) {
            System.err.println("Error: Invalid class hour");
            scanner.nextLine();
          } else {
            String day = scanner.next();
            scanner.nextLine();
            boolean dayFound = false;
            for (int i = 0; i < WEEK_DAYS.length; i++) {
              if (WEEK_DAYS[i].equals(day)) {
                dayFound = true;
                int firstDayIndex = i >= START_OF_THE_MONTH ? i - START_OF_THE_MONTH : i + 7 - START_OF_THE_MONTH;
                if (lessonsSchedule[firstDayIndex][hour - 1]) {
                  System.err.println("Error: Class already scheduled");
                  break;
                }
                // total classes in week must not exceed 10
                int classesInWeek = 0;
                for (int j = firstDayIndex; j < firstDayIndex + 7; j++) {
                  for (int k = 0; k < lessonsSchedule[j].length; k++) {
                    if (lessonsSchedule[j][k]) {
                      classesInWeek++;
                    }
                  }
                }
                if (classesInWeek >= 10) {
                  System.err.println("Error: Classes limit in week exceeded");
                  return;
                }
                for (int j = firstDayIndex; j < DAYS_IN_MONTH; j += 7) {
                  lessonsSchedule[j][hour - 1] = true;
                }
                break;
              }
            }
            if (!dayFound) {
              System.err.println("Error: Invalid day");
            }
          }
          continue;
        } else if (scanner.nextLine().equals(".")) {
          break;
        } else {
          System.err.println("Error: Invalid input");
        }
      } else {
        break;
      }
    }
  }

  private static int checkStudent(String student) {
    for (int i = 0; i < studentsCount; i++) {
      if (students[i].equals(student)) {
        return i;
      }
    }
    return -1;
  }

  private static void recordAttendance() {
    System.out.println("Enter attendance records:");
    while (true) {
      if (scanner.hasNextLine()) {
        // format: <student> <hour> <day> <attendance>
        // example: Mike 2 28 NOT_HERE / Mike 2 28 HERE
        String line = scanner.next();
        if (line.equals(".")) {
          break;
        }
        int studentIndex = checkStudent(line);
        if (studentIndex == -1) {
          System.err.println("Error: Invalid student");
          scanner.nextLine();
          continue;
        }
        if (!scanner.hasNextInt()) {
          System.err.println("Error: Invalid class hour");
          scanner.nextLine();
          continue;
        }
        int hour = scanner.nextInt();
        if (hour < 1 || hour > 6) {
          System.err.println("Error: Invalid class hour");
          scanner.nextLine();
          continue;
        }
        if (!scanner.hasNextInt()) {
          System.err.println("Error: Invalid day");
          scanner.nextLine();
          continue;
        }
        int day = scanner.nextInt();
        if (day < 1 || day > 30) {
          System.err.println("Error: Invalid day");
          scanner.nextLine();
          continue;
        }
        if (!lessonsSchedule[day - 1][hour - 1]) {
          System.err.println("Error: No class at this time");
          scanner.nextLine();
          continue;
        }
        if (!scanner.hasNext()) {
          System.err.println("Error: Invalid attendance");
          scanner.nextLine();
          continue;
        }
        String attendance = scanner.next();
        if (attendance.equals("HERE")) {
          attendanceRecord[day - 1][hour - 1][studentIndex] = 1;
        } else if (attendance.equals("NOT_HERE")) {
          attendanceRecord[day - 1][hour - 1][studentIndex] = -1;
        } else {
          System.err.println("Error: Invalid attendance");
          scanner.nextLine();
          continue;
        }
      } else {
        break;
      }

    }
  }

  private static void printRecords() {

    System.out.printf("%10s|", " ");
    for (int i = 0; i < lessonsSchedule.length; i++) {
      for (int j = 0; j < lessonsSchedule[i].length; j++) {
        if (lessonsSchedule[i][j]) {
          System.out.printf("%1d:00%3s%3d|", j + 1, WEEK_DAYS[(i + START_OF_THE_MONTH) % 7], i + 1);
        }
      }
    }
    System.out.println();

    for (int s = 0; s < studentsCount; s++) {
      System.out.printf("%10s|", students[s]);
      for (int i = 0; i < lessonsSchedule.length; i++) {
        for (int j = 0; j < lessonsSchedule[i].length; j++) {
          if (lessonsSchedule[i][j]) {
            if (attendanceRecord[i][j][s] == 1) {
              System.out.printf("%10s|", "1");
            } else if (attendanceRecord[i][j][s] == -1) {
              System.out.printf("%10s|", "-1");
            } else {
              System.out.printf("%10s|", " ");
            }
          }
        }
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    addStudents();
    scheduleLessons();
    recordAttendance();
    printRecords();

  }
}