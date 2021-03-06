/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.continuous;

import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class WardenOfTheFirstTreeTest extends CardTestPlayerBase {

    @Test
    public void testFirstAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // {1}{W/B}: Warden of the First Tree becomes a Human Warrior with base power and toughness 3/3.
        // {2}{W/B}{W/B}: If Warden of the First Tree is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink.
        // {3}{W/B}{W/B}{W/B}: If Warden of the First Tree is a Spirit, put five +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Warden of the First Tree", 2); // {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warden of the First Tree");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/B}:");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Warden of the First Tree", 3, 3, Filter.ComparisonScope.Any);
        assertType("Warden of the First Tree", CardType.CREATURE, "Human");
        assertType("Warden of the First Tree", CardType.CREATURE, "Warrior");
        assertAbility(playerA, "Warden of the First Tree", TrampleAbility.getInstance(), false);
        assertAbility(playerA, "Warden of the First Tree", LifelinkAbility.getInstance(), false);
    }

    @Test
    public void testSecondAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // {1}{W/B}: Warden of the First Tree becomes a Human Warrior with base power and toughness 3/3.
        // {2}{W/B}{W/B}: If Warden of the First Tree is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink.
        // {3}{W/B}{W/B}{W/B}: If Warden of the First Tree is a Spirit, put five +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Warden of the First Tree", 2); // {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warden of the First Tree");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/B}:");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{W/B}{W/B}:");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Warden of the First Tree", 3, 3, Filter.ComparisonScope.Any);
        assertType("Warden of the First Tree", CardType.CREATURE, "Human");
        assertType("Warden of the First Tree", CardType.CREATURE, "Spirit");
        assertType("Warden of the First Tree", CardType.CREATURE, "Warrior");
        assertAbility(playerA, "Warden of the First Tree", TrampleAbility.getInstance(), true);
        assertAbility(playerA, "Warden of the First Tree", LifelinkAbility.getInstance(), true);
    }

    @Test
    public void testThirdAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // {1}{W/B}: Warden of the First Tree becomes a Human Warrior with base power and toughness 3/3.
        // {2}{W/B}{W/B}: If Warden of the First Tree is a Warrior, it becomes a Human Spirit Warrior with trample and lifelink.
        // {3}{W/B}{W/B}{W/B}: If Warden of the First Tree is a Spirit, put five +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Warden of the First Tree", 2); // {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warden of the First Tree");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W/B}:");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}{W/B}{W/B}:");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{W/B}{W/B}{W/B}:");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Warden of the First Tree", 8, 8, Filter.ComparisonScope.Any);
        assertType("Warden of the First Tree", CardType.CREATURE, "Human");
        assertType("Warden of the First Tree", CardType.CREATURE, "Spirit");
        assertType("Warden of the First Tree", CardType.CREATURE, "Warrior");
        assertAbility(playerA, "Warden of the First Tree", TrampleAbility.getInstance(), true);
        assertAbility(playerA, "Warden of the First Tree", LifelinkAbility.getInstance(), true);
    }

    /**
     * When a Warden of the First Tree enters the battlefield, if it is not the
     * first warden played during the game, it enters with a random
     * power/toughness instead of 1/1. I have had it enter with both 2/2 and
     * 4/4, neither of which are actual values the card can hold.
     */
}
