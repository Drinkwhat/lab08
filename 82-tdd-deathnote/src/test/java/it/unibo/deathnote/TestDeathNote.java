package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private final int NEGATIVE_RULE = -1;
    private final int UPPON_LIMIT_RULE = 10000;

    private final String NAME_1 = "Mario Rossi";
    private final String NAME_2 = "Pippo Baudo";
    private final String DEATH_CAUSE_1= "karting accident";
    private final String DEATH_CAUSE_2= "Annegato in una vasca di coca-cola";

    private final String DEATH_DETAIL_1 = "ran for too long";
    private final String DEATH_DETAIL_2 = "Troppa programmazione ad oggetti";



    
    private DeathNote deathNote;

    @BeforeEach
    public void setUp() {
        deathNote = new DeathNoteImpl();
    }

    @Test
    public void testGetRuleIllegalArguments() {
        // Test rule 0 - should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> deathNote.getRule(0)
        );
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
        assertFalse(exception.getMessage().isBlank());
        
        // Test negative rule - should throw IllegalArgumentException
        exception = assertThrows(
            IllegalArgumentException.class, 
            () -> deathNote.getRule(NEGATIVE_RULE)
        );
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
        assertFalse(exception.getMessage().isBlank());
        
        // Test upper limit rule - should throw IllegalArgumentException
        exception = assertThrows(
            IllegalArgumentException.class, 
            () -> deathNote.getRule(UPPON_LIMIT_RULE)
        );
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
        assertFalse(exception.getMessage().isBlank());
    }

    @Test
    public void testRulesAreValid() {
        for (final String rule : DeathNote.RULES) {
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    public void testWriteName() {
        assertFalse(deathNote.isNameWritten(NAME_1));
        deathNote.writeName(NAME_1);
        assertTrue(deathNote.isNameWritten(NAME_1));
        assertFalse(deathNote.isNameWritten(NAME_2));
        deathNote.writeName("");
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    public void testWriteDeathCause() throws InterruptedException {
        assertThrows(
            IllegalStateException.class, 
            () -> deathNote.writeDeathCause(DEATH_CAUSE_1)
        );
        deathNote.writeName(NAME_1);
        assertEquals(DeathNoteImpl.DEFAULT_DEATH_CAUSE, deathNote.getDeathCause(NAME_1));
        deathNote.writeName(NAME_2);
        assertTrue(deathNote.writeDeathCause(DEATH_CAUSE_1));
        assertEquals(DEATH_CAUSE_1, deathNote.getDeathCause(NAME_2));
        Thread.sleep(100);
        deathNote.writeDeathCause(DEATH_CAUSE_2);
        assertNotEquals(DEATH_CAUSE_2, deathNote.getDeathCause(NAME_2));
    }

    @Test
    public void testWriteDetails() throws InterruptedException {
        assertThrows(
            IllegalStateException.class, 
            () -> deathNote.writeDetails(DEATH_DETAIL_1)
        );

        deathNote.writeName(NAME_1);
        assertTrue(deathNote.getDeathDetails(NAME_1).isEmpty());
        assertTrue(deathNote.writeDetails(DEATH_DETAIL_1));
        assertEquals(DEATH_DETAIL_1, deathNote.getDeathDetails(NAME_1));
        deathNote.writeName(NAME_2);
        Thread.sleep(6100);
        assertFalse(deathNote.writeDetails(DEATH_DETAIL_2));
        assertNotEquals(DEATH_DETAIL_2, deathNote.getDeathDetails(NAME_2));
    }


}