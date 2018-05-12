package betterwithmods.common.penalties;

import betterwithmods.common.penalties.attribute.BWMAttributes;
import org.apache.commons.lang3.Range;

/**
 * Penalty linked to hunger (food level).
 *
 * @author Koward
 */
public class BasicPenalty<T extends Number & Comparable> extends Penalty<T> {


    public BasicPenalty(boolean jump, boolean swim, boolean heal, boolean sprint, boolean attack, boolean pain, float speed, float severity, String name, String lang, String category, Range<T> range) {
        //TODO this needs a better desc
        super(lang, severity, BWMAttributes.getRange(category, name, "Numberic range for whether this penalty it active", range),
                BWMAttributes.getBooleanAttribute(BWMAttributes.JUMP, category, name, "Can the player jump with this penalty active?", jump),
                BWMAttributes.getBooleanAttribute(BWMAttributes.SWIM, category, name, "Can the player swim with this penalty active?", swim),
                BWMAttributes.getBooleanAttribute(BWMAttributes.HEAL, category, name, "Can the player heal hearts with this penalty active?", heal),
                BWMAttributes.getBooleanAttribute(BWMAttributes.SPRINT, category, name, "Can the player sprint with this penalty active?", sprint),
                BWMAttributes.getBooleanAttribute(BWMAttributes.ATTACK, category, name, "Can the player attack with this penalty active?", attack),
                BWMAttributes.getBooleanAttribute(BWMAttributes.PAIN, category, name, "Is the player in pain? (Plays the OOF noise periodically)", pain),
                BWMAttributes.getFloatAttribute(BWMAttributes.SPEED, category, name, "Multiplier on the player's speed when active", speed)
        );
    }


}

