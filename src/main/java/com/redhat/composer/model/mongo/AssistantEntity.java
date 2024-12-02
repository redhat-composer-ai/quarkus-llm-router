package com.redhat.composer.model.mongo;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;


@MongoEntity(collection = "assistant")
public class AssistantEntity extends BaseEntity
{

  String name;
  String description;
  String displayName;
  String userPrompt;


  List<String> exampleQuestions;

  ObjectId llmConnectionId;
  ObjectId retrieverConnectionId;

  public AssistantEntity()
  {
  }

  public AssistantEntity(String name, String description, String displayName, String userPrompt,
                         List<String> exampleQuestions, ObjectId llmConnectionId, ObjectId retrieverConnectionId)
  {
    this.name = name;
    this.description = description;
    this.displayName = displayName;
    this.userPrompt = userPrompt;
    this.exampleQuestions = exampleQuestions;
    this.llmConnectionId = llmConnectionId;
    this.retrieverConnectionId = retrieverConnectionId;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getDisplayName()
  {
    return this.displayName;
  }

  public void setDisplayName(String displayName)
  {
    this.displayName = displayName;
  }


  public List<String> getExampleQuestions()
  {
    return exampleQuestions;
  }

  public void setExampleQuestions(List<String> exampleQuestions)
  {
    this.exampleQuestions = exampleQuestions;
  }

  public String getUserPrompt()
  {
    return userPrompt;
  }

  public void setUserPrompt(String userPrompt)
  {
    this.userPrompt = userPrompt;
  }

  public ObjectId getLlmConnectionId()
  {
    return this.llmConnectionId;
  }

  public void setLlmConnectionId(ObjectId llmConnectionId)
  {
    this.llmConnectionId = llmConnectionId;
  }

  public ObjectId getRetrieverConnectionId()
  {
    return this.retrieverConnectionId;
  }

  public void setRetrieverConnectionId(ObjectId retrieverConnectionId)
  {
    this.retrieverConnectionId = retrieverConnectionId;
  }

  public AssistantEntity name(String name)
  {
    setName(name);
    return this;
  }

  public AssistantEntity description(String description)
  {
    setDescription(description);
    return this;
  }

  public AssistantEntity displayName(String displayName)
  {
    setDisplayName(displayName);
    return this;
  }

  public AssistantEntity userPrompt(String userPrompt)
  {
    setUserPrompt(userPrompt);
    return this;
  }

  public AssistantEntity exampleQuestions(List<String> exampleQuestions)
  {
    setExampleQuestions(exampleQuestions);
    return this;
  }


  public AssistantEntity llmConnectionId(ObjectId llmConnectionId)
  {
    setLlmConnectionId(llmConnectionId);
    return this;
  }

  public AssistantEntity retrieverConnectionId(ObjectId retrieverConnectionId)
  {
    setRetrieverConnectionId(retrieverConnectionId);
    return this;
  }

  @Override
  public boolean equals(Object o)
  {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(name, description, displayName, userPrompt, exampleQuestions, llmConnectionId,
        retrieverConnectionId);
  }

  @Override
  public String toString()
  {
    return "{" +
        " name='" + getName() + "'" +
        ", description='" + getDescription() + "'" +
        ", displayName='" + getDisplayName() + "'" +
        ", userPrompt='" + getUserPrompt() + "'" +
        ", exampleQuestions='" + getExampleQuestions() + "'" +
        ", llmConnectionId='" + getLlmConnectionId() + "'" +
        ", retrieverConnectionId='" + getRetrieverConnectionId() + "'" +
        "}";
  }

}
