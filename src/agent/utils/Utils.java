/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.utils;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author Mauricio
 */
public class Utils {
    
    /**
     * 
     * 
     * @param agent
     * @param agentID
     * @param content
     * @param performative 
     */
    public void sendMessage(Agent agent, AID agentID, String content, int performative){
        ACLMessage message = new ACLMessage();
        message.addReceiver(agentID);
        message.setContent(content);
        message.setPerformative(performative);
        agent.send(message);
    }
    
    /**
     * 
     * 
     * @param agent
     * @param messageTemplate
     * @return 
     */
    public ACLMessage receiveMessage(Agent agent, MessageTemplate messageTemplate){
        ACLMessage message;
        
        if(messageTemplate != null){
            message = agent.receive(messageTemplate);
        }else{
            message = agent.receive();
        }
        
        return message;
    }
    
    /**
     * 
     * 
     * @param agent
     * @param receivedMessage
     * @param performative
     * @param content 
     */
    public void sendReply(Agent agent, ACLMessage receivedMessage, int performative, String content){
        ACLMessage reply = receivedMessage.createReply();
        reply.setPerformative(performative);
        reply.setContent(content);
        agent.send(reply);
    }
    
    public String getJsonObject(String receivedMessage){
        String[] listStrings = receivedMessage.split(";");
        
        return listStrings[1];
    }
    
    public String getMessage(String receivedMessage){
        String[] listStrings = receivedMessage.split(";");
        
        return listStrings[3];
    }
    
}
