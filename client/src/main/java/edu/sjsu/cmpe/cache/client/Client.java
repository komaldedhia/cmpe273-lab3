package edu.sjsu.cmpe.cache.client;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Client {

    public static void main(String[] args) throws Exception {
    	
    	Collection<String> nodes= new ArrayList<String>();
    	nodes.add("http://localhost:3000");
    	nodes.add("http://localhost:3001");
    	nodes.add("http://localhost:3002");
    	
    	HashMap<String,String> input = new HashMap<String,String>();
    	input.put("1","a");
    	input.put("2","b");
    	input.put("3","c");
    	input.put("4","d");
    	input.put("5","e");
    	input.put("6","f");
    	input.put("7","g");
    	input.put("8","h");
    	input.put("9","i");
    	input.put("10","j");
    	
    	ConsistenetHash<String> conshash = new ConsistenetHash<String>(3,nodes);
        System.out.println("Starting Cache Client...");
        
        Iterator<Entry<String, String>> itput = input.entrySet().iterator();
        try {
        while (itput.hasNext()) {
        	Entry<String, String> mapEntry = itput.next();
        	String key = mapEntry.getKey(); 
        	String node = conshash.get(key);
        	System.out.println("Node "+node+" For key "+key );
        	CacheServiceInterface cache = new DistributedCacheService(
        			node);
        	cache.put(Long.parseLong(key),mapEntry.getValue());
        	System.out.println("put("+key+" => "+mapEntry.getValue()+")");
        }
        
        Iterator<Entry<String, String>> itget = input.entrySet().iterator();
        while (itget.hasNext()) {
        	String key = itget.next().getKey(); 
        	String node = conshash.get(key);
        	System.out.println("Node "+node+" For key "+key );
        	CacheServiceInterface cache = new DistributedCacheService(
        			node);
        	String value = cache.get(Long.parseLong(key));
            System.out.println("get("+key+") => " + value);
        }
              

        System.out.println("Existing Cache Client...");
    } catch (NumberFormatException nfe) {
        System.out.println("NumberFormatException: " + nfe.getMessage());
     }
    }

}
