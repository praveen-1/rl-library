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


package org.rlcommunity.environments.continuousgridworld.visualizer;

import org.rlcommunity.environments.continuousgridworld.State;
import rlVizLib.visualization.interfaces.AgentOnValueFunctionDataProvider;
import rlVizLib.visualization.interfaces.GlueStateProvider;
import rlVizLib.visualization.interfaces.ValueFunctionDataProvider;

/**
 *
 * @author btanner
 */
public interface GridWorldVisualizerInterface extends AgentOnValueFunctionDataProvider, GlueStateProvider, ValueFunctionDataProvider {

    State getState();
    void updateAgentState();


}
