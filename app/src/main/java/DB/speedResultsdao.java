package DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import Java.speedResults;

import java.util.List;

@Dao
public interface speedResultsdao {
    @Insert
    void insert(Java.speedResults... speedResult);
    @Update
    void update(Java.speedResults... speedResult);
    @Delete
    void delete(Java.speedResults... speedResult);
    @Query("SELECT * FROM "+ AppDatabase.RESULTS_TABLE + " ORDER BY id ASC")
    List<speedResults> getSpeedResults();
    @Query("SELECT * FROM "+ AppDatabase.RESULTS_TABLE + " WHERE id = :uid")
    Java.speedResults getSpeedResultById(int uid);
}
