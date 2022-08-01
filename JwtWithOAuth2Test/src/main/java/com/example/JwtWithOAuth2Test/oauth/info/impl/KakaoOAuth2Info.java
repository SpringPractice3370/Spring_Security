package com.example.JwtWithOAuth2Test.oauth.info.impl;

import com.example.JwtWithOAuth2Test.oauth.info.OAuth2UserInfo;

import java.util.Map;

public class KakaoOAuth2Info extends OAuth2UserInfo {

    public KakaoOAuth2Info(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("properties");
        if (response == null) {
            return null;
        }
        return (String) response.get("nickname");
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        if (properties == null) {
            return null;
        }
        return (String) properties.get("thumbnail_image");
    }
}
