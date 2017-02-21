/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.behaviour;

import agent.model.LOList;
import agent.model.LOM;
import agent.model.SearchLO;
import agent.utils.Utils;
import com.google.gson.Gson;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONArray;

/**
 *
 * @author magir
 */
public class ControllerBehaviour extends CyclicBehaviour{
    
    public ControllerBehaviour(Agent a) {
        super(a);
        
        myAgent = a;
    }
    
    @Override
    public void action() {
        
        Utils utils = new Utils();
        
        Gson gson = new Gson();
        
        ACLMessage receivedMessage = utils.receiveMessage(myAgent, null);
        
        if(receivedMessage != null){
        
            if(receivedMessage.getPerformative() == ACLMessage.INFORM){

                String messageContent = receivedMessage.getContent();

                if(utils.getMessage(messageContent).
                        equals("search lo")){
                    
                    SearchLO searchLO = gson.fromJson(
                            utils.getJsonObject(receivedMessage.getContent()),
                            SearchLO.class);
                    
                    try {
                        searchLO.setLoList(this.getLO(searchLO.getSearchString()));
                        
                        System.out.println("Lista objetos: " + 
                                searchLO.getLoList().getLOList().size());
                        
                        if(searchLO.getSearchLOId() != null){
                            
                            Predicate<LOM> lomPredicate = lom -> lom.getLoId().compareTo(searchLO.getSearchLOId()) != 0;
                            
                            searchLO.getLoList().getLOList().removeIf(lomPredicate);
                            
                            this.sendLOToEvaluation(searchLO.getLoList().getLOList().get(0), gson, utils);
                        }
                        
                        String json = gson.toJson(searchLO);

                        utils.sendMessage(myAgent, 
                                new AID("InterfaceAgent", AID.ISLOCALNAME), 
                                "obj;" + json + ";" + "msg;" + "lo list", 
                                ACLMessage.INFORM);
                        
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(ControllerBehaviour.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ControllerBehaviour.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JDOMException ex) {
                        Logger.getLogger(ControllerBehaviour.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }else if(utils.getMessage(messageContent).
                        equals("selected lo")){
                    
                    LOM lom = gson.fromJson(
                            utils.getJsonObject(receivedMessage.getContent()),
                            LOM.class);
                     
                    this.sendLOToEvaluation(lom, gson, utils);
                    
                }else if(utils.getMessage(messageContent).
                        equals("lo evaluation")){
                    
                    LOM lom = gson.fromJson(
                            utils.getJsonObject(receivedMessage.getContent()),
                            LOM.class);
                    
                    String json = gson.toJson(lom);
                    
                    utils.sendMessage(myAgent, 
                            new AID("InterfaceAgent", AID.ISLOCALNAME), 
                            "obj;" + json + ";" + "msg;" + "lo evaluation", 
                            ACLMessage.INFORM);
                }
            }
        }
    }
    
    private void sendLOToEvaluation(LOM lom, Gson gson, Utils utils){

        String json = gson.toJson(lom);

        utils.sendMessage(myAgent, 
                new AID("LOEvaluatorAgent", AID.ISLOCALNAME), 
                "obj;" + json + ";" + "msg;" + "evaluate lo", 
                ACLMessage.INFORM);
    }
    
    private LOList getLO(String searchString) throws MalformedURLException, UnsupportedEncodingException, IOException, JDOMException{
        
        System.out.println("Cadena de busqueda OA: " + searchString);
        URL url = new URL("http://froac.manizales.unal.edu.co/froacn/?raim=" + searchString);
        
        URLConnection connection = url.openConnection();
        
        // Leyendo el resultado
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        
        String row;
        LOList loList = new LOList();
        
        while ((row = in.readLine()) != null) {
//            System.out.println("Respuesta servicio busqueda OA:");
            System.out.println(row);
            
            //Json array
            JSONArray jsonArray = new JSONArray(row.substring(1, row.length() - 1));
            
            for(int i = 0; i < jsonArray.length(); i ++){
//                System.out.println(jsonArray.getJSONObject(i).getString("xml"));
                LOM lom = new LOM();
                
                lom.setLoId(Integer.parseInt(jsonArray.getJSONObject(i).getString("lo_id")));
                lom.setId(i);
                
                SAXBuilder builder = new SAXBuilder();
                
                Document xmlDocument = 
                        builder.build(new ByteArrayInputStream(
                                jsonArray.getJSONObject(i).getString("xml").getBytes()));
                
                Element root = xmlDocument.getRootElement();
                
                List general = root.getChildren(
                        "general", root.getNamespace());
                
                Element generalChild = (org.jdom2.Element) general.get(0);
                
                Element title = generalChild.getChild(
                        "title", generalChild.getNamespace());
                
                lom.setTitle(title == null ? "" : title.getValue().trim());
                
                List<Element> keyWordList = generalChild.getChildren(
                        "keywords", generalChild.getNamespace());
                
                for(Element keyWord : keyWordList){
                    lom.addKeyWord(keyWord == null ? "" : keyWord.getValue().trim());
                }
                
                Element description = generalChild.getChild(
                        "description", generalChild.getNamespace());
                
                lom.setDescription(description == null ? "" : description.getValue().trim());
                
                Element generalLanguage = generalChild.getChild(
                        "language", generalChild.getNamespace());
                
                lom.setGeneralLanguage(generalLanguage == null ? "" : generalLanguage.getValue().trim());
                
                /**************************************************************/
                
                List educational = root.getChildren(
                        "educational", root.getNamespace());
                
                Element educationalChild = (org.jdom2.Element) educational.get(0);
                
                List<Element> resourceTypeList = educationalChild.getChildren(
                        "learningresourcetype", educationalChild.getNamespace());
                
                for(Element resourceType : resourceTypeList){
                    lom.addLearningResourceType(resourceType == null ? "" : resourceType.getValue().trim());
                }
                
                Element interactivityLevel = educationalChild.getChild(
                        "interactivitylevel", educationalChild.getNamespace());
                
                lom.setInteractivityLevel(interactivityLevel == null ? "" : interactivityLevel.getValue().trim());
                
                Element interactivityType = educationalChild.getChild(
                        "interactivitytype", educationalChild.getNamespace());
                
                lom.setInteractivityType(interactivityType == null ? "" : interactivityType.getValue().trim());
                
                Element semanticDensity = educationalChild.getChild(
                        "semanticdensity", educationalChild.getNamespace());
                
                lom.setSemanticDensity(semanticDensity == null ? "" : semanticDensity.getValue().trim());
                
                Element educationlLanguage = educationalChild.getChild(
                        "language", educationalChild.getNamespace());
                
                lom.setEducationlLanguage(educationlLanguage == null ? "" : educationlLanguage.getValue().trim());
                
                /***************************************************************/
                
                List technical = root.getChildren(
                        "technical", root.getNamespace());
                
                Element technicalChild = (org.jdom2.Element) technical.get(0);
                
                Element format = technicalChild.getChild(
                        "format", technicalChild.getNamespace());
                
                lom.setFormat(format == null ? "" : format.getValue().trim());
                
                /***************************************************************/
                
                List accessibility = root.getChildren(
                        "accessibility", root.getNamespace());
                
                Element accessibilityChild = (org.jdom2.Element) accessibility.get(0);
                
                Element presentationModeChild = accessibilityChild.getChild(
                        "presentationmode", accessibilityChild.getNamespace());
                
                Element auditory = presentationModeChild.getChild(
                        "auditory", presentationModeChild.getNamespace());
                
                lom.setAuditory(auditory == null ? "" : auditory.getValue().trim());
                
                Element textual = presentationModeChild.getChild(
                        "textual", presentationModeChild.getNamespace());
                
                lom.setTextual(textual == null ? "" : textual.getValue().trim());
                
                Element visual = presentationModeChild.getChild(
                        "visual", presentationModeChild.getNamespace());
                
                lom.setVisual(visual == null ? "" : visual.getValue().trim());
                
                /*****************/
                
                Element interactionModeChild = accessibilityChild.getChild(
                        "interactionmode", accessibilityChild.getNamespace());
                
                Element keyboard = interactionModeChild.getChild(
                        "keyboard", interactionModeChild.getNamespace());
                
                lom.setKeyboard(keyboard == null ? "" : keyboard.getValue().trim());
                
                Element mouse = interactionModeChild.getChild(
                        "mouse", interactionModeChild.getNamespace());
                
                lom.setMouse(mouse == null ? "" : mouse.getValue().trim());
                
                Element voiceRecognition = interactionModeChild.getChild(
                        "voicerecognition", interactionModeChild.getNamespace());
                
                lom.setVoiceRecognition(voiceRecognition == null ? "" : voiceRecognition.getValue().trim());
                
                /*****************/
                
                Element adaptationTypeChild = accessibilityChild.getChild(
                        "adaptationtype", accessibilityChild.getNamespace());
                
                Element hearingAlternative = adaptationTypeChild.getChild(
                        "hearingalternative", adaptationTypeChild.getNamespace());
                
                lom.setHearingAlternative(hearingAlternative == null ? "" : hearingAlternative.getValue().trim());
                
                Element textualAlternative = adaptationTypeChild.getChild(
                        "textualalternative", adaptationTypeChild.getNamespace());
                
                lom.setTextualAlternative(textualAlternative == null ? "" : textualAlternative.getValue().trim());
                
                Element signLanguage = adaptationTypeChild.getChild(
                        "signlanguage", adaptationTypeChild.getNamespace());
                
                lom.setSignLanguage(signLanguage == null ? "" : signLanguage.getValue().trim());
                
                Element audioDescription = adaptationTypeChild.getChild(
                        "audiodescription", adaptationTypeChild.getNamespace());
                
                lom.setAudioDescription(audioDescription == null ? "" : audioDescription.getValue().trim());
                
                Element subtitles = adaptationTypeChild.getChild(
                        "subtitles", adaptationTypeChild.getNamespace());
                
                lom.setSubtitles(subtitles == null ? "" : subtitles.getValue().trim());
                
                loList.addLO(lom);
            }
        }
        
        System.out.println(loList.toString());
        
        return loList;
        
    }
    
    
}
