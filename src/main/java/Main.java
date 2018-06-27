import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static RestHighLevelClient client;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        int cores = Runtime.getRuntime().availableProcessors() - 1;
        ExecutorService service = Executors.newFixedThreadPool(cores);

        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

        for(int i = 0; i < cores; i++){
            service.execute(new ElasticWriter());
        }

        service.shutdown();
    }

    static class ElasticWriter implements Runnable {

        @Override
        public void run() {
            long startTime2 = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                try {
                    IndexRequest request = new IndexRequest("time", "test");
                    String json = objectMapper.writeValueAsString(ElasticObject.genereteObject());
                    request.source(json, XContentType.JSON);
                    long startTime = System.currentTimeMillis();
                    client.index(request);
                    long index_time = System.currentTimeMillis() - startTime;

                    request = new IndexRequest("index-time", "test");
                    IndexTime indexTime = new IndexTime();
                    indexTime.setIndex_time(index_time);
                    indexTime.setTimestamp(new Timestamp(System.currentTimeMillis()));

                    json = objectMapper.writeValueAsString(indexTime);
                    request.source(json, XContentType.JSON);
                    client.index(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread - " + Thread.currentThread().getName() + " - " + i);
            }
            System.out.println("Total time = " + (System.currentTimeMillis() - startTime2));
        }
    }
}
