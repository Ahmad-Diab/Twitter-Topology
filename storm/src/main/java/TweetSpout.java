import org.apache.storm.Config;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class TweetSpout extends BaseRichSpout
{
    // Twitter API authentication credentials
    String custkey, custsecret , accesstoken , accesssecret;

    SpoutOutputCollector collector;

    // Twitter4j - twitter stream to get tweets
    TwitterStream twitterStream;

    // Shared queue for getting buffering tweets received
    LinkedBlockingQueue<String> queue;

    private class TweetListener implements StatusListener
    {

        // Implement the callback function when a tweet arrives
        public void onStatus(Status status)
        {
            // add the tweet into the queue buffer
            queue.offer(status.getText());
        }

        public void onDeletionNotice(StatusDeletionNotice sdn) {}

        public void onTrackLimitationNotice(int i) {}

        public void onScrubGeo(long l, long l1) {}

        public void onStallWarning(StallWarning warning) {}

        public void onException(Exception e) {}
    }

    TweetSpout(String key, String secret, String token,String tokensecret)
    {
        custkey = key;
        custsecret = secret;
        accesstoken = token;
        accesssecret = tokensecret;
    }

    public void open(Map  map,TopologyContext topologyContext, SpoutOutputCollector    spoutOutputCollector)
    {
        // create the buffer to block tweets
        queue = new LinkedBlockingQueue<String>(1000);

        // save the output collector for emitting tuples
        collector = spoutOutputCollector;


        // build the config with credentials for twitter 4j
        ConfigurationBuilder config = new ConfigurationBuilder().setOAuthConsumerKey(custkey).setOAuthConsumerSecret(custsecret).setOAuthAccessToken(accesstoken).setOAuthAccessTokenSecret(accesssecret);

        // create the twitter stream factory with the config
        TwitterStreamFactory fact = new TwitterStreamFactory(config.build());

        // get an instance of twitter stream
        twitterStream = fact.getInstance();

        // provide the handler for twitter stream
        twitterStream.addListener(new TweetListener());

        // start the sampling of tweets
        twitterStream.sample();
    }

    public void nextTuple()
    {
        String ret = queue.poll() ;

        if(ret == null)
        {
            try
            {
                Thread.sleep(50);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return;
        }
        collector.emit(new Values(ret));
    }

    @Override
    public Map<String, Object> getComponentConfiguration()
    {
        Config ret = new Config();
        ret.setMaxTaskParallelism(1);

        return ret;
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(new Fields("tweet"));
    }

    @Override
    public void close()
    {
        // shutdown the stream - when we are going to exit
        twitterStream.shutdown();
    }
}

