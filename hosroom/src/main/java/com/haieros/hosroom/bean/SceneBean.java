package com.haieros.hosroom.bean;

/**
 * Created by Kang on 2017/8/17.
 */

public class SceneBean extends Bean{

    /**
     * sceneID : 1
     * sceneDev1 : 1
     * sceneDev2 : 2
     * sceneDev3 : 3
     */

    private DeviceBean device;
    /**
     * device : {"sceneID":1,"sceneDev1":"1","sceneDev2":"2","sceneDev3":"3"}
     * sceneID : 1
     * sceneImgUrl : www.baidu.com
     * sceneName : scene1
     */

    private String sceneID;
    private String sceneImgUrl;
    private String sceneName;

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public String getSceneID() {
        return sceneID;
    }

    public void setSceneID(String sceneID) {
        this.sceneID = sceneID;
    }

    public String getSceneImgUrl() {
        return sceneImgUrl;
    }

    public void setSceneImgUrl(String sceneImgUrl) {
        this.sceneImgUrl = sceneImgUrl;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public static class DeviceBean extends Bean {
        private String sceneID;
        private String sceneDev1;
        private String sceneDev2;
        private String sceneDev3;

        public String getSceneID() {
            return sceneID;
        }

        public void setSceneID(String sceneID) {
            this.sceneID = sceneID;
        }

        public String getSceneDev1() {
            return sceneDev1;
        }

        public void setSceneDev1(String sceneDev1) {
            this.sceneDev1 = sceneDev1;
        }

        public String getSceneDev2() {
            return sceneDev2;
        }

        public void setSceneDev2(String sceneDev2) {
            this.sceneDev2 = sceneDev2;
        }

        public String getSceneDev3() {
            return sceneDev3;
        }

        public void setSceneDev3(String sceneDev3) {
            this.sceneDev3 = sceneDev3;
        }

        @Override
        public String toString() {
            return "DeviceBean{" +
                    "sceneID='" + sceneID + '\'' +
                    ", sceneDev1='" + sceneDev1 + '\'' +
                    ", sceneDev2='" + sceneDev2 + '\'' +
                    ", sceneDev3='" + sceneDev3 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SceneBean{" +
                "device=" + device +
                ", sceneID='" + sceneID + '\'' +
                ", sceneImgUrl='" + sceneImgUrl + '\'' +
                ", sceneName='" + sceneName + '\'' +
                '}';
    }
}
