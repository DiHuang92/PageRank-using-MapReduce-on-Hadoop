import java.io.IOException;

import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;


public class TrustMapper extends Mapper<IntWritable, Node, IntWritable, NodeOrDouble> {
    public void map(IntWritable key, Node value, Context context) throws IOException, InterruptedException {
    	context.getCounter(mycounter.MY_COUNTER.NODE_NUM).increment((long)Math.pow(10, 5));
        if (value.outgoingSize() == 0){
    		context.write(key, new NodeOrDouble(value));
    		context.getCounter(mycounter.MY_COUNTER.DANGLING_MASS).increment((long)(value.getPageRank() * Math.pow(10, 5)));
    	}else{
    		double p = value.getPageRank() / value.outgoingSize();
        	context.write(key, new NodeOrDouble(value));
        	for (int i : value.outgoing){
        		context.write(new IntWritable(i), new NodeOrDouble(p)); 
        	}
    	}
    }
}
