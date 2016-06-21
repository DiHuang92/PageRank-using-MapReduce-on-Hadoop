import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;

public class TrustReducer extends Reducer<IntWritable, NodeOrDouble, IntWritable, Node> {
    public void reduce(IntWritable key, Iterable<NodeOrDouble> values, Context context)
        throws IOException, InterruptedException {
    	Node M = new Node(key.get());
    	double s = 0.0;        
        for (NodeOrDouble value : values) {
            if (value.isNode()){            
                M.setOutgoing(value.getNode().outgoing);
            }else{
                s = s + value.getDouble();
            }
        }
        M.pageRank = s;
        context.write(new IntWritable(M.nodeid), M); 
    }
}
