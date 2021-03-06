package com.amplifyframework.datastore.generated.model;

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

/** This is an auto generated class representing the Genre type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Genres")
@Index(name = "musician", fields = {"musicianId"})
@Index(name = "band", fields = {"bandId"})
public final class Genre implements Model {
  public static final QueryField ID = field("Genre", "id");
  public static final QueryField NAME = field("Genre", "name");
  public static final QueryField MUSICIAN = field("Genre", "musicianId");
  public static final QueryField BAND = field("Genre", "bandId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Musician") @BelongsTo(targetName = "musicianId", type = Musician.class) Musician musician;
  private final @ModelField(targetType="Band") @BelongsTo(targetName = "bandId", type = Band.class) Band band;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public Musician getMusician() {
      return musician;
  }
  
  public Band getBand() {
      return band;
  }
  
  private Genre(String id, String name, Musician musician, Band band) {
    this.id = id;
    this.name = name;
    this.musician = musician;
    this.band = band;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Genre genre = (Genre) obj;
      return ObjectsCompat.equals(getId(), genre.getId()) &&
              ObjectsCompat.equals(getName(), genre.getName()) &&
              ObjectsCompat.equals(getMusician(), genre.getMusician()) &&
              ObjectsCompat.equals(getBand(), genre.getBand());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getMusician())
      .append(getBand())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Genre {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("musician=" + String.valueOf(getMusician()) + ", ")
      .append("band=" + String.valueOf(getBand()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static Genre justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Genre(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      musician,
      band);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Genre build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep musician(Musician musician);
    BuildStep band(Band band);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private Musician musician;
    private Band band;
    @Override
     public Genre build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Genre(
          id,
          name,
          musician,
          band);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep musician(Musician musician) {
        this.musician = musician;
        return this;
    }
    
    @Override
     public BuildStep band(Band band) {
        this.band = band;
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
    private CopyOfBuilder(String id, String name, Musician musician, Band band) {
      super.id(id);
      super.name(name)
        .musician(musician)
        .band(band);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder musician(Musician musician) {
      return (CopyOfBuilder) super.musician(musician);
    }
    
    @Override
     public CopyOfBuilder band(Band band) {
      return (CopyOfBuilder) super.band(band);
    }
  }
  
}
