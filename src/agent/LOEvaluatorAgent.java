/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import agent.behaviour.LOEvaluatorBehaviour;
import jade.core.Agent;

/**
 *
 * @author magir
 */
public class LOEvaluatorAgent extends Agent{
    
    @Override
    protected void setup() {
        super.setup();
        
        LOEvaluatorBehaviour behaviour = new LOEvaluatorBehaviour(this);
        
        addBehaviour(behaviour);
    }
    
}
