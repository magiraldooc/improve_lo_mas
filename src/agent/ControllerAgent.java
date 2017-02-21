/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import agent.behaviour.ControllerBehaviour;
import jade.core.Agent;

/**
 *
 * @author magir
 */
public class ControllerAgent extends Agent{

    @Override
    protected void setup() {
        super.setup();
        
        ControllerBehaviour behaviour = new ControllerBehaviour(this);
        
        addBehaviour(behaviour);
    }
    
}
