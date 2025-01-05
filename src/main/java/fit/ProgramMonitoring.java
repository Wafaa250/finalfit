package fit;

import java.util.*;
import java.util.stream.Collectors;

public class ProgramMonitoring {

    private List<FitnessProgram> programs;

    public ProgramMonitoring() {
        this.programs = new ArrayList<>();
        initializePrograms();
    }

    // Initialize dummy programs for demonstration purposes
    private void initializePrograms() {
        programs.add(new FitnessProgram("Yoga Basics", 50, 1200.0, 30, true));
        programs.add(new FitnessProgram("Advanced Cardio", 75, 2500.0, 50, true));
        programs.add(new FitnessProgram("Strength Training", 40, 1800.0, 20, false));
        programs.add(new FitnessProgram("Pilates Essentials", 60, 2100.0, 45, true));
    }

    // View popular programs by enrollment
    public List<FitnessProgram> getPopularPrograms() {
        return programs.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getEnrollment(), p1.getEnrollment()))
                .collect(Collectors.toList());
    }

    // Generate revenue report
    public Map<String, Double> generateRevenueReport() {
        return programs.stream()
                .collect(Collectors.toMap(FitnessProgram::getName, FitnessProgram::getRevenue));
    }

    // Monitor attendance in programs
    public Map<String, Integer> getAttendanceStatistics() {
        return programs.stream()
                .collect(Collectors.toMap(FitnessProgram::getName, FitnessProgram::getAttendance));
    }

    // Generate client progress report
    public Map<String, String> generateClientProgressReport() {
        Map<String, String> clientProgress = new HashMap<>();
        clientProgress.put("Client A", "Completed Yoga Basics, Active in Advanced Cardio");
        clientProgress.put("Client B", "Completed Strength Training, Active in Pilates Essentials");
        return clientProgress;
    }

    public Map<String, List<FitnessProgram>> getActiveAndCompletedPrograms() {
        Map<String, List<FitnessProgram>> result = new HashMap<>();
        result.put("Active", programs.stream().filter(FitnessProgram::isActive).toList());
        result.put("Completed", programs.stream().filter(p -> !p.isActive()).toList());
        return result;
    }


    // Inner class to represent fitness programs
    public static class FitnessProgram {
        private String name;
        private int enrollment;
        private double revenue;
        private int attendance;
        private boolean active;

        public FitnessProgram(String name, int enrollment, double revenue, int attendance, boolean active) {
            this.name = name;
            this.enrollment = enrollment;
            this.revenue = revenue;
            this.attendance = attendance;
            this.active = active;
        }

        public String getName() {
            return name;
        }

        public int getEnrollment() {
            return enrollment;
        }

        public double getRevenue() {
            return revenue;
        }

        public int getAttendance() {
            return attendance;
        }

        public boolean isActive() {
            return active;
        }
    }}