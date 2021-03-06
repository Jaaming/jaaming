package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Message type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Messages")
public final class Message implements Model {
  public static final QueryField ID = field("Message", "id");
  public static final QueryField RECIPIENT = field("Message", "recipient");
  public static final QueryField CONTENT = field("Message", "content");
  public static final QueryField DATE = field("Message", "date");
  public static final QueryField MUSICIAN = field("Message", "messageMusicianId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String recipient;
  private final @ModelField(targetType="String") String content;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime date;
  private final @ModelField(targetType="Musician") @BelongsTo(targetName = "messageMusicianId", type = Musician.class) Musician musician;
  public String getId() {
      return id;
  }
  
  public String getRecipient() {
      return recipient;
  }
  
  public String getContent() {
      return content;
  }
  
  public Temporal.DateTime getDate() {
      return date;
  }
  
  public Musician getMusician() {
      return musician;
  }
  
  private Message(String id, String recipient, String content, Temporal.DateTime date, Musician musician) {
    this.id = id;
    this.recipient = recipient;
    this.content = content;
    this.date = date;
    this.musician = musician;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Message message = (Message) obj;
      return ObjectsCompat.equals(getId(), message.getId()) &&
              ObjectsCompat.equals(getRecipient(), message.getRecipient()) &&
              ObjectsCompat.equals(getContent(), message.getContent()) &&
              ObjectsCompat.equals(getDate(), message.getDate()) &&
              ObjectsCompat.equals(getMusician(), message.getMusician());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getRecipient())
      .append(getContent())
      .append(getDate())
      .append(getMusician())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Message {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("recipient=" + String.valueOf(getRecipient()) + ", ")
      .append("content=" + String.valueOf(getContent()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("musician=" + String.valueOf(getMusician()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Message justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Message(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      recipient,
      content,
      date,
      musician);
  }
  public interface BuildStep {
    Message build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep recipient(String recipient);
    BuildStep content(String content);
    BuildStep date(Temporal.DateTime date);
    BuildStep musician(Musician musician);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String recipient;
    private String content;
    private Temporal.DateTime date;
    private Musician musician;
    @Override
     public Message build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Message(
          id,
          recipient,
          content,
          date,
          musician);
    }
    
    @Override
     public BuildStep recipient(String recipient) {
        this.recipient = recipient;
        return this;
    }
    
    @Override
     public BuildStep content(String content) {
        this.content = content;
        return this;
    }
    
    @Override
     public BuildStep date(Temporal.DateTime date) {
        this.date = date;
        return this;
    }
    
    @Override
     public BuildStep musician(Musician musician) {
        this.musician = musician;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String recipient, String content, Temporal.DateTime date, Musician musician) {
      super.id(id);
      super.recipient(recipient)
        .content(content)
        .date(date)
        .musician(musician);
    }
    
    @Override
     public CopyOfBuilder recipient(String recipient) {
      return (CopyOfBuilder) super.recipient(recipient);
    }
    
    @Override
     public CopyOfBuilder content(String content) {
      return (CopyOfBuilder) super.content(content);
    }
    
    @Override
     public CopyOfBuilder date(Temporal.DateTime date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder musician(Musician musician) {
      return (CopyOfBuilder) super.musician(musician);
    }
  }
  
}
