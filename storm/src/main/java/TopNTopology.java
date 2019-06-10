import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import java.util.* ;
public class TopNTopology
{
    public static void main (String [] args) throws Exception
    {
        TopologyBuilder builder = new TopologyBuilder() ;
        TweetSpout tweetSpout = new TweetSpout("qAxHmlleyCvuUTjc6NbNyvMjq" ,"Y4eqJVYIBaYoBlA3kRU7XZQsLYm5DMTFx72q2ds7WY0ZtzRxdh" ,"436434806-Sv3p6WzXtCf1JqH0IFWPIGg0Cn1qhEi1dGhXHosp" , "oPgjhj55myRZgwpxaJdUskMnPl4w5ykcMK3C1lsXWQ5PJ" ) ;

        builder.setSpout("tweet-spout", tweetSpout, 1);
        builder.setBolt("parse-tweet-bolt", new ParseTweetBolt(), 10).shuffleGrouping("tweet-spout");
        builder.setBolt("count-bolt", new CountingObjectsBolt(), 15).fieldsGrouping("parse-tweet-bolt", new Fields("tweet-word"));
        builder.setBolt("rolling-count-bolt", new RollingBolt(30, 10), 1).fieldsGrouping("parse-tweet-bolt", new Fields("tweet-word"));
        builder.setBolt("intermediate-ranker", new IntermediateRanking(10 , 10), 4).fieldsGrouping("count-bolt", new Fields("word"));
        builder.setBolt("total-ranker", new TotalRanking(10 , 10)).fieldsGrouping("intermediate-ranker" , new Fields());
        builder.setBolt("report-bolt", new ResultBolt(), 1).fieldsGrouping("total-ranker" , new Fields());

//        builder.setSpout("tweet-spout", tweetSpout, 1);
//        builder.setBolt("parse-bolt" , new ParseTweetBolt()).shuffleGrouping("tweet-spout") ;
//        builder.setBolt("count-bolt" , new CountingObjectsBolt()).fieldsGrouping("parse-bolt" , new Fields("tweet-word")) ;
//        builder.setBolt("rolling-count-bolt", new RollingBolt(30, 10), 1).fieldsGrouping("parse-bolt", new Fields("tweet-word"));
//        builder.setBolt("intermediate-ranker", new IntermediateRanking(10 , 10), 4).fieldsGrouping("count-bolt", new Fields("word"));
//        builder.setBolt("total-ranker", new TotalRanking(10 , 10)).globalGrouping("intermediate-ranker");
//        builder.setBolt("report-bolt", new ResultBolt(), 1).globalGrouping("total-ranker");
//
        Config config = new Config() ;
        config.setDebug(true);

        LocalCluster cluster = new LocalCluster() ;

        cluster.submitTopology("tweet-word-count" , config , builder.createTopology());

        Thread.sleep(30000);

        cluster.killTopology("tweet-word-count");

        cluster.shutdown();

    }
}
