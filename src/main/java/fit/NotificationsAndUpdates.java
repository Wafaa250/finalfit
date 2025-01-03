package fit;

import java.io.*;
import java.util.*;

public class NotificationsAndUpdates {

	private static final String NOTIFICATIONS_FILE = "src/main/resources/notifications.txt";

	// طريقة إرسال الإشعار مع التحقق من التكرار
	public static String sendNotification(String recipient, String message) {
	    try {
	        List<String> existingNotifications = new ArrayList<>();
	        try (BufferedReader reader = new BufferedReader(new FileReader(NOTIFICATIONS_FILE))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                existingNotifications.add(line.trim());
	            }
	        }

	        String notification = "Recipient:" + recipient + ",Message:" + message.trim();
	        if (existingNotifications.contains(notification)) {
	            return "Notification already exists.";
	        }

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTIFICATIONS_FILE, true))) {
	            writer.write(notification);
	            writer.newLine();
	        }
	        return "Notification sent: " + notification;
	    } catch (IOException e) {
	        System.out.println("Error writing to notifications file: " + e.getMessage());
	        return "Error sending notification.";
	    }
	}

	// طريقة عرض الإشعارات بدون تكرار
	public static List<String> viewNotifications(String recipient) {
	    Set<String> notifications = new LinkedHashSet<>();
	    try (BufferedReader reader = new BufferedReader(new FileReader(NOTIFICATIONS_FILE))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            if (line.startsWith("Recipient:" + recipient)) {
	                String message = line.split(",Message:")[1].trim();
	                notifications.add(message); // استخدام Set لإزالة التكرار
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading notifications file: " + e.getMessage());
	    }
	    return new ArrayList<>(notifications); // تحويل Set إلى List
	}



}
