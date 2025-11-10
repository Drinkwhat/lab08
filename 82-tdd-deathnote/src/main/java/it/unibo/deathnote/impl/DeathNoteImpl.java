package it.unibo.deathnote.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {
    public static final String DEFAULT_DEATH_CAUSE = "Heart attack";
    public static final int DELTA_TIME_DEATH_CAUSE = 40;
    public static final int DELTA_TIME_DEATH_DETAILS = 6040;



    private class Person {
        private String deathCause;
        private String deathDetails;
        private final long nameTimeStamp;
        private long causeTimestamp;
        private long detailsTimestamp;

        private Person() {
            deathCause = DEFAULT_DEATH_CAUSE;
            deathDetails = "";
            nameTimeStamp = System.currentTimeMillis();
            this.causeTimestamp = System.currentTimeMillis();
            this.detailsTimestamp = 0;
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
            this.detailsTimestamp = System.currentTimeMillis();
        }

        private long getNameTimestamp() {
            return nameTimeStamp;
        }

        private long getCauseTimestamp() {
            return causeTimestamp;
        }
    }

    private final Map<String, Person> deathNote = new LinkedHashMap<>();
    private String lastWrittenName;

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber <= 0 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Rule " + ruleNumber + " is not in [1;" + RULES.size() + "]");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(String name) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        if (name.isBlank()) {
            return;
        }
        final Person lastPerson =  deathNote.putIfAbsent(name, new Person());
        lastWrittenName = lastPerson == null ? name : lastWrittenName;
    }

    @Override
    public String getDeathCause(String name) {
        if (!deathNote.containsKey(name)) {
            throw new IllegalArgumentException(name + "is not in deathnote");
        }
        return deathNote.get(name).getDeathCause();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (lastWrittenName == null) {
            throw new IllegalStateException("DeathNote has no written names yet");
        }
        if (cause == null) {
            throw new IllegalStateException("Cause cannot be null");
        }
        final Person p = deathNote.get(lastWrittenName);
        final long now = System.currentTimeMillis();
        if (now - p.getNameTimestamp() > DELTA_TIME_DEATH_CAUSE) {
            return false;
        }
        if (p.getDeathCause() == null) {
            return false;
        }
        p.setDeathCause(cause);
        return true;
    }

    @Override
    public String getDeathDetails(String name) {
        if (!deathNote.containsKey(name)) {
            throw new IllegalArgumentException(name + "is not in deathnote");
        }
        return deathNote.get(name).getDeathDetails();
    }

    @Override
    public boolean writeDetails(String details) {
        if (lastWrittenName == null) {
            throw new IllegalStateException("DeathNote has no written names yet");
        }
        if (details == null) {
            throw new IllegalStateException("Cause cannot be null");
        }
        final Person p = deathNote.get(lastWrittenName);
        final long now = System.currentTimeMillis();
        if (now - p.getCauseTimestamp() > DELTA_TIME_DEATH_DETAILS) {
            System.out.println(now);
            System.out.println(p.getCauseTimestamp());
            return false;
        }
        if (p.getDeathDetails() == null) {
            return false;
        }
        p.setDeathDetails(details);
        return true;
    }
    @Override
    public boolean isNameWritten(String name) {
        return deathNote.containsKey(name);
    }

}
