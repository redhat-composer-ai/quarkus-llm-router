package com.redhat.composer.model.request;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

@SuppressWarnings("all")
public class AssistantCreationRequest {

    String id;
    String name;
    String displayName;
    String description;
    String userPrompt;
    List<String> exampleQuestions;
    String llmConnectionId;
    String retrieverConnectionId;

    public AssistantCreationRequest() {
    }

    public AssistantCreationRequest(String name, String displayName, String description, String userPrompt, List<String> exampleQuestions, String llmConnectionId, String retrieverConnectionId) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.userPrompt = userPrompt;
        this.exampleQuestions = exampleQuestions;
        this.llmConnectionId = llmConnectionId;
        this.retrieverConnectionId = retrieverConnectionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }

    public List<String> getExampleQuestions() {
        return exampleQuestions;
    }

    public void setExampleQuestions(List<String> exampleQuestions) {
        this.exampleQuestions = exampleQuestions;
    }

    public String getLlmConnectionId() {
        return this.llmConnectionId;
    }

    public void setLlmConnectionId(String llmConnectionId) {
        this.llmConnectionId = llmConnectionId;
    }

    public String getRetrieverConnectionId() {
        return this.retrieverConnectionId;
    }

    public void setRetrieverConnectionId(String retrieverConnectionId) {
        this.retrieverConnectionId = retrieverConnectionId;
    }


    public AssistantCreationRequest id(String id) {
        setId(id);
        return this;
    }

    public AssistantCreationRequest name(String name) {
        setName(name);
        return this;
    }

    public AssistantCreationRequest displayName(String displayName) {
        setDisplayName(displayName);
        return this;
    }

    public AssistantCreationRequest description(String description) {
        setDescription(description);
        return this;
    }

    public AssistantCreationRequest userPrompt(String userPrompt) {
        setUserPrompt(userPrompt);
        return this;
    }

    public AssistantCreationRequest exampleQuestions(List<String> exampleQuestions) {
        setExampleQuestions(exampleQuestions);
        return this;
    }

    public AssistantCreationRequest llmConnectionId(String llmConnectionId) {
        setLlmConnectionId(llmConnectionId);
        return this;
    }

    public AssistantCreationRequest retrieverConnectionId(String retrieverConnectionId) {
        setRetrieverConnectionId(retrieverConnectionId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, displayName, description, userPrompt, exampleQuestions, llmConnectionId, retrieverConnectionId);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                " name='" + getName() + "'" +
                ", displayName='" + getDisplayName() + "'" +
                ", description='" + getDescription() + "'" +
                ", userPrompt='" + getUserPrompt() + "'" +
                ", exampleQuestions='" + getExampleQuestions() + "'" +
                ", llmConnectionId='" + getLlmConnectionId() + "'" +
                ", retrieverConnectionId='" + getRetrieverConnectionId() + "'" +
                "}";
    }

}