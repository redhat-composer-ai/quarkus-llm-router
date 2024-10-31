package com.redhat.composer.model.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.langchain4j.rag.content.Content;


public class ContentResponse {
    
    @JsonProperty("content")
    private List<ContentWrapper> contentWrapperList = new ArrayList<>();


    class ContentWrapper {
        private Map<String, Object> metadata;
        private String text;

        public ContentWrapper(Map<String, Object> metadata, String text){
          this.metadata = metadata;
          this.text = text;
        }

        public Map<String, Object> getMetadata(){
            return metadata;
        }

        public String getText(){
            return text;
        }
    }

    public ContentResponse() {

    }

    public ContentResponse(List<Content> contentList) {
        contentList.forEach(content ->
        contentWrapperList.add(new ContentWrapper(content.textSegment().metadata().toMap(), content.textSegment().text())));
    }

    public List<ContentWrapper> getContentWrapperList() {
        return contentWrapperList;
    }


}
