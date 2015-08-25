package Clawer;

import java.util.BitSet;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class SimpleBloomFilter {
	
	// Length is 2e24-1=33554432
	private static final int DEFAULT_SIZE=2<<24;
	private static final int[] seeds=new int[]{7,11,13,31,37,61};
	private BitSet bits=new BitSet(DEFAULT_SIZE);
	private SimpleHash [] func=new SimpleHash[seeds.length];
	
	public static void Test()
	{
		String valueString="baidu.com";
		SimpleBloomFilter filter=new SimpleBloomFilter();
		filter.add(valueString);
		boolean ifExist=filter.contains(valueString);
	}

	public SimpleBloomFilter()
	{
		for(int i=0;i<seeds.length;i++)
		{
			func[i]=new SimpleHash (DEFAULT_SIZE, seeds[i]);
		}
	}
	
	public void add(CrawlUrl value)
	{
		if(value!=null)
		{
			add(value.oriUrl);
		}
	}
	
	public void add(String value)
	{
		for(SimpleHash f:func)
		{
			//Set the bit to true
			bits.set(f.Hash(value),true);
		}
	}
	
	public boolean contains(CrawlUrl value)
	{
		return contains(value.oriUrl);
	}
	
	public boolean contains(String value)
	{
		if(value==null)
		{
			return false;
		}
		boolean ret=true;
		for(SimpleHash f:func)
		{
			ret=ret&&bits.get(f.Hash(value));
		}
			
		return ret;
	}
}
