package iss.medipal.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import iss.medipal.constants.DatabaseConstants;
import iss.medipal.model.PersonalBio;


/**
 * Created by Thirumal on 2/21/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context)
    {
        super(context, DatabaseConstants.DATABASE_NAME,
                null, DatabaseConstants.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL(getPersonalBioTableCreateQuery());
        sqLiteDatabase.execSQL(getHealthBioTableCreateQuery());
        sqLiteDatabase.execSQL(getCategoryTableCreateQuery());
        sqLiteDatabase.execSQL(getMedicineTableCreateQuery());
        sqLiteDatabase.execSQL(getMeasurementTableCreateQuery());
        sqLiteDatabase.execSQL(getConsumptionTableCreateQuery());
        sqLiteDatabase.execSQL(getReminderTableCreateQuery());
        sqLiteDatabase.execSQL(getAppointmentTableCreateQuery());
        sqLiteDatabase.execSQL(getICETableCreateQuery());

        insertDefaultCategoryContentData(sqLiteDatabase);




    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseConstants.TABLE_PERSONAL_BIO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseConstants.TABLE_HEALTH_BIO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseConstants.TABLE_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseConstants.TABLE_MEDICINE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseConstants.TABLE_MEASUREMENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseConstants.TABLE_CONSUMPTION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseConstants.TABLE_REMINDER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseConstants.TABLE_APPOINTMENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseConstants.TABLE_ICE);

        onCreate(sqLiteDatabase);

    }


    // Table Create Statements


    // Personal Bio table create statement
    public String getPersonalBioTableCreateQuery()
    {
        StringBuilder builder=new StringBuilder("CREATE TABLE ");
        builder.append(DatabaseConstants.TABLE_PERSONAL_BIO);
        builder.append("(");
        builder.append("ID"+" INTEGER PRIMARY KEY AUTOINCREMENT,");
        builder.append("Name"+" VARCHAR(100) NOT NULL,");
        builder.append("DOB"+" DATETIME NOT NULL,");
        builder.append("IDNo"+" VARCHAR(20) NOT NULL,");
        builder.append("Address"+" VARCHAR(100) NOT NULL,");
        builder.append("PostalCode"+" VARCHAR(10) NOT NULL,");
        builder.append("Height"+" INTEGER,");
        builder.append("BloodType"+" VARCHAR(10) NOT NULL");
        builder.append(")");

        return builder.toString();
    }


    // Health Bio table create statement
    public String getHealthBioTableCreateQuery()
    {
        StringBuilder builder=new StringBuilder("CREATE TABLE ");
        builder.append(DatabaseConstants.TABLE_HEALTH_BIO);
        builder.append("(");
        builder.append("ID"+" INTEGER PRIMARY KEY AUTOINCREMENT,");
        builder.append("Condition"+" VARCHAR(255) NOT NULL,");
        builder.append("StartDate"+" DATETIME NOT NULL,");
        builder.append("ConditionType"+" VARCHAR(1) NOT NULL");
        builder.append(")");

        return builder.toString();
    }


    // Category table create statement
    public String getCategoryTableCreateQuery()
    {
        StringBuilder builder=new StringBuilder("CREATE TABLE ");
        builder.append(DatabaseConstants.TABLE_CATEGORY);
        builder.append("(");
        builder.append("ID"+" INTEGER PRIMARY KEY AUTOINCREMENT,");
        builder.append("Category"+" VARCHAR(50) NOT NULL,");
        builder.append("Code"+" VARCHAR(5) NOT NULL,");
        builder.append("Description"+" VARCHAR(255) NOT NULL,");
        builder.append("Remind"+" INTEGER NOT NULL");
        builder.append(")");

        return builder.toString();
    }


    // Medicine table create statement
    public String getMedicineTableCreateQuery()
    {
        StringBuilder builder=new StringBuilder("CREATE TABLE ");
        builder.append(DatabaseConstants.TABLE_MEDICINE);
        builder.append("(");
        builder.append("ID"+" INTEGER PRIMARY KEY AUTOINCREMENT,");
        builder.append("Medicine"+" VARCHAR(50) NOT NULL,");
        builder.append("Description"+" VARCHAR(255) ,");
        builder.append("CatID"+" Integer NOT NULL,");
        builder.append("ReminderID"+" INTEGER NOT NULL,");
        builder.append("Remind"+" INTEGER NOT NULL,");
        builder.append("Quantity"+" INTEGER NOT NULL,");
        builder.append("Dosage"+" INTEGER NOT NULL,");
        builder.append("DateIssued"+" DATETIME NOT NULL,");
        builder.append("ExpireFactor"+" INTEGER NOT NULL,");
        builder.append("Threshold"+" INTEGER ");
        builder.append(")");

        return builder.toString();
    }


    // MEASUREMENT table create statement
    public String getMeasurementTableCreateQuery()
    {
        StringBuilder builder=new StringBuilder("CREATE TABLE ");
        builder.append(DatabaseConstants.TABLE_MEASUREMENT);
        builder.append("(");
        builder.append("ID"+" INTEGER PRIMARY KEY AUTOINCREMENT,");
        builder.append("Systolic"+" INTEGER NOT NULL,");
        builder.append("Diastolic"+" INTEGER, ");
        builder.append("Pulse"+" Integer ");
        builder.append("Temperature"+" VARCHAR(5) ,");
        builder.append("Weight"+" INTEGER ,");
        builder.append("MeasuredOn"+" DATETIME ");
        builder.append(")");

        return builder.toString();
    }

    // Consumption table create statement
    public String getConsumptionTableCreateQuery()
    {
        StringBuilder builder=new StringBuilder("CREATE TABLE ");
        builder.append(DatabaseConstants.TABLE_CONSUMPTION);
        builder.append("(");
        builder.append("ID"+" INTEGER PRIMARY KEY AUTOINCREMENT,");
        builder.append("MedicineId"+" INTEGER NOT NULL,");
        builder.append("Quantity"+" INTEGER NOT NULL,");
        builder.append("ConsumedOn"+" DATETIME NOT NULL");
        builder.append(")");

        return builder.toString();
    }


    // REMINDER table create statement
    public String getReminderTableCreateQuery()
    {
        StringBuilder builder=new StringBuilder("CREATE TABLE ");
        builder.append(DatabaseConstants.TABLE_REMINDER);
        builder.append("(");
        builder.append("ID"+" INTEGER PRIMARY KEY AUTOINCREMENT,");
        builder.append("Frequency"+" INTEGER NOT NULL,");
        builder.append("StartTime"+" DATETIME NOT NULL,");
        builder.append("Interval"+" INTEGER NOT NULL");
        builder.append(")");

        return builder.toString();
    }


    // Appointment table create statement
    public String getAppointmentTableCreateQuery()
    {
        StringBuilder builder=new StringBuilder("CREATE TABLE ");
        builder.append(DatabaseConstants.TABLE_APPOINTMENT);
        builder.append("(");
        builder.append("ID"+" INTEGER PRIMARY KEY AUTOINCREMENT,");
        builder.append("Location"+" Varchar(100) NOT NULL,");
        builder.append("Appointment"+" DATETIME NOT NULL,");
        builder.append("Description"+" Varchar(255) NOT NULL");
        builder.append(")");

        return builder.toString();
    }

    // IncaseOfEmergencyContact table create statement
    public String getICETableCreateQuery()
    {
        StringBuilder builder=new StringBuilder("CREATE TABLE ");
        builder.append(DatabaseConstants.TABLE_ICE);
        builder.append("(");
        builder.append("ID"+" INTEGER PRIMARY KEY AUTOINCREMENT,");
        builder.append("Name"+" Varchar(100) NOT NULL,");
        builder.append("ContactNo"+" Varchar(20) NOT NULL,");
        builder.append("ContactType"+" INTEGER NOT NULL,");
        builder.append("Description"+" INTEGER");
        builder.append(")");

        return builder.toString();
    }





    // Insert Personal Bio Data
    // Sample code written in handler to test from activity directly.
    public long createPersonalBio(PersonalBio bio)
    {
        SQLiteDatabase database=this.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("Name",bio.getName());
        values.put("DOB",bio.getDob().toString());
        values.put("IDNo",bio.getIdNo());
        values.put("Address",bio.getAddress());
        values.put("PostalCode",bio.getPostalCode());
        values.put("Height",bio.getHeight());
        values.put("BloodType",bio.getBloodType());

        long id=database.insert(DatabaseConstants.TABLE_PERSONAL_BIO,null,values);
        return id;

    }





    public void insertDefaultCategoryContentData(SQLiteDatabase sqLiteDatabase)
    {

        String[] categoryDataArr=getDefaultCategoryData();


        for(String categoryData:categoryDataArr)
        {
            String[] categoryAttr=categoryData.split("\\|");

            ContentValues values=new ContentValues();
            values.put("Code",categoryAttr[0]);
            values.put("Category",categoryAttr[1]);
            values.put("Description",categoryAttr[2]);
            values.put("Remind",categoryAttr[3]);

            sqLiteDatabase.insert(DatabaseConstants.TABLE_CATEGORY,null,values);

        }

    }
    public String[] getDefaultCategoryData()
    {
        String[] categoryDataList=new String[5];
        categoryDataList[0]="SUP|Supplement|User may opt to set reminder for consumption of supplement|true";
        categoryDataList[1]="CHR|Chronic|This is to categorise medication for long term/life-time consumption for diseases i.e. diabetes, hypertension, heart regulation, etc.|true";
        categoryDataList[2]="INC|Incidental|For Common Cold,flu or Symptions happen to be unplanned or subordinate conjunction with something and prescriptioin from general practitioners.|true";
        categoryDataList[3]="COM|Complete Course|This may applies to medication like antibiotics for sinus infection,pneumonia bronchitis, acne, step throat,cellulitis, etc.|true";
        categoryDataList[4]="SEL|Self Apply|To note down any self-prescribed or consume medication, i.e. applying band aids, balms, etc.|true";

        return  categoryDataList;
    }





}
