
package fit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SiginSource {
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
        // تحديد المسار الكامل للملف
        String filePath = "src/main/resources/Accounts.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // قراءة كل سطر في الملف
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // التحقق إذا كانت البيانات تحتوي على الأقل على 4 عناصر (اسم المستخدم، المدينة، البريد الإلكتروني، كلمة المرور)
                if (parts.length >= 4) {
                    // التحقق من اسم المستخدم
                    if (parts[0].equals(userName)) {
                        found = true;

                        // التحقق من كلمة المرور
                        if (parts[3].equals(pass)) {
                            passFound = true;
                            setCheckValid(1);  // تعيين القيمة لتحديد أن تسجيل الدخول ناجح
                        }

                        // تحديد الدور (الوظيفة)
                        workRole = parts[4];
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getWorkRole() {
        return workRole;
    }

    public boolean getFoundAccount() {
        return found && passFound;
    }
}