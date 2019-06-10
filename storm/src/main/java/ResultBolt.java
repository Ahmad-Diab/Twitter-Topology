import helper.Rankable;
import helper.Ranking;
import org.apache.storm.task.*;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.*;

import com.lambdaworks.redis.*;

public class ResultBolt extends BaseRichBolt
{
    transient RedisConnection<String,String> redis;


    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector)
    {
        RedisClient client = new RedisClient("localhost" , 6379) ;
        redis = client.connect() ;
    }

    public void execute(Tuple input)
    {
        Ranking list = (Ranking) input.getValue(0) ;

        for(Rankable r : list.getRanking())
        {
            String word = r.getObject().toString() ;
            Long count = r.getCount() ;
            redis.publish("TopNTopology", word + "|" + Long.toString(count));
            System.out.println(word+" "+count);
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {}
}
