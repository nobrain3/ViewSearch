package kr.co.kjworld.viewsearch.data.response.data;

import java.util.ArrayList;
import java.util.List;

public class BlogData {
    public List<Document> documents;
    public Meta meta;

    public BlogData() {
        documents = new ArrayList<>();
    }
}
