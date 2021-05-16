package Java;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import DB.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = AppDatabase.RESULTS_TABLE)
public class speedResults implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date date;
    private Double Down;
    private String Network;
    private Double Up;
    private String ping;
    private String jitter;
    private String latitude;
    private String longitude;
    private Double MOS;
    private String stream;
    private String conf;
    private String voip;

    public speedResults() {
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd,yyyy HH:mm");
        this.date = new Date(now);
    }

    protected speedResults(Parcel in) {
        id = in.readInt();
        if (in.readByte() == 0) {
            Down = null;
        } else {
            Down = in.readDouble();
        }
        Network = in.readString();
        if (in.readByte() == 0) {
            Up = null;
        } else {
            Up = in.readDouble();
        }
        ping = in.readString();
        jitter = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        if (in.readByte() == 0) {
            MOS = null;
        } else {
            MOS = in.readDouble();
        }
        stream = in.readString();
        conf = in.readString();
        voip = in.readString();
    }

    public static final Creator<speedResults> CREATOR = new Creator<speedResults>() {
        @Override
        public speedResults createFromParcel(Parcel in) {
            return new speedResults(in);
        }

        @Override
        public speedResults[] newArray(int size) {
            return new speedResults[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public Double getDown() {
        return Down;
    }

    public void setDown(Double down) {
        Down = down;
    }

    public Double getUp() {
        return Up;
    }

    public void setUp(Double up) {
        Up = up;
    }

    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }

    public String getJitter() {
        return jitter;
    }

    public void setJitter(String jitter) {
        this.jitter = jitter;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getMOS() {
        return MOS;
    }

    public void setMOS(Double MOS) {
        this.MOS = MOS;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public String getVoip() {
        return voip;
    }

    public void setVoip(String voip) {
        this.voip = voip;
    }

    public String getNetwork() {
        return Network;
    }

    public void setNetwork(String network) {
        Network = network;
    }

    @Override
    public String toString() {
        return "Java.speedResults{" +
                "id=" + id +
                ", date=" + date +
                ", Down=" + Down +
                ", Network='" + Network + '\'' +
                ", Up=" + Up +
                ", ping='" + ping + '\'' +
                ", jitter='" + jitter + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", MOS=" + MOS +
                ", stream='" + stream + '\'' +
                ", conf='" + conf + '\'' +
                ", voip='" + voip + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        if (Down == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Down);
        }
        dest.writeString(Network);
        if (Up == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Up);
        }
        dest.writeString(ping);
        dest.writeString(jitter);
        dest.writeString(latitude);
        dest.writeString(longitude);
        if (MOS == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(MOS);
        }
        dest.writeString(stream);
        dest.writeString(conf);
        dest.writeString(voip);
    }
}
