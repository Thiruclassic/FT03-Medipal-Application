package iss.medipal.constants;

/**
 * Created by Naveen on 2/24/17.
 */

public class DBConstants {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME="MediPal.db";

    public static final String TABLE_PERSONAL_BIO="PersonalBio";
    public static final String TABLE_HEALTH_BIO="HealthBio";
    public static final String TABLE_CATEGORY="Category";
    public static final String TABLE_MEDICINE="Medicine";
    public static final String TABLE_MEASUREMENT="Measurement";
    public static final String TABLE_CONSUMPTION="Consumption";
    public static final String TABLE_REMINDER="Reminder";
    public static final String TABLE_APPOINTMENT="Appointment";
    public static final String TABLE_ICE="ICE";

    //PersonBio Table
    public static final String PERSON_NAME="Name";
    public static final String PERSON_DOB="DOB";
    public static final String PERSON_IDNO="IDNo";
    public static final String PERSON_ADDRESS="Address";
    public static final String PERSON_POSTALCODE="PostalCode";
    public static final String PERSON_HEIGHT="Height";
    public static final String PERSON_BLOODTYPE="BloodType";

    //healthBio Table
    public static final String HEALTH_CONDITION="Condition";
    public static final String HEALTH_START_DATE="StartDate";
    public static final String HEALTH_CONDITION_TYPE="ConditionType";

    //Medicine Table
    public static final String MEDICINE_NAME="Medicine";
    public static final String MEDICINE_DESCRIPTION="Description";
    public static final String MEDICINE_DOSAGE="Dosage";
    public static final String MEDICINE_QUATITY="Quantity";
    public static final String MEDICINE_THRESHOLD="Threshold";
    public static final String MEDICINE_REMIND="Remind";
    public static final String MEDICINE_EXPIRY_FACTOR="ExpireFactor";
    public static final String MEDICINE_CATID="CatID";
    public static final String MEDICINE_DATE_ISSUED="DateIssued";
    public static final String MEDICINE_REMINDER_ID ="ReminderID";

    //Appointment Table
    public static final String APP_ID="ID";
    public static final String APP_LOCATION="Location";
    public static final String APP_DESCRIPTION="Description";
    public static final String APP_DATETIME="Appointment";

    //Catogery Table
    public static final String CATEGORY_ID="ID";
    public static final String CATEGORY_NAME="Category";
    public static final String CATEGORY_CODE="Code";
    public static final String CATEGORY_DESCRIPTION="Description";
    public static final String CATEGORY_REMIND="Remind";

    //Reminder Table
    public static final String REMINDER_ID="ID";
    public static final String REMINDER_FREQUENCY="Frequency";
    public static final String REMINDER_START_TIME="StartTime";
    public static final String REMINDER_INTERVAL="Interval";



}
