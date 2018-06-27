import com.github.javafaker.Faker;

import java.sql.Timestamp;

public class ElasticObject {

    private static Faker faker = new Faker();

    private String uuid;
    private String AN;
    private String user;
    private String message;
    private Timestamp timestamp;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setAN(String AN) {
        this.AN = AN;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public String getAN() {
        return AN;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public static ElasticObject genereteObject() {
        ElasticObject eo = new ElasticObject();
        eo.setAN(faker.lorem().paragraph());
        eo.setUuid(faker.idNumber().valid());
        eo.setUser(faker.name().fullName());
        eo.setMessage(faker.lorem().paragraph());
        eo.setTimestamp(new Timestamp(System.currentTimeMillis()));

        return eo;
    }
}
