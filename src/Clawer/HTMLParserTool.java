package Clawer;

import java.io.Console;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.util.Asserts;
import org.htmlparser.*;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.FrameTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;

public class HTMLParserTool {

	public static Set<String> ExtractLinks(String URL, LinkFilter filter) {
		Set<String> links = new HashSet<String>();
		try {
			Parser parser = new Parser(URL);
			parser.setEncoding("utf-8");

			Parser parserMeta=new Parser(URL);
			parserMeta.setEncoding("utf-8");
			
			// Create a filter for tag <frame>.
			NodeFilter frameFilter = new NodeFilter() {

				@Override
				public boolean accept(Node arg0) {
					if (arg0.getText().startsWith("frame src="))
						return true;
					else
						return false;
				}
			};

			// TODO:Create a tag filter for Metadata to get "Encoding"
			NodeFilter metaFilter = new NodeClassFilter(MetaTag.class);
			NodeList metaList = parserMeta.extractAllNodesThatMatch(metaFilter);
			for (int index = 0; index < metaList.size(); index++) {
				System.out.print(metaList.size() + "/n");
				Node tagNode = metaList.elementAt(index);
				MetaTag metaTag = (MetaTag) tagNode;
				String metaValueString = metaTag.getText();
				int start = metaValueString.indexOf("charset=");
				if (start > 0) {
					metaValueString = metaValueString.substring(start);
					int end = metaValueString.indexOf(" ");
					if (end == -1)
						end = metaValueString.indexOf(">");
					if(end==-1)
						end=metaValueString.length();
					String encodingString = metaValueString.substring(9,
							end - 1);
					break;
				}

			}

			// create a filter for tag <a>,LinkTat stands for <a>
			NodeFilter aNodeFilter = new NodeClassFilter(LinkTag.class);

			OrFilter orFilter = new OrFilter(frameFilter, aNodeFilter);

			NodeList list = parser.extractAllNodesThatMatch(orFilter);
			for (int i = 0; i < list.size(); i++) {
				Node tag = list.elementAt(i);

				// if it's a <a> tag
				if (tag instanceof LinkTag) {
					LinkTag link = (LinkTag) tag;
					String linkUrlString = link.getLink();
					if (filter.accept(linkUrlString))
						links.add(linkUrlString);
				}

				// ifit's a frame
				else {
					String frameTextString = tag.getText();
					int start = frameTextString.indexOf("src=");
					frameTextString = frameTextString.substring(start);
					int end = frameTextString.indexOf(" ");
					if (end == -1)
						end = frameTextString.indexOf(">");
					// 5 comes from http://, end-1 removes tail "
					String frameURLString = frameTextString.substring(5,
							end - 1);

					if (filter.accept(frameURLString))
						links.add(frameURLString);

				}
			}

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return links;
	}

}
