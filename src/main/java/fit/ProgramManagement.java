package fit;
import java.io.*;
import java.util.*;

public class ProgramManagement {

    // File path for storing programs
    private static final String PROGRAMS_FILE = "src/main/resources/programs.txt";

    // Method to create a new program
    public static String createProgram(String title, String duration, String goals, String price) {
        if (title == null || title.isEmpty()) {
            return "Program title is required";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROGRAMS_FILE, true))) {
            writer.write(title + "," + duration + "," + goals + "," + price);
            writer.newLine();
            return "Program created successfully";
        } catch (IOException e) {
            return "Error creating program: " + e.getMessage();
        }
    }

    // Method to edit an existing program
    public static String editProgram(String oldTitle, String newDuration, String newPrice) {
        File file = new File(PROGRAMS_FILE);
        List<String> updatedPrograms = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(oldTitle)) {
                    found = true;
                    updatedPrograms.add(parts[0] + "," + newDuration + "," + parts[2] + "," + newPrice);
                } else {
                    updatedPrograms.add(line);
                }
            }
        } catch (IOException e) {
            return "Error reading programs: " + e.getMessage();
        }

        if (!found) {
            return "Program not found";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String program : updatedPrograms) {
                writer.write(program);
                writer.newLine();
            }
            return "Program updated successfully";
        } catch (IOException e) {
            return "Error updating program: " + e.getMessage();
        }
    }

    // Method to delete an existing program
    public static String deleteProgram(String title) {
        File file = new File(PROGRAMS_FILE);
        List<String> remainingPrograms = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[0].equalsIgnoreCase(title)) {
                    remainingPrograms.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            return "Error reading programs: " + e.getMessage();
        }

        if (!found) {
            return "Program not found";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String program : remainingPrograms) {
                writer.write(program);
                writer.newLine();
            }
            return "Program deleted successfully";
        } catch (IOException e) {
            return "Error deleting program: " + e.getMessage();
        }
    }

   /* public static void main(String[] args) {
        // Example usage
        System.out.println(createProgram("Yoga for Beginners", "4 weeks", "Improve flexibility and reduce stress", "$50"));
        System.out.println(editProgram("Yoga for Beginners", "6 weeks", "$75"));
        System.out.println(deleteProgram("Yoga for Beginners"));
    }*/
}


/*package fit;
import java.io.*;
import java.util.*;

public class ProgramManagement {

    // File path for storing programs
    private static final String PROGRAMS_FILE = "C:\\Users\\HP\\OneDrive\\Desktop\\software\\fit\\src\\main\\resources\\programs.txt";

    // Method to create a new program
    public static String createProgram(String title, String duration, String goals, String price) {
        if (title == null || title.isEmpty()) {
            return "Program title is required";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROGRAMS_FILE, true))) {
            writer.write(title + "," + duration + "," + goals + "," + price);
            writer.newLine();
            return "Program created successfully";
        } catch (IOException e) {
            return "Error creating program: " + e.getMessage();
        }
    }

    // Method to edit an existing program
    public static String editProgram(String oldTitle, String newDuration, String newPrice) {
        File file = new File(PROGRAMS_FILE);
        List<String> updatedPrograms = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(oldTitle)) {
                    found = true;
                    updatedPrograms.add(parts[0] + "," + newDuration + "," + parts[2] + "," + newPrice);
                } else {
                    updatedPrograms.add(line);
                }
            }
        } catch (IOException e) {
            return "Error reading programs: " + e.getMessage();
        }

        if (!found) {
            return "Program not found";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String program : updatedPrograms) {
                writer.write(program);
                writer.newLine();
            }
            return "Program updated successfully";
        } catch (IOException e) {
            return "Error updating program: " + e.getMessage();
        }
    }

    // Method to delete an existing program
    public static String deleteProgram(String title) {
        File file = new File(PROGRAMS_FILE);
        List<String> remainingPrograms = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[0].equalsIgnoreCase(title)) {
                    remainingPrograms.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            return "Error reading programs: " + e.getMessage();
        }

        if (!found) {
            return "Program not found";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String program : remainingPrograms) {
                writer.write(program);
                writer.newLine();
            }
            return "Program deleted successfully";
        } catch (IOException e) {
            return "Error deleting program: " + e.getMessage();
        }
    }
}/*



   /* public static void main(String[] args) {
        // Example usage
        System.out.println(createProgram("Yoga for Beginners", "4 weeks", "Improve flexibility and reduce stress", "$50"));
        System.out.println(editProgram("Yoga for Beginners", "6 weeks", "$75"));
        System.out.println(deleteProgram("Yoga for Beginners"));
    }*/

