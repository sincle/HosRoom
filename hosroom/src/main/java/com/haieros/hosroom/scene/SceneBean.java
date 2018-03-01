package com.haieros.hosroom.scene;

/**
 * Created by Kang on 2017/10/27.
 */

public class SceneBean {

    private String sceneName;
    private int sceneImg;
    private String sceneId;

    public SceneBean(String sceneName, int sceneImg, String sceneId) {
        this.sceneName = sceneName;
        this.sceneImg = sceneImg;
        this.sceneId = sceneId;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public int getSceneImg() {
        return sceneImg;
    }

    public void setSceneImg(int sceneImg) {
        this.sceneImg = sceneImg;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }
}
