/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.behaviour;

import agent.model.LOM;
import agent.utils.Utils;
import com.google.gson.Gson;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author magir
 */
public class LOEvaluatorBehaviour extends CyclicBehaviour{
    
    public LOEvaluatorBehaviour(Agent agent) {
        super(agent);
        myAgent = agent;
    }
    
    @Override
    public void action() {
        Utils utils = new Utils();
        ACLMessage receivedMessage = utils.receiveMessage(myAgent, null);
        
        Gson gson = new Gson();
        
        if(receivedMessage != null){
            if(receivedMessage.getPerformative() == ACLMessage.INFORM){
                
                if(utils.getMessage(receivedMessage.getContent()).
                        equals("evaluate lo")){
                    
                    LOM lom = gson.fromJson(
                            utils.getJsonObject(receivedMessage.getContent()),
                            LOM.class);
                    
                    //Reglas para hacer las recomendaciones al objeto
                    if(lom.getTitle() != null && !lom.getTitle().trim().equals("")){
                        lom.addOneAccessibilityScore();
                    }
                    
                    if(lom.getKeyWordList().size() > 2){
                        lom.addOneAccessibilityScore();
                    }else if(lom.getKeyWordList().size() < 3){
                        lom.addRecommendation("Un buen objeto de aprendizaje debe tener al menos 3 palabras clave que lo describan.");
                    }
                    
                    if(lom.getDescription() != null && !lom.getDescription().trim().equals("")){
                        lom.addOneAccessibilityScore();
                    }else{
                        lom.addRecommendation("Los objetos de aprendizaje deben tener una descripción que le sirva al usuario para tener una idea del material que se muestra en el OA.");
                    }
                    
                    if(lom.getGeneralLanguage() != null && !lom.getGeneralLanguage().trim().equals("")){
                        lom.addOneAccessibilityScore();
                    }
                    
                    if(lom.getInteractivityType() != null && !lom.getInteractivityType().trim().equals("")){
                        lom.addOneAccessibilityScore();
                    }
                    
                    if(lom.getLearningResourceTypeList().size() > 0){
                        lom.addOneAccessibilityScore();
                    }
                    
                    if(lom.getInteractivityLevel() != null && !lom.getInteractivityLevel().trim().equals("")){
                        lom.addOneAccessibilityScore();
                    }
                    
                    if(lom.getSemanticDensity() != null && !lom.getSemanticDensity().trim().equals("")){
                        lom.addOneAccessibilityScore();
                    }
                    
                    if(lom.getEducationlLanguage() != null && !lom.getEducationlLanguage().trim().equals("")){
                        lom.addOneAccessibilityScore();
                    }
                    
                    if(
                            (lom.getAuditory() != null && !lom.getAuditory().trim().equals("")) ||
                            (lom.getTextual() != null && !lom.getTextual().trim().equals("")) ||
                            (lom.getVisual() != null && !lom.getVisual().trim().equals("")) ||
                            (lom.getKeyboard() != null && !lom.getKeyboard().trim().equals("")) ||
                            (lom.getMouse() != null && !lom.getMouse().trim().equals("")) ||
                            (lom.getVoiceRecognition() != null && !lom.getVoiceRecognition().trim().equals("")) ||
                            (lom.getAudioDescription() != null && !lom.getAudioDescription().trim().equals("")) ||
                            (lom.getHearingAlternative() != null && !lom.getHearingAlternative().trim().equals("")) ||
                            (lom.getTextualAlternative() != null && !lom.getTextualAlternative().trim().equals("")) ||
                            (lom.getSignLanguage() != null && !lom.getSignLanguage().trim().equals("")) ||
                            (lom.getSubtitles() != null && !lom.getSubtitles().trim().equals(""))
                            ){
                        lom.addOneAccessibilityScore();
                    }
                    
                    if(lom.getGeneralLanguage() == null && lom.getGeneralLanguage().trim().equals("") &&
                            lom.getEducationlLanguage() == null && lom.getEducationlLanguage().trim().equals("")){
                        lom.addRecommendation("los objetos de aprendizaje deben de mostrar en sus metadatos en que lenguaje están creados, para que el usuario tenga conocimiento que lenguaje debe de dominar para entender los contenidos del OA.");
                    }
                    
                    if(lom.getLearningResourceTypeList().indexOf("narrative text") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("lecture") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("audio") > -1){
                        lom.addRecommendation("Es accesible para una persona con discapacidad visual nulo.");
                    }
                    
                    if(lom.getLearningResourceTypeList().indexOf("narrative text") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("lecture") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("audio") > -1){
                        lom.addRecommendation("Es accesible para una persona con discapacidad visual baja.");
                    }
                    
                    if(lom.getLearningResourceTypeList().indexOf("diagram") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("selfassessment") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("simulation") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("questionnaire") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("slide") > -1){
                        lom.addRecommendation("Es accesible para una persona con discapacidad auditiva nula.");
                    }
                    
                    if(lom.getLearningResourceTypeList().indexOf("diagram") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("figure") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("graph") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("selfassessment") > -1 ||
                            lom.getLearningResourceTypeList().indexOf("table") > -1){
                        lom.addRecommendation("Es accesible para una persona con discapacidad auditiva baja.");
                    }
                    
                    if(lom.getVoiceRecognition().toLowerCase().trim().equals("si")){
                        lom.addRecommendation("Es accesible para una persona con discapacidad motriz.");
                    }
                    
                    if(lom.getAuditory().toLowerCase().trim().equals("si") || 
                            lom.getKeyboard().toLowerCase().trim().equals("si")){
                        lom.addRecommendation("Es accesible para una persona con discapacidad motriz.");
                    }
                    
                    if(lom.getVoiceRecognition().toLowerCase().trim().equals("si") || 
                            lom.getAudioDescription().toLowerCase().trim().equals("si") ||
                            lom.getHearingAlternative().toLowerCase().trim().equals("si")){
                        lom.addRecommendation("Es accesible para una persona con discapacidad visual (general).");
                    }
                    
                    if(lom.getTextual().toLowerCase().trim().equals("si") || 
                            lom.getVisual().toLowerCase().trim().equals("si") ||
                            lom.getTextualAlternative().toLowerCase().trim().equals("si") ||
                            lom.getSignLanguage().toLowerCase().trim().equals("si") ||
                            lom.getSubtitles().toLowerCase().trim().equals("si")){
                        lom.addRecommendation("Es accesible para una persona con discapacidad auditiva (general).");
                    }
                    
                    String json = gson.toJson(lom);
                    
                    utils.sendMessage(myAgent,
                            new AID("ControllerAgent", AID.ISLOCALNAME),
                            "obj;" + json + ";" + "msg;" + "lo evaluation",
                            ACLMessage.INFORM);
                    
                }
            }
        }
    }
    
    
}
