/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.model;

import java.util.ArrayList;

/**
 *
 * @author magir
 */
public class LOM {
    
    private Integer loId;
    
    private Integer id;
    
    private String title;
    
    private ArrayList<String> learningResourceTypeList;
    
    private ArrayList<String> keyWordList;
    
    private String interactivityLevel;
    
    private String interactivityType;
    
    private String auditory;
    
    private String hearingAlternative;
    
    private String format;
    
    private String textualAlternative;
    
    private String signLanguage;
    
    private String textual;
    
    private Integer accessibilityScore;
    
    private String description;
    
    private String generalLanguage;
    
    private String semanticDensity;
    
    private String educationlLanguage;
    
    private String visual;
    
    private String keyboard;
    
    private String mouse;
    
    private String voiceRecognition;
    
    private String audioDescription;
    
    private String subtitles;
    
    private ArrayList<String> recommendationList;

    public LOM() {
        this.learningResourceTypeList = new ArrayList<>();
        this.keyWordList = new ArrayList<>();
        this.recommendationList = new ArrayList<>();
        this.accessibilityScore = 0;
    }

    public Integer getLoId() {
        return loId;
    }

    public void setLoId(Integer loId) {
        this.loId = loId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getLearningResourceTypeList() {
        return learningResourceTypeList;
    }

    public void addLearningResourceType(String learningResourceType) {
        this.learningResourceTypeList.add(learningResourceType);
    }

    public String getInteractivityLevel() {
        return interactivityLevel;
    }

    public void setInteractivityLevel(String interactivityLevel) {
        this.interactivityLevel = interactivityLevel;
    }

    public String getInteractivityType() {
        return interactivityType;
    }

    public void setInteractivityType(String interactivityType) {
        this.interactivityType = interactivityType;
    }

    public String getAuditory() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }

    public String getHearingAlternative() {
        return hearingAlternative;
    }

    public void setHearingAlternative(String hearingAlternative) {
        this.hearingAlternative = hearingAlternative;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTextualAlternative() {
        return textualAlternative;
    }

    public void setTextualAlternative(String textualAlternative) {
        this.textualAlternative = textualAlternative;
    }

    public String getSignLanguage() {
        return signLanguage;
    }

    public void setSignLanguage(String signLanguage) {
        this.signLanguage = signLanguage;
    }

    public String getTextual() {
        return textual;
    }

    public void setTextual(String textual) {
        this.textual = textual;
    }

    public Integer getAccessibilityScore() {
        return accessibilityScore;
    }

    public void setAccessibilityScore(Integer accessibilityScore) {
        this.accessibilityScore = accessibilityScore;
    }
    
    public void addOneAccessibilityScore() {
        this.accessibilityScore ++;
    }

    public ArrayList<String> getKeyWordList() {
        return keyWordList;
    }

    public void setKeyWordList(ArrayList<String> keyWordList) {
        this.keyWordList = keyWordList;
    }
    
    public void addKeyWord(String keyWord) {
        this.keyWordList.add(keyWord);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeneralLanguage() {
        return generalLanguage;
    }

    public void setGeneralLanguage(String generalLanguage) {
        this.generalLanguage = generalLanguage;
    }

    public String getSemanticDensity() {
        return semanticDensity;
    }

    public void setSemanticDensity(String semanticDensity) {
        this.semanticDensity = semanticDensity;
    }

    public String getEducationlLanguage() {
        return educationlLanguage;
    }

    public void setEducationlLanguage(String educationlLanguage) {
        this.educationlLanguage = educationlLanguage;
    }

    public String getVisual() {
        return visual;
    }

    public void setVisual(String visual) {
        this.visual = visual;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getVoiceRecognition() {
        return voiceRecognition;
    }

    public void setVoiceRecognition(String voiceRecognition) {
        this.voiceRecognition = voiceRecognition;
    }

    public String getAudioDescription() {
        return audioDescription;
    }

    public void setAudioDescription(String audioDescription) {
        this.audioDescription = audioDescription;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }

    public ArrayList<String> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(ArrayList<String> recommendationList) {
        this.recommendationList = recommendationList;
    }
    
    public void addRecommendation(String recommendation) {
        this.recommendationList.add(recommendation);
    }

    @Override
    public String toString() {
        return "LOM{" + "learningResourceTypeList=" + learningResourceTypeList + ", interactivityLevel=" + interactivityLevel + ", interactivityType=" + interactivityType + ", auditory=" + auditory + ", hearingAlternative=" + hearingAlternative + ", format=" + format + ", textualAlternative=" + textualAlternative + ", signLanguage=" + signLanguage + ", textual=" + textual + '}';
    }
    
}
