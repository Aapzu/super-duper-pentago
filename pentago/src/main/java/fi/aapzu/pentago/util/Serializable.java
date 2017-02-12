package fi.aapzu.pentago.util;

/**
 * Interface for classes which can be serialized into Strings.
 */
public interface Serializable {

    /**
     * Serializes the Object into a String.
     *
     * @return serialization
     */
    String serialize();

    /**
     * Deserializes the Object and modifies it to respond the state of serializationString.
     *
     * @param serializationString string to deserialize from
     * @return true if succeeded, else false
     */
    boolean deserialize(String serializationString);
}
