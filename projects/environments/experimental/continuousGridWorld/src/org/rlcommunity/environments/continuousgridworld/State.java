/*
 *  Copyright 2009 Brian Tanner.
 *
 *  brian@tannerpages.com
 *  http://research.tannerpages.com
 *
 *  This source file is from one of:
 *  {rl-coda,rl-glue,rl-library,bt-agentlib,rl-viz}.googlecode.com
 *  Check out http://rl-community.org for more information!
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.rlcommunity.environments.continuousgridworld;

import org.rlcommunity.environments.continuousgridworld.map.SerializableRectangle;
import org.rlcommunity.environments.continuousgridworld.map.SerializablePoint;
import org.rlcommunity.environments.continuousgridworld.map.BarrierRegion;
import org.rlcommunity.environments.continuousgridworld.map.RewardRegion;
import org.rlcommunity.environments.continuousgridworld.map.TerminalRegion;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;
import org.rlcommunity.rlglue.codec.types.Observation;

/**
 *
 * @author btanner
 */
public class State implements Serializable {

    protected  Random randomStateGenerator;
    protected  Random randomNoiseGenerator;

    /** Change this when you make new versions that are not compatible **/
    private static final long serialVersionUID = 1L;
    private double xSpeed = 5.0d;
    private double ySpeed = 5.0d;
    private final double width = 100.0d;
    private final double height = 100.0d;
    private final Rectangle2D worldRect = new SerializableRectangle(0, 0, width, height);
    //Maybe make this random?
    private final Point2D agentSize = new SerializablePoint(1.0d, 1.0d);
    private Point2D agentPos = new SerializablePoint(1.0d, 1.0d);
    private Rectangle2D currentAgentRect;
    private Vector<BarrierRegion> barrierRegions = new Vector<BarrierRegion>();
    private Vector<TerminalRegion> resetRegions = new Vector<TerminalRegion>();
    private Vector<RewardRegion> rewardRegions = new Vector<RewardRegion>();
    protected boolean impactFromMovement = false;
    protected boolean randomStartStates = false;
    protected double transitionNoise = 0.0d;

    /**
     * This is for deserialization
     */
    private State() {
    }

    /**
     *
     * @param randomStartStates
     * @param transitionNoise
     * @param seed 0 means to not use the seed and be truly random.
     */
    public State(boolean randomStartStates, double transitionNoise, long seed) {
        this.randomStartStates = randomStartStates;
        this.transitionNoise = transitionNoise;
        if(seed==0L){
            randomStateGenerator=new Random();
            randomNoiseGenerator=new Random();
        }else{
            randomStateGenerator=new Random(seed);
            randomNoiseGenerator=new Random(seed);
        }
        //Throw away the first few bits because they depend heavily on the seed.
        randomStateGenerator.nextDouble();
        randomStateGenerator.nextDouble();
        randomNoiseGenerator.nextDouble();
        randomNoiseGenerator.nextDouble();
    }

    private double calculateMaxBarrierAtPosition(Rectangle2D r) {
        double maxPenalty = 0.0F;
        for (BarrierRegion thisBarrier : barrierRegions) {
            if (thisBarrier.intersects(r)) {
                if (thisBarrier.getPenalty() > maxPenalty) {
                    maxPenalty = thisBarrier.getPenalty();
                }
            }
        }
        return maxPenalty;
    }

    void reset() {
        setInitialAgentPosition();
        while (calculateMaxBarrierAtPosition(currentAgentRect) >= 1.0F || !worldRect.contains(currentAgentRect)||intersectsResetRegion(currentAgentRect)) {
            setInitialAgentPosition();
        }
    }

    void update(int theAction) {
        impactFromMovement = false;

        double dx = 0;
        double dy = 0;
        //Should find a good way to abstract actions and add them in like the old wya, that was good
        if (theAction == 0) {
            dx = xSpeed;
        }
        if (theAction == 1) {
            dx = -xSpeed;
        }
        if (theAction == 2) {
            dy = ySpeed;
        }
        if (theAction == 3) {
            dy = -ySpeed;
        }

        //Max noise would be equal to the speed.
        double noiseX = 2.0d * transitionNoise * xSpeed * (randomNoiseGenerator.nextDouble() - 0.5);
        double noiseY = 2.0d * transitionNoise * ySpeed * (randomNoiseGenerator.nextDouble() - 0.5);
        dx += noiseX;
        dy += noiseY;

        Point2D nextPos = new SerializablePoint(agentPos.getX() + dx, agentPos.getY() + dy);
        nextPos = updateNextPosBecauseOfWorldBoundary(nextPos);
        nextPos = updateNextPosBecauseOfBarriers(nextPos);
        agentPos = nextPos;

        updateCurrentAgentRect();
    }

    protected void setAgentPosition(Point2D dp) {
        this.agentPos = dp;
        updateCurrentAgentRect();
    }

    protected void updateCurrentAgentRect() {
        currentAgentRect = makeAgentSizedRectFromPosition(agentPos);
    }

    protected boolean impactLastStep() {
        return impactFromMovement;
    }

    protected Point2D updateNextPosBecauseOfBarriers(Point2D nextPos) {
        //See if the agent's current position is in a wall, if so we want to impede his movement.
        double penalty = calculateMaxBarrierAtPosition(currentAgentRect);
        double currentX = agentPos.getX();
        double currentY = agentPos.getY();
        double nextX = nextPos.getX();
        double nextY = nextPos.getY();
        double newNextX = currentX + ((nextX - currentX) * (1.0F - penalty));
        double newNextY = currentY + ((nextY - currentY) * (1.0F - penalty));
        nextPos.setLocation(newNextX, newNextY);
        //Now, find out if he's in an immobile obstacle... and if so move him out
        float fudgeCounter = 0;
        Rectangle2D nextPosRect = makeAgentSizedRectFromPosition(nextPos);
        while (calculateMaxBarrierAtPosition(nextPosRect) == 1.0F) {
            impactFromMovement = true;
            nextPos = findMidPoint(nextPos, agentPos);
            fudgeCounter++;
            if (fudgeCounter == 4) {
                nextPos = (Point2D) agentPos.clone();
                break;
            }
        }
        return nextPos;
    }

    protected Point2D updateNextPosBecauseOfWorldBoundary(Point2D nextPos) {
        //Gotta do this somewhere
        int fudgeCounter = 0;
        Rectangle2D nextPosRect = makeAgentSizedRectFromPosition(nextPos);
        while (!worldRect.contains(nextPosRect)) {
            impactFromMovement = true;
            nextPos = findMidPoint(nextPos, agentPos);
            fudgeCounter++;
            if (fudgeCounter == 4) {
                nextPos = agentPos;
                break;
            }
        }
        return nextPos;
    }

    protected boolean intersectsResetRegion(Rectangle2D r) {
        for (int i = 0; i < resetRegions.size(); i++) {
            if (resetRegions.get(i).intersects(r)) {
                return true;
            }
        }
        return false;
    }

    private Rectangle2D makeAgentSizedRectFromPosition(Point2D thePos) {
        return new SerializableRectangle(thePos.getX(), thePos.getY(), agentSize.getX(), agentSize.getY());
    }

    double getReward() {
        Rectangle2D agentRect = makeAgentSizedRectFromPosition(agentPos);
        double reward = 0.0;
        for (RewardRegion thisRewardState : rewardRegions) {
            if (thisRewardState.intersects(agentRect)) {
                reward += thisRewardState.getRewardValue();
            }
        }
        return reward;
    }

    private Point2D findMidPoint(Point2D a, Point2D b) {
        double newX = (a.getX() + b.getX()) / 2.0;
        double newY = (a.getY() + b.getY()) / 2.0;
        return new SerializablePoint(newX, newY);
    }

    protected void setInitialAgentPosition() {
        double startX = 0.1;
        double startY = 0.1;

        if (randomStartStates) {
            startX = randomStateGenerator.nextDouble() * worldRect.getWidth();
            startY = randomStateGenerator.nextDouble() * worldRect.getHeight();
        }
        setAgentPosition(new SerializablePoint(startX, startY));
    }

    public boolean inResetRegion() {
        return intersectsResetRegion(currentAgentRect);
    }

    public Point2D getAgentPosition() {
        return agentPos;
    }

    public String getSerializedState() {
        return "";
    }

    void addRewardState(RewardRegion thisRewardState) {
        rewardRegions.add(thisRewardState);
    }

    void addTerminalState(TerminalRegion goalState) {
        resetRegions.add(goalState);
    }

    void addBarrier(BarrierRegion barrier) {
        barrierRegions.add(barrier);
    }

    public Vector<TerminalRegion> getResetRegions() {
        return resetRegions;
    }

    public Vector<BarrierRegion> getBarriers() {
        return barrierRegions;
    }

    Observation makeObservation() {
        Observation currentObs = new Observation(0, 2);
        //Normalize each to 0,1
        currentObs.doubleArray[0] = agentPos.getX() / 100.0d;
        currentObs.doubleArray[1] = agentPos.getY() / 100.0d;

        return currentObs;
    }

    Observation getObservationForState(Observation theQueryState) {
        //Value function only loops over dims 1 and 2 so we have to add the others.
        Observation theObs = new Observation(0, 2, 0);
        //States are in [0,100], observations are in [0,1]
        theObs.doubleArray[0] = theQueryState.doubleArray[0] / 100.0d;
        theObs.doubleArray[1] = theQueryState.doubleArray[1] / 100.0d;
        //States are in [-5,5], observations are in [0,1]

        return theObs;
    }

    int getNumVars() {
        return 2;
    }
}
