import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.StringTokenizer;

public class ParseTweetBolt extends BaseRichBolt {
    OutputCollector collector ;

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector)
    {
        this.collector = collector ;
    }

    public void execute(Tuple input)
    {
        String tweet = input.getStringByField("tweet") ;

        StringTokenizer tokenizer = new StringTokenizer(tweet , "[ .,?!@#$%^&*(+)_=/\\\"'|`~{}]+" ) ;

        while(tokenizer.hasMoreTokens()) {
            String s = tokenizer.nextToken() ;
            boolean can = true ;
            for(char c : s.toCharArray())
                can &= ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') ;
            System.out.println(s);
            if(can)collector.emit(new Values(s));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(new Fields("tweet-word"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return super.getComponentConfiguration();
    }
}

