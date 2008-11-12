//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b26-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.02 at 05:14:34 PM EDT 
//


package org.rlcommunity.environment.octopus.config;

import org.rlcommunity.environment.octopus.config.ChoiceSpec;
import org.rlcommunity.environment.octopus.config.ObjectiveSpec;
import org.rlcommunity.environment.octopus.config.SequenceSpec;
import org.rlcommunity.environment.octopus.config.TargetSpec;
import org.rlcommunity.environment.octopus.config.TargetTaskDef;
import org.rlcommunity.environment.octopus.config.TaskDef;
import java.math.BigInteger;


/**
 * <p>Java class for TargetTaskDef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TargetTaskDef">
 *   &lt;complexContent>
 *     &lt;extension base="{}TaskDef">
 *       &lt;sequence>
 *         &lt;element ref="{}objective"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

public class TargetTaskDef
    extends TaskDef
{

    public TargetTaskDef(ObjectiveSpec objective, BigInteger timeLimit, Double rewardPerStep){
        super(timeLimit,rewardPerStep);
        this.objective=objective;
    }
    protected ObjectiveSpec objective;

    /**
     * Gets the value of the objective property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ChoiceSpec }{@code >}
     *     {@link JAXBElement }{@code <}{@link TargetSpec }{@code >}
     *     {@link JAXBElement }{@code <}{@link ObjectiveSpec }{@code >}
     *     {@link JAXBElement }{@code <}{@link SequenceSpec }{@code >}
     *     
     */
    public ObjectiveSpec getObjective() {
        return objective;
    }

    /**
     * Sets the value of the objective property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ChoiceSpec }{@code >}
     *     {@link JAXBElement }{@code <}{@link TargetSpec }{@code >}
     *     {@link JAXBElement }{@code <}{@link ObjectiveSpec }{@code >}
     *     {@link JAXBElement }{@code <}{@link SequenceSpec }{@code >}
     *     
     */
    public void setObjective(ObjectiveSpec value) {
        this.objective = value;
    }

}