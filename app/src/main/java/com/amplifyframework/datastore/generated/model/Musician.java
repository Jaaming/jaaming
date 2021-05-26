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

/** This is an auto generated class representing the Musician type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Musicians")
@Index(name = "band", fields = {"bandId"})
public final class Musician implements Model {
  public static final QueryField ID = field("Musician", "id");
  public static final QueryField FIRST_NAME = field("Musician", "firstName");
  public static final QueryField LAST_NAME = field("Musician", "lastName");
  public static final QueryField EMAIL = field("Musician", "email");
  public static final QueryField VOCALIST = field("Musician", "vocalist");
  public static final QueryField INSTRUMENTS = field("Musician", "instruments");
  public static final QueryField GENRES = field("Musician", "genres");
  public static final QueryField BAND = field("Musician", "bandId");
  public static final QueryField BIO = field("Musician", "bio");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String firstName;
  private final @ModelField(targetType="String", isRequired = true) String lastName;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean vocalist;
  private final @ModelField(targetType="String", isRequired = true) String instruments;
  private final @ModelField(targetType="String", isRequired = true) String genres;
  private final @ModelField(targetType="Band") @BelongsTo(targetName = "bandId", type = Band.class) Band band;
  private final @ModelField(targetType="String", isRequired = true) String bio;
  public String getId() {
      return id;
  }
  
  public String getFirstName() {
      return firstName;
  }
  
  public String getLastName() {
      return lastName;
  }
  
  public String getEmail() {
      return email;
  }
  
  public Boolean getVocalist() {
      return vocalist;
  }
  
  public String getInstruments() {
      return instruments;
  }
  
  public String getGenres() {
      return genres;
  }
  
  public Band getBand() {
      return band;
  }
  
  public String getBio() {
      return bio;
  }
  
  private Musician(String id, String firstName, String lastName, String email, Boolean vocalist, String instruments, String genres, Band band, String bio) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.vocalist = vocalist;
    this.instruments = instruments;
    this.genres = genres;
    this.band = band;
    this.bio = bio;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Musician musician = (Musician) obj;
      return ObjectsCompat.equals(getId(), musician.getId()) &&
              ObjectsCompat.equals(getFirstName(), musician.getFirstName()) &&
              ObjectsCompat.equals(getLastName(), musician.getLastName()) &&
              ObjectsCompat.equals(getEmail(), musician.getEmail()) &&
              ObjectsCompat.equals(getVocalist(), musician.getVocalist()) &&
              ObjectsCompat.equals(getInstruments(), musician.getInstruments()) &&
              ObjectsCompat.equals(getGenres(), musician.getGenres()) &&
              ObjectsCompat.equals(getBand(), musician.getBand()) &&
              ObjectsCompat.equals(getBio(), musician.getBio());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFirstName())
      .append(getLastName())
      .append(getEmail())
      .append(getVocalist())
      .append(getInstruments())
      .append(getGenres())
      .append(getBand())
      .append(getBio())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Musician {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("firstName=" + String.valueOf(getFirstName()) + ", ")
      .append("lastName=" + String.valueOf(getLastName()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("vocalist=" + String.valueOf(getVocalist()) + ", ")
      .append("instruments=" + String.valueOf(getInstruments()) + ", ")
      .append("genres=" + String.valueOf(getGenres()) + ", ")
      .append("band=" + String.valueOf(getBand()) + ", ")
      .append("bio=" + String.valueOf(getBio()))
      .append("}")
      .toString();
  }
  
  public static FirstNameStep builder() {
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
  public static Musician justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Musician(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      firstName,
      lastName,
      email,
      vocalist,
      instruments,
      genres,
      band,
      bio);
  }
  public interface FirstNameStep {
    LastNameStep firstName(String firstName);
  }
  

  public interface LastNameStep {
    EmailStep lastName(String lastName);
  }
  

  public interface EmailStep {
    VocalistStep email(String email);
  }
  

  public interface VocalistStep {
    InstrumentsStep vocalist(Boolean vocalist);
  }
  

  public interface InstrumentsStep {
    GenresStep instruments(String instruments);
  }
  

  public interface GenresStep {
    BioStep genres(String genres);
  }
  

  public interface BioStep {
    BuildStep bio(String bio);
  }
  

  public interface BuildStep {
    Musician build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep band(Band band);
  }
  

  public static class Builder implements FirstNameStep, LastNameStep, EmailStep, VocalistStep, InstrumentsStep, GenresStep, BioStep, BuildStep {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean vocalist;
    private String instruments;
    private String genres;
    private String bio;
    private Band band;
    @Override
     public Musician build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Musician(
          id,
          firstName,
          lastName,
          email,
          vocalist,
          instruments,
          genres,
          band,
          bio);
    }
    
    @Override
     public LastNameStep firstName(String firstName) {
        Objects.requireNonNull(firstName);
        this.firstName = firstName;
        return this;
    }
    
    @Override
     public EmailStep lastName(String lastName) {
        Objects.requireNonNull(lastName);
        this.lastName = lastName;
        return this;
    }
    
    @Override
     public VocalistStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public InstrumentsStep vocalist(Boolean vocalist) {
        Objects.requireNonNull(vocalist);
        this.vocalist = vocalist;
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
     public BuildStep bio(String bio) {
        Objects.requireNonNull(bio);
        this.bio = bio;
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
    private CopyOfBuilder(String id, String firstName, String lastName, String email, Boolean vocalist, String instruments, String genres, Band band, String bio) {
      super.id(id);
      super.firstName(firstName)
        .lastName(lastName)
        .email(email)
        .vocalist(vocalist)
        .instruments(instruments)
        .genres(genres)
        .bio(bio)
        .band(band);
    }
    
    @Override
     public CopyOfBuilder firstName(String firstName) {
      return (CopyOfBuilder) super.firstName(firstName);
    }
    
    @Override
     public CopyOfBuilder lastName(String lastName) {
      return (CopyOfBuilder) super.lastName(lastName);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder vocalist(Boolean vocalist) {
      return (CopyOfBuilder) super.vocalist(vocalist);
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
     public CopyOfBuilder band(Band band) {
      return (CopyOfBuilder) super.band(band);
    }
  }
  
}
