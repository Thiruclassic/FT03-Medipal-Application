package iss.medipal.util;




import iss.medipal.MediPalApplication;
import iss.medipal.helper.DatabaseHandler;

/**
 * Created by Thirumal on 2/24/17.
 */

public class DatabaseUtility
{
    static DatabaseHandler dbHandler;
    public static DatabaseHandler getDatabaseHandler()
    {
     if(dbHandler==null) {

         dbHandler=new DatabaseHandler(MediPalApplication.getAppContext());
     }
        return dbHandler;
    }
}
