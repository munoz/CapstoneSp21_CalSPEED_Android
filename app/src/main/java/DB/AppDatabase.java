package DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import TypeConverter.DateTypeConverter;
import DB.AppDatabase;
import Java.speedResults;

@Database(entities = {speedResults.class}, version = 1)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String dbName="db-results";
    public static final String RESULTS_TABLE="results";

    public abstract speedResultsdao getspeedResultsdao();

}
