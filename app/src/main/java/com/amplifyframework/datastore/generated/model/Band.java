package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;

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

/** This is an auto generated class representing the Band type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Bands")
public final class Band implements Model {
  public static final QueryField ID = field("Band", "id");
  public static final QueryField NAME = field("Band", "name");
  public static final QueryField INSTRUMENTS = field("Band", "instruments");
  public static final QueryField GENRES = field("Band", "genres");
  public static final QueryField BIO = field("Band", "bio");
  public static final QueryField VOCALIST = field("Band", "vocalist");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String instruments;
  private final @ModelField(targetType="String", isRequired = true) String genres;
  private final @ModelField(targetType="Musician") @HasMany(associatedWith = "band", type = Musician.class) List<Musician> musicians = null;
  private final @ModelField(targetType="String", isRequired = true) String bio;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean vocalist;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getInstruments() {
      return instruments;
  }
  
  public String getGenres() {
      return genres;
  }
  
  public List<Musician> getMusicians() {
      return musicians;
  }
  
  public String getBio() {
      return bio;
  }
  
  public Boolean getVocalist() {
      return vocalist;
  }
  
  private Band(String id, String name, String instruments, String genres, String bio, Boolean vocalist) {
    this.id = id;
    this.name = name;
    this.instruments = instruments;
    this.genres = genres;
    this.bio = bio;
    this.vocalist = vocalist;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Band band = (Band) obj;
      return ObjectsCompat.equals(getId(), band.getId()) &&
              ObjectsCompat.equals(getName(), band.getName()) &&
              ObjectsCompat.equals(getInstruments(), band.getInstruments()) &&
              ObjectsCompat.equals(getGenres(), band.getGenres()) &&
              ObjectsCompat.equals(getBio(), band.getBio()) &&
              ObjectsCompat.equals(getVocalist(), band.getVocalist());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getInstruments())
      .append(getGenres())
      .append(getBio())
      .append(getVocalist())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Band {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("instruments=" + String.valueOf(getInstruments()) + ", ")
      .append("genres=" + String.valueOf(getGenres()) + ", ")
      .append("bio=" + String.valueOf(getBio()) + ", ")
      .append("vocalist=" + String.valueOf(getVocalist()))
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
  public static Band justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Band(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      instruments,
      genres,
      bio,
      vocalist);
  }
  public interface NameStep {
    InstrumentsStep name(String name);
  }
  

  public interface InstrumentsStep {
    GenresStep instruments(String instruments);
  }
  

  public interface GenresStep {
    BioStep genres(String genres);
  }
  

  public interface BioStep {
    VocalistStep bio(String bio);
  }
  

  public interface VocalistStep {
    BuildStep vocalist(Boolean vocalist);
  }
  

  public interface BuildStep {
    Band build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements NameStep, InstrumentsStep, GenresStep, BioStep, VocalistStep, BuildStep {
    private String id;
    private String name;
    private String instruments;
    private String genres;
    private String bio;
    private Boolean vocalist;
    @Override
     public Band build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Band(
          id,
          name,
          instruments,
          genres,
          bio,
          vocalist);
    }
    
    @Override
     public InstrumentsStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public GenresStep instruments(String instruments) {
        Objects.requireNonNull(instruments);
        this.instruments = instruments;
        return this;
    }
    
    @Override
     public BioStep genres(String genres) {
        Objects.requireNonNull(genres);
        this.genres = genres;
        return this;
    }
    
    @Override
     public VocalistStep bio(String bio) {
        Objects.requireNonNull(bio);
        this.bio = bio;
        return this;
    }
    
    @Override
     public BuildStep vocalist(Boolean vocalist) {
        Objects.requireNonNull(vocalist);
        this.vocalist = vocalist;
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
    private CopyOfBuilder(String id, String name, String instruments, String genres, String bio, Boolean vocalist) {
      super.id(id);
      super.name(name)
        .instruments(instruments)
        .genres(genres)
        .bio(bio)
        .vocalist(vocalist);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder instruments(String instruments) {
      return (CopyOfBuilder) super.instruments(instruments);
    }
    
    @Override
     public CopyOfBuilder genres(String genres) {
      return (CopyOfBuilder) super.genres(genres);
    }
    
    @Override
     public CopyOfBuilder bio(String bio) {
      return (CopyOfBuilder) super.bio(bio);
    }
    
    @Override
     public CopyOfBuilder vocalist(Boolean vocalist) {
      return (CopyOfBuilder) super.vocalist(vocalist);
    }
  }
  
}
