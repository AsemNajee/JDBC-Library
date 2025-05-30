---

## دليل استخدام مكتبة `jdbclibrary`

تهدف مكتبة `jdbclibrary` إلى تبسيط التفاعل مع قواعد بيانات MySQL في تطبيقات Java، مع توفير هيكل واضح يتبع نمط MVC. يوضح هذا الدليل الخطوات الأساسية لاستخدام المكتبة لإنشاء كائنات جديدة وتنفيذ عمليات قاعدة البيانات عليها.

### 🚀 الخطوات من الصفر لإنشاء كائن جديد باستخدام المكتبة

لنفترض أنك تريد إنشاء كائن جديد يمثل **"المنتج" (Product)**.

---

### 1️⃣ إنشاء الجدول (Migration)

**الهدف:** تعريف هيكل جدول `products` في قاعدة البيانات.

أنشئ ملفًا جديدًا (مثل `ProductMigration.java`) داخل حزمة `jdbclibrary.database.migration` (أو حزمة مخصصة لعمليات الترحيل لديك) وأضف الكود التالي:

```java
// jdbclibrary/database/migration/ProductMigration.java
package jdbclibrary.database.migration;

import java.sql.SQLException;
import jdbclibrary.libs.querybuilder.createschema.Column;

public class ProductMigration {
    public static void migrate() throws SQLException {
        TableSchema.create("products", // اسم الجدول
            // ID: عدد صحيح، مفتاح أساسي، غير سالب
            Column.integer("id").primary(true).unsigned(true), 
            // الاسم: نص، لا يمكن أن يكون فارغًا، فريد
            Column.string("name", 255).notNull().unique(),     
            // السعر: رقم عشري (10 أرقام، 2 بعد الفاصلة)، لا يمكن أن يكون فارغًا
            Column.decimal("price", 10, 2).notNull(),          
            // الوصف: نص طويل
            Column.text("description"),                        
             // المخزون: عدد صحيح، لا يمكن أن يكون فارغًا، القيمة الافتراضية 0
            Column.integer("stock").notNull().defaultValue(0) 
        );
        System.out.println("Table 'products' created/replaced successfully.");
    }
}
```

---

### 2️⃣ إنشاء نموذج البيانات (Model)

**الهدف:** توفير وظائف CRUD التلقائية لجدول `products`.

أنشئ ملفًا جديدًا (مثل `ProductModel.java`) داخل حزمة `jdbclibrary.model` وأضف الكود التالي:

```java
// jdbclibrary/model/ProductModel.java
package jdbclibrary.model;

import jdbclibrary.model.helper.ColumnInfo;
import jdbclibrary.model.helper.ColumnType;

import java.sql.SQLException;
import java.util.HashMap;

public class ProductModel extends AbstractModel {

    /**
     * مُنشئ لـ ProductModel.
     * يهيئ AbstractModel لجدول 'products' باستخدام تعريفات الأعمدة.
     * @throws SQLException إذا حدث خطأ في تهيئة الاتصال بقاعدة البيانات.
     */
    public ProductModel() throws SQLException {
        super("products", getColumns());
    }

    /**
     * يحدد خصائص كل عمود في جدول 'products' لغرض التحقق من صحة البيانات والأذونات.
     * @return خريطة بأسماء الأعمدة ومعلومات ColumnInfo الخاصة بها.
     */
    private static HashMap<String, ColumnInfo> getColumns() {
        HashMap<String, ColumnInfo> columns = new HashMap<>();

        // خصائص الأعمدة: اسم_العمود, ColumnInfo(النوع, مطلوب؟, قابل_للتحديث؟, قابل_للإدراج؟)
        // (true, true, true) تعني: مطلوب عند الإدخال، يمكن تحديثه، يمكن إظهاره في الاستعلامات.
        
        // ID لا يُحدث يدويًا، لكن يمكن قراءته وإدراجه (عندما يكون auto-increment)
        columns.put("id", new ColumnInfo(ColumnType.INT, false, false, true)); 
        columns.put("name", new ColumnInfo(ColumnType.STRING, true, true, true));
        columns.put("price", new ColumnInfo(ColumnType.DECIMAL, true, true, true)); 
        columns.put("description", new ColumnInfo(ColumnType.STRING, false, true, true)); 
        // الوصف ليس مطلوبًا
        columns.put("stock", new ColumnInfo(ColumnType.INT, true, true, true));

        return columns;
    }
}
```
---

### 3️⃣ إنشاء المتحكم (Controller)

**الهدف:** التعامل مع نموذج `ProductModel` لتوفير وظائف الإدخال، التحديث، الحذف، والاستعلام.

أنشئ ملفًا جديدًا (مثل `ProductController.java`) داخل حزمة `jdbclibrary.controller` وأضف الكود التالي:

```java
// jdbclibrary/controller/ProductController.java
package jdbclibrary.controller;

import jdbclibrary.model.ProductModel;
import java.sql.SQLException;
import java.util.HashMap;

public class ProductController {

    private final ProductModel model;

    /**
     * مُنشئ لـ ProductController.
     * يهيئ مثيل من ProductModel للتفاعل مع جدول المنتجات.
     * @throws SQLException إذا حدث خطأ في تهيئة النموذج.
     */
    public ProductController() throws SQLException {
        this.model = new ProductModel();
    }

    /**
     * لإنشاء منتج جديد في قاعدة البيانات.
     * @param name اسم المنتج.
     * @param price سعر المنتج.
     * @param description وصف المنتج.
     * @param stock كمية المخزون.
     */
    public void createProduct(String name, double price, String description, int stock) throws SQLException {
        HashMap<String, String> data = new HashMap<>();
        data.put("name", name);
        // تحويل الأرقام إلى String
        data.put("price", String.valueOf(price)); 
        data.put("description", description);
        data.put("stock", String.valueOf(stock));
        if (model.create(data)) {
            System.out.println("Product '" + name + "' created successfully.");
        } else {
            System.out.println("Failed to create product '" + name + "'.");
        }
    }

    /**
     * لتحديث معلومات منتج موجود.
     * @param id معرف المنتج المراد تحديثه.
     * @param newName الاسم الجديد للمنتج (يمكن أن يكون null إذا لم يتغير).
     * @param newPrice السعر الجديد للمنتج (يمكن أن يكون null إذا لم يتغير).
     * @param newDescription الوصف الجديد للمنتج (يمكن أن يكون null إذا لم يتغير).
     * @param newStock الكمية الجديدة للمخزون (يمكن أن يكون null إذا لم تتغير).
     */
    public void updateProduct(int id, String newName, Double newPrice, String newDescription, Integer newStock) throws SQLException {
        HashMap<String, String> data = new HashMap<>();
        if (newName != null) data.put("name", newName);
        if (newPrice != null) data.put("price", String.valueOf(newPrice));
        if (newDescription != null) data.put("description", newDescription);
        if (newStock != null) data.put("stock", String.valueOf(newStock));

        if (data.isEmpty()) {
            System.out.println("No data provided for update.");
            return;
        }

        if (model.update(id, data)) {
            System.out.println("Product with ID " + id + " updated successfully.");
        } else {
            System.out.println("Failed to update product with ID " + id + ".");
        }
    }

    /**
     * لحذف منتج من قاعدة البيانات.
     * @param id معرف المنتج المراد حذفه.
     */
    public void deleteProduct(int id) throws SQLException {
        if (model.delete(id)) {
            System.out.println("Product with ID " + id + " deleted successfully.");
        } else {
            System.out.println("Failed to delete product with ID " + id + ".");
        }
    }

    /**
     * لطباعة جميع المنتجات الموجودة في قاعدة البيانات.
     */
    public void printAllProducts() throws SQLException {
        var result = model.getAll();
        System.out.println("\n--- All Products ---");
        if (result.getData().isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (var row : result.getData()) {
                System.out.println(row);
            }
        }
    }

    /**
     * لطباعة منتج واحد بناءً على معرفه.
     * @param id معرف المنتج.
     */
    public void printProductById(int id) throws SQLException {
        var product = model.get(id);
        if (product != null && !product.isEmpty()) {
            System.out.println("Product by ID " + id + ": " + product);
        } else {
            System.out.println("Product with ID " + id + " not found.");
        }
    }

    /**
     * لطباعة المنتجات التي تطابق شرط معين.
     * @param column العمود المراد البحث فيه.
     * @param value القيمة المراد مطابقتها.
     */
    public void printProductsWhere(String column, String value) throws SQLException {
        var result = model.getWhere(column, value);
        System.out.println("\n--- Products where " + column + " = " + value + " ---");
        if (result.getData().isEmpty()) {
            System.out.println("No products found matching the criteria.");
        } else {
            for (var row : result.getData()) {
                System.out.println(row);
            }
        }
    }
}
```

---

### 🧪 مثال تجريبي لتشغيل كل شيء

**الهدف:** اختبار الكائن `Product` بالكامل (الترحيل، الإضافة، التحديث، الحذف، العرض).

يمكنك استخدام دالة `main` في أي مكان (مثل `Main.java` أو `App.java`) لتشغيل هذه الخطوات:

```java
// Main.java أو App.java
// ستحتاج لتهيئة الاتصال أولًا
import jdbclibrary.database.Connector; 
import jdbclibrary.database.migration.ProductMigration;
import jdbclibrary.controller.ProductController;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // **خطوة إضافية: تهيئة اتصال قاعدة البيانات**
            // تأكد من تعديل تفاصيل الاتصال في jdbclibrary.database.Connector.java
            // يقوم بتهيئة الاتصال عند أول استدعاء
            Connector.getInstance(); 

            // 1. تشغيل عملية الترحيل لإنشاء جدول المنتجات
            ProductMigration.migrate();

            // 2. إنشاء مثيل من المتحكم الخاص بالمنتجات
            ProductController controller = new ProductController();

            // 3. إضافة منتجات جديدة
            controller.createProduct("Laptop Pro", 1200.00, "Powerful laptop for professionals", 50);
            controller.createProduct("Wireless Mouse", 25.50, "Ergonomic wireless mouse", 200);
            controller.createProduct("Mechanical Keyboard", 150.00, "Tactile gaming keyboard", 75);

            // 4. عرض جميع المنتجات
            controller.printAllProducts();

            // 5. تحديث منتج (مثلاً، تحديث سعر المخزون لـ Laptop Pro الذي نفترض أن معرفه هو 1)
            // ستحتاج لمعرفة الـ ID، في تطبيق حقيقي قد تسترجع البيانات أولاً
            System.out.println("\n--- Updating Product ---");
            // تحديث السعر والمخزون فقط
            controller.updateProduct(1, null, 1150.00, null, 45); 

            // 6. عرض المنتج المحدث
            controller.printProductById(1);

            // 7. عرض المنتجات التي سعرها 1150.0 (مثال على getWhere)
            // قيمة السعر بعد التحديث
            controller.printProductsWhere("price", "1150.0"); 

            // 8. حذف منتج (مثلاً، Wireless Mouse الذي نفترض أن معرفه هو 2)
            System.out.println("\n--- Deleting Product ---");
            controller.deleteProduct(2);

            // 9. عرض جميع المنتجات مرة أخرى للتأكد من الحذف
            controller.printAllProducts();

        } catch (SQLException e) {
            System.err.println("A database error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) { // لالتقاط أي استثناءات أخرى قد تحدث
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

---

### ✅ ملخص الخطوات لإنشاء أي كائن جديد

| الخطوة      | الوصف                                                               |
| :---------- | :------------------------------------------------------------------ |
| 🛠️ **Migration** | أنشئ ملف Migration جديد (مثل `ProductMigration.java`) وحدد هيكل الجدول وأعمدته باستخدام `TableSchema.create()`. |
| 🧱 **Model** | أنشئ ملف Model جديد (مثل `ProductModel.java`) يرث من `AbstractModel`، وحدد خصائص الأعمدة فيه باستخدام `HashMap<String, ColumnInfo>`. |
| 🧭 **Controller** | أنشئ ملف Controller جديد (مثل `ProductController.java`) يحتوي على دوال للتعامل مع عمليات CRUD (إنشاء، تحديث، حذف، قراءة) باستخدام مثيل الـ Model الخاص به. |
| 🧪 **Test/Run** | في دالة `main` أو نقطة دخول التطبيق، استدعاء `Connector.getInstance()` لتهيئة الاتصال، ثم `Migration.migrate()` مرة واحدة، ثم أنشئ مثيلًا من الـ Controller واستدعِ دواله لتنفيذ العمليات. |

---

آمل أن يكون هذا التنسيق في ملف Markdown مفيدًا جدًا لك! هل هناك أي جوانب أخرى تود توضيحها أو إضافة المزيد من الأمثيلات عليها؟