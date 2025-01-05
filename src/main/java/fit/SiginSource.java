package fit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SiginSource {
    private static final Logger LOGGER = Logger.getLogger(SiginSource.class.getName()); // إعداد Logger
    private boolean found = false;
    private boolean passFound = false;
    private String workRole;
    private int checkValid = 0;

    public int getCheckValid() {
        return checkValid;
    }

    public void setCheckValid(int checkValid) {
        this.checkValid = checkValid;
    }

    public void checkLoginValidInFile(String userName, String pass) {
        String filePath = "src/main/resources/Accounts.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(userName)) {
                    found = true;
                    if (parts[3].equals(pass)) {
                        passFound = true;
                        setCheckValid(1);
                    }
                    workRole = parts[4];
                    break;
                }
            }
        } 
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading the accounts file", e);
        }
    }

    public String getWorkRole() {
        return workRole;
    }

    public boolean getFoundAccount() {
        return found && passFound;
    }
}
