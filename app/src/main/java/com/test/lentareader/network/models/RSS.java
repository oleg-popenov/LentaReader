package com.test.lentareader.network.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.Date;
import java.util.List;

@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
})

@Root
public class RSS {

    @Attribute
    String version;

    @Element
    Channel channel;

    public Channel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "RSS{" +
                "version='" + version + '\'' +
                ", channel=" + channel +
                '}';
    }

    @Root(strict = false)
    public static class Channel {
        // Tricky part in Simple XML because the link is named twice
        @ElementList(entry = "link", inline = true, required = false)
        public List<Link> links;

        @ElementList(name = "item", required = true, inline = true)
        public List<Item> itemList;


        @Element
        String title;
        @Element
        String language;

        @Element(name = "ttl", required = false)
        int ttl;

        @Element(name = "pubDate", required = false)
        String pubDate;

        @Override
        public String toString() {
            return "Channel{" +
                    "links=" + links +
                    ", itemList=" + itemList +
                    ", title='" + title + '\'' +
                    ", language='" + language + '\'' +
                    ", ttl=" + ttl +
                    ", pubDate='" + pubDate + '\'' +
                    '}';
        }

        public static class Link {
            @Attribute(required = false)
            public String href;

            @Attribute(required = false)
            public String rel;

            @Attribute(name = "type", required = false)
            public String contentType;

            @Text(required = false)
            public String link;
        }

        @Root(name = "item", strict = false)
        public static class Item {

            @Element(name = "title", required = true)
            String title;
            @Element(name = "link", required = true)
            String link;
            @Element(name = "description", required = true)
            String description;
            @Element(name = "author", required = false)
            String author;
            @Element(name = "category", required = false)
            String category;
            @Element(name = "comments", required = false)
            String comments;
            @Element(name = "enclosure", required = false)
            String enclosure;
            @Element(name = "guid", required = false)
            String guid;
            @Element(name = "pubDate", required = false)
            Date pubDate;
            @Element(name = "source", required = false)
            String source;

            @Override
            public String toString() {
                return "Item{" +
                        "title='" + title + '\'' +
                        ", link='" + link + '\'' +
                        ", description='" + description + '\'' +
                        ", author='" + author + '\'' +
                        ", category='" + category + '\'' +
                        ", comments='" + comments + '\'' +
                        ", enclosure='" + enclosure + '\'' +
                        ", guid='" + guid + '\'' +
                        ", pubDate='" + pubDate + '\'' +
                        ", source='" + source + '\'' +
                        '}';
            }

            public String getTitle() {
                return title;
            }

            public String getLink() {
                return link;
            }

            public String getDescription() {
                return description;
            }

            public String getAuthor() {
                return author;
            }

            public String getCategory() {
                return category;
            }

            public String getComments() {
                return comments;
            }

            public String getEnclosure() {
                return enclosure;
            }

            public String getGuid() {
                return guid;
            }

            public Date getPubDate() {
                return pubDate;
            }

            public String getSource() {
                return source;
            }
        }
    }
}
