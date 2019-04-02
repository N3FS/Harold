package uk.co.n3fs.mc.haroldplugin.storage;

import uk.co.n3fs.mc.haroldplugin.platform.User;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class Punishment {

    private final UUID uniqueId;
    private final InfractionType type;
    private final User<?> offender;
    private final User<?> creator;
    private final Long timestamp;
    private final Long expiry;
    private final String reason;

    private Punishment(UUID uniqueId, InfractionType type, User<?> offender, User<?> creator, Long timestamp, Long expiry, String reason) {
        Objects.requireNonNull(type, "An infraction type must be specified");
        Objects.requireNonNull(offender, "An offender must be specified");
        this.uniqueId = uniqueId != null ? uniqueId : UUID.randomUUID();
        this.type = type;
        this.offender = offender;
        this.creator = creator;
        this.timestamp = timestamp != null ? timestamp : Instant.now().getEpochSecond();
        this.expiry = expiry;
        this.reason = reason;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public InfractionType getType() {
        return type;
    }

    public User<?> getOffender() {
        return offender;
    }

    public Optional<User<?>> getCreator() {
        return Optional.ofNullable(creator);
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Optional<Long> getExpiry() {
        return Optional.ofNullable(expiry);
    }

    public Optional<String> getReason() {
        return Optional.ofNullable(reason);
    }

    public boolean isActive() {
        return expiry == null || Instant.now().isBefore(Instant.ofEpochSecond(expiry));
    }

    public static InfractionBuilder builder() {
        return new InfractionBuilder();
    }

    public static InfractionBuilder builder(Punishment punishment) {
        return new InfractionBuilder(
            punishment.uniqueId,
            punishment.type,
            punishment.offender,
            punishment.creator,
            punishment.timestamp,
            punishment.expiry,
            punishment.reason);
    }

    public static class InfractionBuilder {
        private UUID uniqueId = null;
        private InfractionType type = null;
        private User<?> offender = null;
        private User<?> creator = null;
        private Long timestamp = null;
        private Long expiry = null;
        private String reason = null;

        private InfractionBuilder() {}

        private InfractionBuilder(UUID uniqueId, InfractionType type, User<?> offender, User<?> creator, Long timestamp, Long expiry, String reason) {
            this.uniqueId = uniqueId;
            this.type = type;
            this.offender = offender;
            this.creator = creator;
            this.timestamp = timestamp;
            this.expiry = expiry;
            this.reason = reason;
        }

        public InfractionBuilder setUniqueId(UUID uniqueId) {
            this.uniqueId = uniqueId;
            return this;
        }

        /**
         * Set the type of the infraction.
         *
         * @param type The infraction type
         * @return The builder
         */
        public InfractionBuilder setType(InfractionType type) {
            this.type = type;
            return this;
        }

        /**
         * Set the user to whom this infraction applies.
         *
         * @param offender The offending user
         * @return The builder
         */
        public InfractionBuilder setOffender(User<?> offender) {
            this.offender = offender;
            return this;
        }

        /**
         * Set the user that created this infraction.
         *
         * @param creator The creator
         * @return The builder
         */
        public InfractionBuilder setCreator(User<?> creator) {
            this.creator = creator;
            return this;
        }

        /**
         * Set the timestamp at which this infraction was created
         *
         * @param timestamp
         * @return
         */
        public InfractionBuilder setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public InfractionBuilder setExpiry(Long expiry) {
            this.expiry = expiry;
            return this;
        }

        public InfractionBuilder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Punishment build() {
            return new Punishment(uniqueId, type, offender, creator, timestamp, expiry, reason);
        }
    }

    public enum InfractionType {
        WARNING,
        MUTE,
        KICK,
        BAN
    }
}
