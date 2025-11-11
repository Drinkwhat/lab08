package it.unibo.deathnote.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

/**
 * Implementazione concreta dell'interfaccia {@link DeathNote}.
 *
 * <p>Gestisce la logica per la scrittura dei nomi e l'aggiornamento di causa e dettagli
 * della morte, rispettando le finestre temporali definite dalle costanti
 * {@link #DELTA_TIME_DEATH_CAUSE} e {@link #DELTA_TIME_DEATH_DETAILS}.</p>
 *
 * <p>La classe è finale perché non è pensata per essere estesa; l'inner class
 * {@code Person} incapsula lo stato relativo a ciascun nome scritto.</p>
 *
 * @see DeathNote
 */
public final class DeathNoteImpl implements DeathNote {

    public static final String DEFAULT_DEATH_CAUSE = "Heart attack";
    public static final String DEFAULT_DEATH_DETAILS = "";

    public static final int DELTA_TIME_DEATH_CAUSE = 40;
    public static final int DELTA_TIME_DEATH_DETAILS = 6040;
    private static final String ERR_NAME_NULL = "name cannot be null";

    private final Map<String, Person> deathNote = new LinkedHashMap<>();
    private String lastWrittenName;

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber <= 0 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Rule " + ruleNumber + " is not in [1;" + RULES.size() + "]");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name, ERR_NAME_NULL);
        if (name.isBlank()) {
            return;
        }
        final Person lastPerson = deathNote.putIfAbsent(name, new Person());
        lastWrittenName = lastPerson == null ? name : lastWrittenName;
    }

    @Override
    public String getDeathCause(final String name) {
        Objects.requireNonNull(name, ERR_NAME_NULL);
        if (!deathNote.containsKey(name)) {
            throw new IllegalArgumentException(name + "is not in deathnote");
        }
        return deathNote.get(name).getDeathCause();
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (lastWrittenName == null) {
            throw new IllegalStateException("DeathNote has no written names yet");
        }
        Objects.requireNonNull(cause, "Cause cannot be null");
        final Person p = deathNote.get(lastWrittenName);
        final long now = System.currentTimeMillis();
        if (now - p.getNameTimestamp() > DELTA_TIME_DEATH_CAUSE) {
            return false;
        }
        if (!DEFAULT_DEATH_CAUSE.equals(p.getDeathCause())) {
            return false;
        }
        p.setDeathCause(cause);
        return true;
    }

    @Override
    public String getDeathDetails(final String name) {
        Objects.requireNonNull(name, ERR_NAME_NULL);
        if (!deathNote.containsKey(name)) {
            throw new IllegalArgumentException(name + "is not in deathnote");
        }
        return deathNote.get(name).getDeathDetails();
    }

    @Override
    public boolean writeDetails(final String details) {
        if (lastWrittenName == null) {
            throw new IllegalStateException("DeathNote has no written names yet");
        }
        Objects.requireNonNull(details, "Details cannot be null");
        final Person p = deathNote.get(lastWrittenName);
        final long now = System.currentTimeMillis();
        if (now - p.getCauseTimestamp() > DELTA_TIME_DEATH_DETAILS) {
            return false;
        }
        if (!DEFAULT_DEATH_DETAILS.equals(p.getDeathDetails())) {
            return false;
        }
        p.setDeathDetails(details);
        return true;
    }

    @Override
    public boolean isNameWritten(final String name) {
        Objects.requireNonNull(name, ERR_NAME_NULL);
        return deathNote.containsKey(name);
    }

    private final class Person {
        private String deathCause;
        private String deathDetails;
        private final long nameTimeStamp;
        private long causeTimestamp;

        private Person() {
            deathCause = DEFAULT_DEATH_CAUSE;
            deathDetails = DEFAULT_DEATH_DETAILS;
            nameTimeStamp = System.currentTimeMillis();
            causeTimestamp = System.currentTimeMillis();
        }

        private String getDeathCause() {
            return deathCause;
        }

        private void setDeathCause(final String deathCause) {
            this.deathCause = deathCause;
            this.causeTimestamp = System.currentTimeMillis();
        }

        private String getDeathDetails() {
            return deathDetails;
        }

        private void setDeathDetails(final String deathDetail) {
            this.deathDetails = deathDetail;
        }

        private long getNameTimestamp() {
            return nameTimeStamp;
        }

        private long getCauseTimestamp() {
            return causeTimestamp;
        }
    }
}
