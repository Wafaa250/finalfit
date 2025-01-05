package fit;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SubscriptionManagementSource {

    private List<SubscriptionPlan> plans = new ArrayList<>();
    List<Subscription> subscriptions = new ArrayList<>();
    private Map<String, String> users = new HashMap<>(); // حفظ الـ Client ID بناءً على البريد الإلكتروني
    private Map<String, String> usernameMap = new HashMap<>();
    private static final Logger logger = Logger.getLogger(SubscriptionManagementSource.class.getName());

    public SubscriptionManagementSource() {
        loadUsers();
    }

    // تحميل بيانات المستخدمين من ملف Accounts.txt
    public void loadUsers() {
        String filePath = "src/main/resources/Accounts.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                if (user.length >= 5) {
                    String username = user[0];
                    String email = user[2]; // البريد الإلكتروني
                    String clientId = user[3]; // Client ID
                    users.put(email, clientId); // تخزين البريد الإلكتروني و Client ID
                    usernameMap.put(clientId, username); // تخزين Client ID والاسم
                }
            }
        } catch (IOException e) {
        	logger.log(Level.SEVERE, "Error reading accounts file: " + e.getMessage(), e);
        }
    }

    // البحث عن الـ Client ID باستخدام البريد الإلكتروني
    public String getClientId(String email) {
        return users.get(email); // إرجاع الـ Client ID بناءً على البريد الإلكتروني
    }

    public boolean addSubscriptionPlan(String name, int price) {
        for (SubscriptionPlan plan : plans) {
            if (plan.getName().equalsIgnoreCase(name)) {
            	logger.log(Level.INFO, () -> "Plan already exists: " + name);
                return false; // Plan already exists
            }
        }
        plans.add(new SubscriptionPlan(name, price));
        savePlansToFile();
        logger.log(Level.INFO, "Plan added successfully: " + name);
        return true;
    }

    public void clearPlans() {
        plans.clear();
        savePlansToFile();
    }

    // تعديل خطة اشتراك موجودة
    public boolean modifySubscriptionPlan(String oldName, String newName, int newPrice) {
        for (SubscriptionPlan plan : plans) {
            if (plan.getName().equalsIgnoreCase(oldName)) {
                plan.setName(newName);
                plan.setPrice(newPrice);
                savePlansToFile();
                return true;
            }
        }
        return false; // Plan not found to modify
    }

    // حذف خطة اشتراك
    public boolean deleteSubscriptionPlan(String name) {
        for (SubscriptionPlan plan : plans) {
            if (plan.getName().equalsIgnoreCase(name)) {
                plans.remove(plan);
                savePlansToFile();
                return true;
            }
        }
        return false; 
    }

    // طلب ترقية اشتراك للعميل
    public void requestUpgrade(String email) {
        String clientId = getClientId(email);  // استخراج Client ID باستخدام البريد الإلكتروني
        if (clientId != null) {
            subscriptions.add(new Subscription(clientId, "Upgrade Requested"));
        } else {
            logger.log(Level.INFO, "Client not found!");
        }
    }

    // رفض طلب ترقية الاشتراك للعميل
    public boolean rejectSubscriptionUpgrade(String email) {
        String clientId = getClientId(email);  // استخراج Client ID باستخدام البريد الإلكتروني
        for (Subscription subscription : subscriptions) {
            if (subscription.getClientId().equals(clientId) && subscription.getPlanName().equals("Upgrade Requested")) {
                subscription.setPlanName("Basic");  // الترقية تم رفضها، لذلك نعيد اشتراك العميل إلى الخطة الأساسية
                saveSubscriptionsToFile();
                return true; // رفض الترقية بنجاح
            }
        }
        return false; // لم يتم العثور على طلب ترقية
    }


    // دالة لحفظ خطط الاشتراك في الملف
    private void savePlansToFile() {
        String filePath = "src/main/resources/SubscriptionPlans.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (SubscriptionPlan plan : plans) {
                writer.write(plan.getName() + "," + plan.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing subscription plans to file", e);
        }
    }

  


    // دالة لحفظ الاشتراكات في الملف
    private void saveSubscriptionsToFile() {
        String filePath = "src/main/resources/Subscriptions.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Subscription subscription : subscriptions) {
                String username = usernameMap.get(subscription.getClientId()); // استخراج اسم المستخدم باستخدام Client ID
                if (username != null) {
                    writer.write(username + "," + subscription.getPlanName()); // تخزين اسم المستخدم بدلاً من Client ID
                    writer.newLine();
                } else {
                    logger.log(Level.WARNING, "No username found for clientId: {0}", subscription.getClientId());
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing subscriptions to file", e);
        }
    }

    public boolean addSubscription(String email, String planName) {
        String clientId = getClientId(email);
        if (clientId != null) {
            Subscription newSubscription = new Subscription(clientId, planName);
            subscriptions.add(newSubscription);
            saveSubscriptionsToFile();
            logger.log(Level.INFO, "Subscription added successfully");

            return true;
        } else {
            logger.log(Level.INFO, "Client not found");
            return false;
        }
    }
    
    // الكلاسات الداخلية الخاصة بخطط الاشتراك والاشتراكات
    public static class SubscriptionPlan {
        private String name;
        private int price;

        public SubscriptionPlan(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    public static class Subscription {
        private String clientId;
        private String planName;

        public Subscription(String clientId, String planName) {
            this.clientId = clientId;
            this.planName = planName;
        }

        public String getClientId() {
            return clientId;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }
    }

	public List<Subscription> getSubscriptions() {
        return subscriptions;
	}
	public List<SubscriptionPlan> getPlans() {
	    return plans;
	}

}
